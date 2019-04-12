/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.intentions

import com.intellij.codeInsight.intention.HighPriorityAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility
import org.jetbrains.kotlin.descriptors.Visibilities
import org.jetbrains.kotlin.idea.core.*
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.startOffset
import org.jetbrains.kotlin.psi.psiUtil.toVisibility
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifierType

open class ChangeVisibilityModifierIntention protected constructor(
    val modifier: KtModifierKeywordToken
) : SelfTargetingRangeIntention<KtDeclaration>(KtDeclaration::class.java, "Make ${modifier.value}") {

    override fun applicabilityRange(element: KtDeclaration): TextRange? {
        val modifierList = element.modifierList
        if (modifierList?.hasModifier(modifier) == true) return null

        if (KtPsiUtil.isLocal((element as? KtPropertyAccessor)?.property ?: element)) return null

        val descriptor = element.toDescriptor() as? DeclarationDescriptorWithVisibility ?: return null
        val targetVisibility = modifier.toVisibility()
        if (descriptor.visibility == targetVisibility) return null

        if (modifierList?.hasModifier(KtTokens.OVERRIDE_KEYWORD) == true) {
            val callableDescriptor = descriptor  as? CallableDescriptor ?: return null
            // cannot make visibility less than (or non-comparable with) any of the supers
            if (callableDescriptor.overriddenDescriptors
                    .map { Visibilities.compare(it.visibility, targetVisibility) }
                    .any { it == null || it > 0 }) return null
        }

        text = defaultText

        if (element is KtPropertyAccessor) {
            if (element.isGetter) return null
            if (targetVisibility == Visibilities.PUBLIC) {
                val explicitVisibility = element.modifierList?.visibilityModifierType()?.value ?: return null
                text = "Remove '$explicitVisibility' modifier"
            } else {
                val propVisibility = (element.property.toDescriptor() as? DeclarationDescriptorWithVisibility)?.visibility ?: return null
                if (propVisibility == targetVisibility) return null
                val compare = Visibilities.compare(targetVisibility, propVisibility)
                if (compare == null || compare > 0) return null
            }
        }
        val defaultRange = noModifierYetApplicabilityRange(element) ?: return null

        if (element is KtPrimaryConstructor && defaultRange.isEmpty && element.visibilityModifier() == null) {
            text = "Make primary constructor ${modifier.value}" // otherwise it may be confusing
        }

        return if (modifierList != null)
            TextRange(modifierList.startOffset, defaultRange.endOffset) //TODO: smaller range? now it includes annotations too
        else
            defaultRange
    }

    override fun applyTo(element: KtDeclaration, editor: Editor?) {
        element.setVisibility(modifier)
        if (element is KtPropertyAccessor) element.modifierList?.nextSibling?.replace(KtPsiFactory(element).createWhiteSpace())
    }

    private fun noModifierYetApplicabilityRange(declaration: KtDeclaration): TextRange? {
        return when (declaration) {
            is KtNamedFunction -> declaration.funKeyword?.textRange
            is KtProperty -> declaration.valOrVarKeyword.textRange
            is KtPropertyAccessor -> declaration.namePlaceholder.textRange
            is KtClass -> declaration.getClassOrInterfaceKeyword()?.textRange
            is KtObjectDeclaration -> declaration.getObjectKeyword()?.textRange
            is KtPrimaryConstructor -> declaration.valueParameterList?.let {
                //TODO: use constructor keyword if exist
                TextRange.from(it.startOffset, 0)
            }
            is KtSecondaryConstructor -> declaration.getConstructorKeyword().textRange
            is KtParameter -> declaration.valOrVarKeyword?.textRange
            is KtTypeAlias -> declaration.getTypeAliasKeyword()?.textRange
            else -> null
        }
    }

    class Public : ChangeVisibilityModifierIntention(KtTokens.PUBLIC_KEYWORD), HighPriorityAction

    class Private : ChangeVisibilityModifierIntention(KtTokens.PRIVATE_KEYWORD), HighPriorityAction {
        override fun applicabilityRange(element: KtDeclaration): TextRange? {
            return if (element.canBePrivate()) super.applicabilityRange(element) else null
        }
    }

    class Protected : ChangeVisibilityModifierIntention(KtTokens.PROTECTED_KEYWORD) {
        override fun applicabilityRange(element: KtDeclaration): TextRange? {
            return if (element.canBeProtected()) super.applicabilityRange(element) else null
        }
    }

    class Internal : ChangeVisibilityModifierIntention(KtTokens.INTERNAL_KEYWORD) {
        override fun applicabilityRange(element: KtDeclaration): TextRange? {
            return if (element.canBeInternal()) super.applicabilityRange(element) else null
        }
    }
}
