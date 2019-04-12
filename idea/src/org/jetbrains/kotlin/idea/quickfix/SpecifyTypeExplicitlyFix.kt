/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.quickfix

import com.intellij.codeInsight.FileModificationService
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.idea.intentions.SpecifyTypeExplicitlyIntention
import org.jetbrains.kotlin.psi.KtCallableDeclaration
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.types.isError

class SpecifyTypeExplicitlyFix : PsiElementBaseIntentionAction() {
    override fun getFamilyName() = "Specify type explicitly"

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        if (!FileModificationService.getInstance().preparePsiElementForWrite(element)) return

        val declaration = declarationByElement(element)!!
        val type = SpecifyTypeExplicitlyIntention.getTypeForDeclaration(declaration)
        SpecifyTypeExplicitlyIntention.addTypeAnnotation(editor, declaration, type)
    }

    override fun isAvailable(project: Project, editor: Editor, element: PsiElement): Boolean {
        val declaration = declarationByElement(element)
        if (declaration?.typeReference != null) return false
        text = when (declaration) {
            is KtProperty -> "Specify type explicitly"
            is KtNamedFunction -> "Specify return type explicitly"
            else -> return false
        }

        return !SpecifyTypeExplicitlyIntention.getTypeForDeclaration(declaration).isError
    }

    private fun declarationByElement(element: PsiElement): KtCallableDeclaration? {
        return PsiTreeUtil.getParentOfType(element, KtProperty::class.java, KtNamedFunction::class.java) as KtCallableDeclaration?
    }
}
