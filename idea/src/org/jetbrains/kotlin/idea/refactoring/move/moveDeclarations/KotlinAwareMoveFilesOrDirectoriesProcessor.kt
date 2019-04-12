/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.refactoring.move.moveDeclarations

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.EmptyRunnable
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile
import com.intellij.refactoring.move.MoveCallback
import com.intellij.refactoring.move.moveFilesOrDirectories.MoveFilesOrDirectoriesProcessor
import com.intellij.usageView.UsageInfo
import com.intellij.usageView.UsageViewDescriptor
import com.intellij.util.containers.MultiMap
import org.jetbrains.kotlin.idea.refactoring.move.moveFilesOrDirectories.allElementsToMove
import org.jetbrains.kotlin.idea.refactoring.move.moveFilesOrDirectories.shouldFixFqName
import org.jetbrains.kotlin.psi.KtFile

class KotlinAwareMoveFilesOrDirectoriesProcessor @JvmOverloads constructor (
        project: Project,
        private val elementsToMove: List<PsiElement>,
        private val targetDirectory: PsiDirectory,
        private val searchReferences: Boolean,
        searchInComments: Boolean,
        searchInNonJavaFiles: Boolean,
        moveCallback: MoveCallback?,
        prepareSuccessfulCallback: Runnable = EmptyRunnable.INSTANCE
) : MoveFilesOrDirectoriesProcessor(project,
                                    elementsToMove.toTypedArray<PsiElement>(),
                                    targetDirectory,
                                    true,
                                    searchInComments,
                                    searchInNonJavaFiles,
                                    moveCallback,
                                    prepareSuccessfulCallback) {
    override fun createUsageViewDescriptor(usages: Array<out UsageInfo>): UsageViewDescriptor {
        return MoveFilesWithDeclarationsViewDescriptor(elementsToMove.toTypedArray<PsiElement>(), targetDirectory)
    }

    override fun findUsages(): Array<UsageInfo> {
        try {
            markScopeToMove(elementsToMove)
            return if (searchReferences) super.findUsages() else UsageInfo.EMPTY_ARRAY
        }
        finally {
            markScopeToMove(null)
        }
    }

    override fun preprocessUsages(refUsages: Ref<Array<UsageInfo>>): Boolean {
        val usages = refUsages.get()

        val (conflictUsages, usagesToProcess) = usages.partition { it is ConflictUsageInfo }

        val conflicts = MultiMap<PsiElement, String>()
        for (conflictUsage in conflictUsages) {
            conflicts.putValues(conflictUsage.element, (conflictUsage as ConflictUsageInfo).messages)
        }

        refUsages.set(usagesToProcess.toTypedArray())

        return showConflicts(conflicts, usages)
    }

    private fun markPsiFiles(mark: PsiFile.() -> Unit) {
        fun PsiElement.doMark(mark: PsiFile.() -> Unit) {
            when (this) {
                is PsiFile -> mark()
                is PsiDirectory -> children.forEach { it.doMark(mark) }
            }
        }

        elementsToMove.forEach { it.doMark(mark) }
    }

    private fun markShouldFixFqName(value: Boolean) {
        markPsiFiles { (this as? PsiJavaFile)?.shouldFixFqName = value }
    }

    private fun markScopeToMove(allElementsToMove: List<PsiElement>?) {
        markPsiFiles { (this as? KtFile)?.allElementsToMove = allElementsToMove }
    }

    override fun performRefactoring(usages: Array<UsageInfo>) {
        try {
            markShouldFixFqName(true)
            super.performRefactoring(usages)
        }
        finally {
            markShouldFixFqName(false)
        }
    }
}
