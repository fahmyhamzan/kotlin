/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.codeInsight.surroundWith.statement;

import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFinallySection;
import org.jetbrains.kotlin.psi.KtTryExpression;

public class KotlinTryFinallySurrounder extends KotlinTrySurrounderBase {

    @Override
    protected String getCodeTemplate() {
        return "try { \n} finally {\nb\n}";
    }

    @Nullable
    @Override
    protected TextRange surroundStatements(@NotNull Project project, @NotNull Editor editor, @NotNull PsiElement container, @NotNull PsiElement[] statements) {
        TextRange textRange = super.surroundStatements(project, editor, container, statements);
        if (textRange == null) {
            return null;
        }
        // Delete dummy "b" element for caret
        editor.getDocument().deleteString(textRange.getStartOffset(), textRange.getEndOffset());
        return new TextRange(textRange.getStartOffset(), textRange.getStartOffset());
    }

    @NotNull
    @Override
    protected TextRange getTextRangeForCaret(@NotNull KtTryExpression expression) {
        KtFinallySection block = expression.getFinallyBlock();
        assert block != null : "Finally block should exists for " + expression.getText();
        KtExpression blockExpression = block.getFinalExpression().getStatements().get(0);
        return blockExpression.getTextRange();
    }

    @Override
    public String getTemplateDescription() {
        return CodeInsightBundle.message("surround.with.try.finally.template");
    }
}
