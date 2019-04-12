/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.resolve.checkers

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.diagnostics.Errors.*
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext
import org.jetbrains.kotlin.resolve.calls.checkers.isComputingDeferredType
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.utils.newLinkedHashSetWithExpectedSize

object MissingDependencyClassChecker : CallChecker {
    override fun check(resolvedCall: ResolvedCall<*>, reportOn: PsiElement, context: CallCheckerContext) {
        val resultingDescriptor = resolvedCall.resultingDescriptor
        for (diagnostic in collectDiagnostics(reportOn, resultingDescriptor)) {
            context.trace.report(diagnostic)
        }

        val containerSource = (resultingDescriptor as? DeserializedMemberDescriptor)?.containerSource
        incompatibilityDiagnosticFor(containerSource, reportOn)?.let(context.trace::report)
    }

    private fun diagnosticFor(descriptor: ClassifierDescriptor, reportOn: PsiElement): Diagnostic? {
        if (descriptor is NotFoundClasses.MockClassDescriptor) {
            return MISSING_DEPENDENCY_CLASS.on(reportOn, descriptor.fqNameSafe)
        }

        return incompatibilityDiagnosticFor(descriptor.source, reportOn)
    }

    private fun incompatibilityDiagnosticFor(source: SourceElement?, reportOn: PsiElement): Diagnostic? {
        if (source is DeserializedContainerSource) {
            val incompatibility = source.incompatibility
            if (incompatibility != null) {
                return INCOMPATIBLE_CLASS.on(reportOn, source.presentableString, incompatibility)
            }
            if (source.isPreReleaseInvisible) {
                return PRE_RELEASE_CLASS.on(reportOn, source.presentableString)
            }
        }

        return null
    }

    private fun collectDiagnostics(reportOn: PsiElement, descriptor: CallableDescriptor): Set<Diagnostic> {
        val result: MutableSet<Diagnostic> = newLinkedHashSetWithExpectedSize(1)

        fun consider(classDescriptor: ClassDescriptor) {
            val diagnostic = diagnosticFor(classDescriptor, reportOn)
            if (diagnostic != null) {
                result.add(diagnostic)
                return
            }
            (classDescriptor.containingDeclaration as? ClassDescriptor)?.let(::consider)
        }

        fun consider(type: KotlinType) {
            if (!isComputingDeferredType(type)) {
                (type.constructor.declarationDescriptor as? ClassDescriptor)?.let(::consider)
            }
        }

        descriptor.returnType?.let(::consider)
        descriptor.extensionReceiverParameter?.value?.type?.let(::consider)
        descriptor.valueParameters.forEach { consider(it.type) }

        return result
    }

    object ClassifierUsage : ClassifierUsageChecker {
        override fun check(targetDescriptor: ClassifierDescriptor, element: PsiElement, context: ClassifierUsageCheckerContext) {
            diagnosticFor(targetDescriptor, element)?.let(context.trace::report)

            val containerSource = (targetDescriptor as? DeserializedMemberDescriptor)?.containerSource
            incompatibilityDiagnosticFor(containerSource, element)?.let(context.trace::report)
        }
    }
}
