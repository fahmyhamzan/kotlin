/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.editor.fixers

import com.intellij.lang.SmartEnterProcessorWithFixers
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.builtins.isFunctionType
import org.jetbrains.kotlin.idea.caches.resolve.allowResolveInWriteAction
import org.jetbrains.kotlin.idea.caches.resolve.resolveToCall
import org.jetbrains.kotlin.idea.editor.KotlinSmartEnterHandler
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.psiUtil.endOffset

class KotlinLastLambdaParameterFixer : SmartEnterProcessorWithFixers.Fixer<KotlinSmartEnterHandler>() {
    override fun apply(editor: Editor, processor: KotlinSmartEnterHandler, element: PsiElement) {
        if (element !is KtCallExpression) return

        val resolvedCall = allowResolveInWriteAction { element.resolveToCall() } ?: return

        val valueParameters = resolvedCall.candidateDescriptor.valueParameters

        if (resolvedCall.valueArguments.size == valueParameters.size - 1) {
            val type = valueParameters.last().type
            if (type.isFunctionType) {
                val doc = editor.document

                var offset = element.endOffset
                if (element.valueArgumentList?.rightParenthesis == null) {
                    doc.insertString(offset, ")")
                    offset++
                }

                doc.insertString(offset, "{ }")
                processor.registerUnresolvedError(offset + 2)
                processor.commit(editor)
            }
        }
    }
}
