/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.quickfix

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.psi.KtAnnotatedExpression
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtPsiFactory

class RemoveAtFromAnnotationArgument(constructor: KtAnnotationEntry) : KotlinQuickFixAction<KtAnnotationEntry>(constructor) {

    override fun getText() = "Remove @ from annotation argument"

    override fun getFamilyName() = text

    override fun invoke(project: Project, editor: Editor?, file: KtFile) {
        val elementToReplace = (element?.parent as? KtAnnotatedExpression) ?: return

        val noAt = KtPsiFactory(elementToReplace.project).createExpression(elementToReplace.text.replaceFirst("@", ""))
        elementToReplace.replace(noAt)
    }

    companion object : KotlinSingleIntentionActionFactory() {
        override fun createAction(diagnostic: Diagnostic): KotlinQuickFixAction<KtAnnotationEntry>? =
            (diagnostic.psiElement as? KtAnnotationEntry)?.let { RemoveAtFromAnnotationArgument(it) }
    }

}
