/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.generators.builtins.progressions

import org.jetbrains.kotlin.generators.builtins.*
import org.jetbrains.kotlin.generators.builtins.generateBuiltIns.*
import org.jetbrains.kotlin.generators.builtins.ProgressionKind.*
import java.io.PrintWriter

class GenerateProgressions(out: PrintWriter) : BuiltInsSourceGenerator(out) {

    override fun getPackage() = "kotlin.ranges"
    private fun generateDiscreteBody(kind: ProgressionKind) {
        val t = kind.capitalized
        val progression = "${t}Progression"

        val incrementType = progressionIncrementType(kind)
        fun compare(v: String) = areEqualNumbers(v)

        val zero = when (kind) {
            LONG -> "0L"
            else -> "0"
        }
        val checkZero = """if (step == $zero) throw kotlin.IllegalArgumentException("Step must be non-zero.")"""

        val stepMinValue = "$incrementType.MIN_VALUE"
        val checkMin = """if (step == $stepMinValue) throw kotlin.IllegalArgumentException("Step must be greater than $stepMinValue to avoid overflow on negation.")"""

        val hashCode = "=\n" + when (kind) {
            CHAR ->
                "        if (isEmpty()) -1 else (31 * (31 * first.toInt() + last.toInt()) + step)"
            INT ->
                "        if (isEmpty()) -1 else (31 * (31 * first + last) + step)"
            LONG ->
                "        if (isEmpty()) -1 else (31 * (31 * ${hashLong("first")} + ${hashLong("last")}) + ${hashLong("step")}).toInt()"
            else -> throw IllegalArgumentException()
        }

        out.println(
                """/**
 * A progression of values of type `$t`.
 */
public open class $progression
    internal constructor
    (
            start: $t,
            endInclusive: $t,
            step: $incrementType
    ) : Iterable<$t> {
    init {
        $checkZero
        $checkMin
    }

    /**
     * The first element in the progression.
     */
    public val first: $t = start

    /**
     * The last element in the progression.
     */
    public val last: $t = getProgressionLastElement(start.to$incrementType(), endInclusive.to$incrementType(), step).to$t()

    /**
     * The step of the progression.
     */
    public val step: $incrementType = step

    override fun iterator(): ${t}Iterator = ${t}ProgressionIterator(first, last, step)

    /** Checks if the progression is empty. */
    public open fun isEmpty(): Boolean = if (step > 0) first > last else first < last

    override fun equals(other: Any?): Boolean =
        other is $progression && (isEmpty() && other.isEmpty() ||
        ${compare("first")} && ${compare("last")} && ${compare("step")})

    override fun hashCode(): Int $hashCode

    override fun toString(): String = ${"if (step > 0) \"\$first..\$last step \$step\" else \"\$first downTo \$last step \${-step}\""}

    companion object {
        /**
         * Creates $progression within the specified bounds of a closed range.

         * The progression starts with the [rangeStart] value and goes toward the [rangeEnd] value not excluding it, with the specified [step].
         * In order to go backwards the [step] must be negative.
         *
         * [step] must be greater than `$stepMinValue` and not equal to zero.
         */
        public fun fromClosedRange(rangeStart: $t, rangeEnd: $t, step: $incrementType): $progression = $progression(rangeStart, rangeEnd, step)
    }
}""")
        out.println()

    }

    override fun generateBody() {
        out.println("import kotlin.internal.getProgressionLastElement")
        out.println()
        for (kind in ProgressionKind.values()) {
            generateDiscreteBody(kind)
        }
    }
}
