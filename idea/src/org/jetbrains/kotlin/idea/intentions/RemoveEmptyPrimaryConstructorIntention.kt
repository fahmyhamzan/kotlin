/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.intentions

import com.intellij.codeInspection.CleanupLocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.openapi.editor.Editor
import org.jetbrains.kotlin.idea.inspections.IntentionBasedInspection
import org.jetbrains.kotlin.idea.util.isExpectDeclaration
import org.jetbrains.kotlin.psi.KtPrimaryConstructor
import org.jetbrains.kotlin.psi.psiUtil.containingClass

class RemoveEmptyPrimaryConstructorInspection : IntentionBasedInspection<KtPrimaryConstructor>(
    RemoveEmptyPrimaryConstructorIntention::class
), CleanupLocalInspectionTool {
    override fun problemHighlightType(element: KtPrimaryConstructor): ProblemHighlightType = ProblemHighlightType.LIKE_UNUSED_SYMBOL
}

class RemoveEmptyPrimaryConstructorIntention :
    SelfTargetingOffsetIndependentIntention<KtPrimaryConstructor>(KtPrimaryConstructor::class.java, "Remove empty primary constructor") {

    override fun applyTo(element: KtPrimaryConstructor, editor: Editor?) = element.delete()

    override fun isApplicableTo(element: KtPrimaryConstructor) = when {
        element.valueParameters.isNotEmpty() -> false
        element.annotations.isNotEmpty() -> false
        element.modifierList?.text?.isBlank() == false -> false
        element.containingClass()?.secondaryConstructors?.isNotEmpty() == true -> false
        element.isExpectDeclaration() -> false
        else -> true
    }
}
