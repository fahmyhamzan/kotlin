/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.editor.fixers

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtForExpression

class KotlinForConditionFixer : MissingConditionFixer<KtForExpression>() {
    override val keyword = "for"
    override fun getElement(element: PsiElement?) = element as? KtForExpression
    override fun getCondition(element: KtForExpression) =
        element.loopRange ?: element.loopParameter ?: element.destructuringDeclaration

    override fun getLeftParenthesis(element: KtForExpression) = element.leftParenthesis
    override fun getRightParenthesis(element: KtForExpression) = element.rightParenthesis
    override fun getBody(element: KtForExpression) = element.body
}
