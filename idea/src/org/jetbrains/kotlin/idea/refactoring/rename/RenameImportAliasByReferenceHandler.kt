/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.refactoring.rename

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.references.mainReference

class RenameImportAliasByReferenceHandler : AbstractReferenceSubstitutionRenameHandler(KotlinVariableInplaceRenameHandler()) {
    override fun getElementToRename(dataContext: DataContext): PsiElement? {
        val refExpr = getReferenceExpression(dataContext) ?: return null
        return refExpr.mainReference.getImportAlias()
    }
}