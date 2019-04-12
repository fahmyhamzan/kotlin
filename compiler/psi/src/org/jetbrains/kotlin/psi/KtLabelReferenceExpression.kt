/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.psi

import com.intellij.lang.ASTNode

class KtLabelReferenceExpression(node: ASTNode) : KtSimpleNameExpressionImpl(node) {
    override fun getReferencedNameElement() = getIdentifier() ?: this
}
