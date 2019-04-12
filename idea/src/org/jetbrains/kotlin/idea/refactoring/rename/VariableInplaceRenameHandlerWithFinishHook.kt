/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.refactoring.rename

import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.refactoring.rename.inplace.VariableInplaceRenamer

class VariableInplaceRenameHandlerWithFinishHook(private val onFinish: () -> Unit) : KotlinVariableInplaceRenameHandler() {
    override fun createRenamer(elementToRename: PsiElement, editor: Editor): VariableInplaceRenamer? {
        return object : RenamerImpl(elementToRename as PsiNamedElement, editor) {
            override fun performRefactoring(): Boolean {
                try {
                    return super.performRefactoring()
                } finally {
                    onFinish()
                }
            }
        }
    }
}