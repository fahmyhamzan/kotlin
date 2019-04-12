/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.generators.tests

import com.intellij.openapi.util.io.FileUtil
import java.io.File
import java.io.PrintWriter

object GeneratePrimitiveVsObjectEqualityTestData {
    private val TEST_DATA_DIR = File("compiler/testData/codegen/box/primitiveTypes/equalityWithObject")
    private val GENERATED_DIR = File(TEST_DATA_DIR, "generated")

    private val PREAMBLE_MESSAGE = "Auto-generated by ${this::class.java.simpleName}. Do not edit!"

    private fun generateBoxedVsPrimitiveTest(type: String, x: String, y: String) {
        PrintWriter(File(GENERATED_DIR, "boxedEqPrimitive$type.kt")).use {
            it.generateBoxedVsPrimitiveTestBody(type, x, y)
        }
    }

    private fun PrintWriter.generateBoxedVsPrimitiveTestBody(type: String, x: String, y: String) {
        println("// $PREAMBLE_MESSAGE")
        println()
        generateGlobalVals(type, x, y)
        println()
        println("fun box(): String {")
        generateLocalVals(type, x, y)
        println()

        println("    return when {")

        generateFailureClauses(
                *failuresForEqualAndUnequalLeft("nx", x, y),
                *failuresForEqualAndUnequalLeft("nx", "x", "y"),
                *failuresForUnequalLeft("nn", x),
                *failuresForUnequalLeft("nn", "x"),
                *failuresForEqualAndUnequalLeft("ax", x, y),
                *failuresForEqualAndUnequalLeft("ax", "x", "y"),
                *failuresForEqualAndUnequalLeft("ax", "bx", "by"),
                *failuresForUnequalLeft("an", x),
                *failuresForUnequalLeft("an", "x"),
                *failuresForUnequalLeft("an", "bx")
        )

        println("        else -> \"OK\"")
        println("    }")
        println("}")
    }

    private fun failuresForEqualAndUnequalLeft(lhs: String, equalRhs: String, unequalRhs: String) =
            arrayOf(
                    "$lhs != $equalRhs",
                    "$lhs == $unequalRhs",
                    "!($lhs == $equalRhs)",
                    "!($lhs != $unequalRhs)"
            )

    private fun failuresForUnequalLeft(lhs: String, unequalRhs: String) =
            arrayOf(
                    "$lhs == $unequalRhs",
                    "!($lhs != $unequalRhs)"
            )

    private fun PrintWriter.generateLocalVals(type: String, x: String, y: String, boxedType: String = "$type?") {
        println("    val ax: $boxedType = $x")
        println("    val an: $boxedType = null")
        println("    val bx: $type = $x")
        println("    val by: $type = $y")
    }

    private fun PrintWriter.generateGlobalVals(type: String, x: String, y: String, boxedType: String = "$type?") {
        println("val nx: $boxedType = $x")
        println("val nn: $boxedType = null")
        println("val x: $type = $x")
        println("val y: $type = $y")
    }

    private fun PrintWriter.generateFailureClauses(vararg failures: String) {
        failures.forEachIndexed { i, condition ->
            println("        $condition -> \"Fail $i\"")
        }
    }

    private fun generatePrimitiveVsBoxedTest(type: String, x: String, y: String) {
        PrintWriter(File(GENERATED_DIR, "primitiveEqBoxed$type.kt")).use {
            it.generatePrimitiveVsBoxedTestBody(type, x, y)
        }
    }

    private fun PrintWriter.generatePrimitiveVsBoxedTestBody(type: String, x: String, y: String) {
        println("// $PREAMBLE_MESSAGE")
        println()

        generateGlobalVals(type, x, y)
        println()
        println("fun box(): String {")
        generateLocalVals(type, x, y)
        println()

        println("    return when {")

        generateFailureClauses(
                *failuresForEqualAndUnequalRight(x, y, "nx"),
                *failuresForEqualAndUnequalRight("x", "y", "nx"),
                *failuresForUnequalRight(x, "nn"),
                *failuresForUnequalRight("x", "nn"),
                *failuresForEqualAndUnequalRight(x, y, "ax"),
                *failuresForEqualAndUnequalRight("x", "y", "ax"),
                *failuresForEqualAndUnequalRight("bx", "by", "ax"),
                *failuresForUnequalRight(x, "an"),
                *failuresForUnequalRight("x", "an"),
                *failuresForUnequalRight("bx", "an")
        )

        println("        else -> \"OK\"")
        println("    }")
        println("}")
    }

