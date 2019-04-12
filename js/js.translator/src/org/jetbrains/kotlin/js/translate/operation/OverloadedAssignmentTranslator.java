/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.js.translate.operation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.reference.AccessTranslationUtils;
import org.jetbrains.kotlin.js.translate.reference.AccessTranslator;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.callUtil.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

import java.util.HashMap;
import java.util.Map;

public final class OverloadedAssignmentTranslator extends AssignmentTranslator {
    @NotNull
    public static JsExpression doTranslate(@NotNull KtBinaryExpression expression, @NotNull TranslationContext context) {
        return (new OverloadedAssignmentTranslator(expression, context)).translate();
    }

    @NotNull
    private final ResolvedCall<? extends FunctionDescriptor> resolvedCall;

    private OverloadedAssignmentTranslator(@NotNull KtBinaryExpression expression, @NotNull TranslationContext context) {
        super(expression, context);
        resolvedCall = CallUtilKt.getFunctionResolvedCallWithAssert(expression, context.bindingContext());
    }

    @NotNull
    private JsExpression translate() {
        if (isVariableReassignment) {
            return reassignment();
        }
        KtExpression left = expression.getLeft();
        assert left != null;
        return overloadedMethodInvocation(AccessTranslationUtils.getAccessTranslator(left, context()));
    }

    @NotNull
    private JsExpression reassignment() {
        KtExpression left = expression.getLeft();
        assert left != null;
        AccessTranslator accessTranslator = AccessTranslationUtils.getAccessTranslator(left, context()).getCached();
        JsExpression newValue = overloadedMethodInvocation(accessTranslator);
        return accessTranslator.translateAsSet(newValue);
    }

    @NotNull
    private JsExpression overloadedMethodInvocation(AccessTranslator accessTranslator) {
        JsBlock innerBlock = new JsBlock();
        TranslationContext innerContext = context().innerBlock(innerBlock);
        JsExpression oldValue = accessTranslator.translateAsGet();

        JsBlock argumentBlock = new JsBlock();
        TranslationContext argumentContext = innerContext.innerBlock(argumentBlock);
        KtExpression argumentPsi = expression.getRight();
        assert argumentPsi != null;
        JsExpression argument = Translation.translateAsExpression(argumentPsi, argumentContext);
        if (!argumentBlock.isEmpty()) {
            oldValue = innerContext.defineTemporary(oldValue);
            innerContext.addStatementsToCurrentBlockFrom(argumentBlock);
        }

        Map<KtExpression, JsExpression> aliases = new HashMap<>();
        aliases.put(argumentPsi, argument);
        innerContext = innerContext.innerContextWithAliasesForExpressions(aliases);

        JsExpression result = CallTranslator.translate(innerContext, resolvedCall, oldValue);
        context().addStatementsToCurrentBlockFrom(innerBlock);
        return result;
    }
}
