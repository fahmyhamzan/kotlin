/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.scratch.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.keymap.KeymapManager
import com.intellij.openapi.keymap.KeymapUtil
import com.intellij.openapi.project.DumbService
import com.intellij.task.ProjectTaskManager
import org.jetbrains.kotlin.idea.KotlinBundle
import org.jetbrains.kotlin.idea.scratch.*
import org.jetbrains.kotlin.idea.scratch.output.ScratchOutputHandlerAdapter
import org.jetbrains.kotlin.idea.scratch.ui.ScratchTopPanel
import org.jetbrains.kotlin.idea.scratch.LOG as log

class RunScratchAction : ScratchAction(
    KotlinBundle.message("scratch.run.button"),
    AllIcons.Actions.Execute
) {

    init {
        KeymapManager.getInstance().activeKeymap.getShortcuts("Kotlin.RunScratch").firstOrNull()?.let {
            templatePresentation.text += " (${KeymapUtil.getShortcutText(it)})"
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val scratchPanel = getScratchPanelFromSelectedEditor(project) ?: return

        doAction(scratchPanel, false)
    }

    companion object {
        fun doAction(scratchPanel: ScratchTopPanel, isAutoRun: Boolean) {
            val scratchFile = scratchPanel.scratchFile
            val psiFile = scratchFile.getPsiFile() ?: return

            val isMakeBeforeRun = scratchFile.options.isMakeBeforeRun
            val isRepl = scratchFile.options.isRepl

            val provider = ScratchFileLanguageProvider.get(psiFile.language) ?: return

            log.printDebugMessage("Run Action: isMakeBeforeRun = $isMakeBeforeRun, isRepl = $isRepl")

            val defaultOutputHandler = provider.getOutputHandler()

            val module = scratchPanel.getModule()

            val executor = if (isRepl) provider.createReplExecutor(scratchFile) else provider.createCompilingExecutor(scratchFile)

            @Suppress("FoldInitializerAndIfToElvis")
            if (executor == null) {
                return defaultOutputHandler.error(scratchFile, "Couldn't run ${psiFile.name}")
            }

            executor.addOutputHandler(defaultOutputHandler)

            executor.addOutputHandler(object : ScratchOutputHandlerAdapter() {
                override fun onStart(file: ScratchFile) {
                    ScratchCompilationSupport.start(file, executor)
                    scratchPanel.updateToolbar()
                }

                override fun onFinish(file: ScratchFile) {
                    ScratchCompilationSupport.stop()
                    scratchPanel.updateToolbar()
                }
            })

            fun executeScratch() {
                try {
                    executor.execute()
                } catch (ex: Throwable) {
                    executor.errorOccurs("Exception occurs during Run Scratch Action", ex, true)
                }
            }

            if (!isAutoRun && module != null && isMakeBeforeRun) {
                val project = scratchPanel.scratchFile.project
                ProjectTaskManager.getInstance(project).build(arrayOf(module)) { result ->
                    if (result.isAborted || result.errors > 0) {
                        executor.errorOccurs("There were compilation errors in module ${module.name}")
                    }

                    if (DumbService.isDumb(project)) {
                        DumbService.getInstance(project).smartInvokeLater {
                            executeScratch()
                        }
                    } else {
                        executeScratch()
                    }
                }
            } else {
                executeScratch()
            }
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)

        e.presentation.isEnabled = !ScratchCompilationSupport.isAnyInProgress()

        if (e.presentation.isEnabled) {
            e.presentation.text = templatePresentation.text
        } else {
            e.presentation.text = "Other Scratch file execution is in progress"
        }

        val project = e.project ?: return
        val panel = getScratchPanelFromSelectedEditor(project) ?: return

        e.presentation.isVisible = !ScratchCompilationSupport.isInProgress(panel.scratchFile)
    }
}