/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.quickfix

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.diagnostics.Errors
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtTypeParameter
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType

class RemoveFinalUpperBoundFix(val typeReference: KtTypeReference) : KotlinQuickFixAction<KtTypeReference>(typeReference) {
    override fun invoke(project: Project, editor: Editor?, file: KtFile) {
        typeReference.getStrictParentOfType<KtTypeParameter>()?.extendsBound = null
    }

    override fun getText() = "Remove final upper bound"

    override fun getFamilyName() = text

    companion object Factory : KotlinSingleIntentionActionFactory() {
        override fun createAction(diagnostic: Diagnostic) = RemoveFinalUpperBoundFix(Errors.FINAL_UPPER_BOUND.cast(diagnostic).psiElement)
    }
}