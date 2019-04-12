/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.highlighter;

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
@TestMetadata("idea/testData/usageHighlighter")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class UsageHighlightingTestGenerated extends AbstractUsageHighlightingTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
    }

    public void testAllFilesPresentInUsageHighlighter() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/usageHighlighter"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
    }

    @TestMetadata("implicitIt.kt")
    public void testImplicitIt() throws Exception {
        runTest("idea/testData/usageHighlighter/implicitIt.kt");
    }

    @TestMetadata("implicitReturnExpressionsFromExplicitReturnInLambdas.kt")
    public void testImplicitReturnExpressionsFromExplicitReturnInLambdas() throws Exception {
        runTest("idea/testData/usageHighlighter/implicitReturnExpressionsFromExplicitReturnInLambdas.kt");
    }

    @TestMetadata("implicitReturnExpressionsInLambdasNoHightlighting.kt")
    public void testImplicitReturnExpressionsInLambdasNoHightlighting() throws Exception {
        runTest("idea/testData/usageHighlighter/implicitReturnExpressionsInLambdasNoHightlighting.kt");
    }

    @TestMetadata("importAlias.kt")
    public void testImportAlias() throws Exception {
        runTest("idea/testData/usageHighlighter/importAlias.kt");
    }

    @TestMetadata("labeledAnonymousFun.kt")
    public void testLabeledAnonymousFun() throws Exception {
        runTest("idea/testData/usageHighlighter/labeledAnonymousFun.kt");
    }

    @TestMetadata("labeledLambda.kt")
    public void testLabeledLambda() throws Exception {
        runTest("idea/testData/usageHighlighter/labeledLambda.kt");
    }

    @TestMetadata("labeledLoop.kt")
    public void testLabeledLoop() throws Exception {
        runTest("idea/testData/usageHighlighter/labeledLoop.kt");
    }

    @TestMetadata("lambdaCallReturnExpressions.kt")
    public void testLambdaCallReturnExpressions() throws Exception {
        runTest("idea/testData/usageHighlighter/lambdaCallReturnExpressions.kt");
    }

    @TestMetadata("lambdaCallReturnExpressionsInline.kt")
    public void testLambdaCallReturnExpressionsInline() throws Exception {
        runTest("idea/testData/usageHighlighter/lambdaCallReturnExpressionsInline.kt");
    }

    @TestMetadata("lambdaCallReturnExpressionsInlineUnit.kt")
    public void testLambdaCallReturnExpressionsInlineUnit() throws Exception {
        runTest("idea/testData/usageHighlighter/lambdaCallReturnExpressionsInlineUnit.kt");
    }

    @TestMetadata("localVal.kt")
    public void testLocalVal() throws Exception {
        runTest("idea/testData/usageHighlighter/localVal.kt");
    }
}
