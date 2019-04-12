/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.expressions

import org.jetbrains.kotlin.fir.visitors.FirVisitor

interface FirTryExpression : FirExpression {
    val tryBlock: FirBlock

    val catches: List<FirCatch>

    val finallyBlock: FirBlock?

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitTryExpression(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        tryBlock.accept(visitor, data)
        catches.forEach { it.accept(visitor, data) }
        finallyBlock?.accept(visitor, data)
        super.acceptChildren(visitor, data)
    }
}