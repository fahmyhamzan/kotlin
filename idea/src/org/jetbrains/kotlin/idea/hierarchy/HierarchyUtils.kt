/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.hierarchy

import com.intellij.codeInsight.TargetElementUtil
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.util.ProjectRootsUtil

fun getCurrentElement(dataContext: DataContext, project: Project): PsiElement? {
    val editor = CommonDataKeys.EDITOR.getData(dataContext)
    if (editor != null) {
        val file = PsiDocumentManager.getInstance(project).getPsiFile(editor.document) ?: return null
        if (!ProjectRootsUtil.isInProjectOrLibSource(file)) return null
        return TargetElementUtil.findTargetElement(editor, TargetElementUtil.getInstance().allAccepted)
    }

    return CommonDataKeys.PSI_ELEMENT.getData(dataContext)
}
