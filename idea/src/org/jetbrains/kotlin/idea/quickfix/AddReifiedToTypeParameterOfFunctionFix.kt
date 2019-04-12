/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.quickfix

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.diagnostics.Errors
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtTypeParameter
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType

class AddReifiedToTypeParameterOfFunctionFix(
        typeParameter: KtTypeParameter,
        val function: KtNamedFunction
) : AddModifierFix(typeParameter, KtTokens.REIFIED_KEYWORD) {

    private val inlineFix = AddInlineToFunctionWithReifiedFix(function)

    override fun getText() = element?.let { "Make ${getElementName(it)} reified and ${getElementName(function)} inline" } ?: ""

    override fun invokeImpl(project: Project, editor: Editor?, file: PsiFile) {
        super.invokeImpl(project, editor, file)
        inlineFix.invoke(project, editor, file)
    }

    companion object Factory : KotlinSingleIntentionActionFactory() {
        override fun createAction(diagnostic: Diagnostic): IntentionAction? {
            val element = Errors.TYPE_PARAMETER_AS_REIFIED.cast(diagnostic)
            val function = element.psiElement.getStrictParentOfType<KtNamedFunction>()
            val parameter = function?.typeParameterList?.parameters?.get(element.a.index) ?: return null
            return AddReifiedToTypeParameterOfFunctionFix(parameter, function)
        }
    }
}