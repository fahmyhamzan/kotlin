/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.quickfix

import com.intellij.codeInsight.intention.IntentionAction
import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.diagnostics.Errors
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType

class AddInlineToFunctionWithReifiedFix(function: KtNamedFunction) : AddModifierFix(function, KtTokens.INLINE_KEYWORD) {
    companion object Factory : KotlinSingleIntentionActionFactory() {
        override fun createAction(diagnostic: Diagnostic): IntentionAction? {
            val element = Errors.REIFIED_TYPE_PARAMETER_NO_INLINE.cast(diagnostic)
            val function = element.psiElement.getStrictParentOfType<KtNamedFunction>() ?: return null
            return AddInlineToFunctionWithReifiedFix(function)
        }
    }
}