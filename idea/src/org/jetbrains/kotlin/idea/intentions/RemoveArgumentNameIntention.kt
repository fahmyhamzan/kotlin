/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.intentions

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import org.jetbrains.kotlin.idea.caches.resolve.resolveToCall
import org.jetbrains.kotlin.psi.KtCallElement
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.psi.KtValueArgumentList
import org.jetbrains.kotlin.psi.psiUtil.startOffset
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch

class RemoveArgumentNameIntention
  : SelfTargetingRangeIntention<KtValueArgument>(KtValueArgument::class.java, "Remove argument name") {

    override fun applicabilityRange(element: KtValueArgument): TextRange? {
        if (!element.isNamed()) return null

        val argumentList = element.parent as? KtValueArgumentList ?: return null
        val arguments = argumentList.arguments
        if (arguments.asSequence().takeWhile { it != element }.any { it.isNamed() }) return null

        val callExpr = argumentList.parent as? KtCallElement ?: return null
        val resolvedCall = callExpr.resolveToCall() ?: return null
        val argumentMatch = resolvedCall.getArgumentMapping(element) as? ArgumentMatch ?: return null
        if (argumentMatch.valueParameter.index != arguments.indexOf(element)) return null

        val expression = element.getArgumentExpression() ?: return null
        return TextRange(element.startOffset, expression.startOffset)
    }

    override fun applyTo(element: KtValueArgument, editor: Editor?) {
        val newArgument = KtPsiFactory(element).createArgument(element.getArgumentExpression()!!, null, element.getSpreadElement() != null)
        element.replace(newArgument)
    }
}