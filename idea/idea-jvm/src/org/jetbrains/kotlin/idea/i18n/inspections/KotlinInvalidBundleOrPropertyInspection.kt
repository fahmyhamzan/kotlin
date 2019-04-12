/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.i18n.inspections

import com.intellij.codeInsight.CodeInsightBundle
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.i18n.JavaI18nUtil
import com.intellij.lang.properties.ResourceBundleReference
import com.intellij.lang.properties.psi.Property
import com.intellij.lang.properties.references.PropertyReference
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.idea.caches.resolve.resolveToCall
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType
import org.jetbrains.kotlin.psi.psiUtil.parents
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument
import org.jetbrains.kotlin.utils.addToStdlib.firstIsInstanceOrNull

class KotlinInvalidBundleOrPropertyInspection : AbstractKotlinInspection() {
    override fun getDisplayName() = CodeInsightBundle.message("inspection.unresolved.property.key.reference.name")

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : KtVisitorVoid() {
            private fun processResourceBundleReference(ref: ResourceBundleReference, template: KtStringTemplateExpression) {
                if (ref.resolve() == null) {
                    holder.registerProblem(
                            template,
                            CodeInsightBundle.message("inspection.invalid.resource.bundle.reference", ref.canonicalText),
                            ProblemHighlightType.LIKE_UNKNOWN_SYMBOL,
                            TextRange(0, template.textLength)
                    )
                }
            }

            private fun processPropertyReference(ref: PropertyReference, template: KtStringTemplateExpression) {
                val property = ref.resolve() as? Property
                if (property == null) {
                    if (!ref.isSoft) {
                        holder.registerProblem(
                            template,
                            CodeInsightBundle.message("inspection.unresolved.property.key.reference.message", ref.canonicalText),
                            ProblemHighlightType.LIKE_UNKNOWN_SYMBOL,
                            TextRange(0, template.textLength),
                            *ref.quickFixes
                        )
                    }
                    return
                }

                val argument = template.parents.firstIsInstanceOrNull<KtValueArgument>() ?: return
                if (argument.getArgumentExpression() != KtPsiUtil.deparenthesize(template) ) return

                val callExpression = argument.getStrictParentOfType<KtCallExpression>() ?: return
                val resolvedCall = callExpression.resolveToCall() ?: return

                val resolvedArguments = resolvedCall.valueArgumentsByIndex ?: return
                val keyArgumentIndex = resolvedArguments.indexOfFirst { it is ExpressionValueArgument && it.valueArgument == argument }
                if (keyArgumentIndex < 0) return

                val callable = resolvedCall.resultingDescriptor
                if (callable.valueParameters.size != keyArgumentIndex + 2) return

                val messageArgument = resolvedArguments[keyArgumentIndex + 1] as? VarargValueArgument ?: return
                if (messageArgument.arguments.singleOrNull()?.getSpreadElement() != null) return

                val expectedArgumentCount = JavaI18nUtil.getPropertyValuePlaceholdersCount(property.value ?: "")
                val actualArgumentCount = messageArgument.arguments.size
                if (actualArgumentCount < expectedArgumentCount) {
                    val description = CodeInsightBundle.message(
                            "property.has.more.parameters.than.passed",
                            ref.canonicalText, expectedArgumentCount, actualArgumentCount
                    )
                    holder.registerProblem(template, description, ProblemHighlightType.GENERIC_ERROR)
                }
            }

            override fun visitStringTemplateExpression(expression: KtStringTemplateExpression) {
                for (ref in expression.references) {
                    when (ref) {
                        is ResourceBundleReference -> processResourceBundleReference(ref, expression)
                        is PropertyReference -> processPropertyReference(ref, expression)
                    }
                }
            }
        }
    }
}