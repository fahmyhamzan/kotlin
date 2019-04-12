/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.generators.builtins.arrays

import org.jetbrains.kotlin.generators.builtins.PrimitiveType
import org.jetbrains.kotlin.generators.builtins.generateBuiltIns.BuiltInsSourceGenerator
import java.io.PrintWriter

class GenerateArrays(out: PrintWriter) : BuiltInsSourceGenerator(out) {
    override fun getPackage() = "kotlin"

    override fun generateBody() {
        for (kind in PrimitiveType.values()) {
            val typeLower = kind.name.toLowerCase()
            val s = kind.capitalized
            val defaultValue = when (kind) {
                PrimitiveType.CHAR -> "null char (`\\u0000')"
                PrimitiveType.BOOLEAN -> "`false`"
                else -> "zero"
            }
            out.println("/**")
            out.println(" * An array of ${typeLower}s. When targeting the JVM, instances of this class are represented as `$typeLower[]`.")
            out.println(" * @constructor Creates a new array of the specified [size], with all elements initialized to $defaultValue.")
            out.println(" */")
            out.println("public class ${s}Array(size: Int) {")
            out.println("    /**")
            out.println("     * Creates a new array of the specified [size], where each element is calculated by calling the specified")
            out.println("     * [init] function. The [init] function returns an array element given its index.")
            out.println("     */")
            out.println("    public inline constructor(size: Int, init: (Int) -> $s)")
            out.println()
            out.println("    /**")
            out.println("     * Returns the array element at the given [index].  This method can be called using the index operator.")
            out.println("     *")
            out.println("     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except in Kotlin/JS")
            out.println("     * where the behavior is unspecified.")
            out.println("     */")
            out.println("    public operator fun get(index: Int): $s")
            out.println()
            out.println("    /**")
            out.println("     * Sets the element at the given [index] to the given [value]. This method can be called using the index operator.")
            out.println("     *")
            out.println("     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except in Kotlin/JS")
            out.println("     * where the behavior is unspecified.")
            out.println("     */")
            out.println("    public operator fun set(index: Int, value: $s): Unit")
            out.println()
            out.println("    /** Returns the number of elements in the array. */")
            out.println("    public val size: Int")
            out.println()
            out.println("    /** Creates an iterator over the elements of the array. */")
            out.println("    public operator fun iterator(): ${s}Iterator")
            out.println("}")
            out.println()
        }
    }
}
