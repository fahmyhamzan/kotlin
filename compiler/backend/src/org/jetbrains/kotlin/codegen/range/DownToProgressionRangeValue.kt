/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.codegen.range

import org.jetbrains.kotlin.codegen.ExpressionCodegen
import org.jetbrains.kotlin.codegen.generateCallReceiver
import org.jetbrains.kotlin.codegen.generateCallSingleArgument
import org.jetbrains.kotlin.codegen.range.comparison.getComparisonGeneratorForKotlinType
import org.jetbrains.kotlin.codegen.range.forLoop.ForInSimpleProgressionLoopGenerator
import org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.psi.KtForExpression
import org.jetbrains.kotlin.resolve.calls.callUtil.getFirstArgumentExpression
import org.jetbrains.kotlin.resolve.calls.callUtil.getReceiverExpression
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall

class DownToProgressionRangeValue(rangeCall: ResolvedCall<out CallableDescriptor>) :
    PrimitiveNumberRangeIntrinsicRangeValue(rangeCall), ReversableRangeValue {

    override fun getBoundedValue(codegen: ExpressionCodegen) =
        SimpleBoundedValue(
            instanceType = codegen.asmType(rangeCall.resultingDescriptor.returnType!!),
            lowBound = codegen.generateCallSingleArgument(rangeCall),
            highBound = codegen.generateCallReceiver(rangeCall)
        )

    override fun createForLoopGenerator(codegen: ExpressionCodegen, forExpression: KtForExpression) =
        createConstBoundedForInDownToGenerator(codegen, forExpression)
            ?: ForInSimpleProgressionLoopGenerator.fromBoundedValueWithStepMinus1(
                codegen, forExpression, getBoundedValue(codegen),
                getComparisonGeneratorForKotlinType(elementKotlinType)
            )

    override fun createForInReversedLoopGenerator(codegen: ExpressionCodegen, forExpression: KtForExpression) =
        createConstBoundedForInReversedDownToGenerator(codegen, forExpression)
            ?: ForInSimpleProgressionLoopGenerator.fromBoundedValueWithStep1(
                codegen, forExpression, getBoundedValue(codegen),
                getComparisonGeneratorForKotlinType(elementKotlinType),
                inverseBoundsEvaluationOrder = true
            )

    private fun createConstBoundedForInDownToGenerator(
        codegen: ExpressionCodegen,
        forExpression: KtForExpression
    ): ForLoopGenerator? {
        val endExpression = rangeCall.getFirstArgumentExpression() ?: return null
        return createConstBoundedForLoopGeneratorOrNull(
            codegen, forExpression,
            codegen.generateCallReceiver(rangeCall),
            endExpression,
            -1
        )
    }

    private fun createConstBoundedForInReversedDownToGenerator(
        codegen: ExpressionCodegen,
        forExpression: KtForExpression
    ): ForLoopGenerator? {
        val endExpression = rangeCall.getReceiverExpression() ?: return null
        return createConstBoundedForLoopGeneratorOrNull(
            codegen, forExpression,
            codegen.generateCallSingleArgument(rangeCall),
            endExpression,
            1
        )
    }
}