/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */
package org.jetbrains.kotlin.idea.debugger.sequence.trace.dsl

import com.intellij.debugger.streams.trace.dsl.Expression
import com.intellij.debugger.streams.trace.dsl.ForLoopBody
import com.intellij.debugger.streams.trace.dsl.StatementFactory
import com.intellij.debugger.streams.trace.dsl.Variable
import com.intellij.debugger.streams.trace.dsl.impl.TextExpression

class KotlinForLoopBody(
    override val loopVariable: Variable,
    statementFactory: StatementFactory
) : KotlinCodeBlock(statementFactory), ForLoopBody {
    private companion object {
        val BREAK = TextExpression("break")
    }

    override fun breakIteration(): Expression = BREAK
}