/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.j2k.ast

import org.jetbrains.kotlin.j2k.CodeBuilder

class NewClassExpression(
        val name: ReferenceElement?,
        val argumentList: ArgumentList,
        val qualifier: Expression = Expression.Empty,
        val anonymousClass: AnonymousClassBody? = null
) : Expression() {

    override fun generateCode(builder: CodeBuilder) {
        if (anonymousClass != null) {
            builder.append("object:")
        }

        if (!qualifier.isEmpty) {
            builder.append(qualifier).append(if (qualifier.isNullable) "!!." else ".")
        }

        if (name != null) {
            builder.append(name)
        }

        if (anonymousClass == null || !anonymousClass.extendsInterface) {
            builder.append(argumentList)
        }

        if (anonymousClass != null) {
            builder.append(anonymousClass)
        }
    }
}
