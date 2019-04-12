/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.expressions

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol

interface IrFieldAccessExpression : IrDeclarationReference {
    override val descriptor: PropertyDescriptor
    override val symbol: IrFieldSymbol

    val superQualifier: ClassDescriptor?
    val superQualifierSymbol: IrClassSymbol?

    var receiver: IrExpression?
    val origin: IrStatementOrigin?
}

interface IrGetField : IrFieldAccessExpression

interface IrSetField : IrFieldAccessExpression {
    var value: IrExpression
}
