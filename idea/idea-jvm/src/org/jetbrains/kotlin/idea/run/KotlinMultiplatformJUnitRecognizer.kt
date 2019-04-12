/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.run

import com.intellij.execution.JUnitRecognizer
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.asJava.elements.KtLightMethod
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
import org.jetbrains.kotlin.idea.caches.project.implementingDescriptors
import org.jetbrains.kotlin.idea.caches.resolve.findModuleDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.resolveToDescriptorIfAny
import org.jetbrains.kotlin.idea.project.platform
import org.jetbrains.kotlin.idea.util.module
import org.jetbrains.kotlin.platform.impl.isCommon
import org.jetbrains.kotlin.resolve.descriptorUtil.annotationClass
import org.jetbrains.kotlin.resolve.descriptorUtil.classId
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter
import org.jetbrains.kotlin.resolve.scopes.getDescriptorsFiltered

class KotlinMultiplatformJUnitRecognizer : JUnitRecognizer() {
    override fun isTestAnnotated(method: PsiMethod): Boolean {
        if (method !is KtLightMethod) return false
        val origin = method.kotlinOrigin ?: return false
        if (!origin.module?.platform.isCommon) return false

        val moduleDescriptor = origin.containingKtFile.findModuleDescriptor()
        val implModules = moduleDescriptor.implementingDescriptors
        if (implModules.isEmpty()) return false

        val methodDescriptor = origin.resolveToDescriptorIfAny() ?: return false
        return methodDescriptor.annotations.any { it.isExpectOfAnnotation("org.junit.Test", implModules) }
    }
}

private fun AnnotationDescriptor.isExpectOfAnnotation(fqName: String, implModules: Collection<ModuleDescriptor>): Boolean {
    val annotationClass = annotationClass ?: return false
    if (!annotationClass.isExpect) return false
    val classId = annotationClass.classId ?: return false
    val segments = classId.relativeClassName.pathSegments()

    return implModules
        .any { module ->
            module
                .getPackage(classId.packageFqName).memberScope
                .getDescriptorsFiltered(DescriptorKindFilter.CLASSIFIERS) { it == segments.first() }
                .filterIsInstance<TypeAliasDescriptor>()
                .any { it.classDescriptor?.fqNameSafe?.asString() == fqName }
        }
}
