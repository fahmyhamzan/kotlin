/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.spec.validators

import org.jetbrains.kotlin.spec.models.AbstractSpecTest
import org.jetbrains.kotlin.spec.SpecTestLinkedType
import org.jetbrains.kotlin.spec.TestType
import org.jetbrains.kotlin.spec.parsers.CommonPatterns
import java.io.File

enum class SpecTestValidationFailedReason(val description: String) {
    FILENAME_NOT_VALID("Incorrect test filename or folder name."),
    TESTINFO_NOT_VALID("Test info is incorrect."),
    FILEPATH_AND_TESTINFO_IN_FILE_NOT_CONSISTENCY("Test info from filepath and file content is not consistency"),
    TEST_IS_NOT_POSITIVE("Test is not positive because it contains error elements (PsiErrorElement or diagnostic with error severity)."),
    TEST_IS_NOT_NEGATIVE("Test is not negative because it not contains error type elements (PsiErrorElement or diagnostic with error severity)."),
    INVALID_TEST_CASES_STRUCTURE(
        "All code in the test file must be divided and marked as a 'test case' label.${CommonPatterns.ls}Example:${CommonPatterns.ls.repeat(2)}// TESTCASE NUMBER: 1${CommonPatterns.ls}fun main() { println(\"Hello, Kotlin!\") }${CommonPatterns.ls.repeat(2)}"
    ),
    UNKNOWN_FRONTEND_EXCEPTION("Unknown frontend exception. Manual analysis is required."),
    UNMATCHED_FRONTEND_EXCEPTION("Unmatched frontend exception. Manual analysis is required."),
    UNKNOWN("Unknown validation error.")
}

class SpecTestValidationException(reason: SpecTestValidationFailedReason, details: String = "") : Exception() {
    val description = "${reason.description} $details"
}

abstract class AbstractTestValidator(private val testInfo: AbstractSpecTest, private val testDataFile: File) {
    fun validatePathConsistency(testLinkedType: SpecTestLinkedType) {
        val matcher = testLinkedType.patterns.value.testPathPattern.matcher(testDataFile.canonicalPath).apply { find() }

        if (!testInfo.checkPathConsistency(matcher))
            throw SpecTestValidationException(SpecTestValidationFailedReason.FILEPATH_AND_TESTINFO_IN_FILE_NOT_CONSISTENCY)
    }

    abstract fun computeTestTypes(): Map<Int, TestType>

    fun validateTestType() {
        val computedTestTypes = computeTestTypes()

        for ((caseNumber, case) in testInfo.cases.byNumbers) {
            val testType = computedTestTypes[caseNumber] ?: TestType.POSITIVE

            if (testType != testInfo.testType && !testInfo.unexpectedBehavior && !case.unexpectedBehavior) {
                val isNotNegative = testType == TestType.POSITIVE && testInfo.testType == TestType.NEGATIVE
                val isNotPositive = testType == TestType.NEGATIVE && testInfo.testType == TestType.POSITIVE
                val reason = when {
                    isNotNegative -> SpecTestValidationFailedReason.TEST_IS_NOT_NEGATIVE
                    isNotPositive -> SpecTestValidationFailedReason.TEST_IS_NOT_POSITIVE
                    else -> SpecTestValidationFailedReason.UNKNOWN
                }
                throw SpecTestValidationException(reason, details = "TESTCASE: $caseNumber")
            }
        }
    }
}
