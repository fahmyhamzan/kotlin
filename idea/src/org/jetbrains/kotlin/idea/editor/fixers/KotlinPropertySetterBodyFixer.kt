/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.editor.fixers

import com.intellij.lang.SmartEnterProcessorWithFixers
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.editor.KotlinSmartEnterHandler
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtPropertyAccessor
import org.jetbrains.kotlin.psi.KtPsiUtil
import org.jetbrains.kotlin.psi.psiUtil.endOffset
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType

class KotlinPropertySetterBodyFixer : SmartEnterProcessorWithFixers.Fixer<KotlinSmartEnterHandler>() {
    override fun apply(editor: Editor, processor: KotlinSmartEnterHandler, psiElement: PsiElement) {
        if (psiElement !is KtPropertyAccessor) return

        if (!psiElement.isSetter) return

        if (psiElement.bodyExpression != null || psiElement.equalsToken != null) return

        val parentDeclaration = psiElement.getStrictParentOfType<KtDeclaration>()
        if (parentDeclaration is KtClassOrObject) {
            if (KtPsiUtil.isTrait(parentDeclaration) || psiElement.hasModifier(KtTokens.ABSTRACT_KEYWORD)) {
                return
            }
        }

        //setter without parameter and body is valid
        if (psiElement.namePlaceholder.endOffset == psiElement.endOffset) return

        val doc = editor.document
        var endOffset = psiElement.range.end

        if (psiElement.text?.last() == ';') {
            doc.deleteString(endOffset - 1, endOffset)
            endOffset--
        }

        doc.insertString(endOffset, "{\n}")
    }
}