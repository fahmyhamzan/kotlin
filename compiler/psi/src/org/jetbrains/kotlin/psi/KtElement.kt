/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.psi

import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiReference

interface KtElement : NavigatablePsiElement, KtPureElement {
    fun <D> acceptChildren(visitor: KtVisitor<Void, D>, data: D)

    fun <R, D> accept(visitor: KtVisitor<R, D>, data: D): R

    @Deprecated("Don't use getReference() on JetElement for the choice is unpredictable")
    override fun getReference(): PsiReference?
}

fun KtElement.getModificationStamp(): Long {
    return when (this) {
        is KtFile -> this.modificationStamp
        is KtDeclarationStub<*> -> this.modificationStamp
        is KtSuperTypeList -> this.modificationStamp
        else -> (parent as KtElement).getModificationStamp()
    }
}
