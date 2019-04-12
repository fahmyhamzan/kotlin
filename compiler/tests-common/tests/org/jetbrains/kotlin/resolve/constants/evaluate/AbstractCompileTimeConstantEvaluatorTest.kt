/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.resolve.constants.evaluate

import com.intellij.openapi.util.io.FileUtil
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl
import org.jetbrains.kotlin.descriptors.VariableDescriptor
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils
import org.jetbrains.kotlin.resolve.annotation.AbstractAnnotationDescriptorResolveTest
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant
import org.jetbrains.kotlin.resolve.constants.StringValue
import org.jetbrains.kotlin.resolve.descriptorUtil.module
import org.jetbrains.kotlin.test.InTextDirectivesUtils
import org.jetbrains.kotlin.test.KotlinTestUtils
import java.io.File
import java.util.regex.Pattern

abstract class AbstractCompileTimeConstantEvaluatorTest : AbstractAnnotationDescriptorResolveTest() {

    // Test directives should look like [// val testedPropertyName: expectedValue]
    fun doConstantTest(path: String) {
        doTest(path) {
            property, _ ->
            val compileTimeConstant = property.compileTimeInitializer
            if (compileTimeConstant is StringValue) {
                "\\\"${compileTimeConstant.value}\\\""
            } else {
                "$compileTimeConstant"
            }
        }
    }

    // Test directives should look like [// val testedPropertyName: expectedValue]
    fun doIsPureTest(path: String) {
        doTest(path) {
            property, context ->
            evaluateInitializer(context, property)?.isPure.toString()
        }
    }

    // Test directives should look like [// val testedPropertyName: expectedValue]
    fun doUsesVariableAsConstantTest(path: String) {
        doTest(path) {
            property, context ->
            evaluateInitializer(context, property)?.usesVariableAsConstant.toString()
        }
    }

    private fun evaluateInitializer(context: BindingContext, property: VariableDescriptor): CompileTimeConstant<*>? {
        val propertyDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(property) as KtProperty
        return ConstantExpressionEvaluator(property.module, LanguageVersionSettingsImpl.DEFAULT, project).evaluateExpression(
                propertyDeclaration.initializer!!,
                DelegatingBindingTrace(context, "trace for evaluating compile time constant"),
                property.type
        )
    }

    private fun doTest(path: String, getValueToTest: (VariableDescriptor, BindingContext) -> String) {
        val myFile = File(path)
        val fileText = FileUtil.loadFile(myFile, true)
        val packageView = getPackage(fileText)

        val propertiesForTest = getObjectsToTest(fileText)

        val expectedActual = arrayListOf<Pair<String, String>>()

        for (propertyName in propertiesForTest) {
            val expectedPropertyPrefix = "// val ${propertyName}: "
            val expected = InTextDirectivesUtils.findStringWithPrefixes(fileText, expectedPropertyPrefix)
            assertNotNull(expected, "Failed to find expected directive: $expectedPropertyPrefix")

            val property = AbstractAnnotationDescriptorResolveTest.getPropertyDescriptor(packageView, propertyName, false)
            ?: AbstractAnnotationDescriptorResolveTest.getLocalVarDescriptor(context!!, propertyName)

            val testedObject = getValueToTest(property, context!!)
            expectedActual.add(expectedPropertyPrefix + expected!! to expectedPropertyPrefix + testedObject)
        }

        var actualFileText = fileText
        for ((expected, actual) in expectedActual) {
            assert(actualFileText.contains(expected)) { "File text should contain $expected" }
            actualFileText = actualFileText.replace(expected, actual)
        }

        KotlinTestUtils.assertEqualsToFile(myFile, actualFileText)
    }

    fun getObjectsToTest(fileText: String): List<String> {
        return InTextDirectivesUtils.findListWithPrefixes(fileText, "// val").map {
            val matcher = pattern.matcher(it)
            if (matcher.find()) {
                matcher.group(0) ?: "Couldn't match tested object $it"
            } else "Couldn't match tested object $it"
        }
    }

    companion object {
        val pattern = Pattern.compile(".+(?=:)")
    }
}
