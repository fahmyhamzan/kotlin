/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.contracts.parsing

import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.contracts.description.BooleanExpression
import org.jetbrains.kotlin.contracts.description.CallsEffectDeclaration
import org.jetbrains.kotlin.contracts.description.ContractDescription
import org.jetbrains.kotlin.contracts.description.EffectDeclaration
import org.jetbrains.kotlin.contracts.description.expressions.BooleanVariableReference
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference
import org.jetbrains.kotlin.contracts.description.expressions.ContractDescriptionValue
import org.jetbrains.kotlin.contracts.description.expressions.VariableReference
import org.jetbrains.kotlin.contracts.parsing.ContractsDslNames.CALLS_IN_PLACE_EFFECT
import org.jetbrains.kotlin.contracts.parsing.ContractsDslNames.CONDITIONAL_EFFECT
import org.jetbrains.kotlin.contracts.parsing.ContractsDslNames.RETURNS_EFFECT
import org.jetbrains.kotlin.contracts.parsing.ContractsDslNames.RETURNS_NOT_NULL_EFFECT
import org.jetbrains.kotlin.contracts.parsing.effects.PsiCallsEffectParser
import org.jetbrains.kotlin.contracts.parsing.effects.PsiConditionalEffectParser
import org.jetbrains.kotlin.contracts.parsing.effects.PsiReturnsEffectParser
import org.jetbrains.kotlin.descriptors.ParameterDescriptor
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.resolve.calls.callUtil.getResolvedCall
import org.jetbrains.kotlin.resolve.calls.callUtil.getType
import org.jetbrains.kotlin.storage.StorageManager

internal class PsiContractParserDispatcher(
    private val collector: ContractParsingDiagnosticsCollector,
    private val callContext: ContractCallContext,
    private val storageManager: StorageManager
) {
    private val conditionParser = PsiConditionParser(collector, callContext, this)
    private val constantParser = PsiConstantParser(callContext)
    private val effectsParsers: Map<Name, PsiEffectParser> = mapOf(
        RETURNS_EFFECT to PsiReturnsEffectParser(collector, callContext, this),
        RETURNS_NOT_NULL_EFFECT to PsiReturnsEffectParser(collector, callContext, this),
        CALLS_IN_PLACE_EFFECT to PsiCallsEffectParser(collector, callContext, this),
        CONDITIONAL_EFFECT to PsiConditionalEffectParser(collector, callContext, this)
    )

    fun parseContract(): ContractDescription? {
        // Must be non-null because of checks in 'checkContractAndRecordIfPresent', but actually is not, see EA-124365
        val resolvedCall = callContext.contractCallExpression.getResolvedCall(callContext.bindingContext) ?: return null

        val firstArgumentExpression = resolvedCall.firstArgumentAsExpressionOrNull()
        val lambda = if (firstArgumentExpression is KtLambdaExpression) {
            firstArgumentExpression
        } else {
            val reportOn = firstArgumentExpression ?: callContext.contractCallExpression
            collector.badDescription("first argument of 'contract'-call should be a lambda expression", reportOn)
            return null
        }

        val effectsWithExpression = lambda.bodyExpression?.statements?.map { parseEffect(it) to it } ?: return null
        checkDuplicatedCallsEffectsAndReport(effectsWithExpression)
        val effects = effectsWithExpression.mapNotNull { it.first }
        if (effects.isEmpty()) return null

        return ContractDescription(effects, callContext.functionDescriptor, storageManager)
    }

    fun parseCondition(expression: KtExpression?): BooleanExpression? = expression?.accept(conditionParser, Unit)

    fun parseEffect(expression: KtExpression?): EffectDeclaration? {
        if (expression == null) return null
        if (!isValidEffectDeclaration(expression)) return null

        val returnType = expression.getType(callContext.bindingContext) ?: return null
        val parser = effectsParsers[returnType.constructor.declarationDescriptor?.name]
        if (parser == null) {
            collector.badDescription("unrecognized effect", expression)
            return null
        }

        return parser.tryParseEffect(expression)
    }

    private fun checkDuplicatedCallsEffectsAndReport(effects: List<Pair<EffectDeclaration?, KtExpression>>) {
        val descriptorsWithCallsEffect = mutableSetOf<ParameterDescriptor>()
        for ((effect, expression) in effects) {
            if (effect !is CallsEffectDeclaration) continue
            val descriptor = effect.variableReference.descriptor
            if (descriptor in descriptorsWithCallsEffect) {
                collector.badDescription("Duplicated contract for ${descriptor.name}. Only one `callsInPlace` contract per parameter is allowed.", expression)
            } else {
                descriptorsWithCallsEffect.add(descriptor)
            }
        }
    }

    private fun isValidEffectDeclaration(expression: KtExpression): Boolean {
        if (expression !is KtCallExpression && expression !is KtBinaryExpression) {
            collector.badDescription("unexpected construction in contract description", expression)
            return false
        }

        val resultingDescriptor = expression.getResolvedCall(callContext.bindingContext)?.resultingDescriptor ?: return false
        if (!resultingDescriptor.isFromContractDsl()) {
            collector.badDescription("effects can be produced only by direct calls to ContractsDSL", expression)
            return false
        }

        return true
    }

    fun parseConstant(expression: KtExpression?): ConstantReference? {
        if (expression == null) return null
        return expression.accept(constantParser, Unit)
    }

    fun parseVariable(expression: KtExpression?): VariableReference? {
        if (expression == null) return null
        val descriptor = expression.getResolvedCall(callContext.bindingContext)?.resultingDescriptor
        if (descriptor !is ParameterDescriptor) {
            if (expression !is KtConstantExpression)
                collector.badDescription("only references to parameters are allowed in contract description", expression)
            return null
        }

        if (descriptor is ReceiverParameterDescriptor && descriptor.type.constructor.declarationDescriptor?.isFromContractDsl() == true) {
            collector.badDescription("only references to parameters are allowed. Did you miss label on <this>?", expression)
            return null
        }

        return if (KotlinBuiltIns.isBoolean(descriptor.type))
            BooleanVariableReference(descriptor)
        else
            VariableReference(descriptor)
    }

    fun parseValue(expression: KtExpression?): ContractDescriptionValue? {
        val variable = parseVariable(expression)
        if (variable != null) return variable

        return parseConstant(expression)
    }
}