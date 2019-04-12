/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.android.parcel.quickfixes

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.idea.core.ShortenReferences
import org.jetbrains.kotlin.idea.quickfix.KotlinQuickFixAction
import org.jetbrains.kotlin.idea.quickfix.KotlinSingleIntentionActionFactory
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.psiUtil.getNonStrictParentOfType

abstract class AbstractParcelableQuickFix<T: KtElement>(element: T) : KotlinQuickFixAction<T>(element) {
    protected companion object {
        fun <T : KtElement> T.shortenReferences() = ShortenReferences.DEFAULT.process(this)
    }

    override fun getFamilyName() = text

    abstract fun invoke(ktPsiFactory: KtPsiFactory, element: T)

    final override fun invoke(project: Project, editor: Editor?, file: KtFile) {
        val clazz = element ?: return
        val ktPsiFactory = KtPsiFactory(project, markGenerated = true)
        invoke(ktPsiFactory, clazz)
    }

    abstract class AbstractFactory(private val f: Diagnostic.() -> IntentionAction?) : KotlinSingleIntentionActionFactory() {
        companion object {
            inline fun <reified T: KtElement> Diagnostic.findElement() = psiElement.getNonStrictParentOfType<T>()
        }

        override fun createAction(diagnostic: Diagnostic) = f(diagnostic)
    }
}