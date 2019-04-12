/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.js.resolve.diagnostics

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.DescriptorUtils
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
import org.jetbrains.kotlin.resolve.descriptorUtil.getSuperClassNotAny

object JsModuleChecker : DeclarationChecker {
    override fun check(declaration: KtDeclaration, descriptor: DeclarationDescriptor, context: DeclarationCheckerContext) {
        val trace = context.trace
        checkSuperClass(declaration, descriptor, trace)
        if (AnnotationsUtils.getModuleName(descriptor) == null && !AnnotationsUtils.isNonModule(descriptor)) return

        if (descriptor is PropertyDescriptor && descriptor.isVar) {
            trace.report(ErrorsJs.JS_MODULE_PROHIBITED_ON_VAR.on(declaration))
        }

        if (!AnnotationsUtils.isNativeObject(descriptor)) {
            trace.report(ErrorsJs.JS_MODULE_PROHIBITED_ON_NON_NATIVE.on(declaration))
        }

        if (DescriptorUtils.isTopLevelDeclaration(descriptor)) {
            val isFileModuleOrNonModule = AnnotationsUtils.getFileModuleName(trace.bindingContext, descriptor) != null ||
                                          AnnotationsUtils.isFromNonModuleFile(trace.bindingContext, descriptor)
            if (isFileModuleOrNonModule) {
                trace.report(ErrorsJs.NESTED_JS_MODULE_PROHIBITED.on(declaration))
            }
        }
    }

    private fun checkSuperClass(declaration: KtDeclaration, descriptor: DeclarationDescriptor, trace: BindingTrace) {
        if (descriptor !is ClassDescriptor) return
        val superClass = descriptor.getSuperClassNotAny() ?: return

        val psi = (declaration as KtClassOrObject).superTypeListEntries.firstOrNull { entry ->
            trace[BindingContext.TYPE, entry.typeReference]?.constructor?.declarationDescriptor == superClass
        }

        checkJsModuleUsage(trace.bindingContext, trace, descriptor, superClass, psi ?: declaration)
    }
}
