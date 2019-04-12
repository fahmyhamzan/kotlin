/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.debugger

import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.diagnostics.Errors
import org.jetbrains.kotlin.psi.KtCodeFragment
import org.jetbrains.kotlin.resolve.diagnostics.DiagnosticSuppressor

class DiagnosticSuppressorForDebugger : DiagnosticSuppressor {
    override fun isSuppressed(diagnostic: Diagnostic): Boolean {
        val element = diagnostic.psiElement
        val containingFile = element.containingFile

        if (containingFile is KtCodeFragment) {
            val diagnosticFactory = diagnostic.factory
            return diagnosticFactory == Errors.UNSAFE_CALL
        }

        return false
    }
}
