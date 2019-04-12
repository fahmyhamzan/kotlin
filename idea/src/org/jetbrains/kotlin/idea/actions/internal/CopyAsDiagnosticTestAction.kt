/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.actions.internal

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import org.jetbrains.kotlin.checkers.utils.CheckerTestUtil
import org.jetbrains.kotlin.config.LanguageVersionSettings
import org.jetbrains.kotlin.idea.caches.resolve.*
import org.jetbrains.kotlin.psi.KtFile
import java.awt.*
import java.awt.datatransfer.StringSelection

class CopyAsDiagnosticTestAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
        assert(editor != null && psiFile != null)

        val bindingContext = (psiFile as KtFile).analyzeWithContent()

        // Parameters `languageVersionSettings`, `dataFlowValueFactory` and `moduleDescriptor` are not-null only for compiler diagnostic tests
        val diagnostics = CheckerTestUtil.getDiagnosticsIncludingSyntaxErrors(
            bindingContext,
            psiFile,
            false,
            mutableListOf(),
            null,
            false,
            null,
            null,
            null
        )
        val result = CheckerTestUtil.addDiagnosticMarkersToText(psiFile, diagnostics).toString()

        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(StringSelection(result)) { _, _ -> }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isVisible = ApplicationManager.getApplication().isInternal

        val editor = e.getData(CommonDataKeys.EDITOR)
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
        e.presentation.isEnabled = editor != null && psiFile is KtFile
    }
}