    private fun generatePrimitiveVsObjectTest(type: String, x: String, y: String, header: String = "") {
        PrintWriter(File(GENERATED_DIR, "primitiveEqObject$type.kt")).use {
            if (header.isNotBlank()) it.println(header)
            it.generatePrimitiveVsObjectTestBody(type, x, y)
        }
    }

    private fun PrintWriter.generatePrimitiveVsObjectTestBody(type: String, x: String, y: String) {
        println("// $PREAMBLE_MESSAGE")
        println()

        generateGlobalVals(type, x, y, boxedType = "Any?")
        println()
        println("fun box(): String {")
        generateLocalVals(type, x, y, boxedType = "Any?")
        println()

        println("    return when {")

        generateFailureClauses(
                *failuresForEqualAndUnequalRight(x, y, "nx"),
                *failuresForEqualAndUnequalRight("x", "y", "nx"),
                *failuresForUnequalRight(x, "nn"),
                *failuresForUnequalRight("x", "nn"),
                *failuresForEqualAndUnequalRight(x, y, "ax"),
                *failuresForEqualAndUnequalRight("x", "y", "ax"),
                *failuresForEqualAndUnequalRight("bx", "by", "ax"),
                *failuresForUnequalRight(x, "an"),
                *failuresForUnequalRight("x", "an"),
                *failuresForUnequalRight("bx", "an")
        )

        println("        else -> \"OK\"")
        println("    }")
        println("}")
    }

    private fun failuresForEqualAndUnequalRight(equalLhs: String, unequalLhs: String, rhs: String) =
            arrayOf(
                    "$equalLhs != $rhs",
                    "$unequalLhs == $rhs",
                    "!($equalLhs == $rhs)",
                    "!($unequalLhs != $rhs)"
            )

    private fun failuresForUnequalRight(unequalLhs: String, rhs: String) =
            arrayOf(
                    "$unequalLhs == $rhs",
                    "!($unequalLhs != $rhs)"
            )


    @JvmStatic
    fun main(args: Array<String>) {
        if (!TEST_DATA_DIR.exists()) throw AssertionError("${TEST_DATA_DIR.path} doesn't exist")

        FileUtil.delete(GENERATED_DIR)
        GENERATED_DIR.mkdirs()

        generateBoxedVsPrimitiveTest("Boolean", "true", "false")
        generateBoxedVsPrimitiveTest("Char", "'0'", "'1'")
        generateBoxedVsPrimitiveTest("Byte", "0.toByte()", "1.toByte()")
        generateBoxedVsPrimitiveTest("Short", "0.toShort()", "1.toShort()")
        generateBoxedVsPrimitiveTest("Int", "0", "1")
        generateBoxedVsPrimitiveTest("Long", "0L", "1L")

        generatePrimitiveVsBoxedTest("Boolean", "true", "false")
        generatePrimitiveVsBoxedTest("Char", "'0'", "'1'")
        generatePrimitiveVsBoxedTest("Byte", "0.toByte()", "1.toByte()")
        generatePrimitiveVsBoxedTest("Short", "0.toShort()", "1.toShort()")
        generatePrimitiveVsBoxedTest("Int", "0", "1")
        generatePrimitiveVsBoxedTest("Long", "0L", "1L")

        generatePrimitiveVsObjectTest("Boolean", "true", "false")
        generatePrimitiveVsObjectTest("Char", "'0'", "'1'", header = "// IGNORE_BACKEND: JS") // KT-19081
        generatePrimitiveVsObjectTest("Byte", "0.toByte()", "1.toByte()")
        generatePrimitiveVsObjectTest("Short", "0.toShort()", "1.toShort()")
        generatePrimitiveVsObjectTest("Int", "0", "1")
        generatePrimitiveVsObjectTest("Long", "0L", "1L")
    }
}