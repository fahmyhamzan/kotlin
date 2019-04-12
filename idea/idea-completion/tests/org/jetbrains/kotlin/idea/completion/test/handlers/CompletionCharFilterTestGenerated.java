/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.completion.test.handlers;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TargetBackend;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/idea-completion/testData/handlers/charFilter")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class CompletionCharFilterTestGenerated extends AbstractCompletionCharFilterTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
    }

    public void testAllFilesPresentInCharFilter() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/idea-completion/testData/handlers/charFilter"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
    }

    @TestMetadata("Colon.kt")
    public void testColon() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/Colon.kt");
    }

    @TestMetadata("Comma1.kt")
    public void testComma1() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/Comma1.kt");
    }

    @TestMetadata("Comma2.kt")
    public void testComma2() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/Comma2.kt");
    }

    @TestMetadata("Comma3.kt")
    public void testComma3() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/Comma3.kt");
    }

    @TestMetadata("Comma4.kt")
    public void testComma4() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/Comma4.kt");
    }

    @TestMetadata("Comma5.kt")
    public void testComma5() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/Comma5.kt");
    }

    @TestMetadata("CommaForFunction1.kt")
    public void testCommaForFunction1() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/CommaForFunction1.kt");
    }

    @TestMetadata("CommaForFunction2.kt")
    public void testCommaForFunction2() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/CommaForFunction2.kt");
    }

    @TestMetadata("ConstructorWithLambdaArg1.kt")
    public void testConstructorWithLambdaArg1() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/ConstructorWithLambdaArg1.kt");
    }

    @TestMetadata("ConstructorWithLambdaArg2.kt")
    public void testConstructorWithLambdaArg2() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/ConstructorWithLambdaArg2.kt");
    }

    @TestMetadata("Dot.kt")
    public void testDot() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/Dot.kt");
    }

    @TestMetadata("DotAfterFun1.kt")
    public void testDotAfterFun1() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/DotAfterFun1.kt");
    }

    @TestMetadata("DotAfterFun2.kt")
    public void testDotAfterFun2() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/DotAfterFun2.kt");
    }

    @TestMetadata("Eq1.kt")
    public void testEq1() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/Eq1.kt");
    }

    @TestMetadata("Eq2.kt")
    public void testEq2() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/Eq2.kt");
    }

    @TestMetadata("FunctionLiteralParameter1.kt")
    public void testFunctionLiteralParameter1() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/FunctionLiteralParameter1.kt");
    }

    @TestMetadata("FunctionLiteralParameter2.kt")
    public void testFunctionLiteralParameter2() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/FunctionLiteralParameter2.kt");
    }

    @TestMetadata("FunctionLiteralParameter3.kt")
    public void testFunctionLiteralParameter3() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/FunctionLiteralParameter3.kt");
    }

    @TestMetadata("FunctionWithLambdaArg1.kt")
    public void testFunctionWithLambdaArg1() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/FunctionWithLambdaArg1.kt");
    }

    @TestMetadata("FunctionWithLambdaArg2.kt")
    public void testFunctionWithLambdaArg2() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/FunctionWithLambdaArg2.kt");
    }

    @TestMetadata("InfixCallAndSpace.kt")
    public void testInfixCallAndSpace() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/InfixCallAndSpace.kt");
    }

    @TestMetadata("KeywordAndSpace.kt")
    public void testKeywordAndSpace() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/KeywordAndSpace.kt");
    }

    @TestMetadata("LParenth.kt")
    public void testLParenth() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/LParenth.kt");
    }

    @TestMetadata("NamedParameter1.kt")
    public void testNamedParameter1() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/NamedParameter1.kt");
    }

    @TestMetadata("NamedParameter2.kt")
    public void testNamedParameter2() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/NamedParameter2.kt");
    }

    @TestMetadata("QualifiedThis.kt")
    public void testQualifiedThis() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/QualifiedThis.kt");
    }

    @TestMetadata("RangeTyping.kt")
    public void testRangeTyping() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/RangeTyping.kt");
    }

    @TestMetadata("Space.kt")
    public void testSpace() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/Space.kt");
    }

    @TestMetadata("VariableName.kt")
    public void testVariableName() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/VariableName.kt");
    }

    @TestMetadata("VariableName2.kt")
    public void testVariableName2() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/VariableName2.kt");
    }

    @TestMetadata("VariableName3.kt")
    public void testVariableName3() throws Exception {
        runTest("idea/idea-completion/testData/handlers/charFilter/VariableName3.kt");
    }
}
