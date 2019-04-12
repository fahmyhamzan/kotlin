/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.refactoring.rename

import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.refactoring.rename.RenameHandler
import org.jetbrains.kotlin.idea.caches.resolve.resolveToCall
import org.jetbrains.kotlin.idea.codeInsight.CodeInsightUtils
import org.jetbrains.kotlin.psi.KtSimpleNameExpression
import org.jetbrains.kotlin.resolve.calls.tower.isSynthesized
import org.jetbrains.kotlin.idea.statistics.KotlinEventTrigger
import org.jetbrains.kotlin.idea.statistics.KotlinStatisticsTrigger

class RenameSyntheticDeclarationByReferenceHandler : RenameHandler {
    override fun isAvailableOnDataContext(dataContext: DataContext): Boolean {
        val file = CommonDataKeys.PSI_FILE.getData(dataContext) ?: return false
        val editor = CommonDataKeys.EDITOR.getData(dataContext) ?: return false
        val refExpression = file.findElementForRename<KtSimpleNameExpression>(editor.caretModel.offset) ?: return false
        return (refExpression.resolveToCall()?.resultingDescriptor)?.isSynthesized ?: false
    }

    override fun isRenaming(dataContext: DataContext) = isAvailableOnDataContext(dataContext)

    override fun invoke(project: Project, editor: Editor, file: PsiFile, dataContext: DataContext?) {
        CodeInsightUtils.showErrorHint(project, editor, "Rename is not applicable to synthetic declaration", "Rename", null)
        KotlinStatisticsTrigger.trigger(KotlinEventTrigger.KotlinIdeRefactoringTrigger, this::class.java.name)
    }

    override fun invoke(project: Project, elements: Array<out PsiElement>, dataContext: DataContext?) {
        // Do nothing: this method is not called from editor
    }
}
