/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.inspections.collections

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.idea.core.replaced
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.forEachDescendantOfType

class RenameUselessCallFix(val newName: String) : LocalQuickFix {
    override fun getName() = "Change call to '$newName'"

    override fun getFamilyName() = name

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        (descriptor.psiElement as? KtQualifiedExpression)?.let {
            val factory = KtPsiFactory(it)
            val selectorCallExpression = it.selectorExpression as? KtCallExpression
            val calleeExpression = selectorCallExpression?.calleeExpression ?: return
            calleeExpression.replaced(factory.createExpression(newName))
            selectorCallExpression.renameGivenReturnLabels(factory, calleeExpression.text, newName)
        }
    }

    private fun KtCallExpression.renameGivenReturnLabels(factory: KtPsiFactory, labelName: String, newName: String) {
        val lambdaExpression = lambdaArguments.firstOrNull()?.getLambdaExpression() ?: return
        val bodyExpression = lambdaExpression.bodyExpression ?: return

        bodyExpression.forEachDescendantOfType<KtReturnExpression> {
            if (it.getLabelName() != labelName) return@forEachDescendantOfType

            it.replaced(
                factory.createExpressionByPattern(
                    "return@$0 $1",
                    newName,
                    it.returnedExpression ?: ""
                )
            )
        }
    }
}