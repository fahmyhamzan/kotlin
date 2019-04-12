/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.psi;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

import java.util.List;

public class KtFileAnnotationList extends KtElementImplStub<KotlinPlaceHolderStub<KtFileAnnotationList>> implements
                                                                                                            KtAnnotationsContainer {

    public KtFileAnnotationList(@NotNull ASTNode node) {
        super(node);
    }

    public KtFileAnnotationList(@NotNull KotlinPlaceHolderStub<KtFileAnnotationList> stub) {
        super(stub, KtStubElementTypes.FILE_ANNOTATION_LIST);
    }

    @Override
    public <R, D> R accept(@NotNull KtVisitor<R, D> visitor, D data) {
        return visitor.visitFileAnnotationList(this, data);
    }

    @NotNull
    public List<KtAnnotation> getAnnotations() {
        return getStubOrPsiChildrenAsList(KtStubElementTypes.ANNOTATION);
    }

    @NotNull
    public List<KtAnnotationEntry> getAnnotationEntries() {
        return KtPsiUtilKt.collectAnnotationEntriesFromStubOrPsi(this);
    }
}
