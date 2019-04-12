/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.uast.kotlin

import org.jetbrains.uast.UastBinaryExpressionWithTypeKind

object KotlinBinaryExpressionWithTypeKinds {
    @JvmField
    val NEGATED_INSTANCE_CHECK = UastBinaryExpressionWithTypeKind.InstanceCheck("!is")

    @JvmField
    val SAFE_TYPE_CAST = UastBinaryExpressionWithTypeKind.TypeCast("as?")
}