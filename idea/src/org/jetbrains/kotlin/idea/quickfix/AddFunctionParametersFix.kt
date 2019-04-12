/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.quickfix

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.search.searches.ReferencesSearch
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.idea.core.CollectingNameValidator
import org.jetbrains.kotlin.idea.quickfix.createFromUsage.callableBuilder.getDataFlowAwareTypes
import org.jetbrains.kotlin.idea.refactoring.changeSignature.*
import org.jetbrains.kotlin.psi.KtCallElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.ValueArgument
import org.jetbrains.kotlin.psi.psiUtil.getParentOfType
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.calls.callUtil.getCall
import org.jetbrains.kotlin.resolve.descriptorUtil.builtIns
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker
import java.util.*

class AddFunctionParametersFix(
    callElement: KtCallElement,
    functionDescriptor: FunctionDescriptor,
    private val hasTypeMismatches: Boolean
) : ChangeFunctionSignatureFix(callElement, functionDescriptor) {
    private val callElement: KtCallElement?
        get() = element as? KtCallElement

    private val typesToShorten = ArrayList<KotlinType>()

    override fun getText(): String {
        val callElement = callElement ?: return ""

        val parameters = functionDescriptor.valueParameters
        val arguments = callElement.valueArguments
        val newParametersCnt = arguments.size - parameters.size
        assert(newParametersCnt > 0)

        val subjectSuffix = if (newParametersCnt > 1) "s" else ""

        val callableDescription = if (isConstructor()) {
            val className = functionDescriptor.containingDeclaration.name.asString()
            "constructor '$className'"
        } else {
            val functionName = functionDescriptor.name.asString()
            "function '$functionName'"
        }

        return if (hasTypeMismatches)
            "Change the signature of $callableDescription"
        else
            "Add parameter$subjectSuffix to $callableDescription"
    }

    override fun isAvailable(project: Project, editor: Editor?, file: KtFile): Boolean {
        if (!super.isAvailable(project, editor, file)) return false
        val callElement = callElement ?: return false

        // newParametersCnt <= 0: psi for this quickfix is no longer valid
        val newParametersCnt = callElement.valueArguments.size - functionDescriptor.valueParameters.size
        return newParametersCnt > 0
    }

    override fun invoke(project: Project, editor: Editor?, file: KtFile) {
        val callElement = callElement ?: return
        runChangeSignature(project, functionDescriptor, addParameterConfiguration(), callElement, text)
    }

    private fun addParameterConfiguration(): KotlinChangeSignatureConfiguration {
        return object : KotlinChangeSignatureConfiguration {
            override fun configure(originalDescriptor: KotlinMethodDescriptor): KotlinMethodDescriptor {
                return originalDescriptor.modify(fun(descriptor: KotlinMutableMethodDescriptor) {
                    val callElement = callElement ?: return
                    val call = callElement.getCall(callElement.analyze()) ?: return
                    val parameters = functionDescriptor.valueParameters
                    val arguments = callElement.valueArguments
                    val validator = CollectingNameValidator()

                    for (i in arguments.indices) {
                        val argument = arguments[i]
                        val expression = argument.getArgumentExpression()

                        if (i < parameters.size) {
                            validator.addName(parameters[i].name.asString())
                            val argumentType = expression?.let {
                                val bindingContext = it.analyze()
                                val smartCasts = bindingContext[BindingContext.SMARTCAST, it]
                                smartCasts?.defaultType ?: smartCasts?.type(call) ?: bindingContext.getType(it)
                            }
                            val parameterType = parameters[i].type

                            if (argumentType != null && !KotlinTypeChecker.DEFAULT.isSubtypeOf(argumentType, parameterType)) {
                                descriptor.parameters[i].currentTypeInfo = KotlinTypeInfo(false, argumentType)
                                typesToShorten.add(argumentType)
                            }
                        } else {
                            val parameterInfo = getNewParameterInfo(
                                originalDescriptor.baseDescriptor as FunctionDescriptor,
                                argument,
                                validator
                            )
                            parameterInfo.originalTypeInfo.type?.let { typesToShorten.add(it) }

                            if (expression != null) {
                                parameterInfo.defaultValueForCall = expression
                            }

                            descriptor.addParameter(parameterInfo)
                        }
                    }
                })
            }

            override fun performSilently(affectedFunctions: Collection<PsiElement>): Boolean {
                val onlyFunction = affectedFunctions.singleOrNull() ?: return false
                return !hasTypeMismatches && !isConstructor() && !hasOtherUsages(onlyFunction)
            }
        }
    }

    private fun getNewParameterInfo(
        functionDescriptor: FunctionDescriptor,
        argument: ValueArgument,
        validator: (String) -> Boolean
    ): KotlinParameterInfo {
        val name = getNewArgumentName(argument, validator)
        val expression = argument.getArgumentExpression()
        val type = expression?.let { getDataFlowAwareTypes(it).firstOrNull() } ?: functionDescriptor.builtIns.nullableAnyType
        return KotlinParameterInfo(functionDescriptor, -1, name, KotlinTypeInfo(false, null))
            .apply { currentTypeInfo = KotlinTypeInfo(false, type) }
    }

    private fun hasOtherUsages(function: PsiElement): Boolean {
        return ReferencesSearch.search(function).any {
            val call = it.element.getParentOfType<KtCallElement>(false)
            call != null && callElement != call
        }
    }

    private fun isConstructor() = functionDescriptor is ConstructorDescriptor
}
