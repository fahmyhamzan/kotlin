/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.inspections

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.SmartPointerManager

class ReplaceStringInDocumentFix(element: PsiElement, private val oldString: String, private val newString: String) : LocalQuickFix {
    private val elementRef = SmartPointerManager.getInstance(element.project).createSmartPsiElementPointer(element)

    override fun applyFix(project: Project, problemDescriptor: ProblemDescriptor) {
        val element = elementRef.element ?: return
        val virtualFile = element.containingFile?.virtualFile ?: return
        val document = FileDocumentManager.getInstance().getDocument(virtualFile) ?: return

        val text = element.text
        val index = text.indexOf(oldString)
        if (index < 0) return

        val start = element.textOffset + index
        val end = start + oldString.length
        val documentText = document.text
        if (end > documentText.length) return

        if (documentText.substring(start, end) != oldString) return
        document.replaceString(start, end, newString)
    }

    override fun getFamilyName() = "Replace '$oldString' with '$newString'"
}