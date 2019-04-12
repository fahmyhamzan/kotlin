/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.uast.kotlin

import org.jetbrains.kotlin.psi.KtParenthesizedExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UParenthesizedExpression

class KotlinUParenthesizedExpression(
        override val psi: KtParenthesizedExpression,
        givenParent: UElement?
) : KotlinAbstractUExpression(givenParent), UParenthesizedExpression, KotlinUElementWithType {
    override val expression by lz { KotlinConverter.convertOrEmpty(psi.expression, this) }
}