/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.inspections

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInspection.IntentionWrapper
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.diagnostics.Diagnostic
import org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters2
import org.jetbrains.kotlin.idea.codeInsight.DescriptorToSourceUtilsIde
import org.jetbrains.kotlin.idea.refactoring.canRefactor
import org.jetbrains.kotlin.idea.intentions.InfixCallToOrdinaryIntention
import org.jetbrains.kotlin.idea.quickfix.KotlinSingleIntentionActionFactory
import org.jetbrains.kotlin.psi.KtBinaryExpression
import org.jetbrains.kotlin.psi.KtModifierListOwner
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression

object InfixCallFixActionFactory : KotlinSingleIntentionActionFactory() {
    override fun createAction(diagnostic: Diagnostic): IntentionAction? {
        val functionDescriptor = (diagnostic as? DiagnosticWithParameters2<*, *, *>)?.a as? FunctionDescriptor ?: return null
        val target = DescriptorToSourceUtilsIde.getAnyDeclaration(diagnostic.psiFile.project, functionDescriptor)
                as? KtModifierListOwner
        if (target == null || target.canRefactor()) {
            // we'll fix the problem by adding the 'infix' modifier to the target
            return null
        }

        val infixCall = (diagnostic.psiElement as? KtOperationReferenceExpression)?.parent as? KtBinaryExpression ?: return null
        return IntentionWrapper(InfixCallToOrdinaryIntention(), infixCall.containingFile)
    }
}
