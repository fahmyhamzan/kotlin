/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.core.overrideImplement

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.idea.KotlinBundle
import org.jetbrains.kotlin.idea.caches.resolve.resolveToDescriptorIfAny
import org.jetbrains.kotlin.idea.util.expectedDescriptors
import org.jetbrains.kotlin.js.descriptorUtils.hasPrimaryConstructor
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtEnumEntry
import org.jetbrains.kotlin.resolve.OverrideResolver

open class ImplementMembersHandler : OverrideImplementMembersHandler(), IntentionAction {
    override fun collectMembersToGenerate(descriptor: ClassDescriptor, project: Project): Collection<OverrideMemberChooserObject> {
        return OverrideResolver.getMissingImplementations(descriptor)
            .map { OverrideMemberChooserObject.create(project, it, it, OverrideMemberChooserObject.BodyType.FROM_TEMPLATE) }
    }

    override fun getChooserTitle() = "Implement Members"

    override fun getNoMembersFoundHint() = "No members to implement have been found"

    override fun getText() = KotlinBundle.message("implement.members")
    override fun getFamilyName() = KotlinBundle.message("implement.members")

    override fun isAvailable(project: Project, editor: Editor, file: PsiFile) = isValidFor(editor, file)
}

class ImplementAsConstructorParameter : ImplementMembersHandler() {
    override fun getText() = "Implement as constructor parameters"

    override fun isValidForClass(classOrObject: KtClassOrObject): Boolean {
        if (classOrObject !is KtClass || classOrObject is KtEnumEntry || classOrObject.isInterface()) return false
        val classDescriptor = classOrObject.resolveToDescriptorIfAny() ?: return false
        if (classDescriptor.isActual) {
            if (classDescriptor.expectedDescriptors().any {
                    it is ClassDescriptor && it.hasPrimaryConstructor()
                }
            ) {
                return false
            }
        }
        return OverrideResolver.getMissingImplementations(classDescriptor).any { it is PropertyDescriptor }
    }

    override fun collectMembersToGenerate(descriptor: ClassDescriptor, project: Project): Collection<OverrideMemberChooserObject> {
        return OverrideResolver.getMissingImplementations(descriptor)
            .filter { it is PropertyDescriptor }
            .map { OverrideMemberChooserObject.create(project, it, it, OverrideMemberChooserObject.BodyType.FROM_TEMPLATE, true) }
    }
}