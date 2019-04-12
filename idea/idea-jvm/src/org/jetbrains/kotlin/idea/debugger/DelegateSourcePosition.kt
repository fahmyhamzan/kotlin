/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.debugger

import com.intellij.debugger.SourcePosition
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

abstract class DelegateSourcePosition(private var delegate: SourcePosition) : SourcePosition() {
    override fun getFile(): PsiFile = delegate.file
    override fun getElementAt(): PsiElement? = delegate.elementAt
    override fun getLine(): Int = delegate.line
    override fun getOffset(): Int = delegate.offset

    override fun openEditor(requestFocus: Boolean): Editor = delegate.openEditor(requestFocus)

    override fun canNavigate() = delegate.canNavigate()
    override fun canNavigateToSource() = delegate.canNavigateToSource()

    override fun navigate(requestFocus: Boolean) {
        delegate.navigate(requestFocus)
    }

    override fun hashCode(): Int = delegate.hashCode()
    override fun equals(other: Any?) = delegate == other

    override fun toString() = "DSP($delegate)"
}