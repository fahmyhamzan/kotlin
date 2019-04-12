/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.refactoring.changeSignature.ui;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiCodeFragment;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.changeSignature.ParameterTableModelBase;
import com.intellij.refactoring.changeSignature.ParameterTableModelItemBase;
import com.intellij.util.ui.ColumnInfo;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.idea.refactoring.changeSignature.*;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtPsiFactoryKt;

public abstract class KotlinCallableParameterTableModel extends ParameterTableModelBase<KotlinParameterInfo, ParameterTableModelItemBase<KotlinParameterInfo>> {
    private final Project project;
    private final KotlinMethodDescriptor methodDescriptor;

    protected KotlinCallableParameterTableModel(
            KotlinMethodDescriptor methodDescriptor,
            PsiElement typeContext,
            PsiElement defaultValueContext,
            ColumnInfo... columnInfos
    ) {
        super(typeContext, defaultValueContext, columnInfos);
        this.methodDescriptor = methodDescriptor;
        project = typeContext.getProject();
    }

    @Nullable
    public KotlinParameterInfo getReceiver() {
        return null;
    }

    @Override
    protected ParameterTableModelItemBase<KotlinParameterInfo> createRowItem(@Nullable KotlinParameterInfo parameterInfo) {
        if (parameterInfo == null) {
            parameterInfo = new KotlinParameterInfo(methodDescriptor.getBaseDescriptor(),
                                                    -1,
                                                    "",
                                                    new KotlinTypeInfo(false, null, null),
                                                    null,
                                                    null,
                                                    KotlinValVar.None,
                                                    null);
        }
        KtPsiFactory psiFactory = KtPsiFactoryKt.KtPsiFactory(project);
        PsiCodeFragment paramTypeCodeFragment = psiFactory.createTypeCodeFragment(KotlinTypeInfoKt.render(parameterInfo.getCurrentTypeInfo()), myTypeContext);
        KtExpression defaultValueForCall = parameterInfo.getDefaultValueForCall();
        PsiCodeFragment defaultValueCodeFragment = psiFactory.createExpressionCodeFragment(
                defaultValueForCall != null ? defaultValueForCall.getText() : "",
                myDefaultValueContext
        );
        return new ParameterTableModelItemBase<KotlinParameterInfo>(parameterInfo, paramTypeCodeFragment, defaultValueCodeFragment) {
            @Override
            public boolean isEllipsisType() {
                return false;
            }
        };
    }

    public static boolean isTypeColumn(ColumnInfo column) {
        return column instanceof TypeColumn;
    }

    public static boolean isNameColumn(ColumnInfo column) {
        return column instanceof NameColumn;
    }

    public static boolean isDefaultValueColumn(ColumnInfo column) {
        return column instanceof DefaultValueColumn;
    }
}
