/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.quickfix

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.idea.util.CommentSaver
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtParameter

class RemoveDefaultParameterValueFix(parameter: KtParameter) : KotlinQuickFixAction<KtParameter>(parameter) {

    override fun getText() = "Remove default parameter value"

    override fun getFamilyName() = text

    override fun invoke(project: Project, editor: Editor?, file: KtFile) {
        val parameter = element ?: return
        val typeReference = parameter.typeReference ?: return
        val defaultValue = parameter.defaultValue ?: return
        val commentSaver = CommentSaver(parameter)
        parameter.deleteChildRange(typeReference.nextSibling, defaultValue)
        commentSaver.restore(parameter)
    }

    companion object : KotlinSingleIntentionActionFactory() {
        override fun createAction(diagnostic: Diagnostic): KotlinQuickFixAction<KtParameter>? =
            (diagnostic.psiElement as? KtParameter)?.let { RemoveDefaultParameterValueFix(it) }
    }

}