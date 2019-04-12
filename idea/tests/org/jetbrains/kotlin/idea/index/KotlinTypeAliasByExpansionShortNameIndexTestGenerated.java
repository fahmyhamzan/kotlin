/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.index;

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
@TestMetadata("idea/testData/typealiasExpansionIndex")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class KotlinTypeAliasByExpansionShortNameIndexTestGenerated extends AbstractKotlinTypeAliasByExpansionShortNameIndexTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
    }

    public void testAllFilesPresentInTypealiasExpansionIndex() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/typealiasExpansionIndex"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
    }

    @TestMetadata("functionalTypes.kt")
    public void testFunctionalTypes() throws Exception {
        runTest("idea/testData/typealiasExpansionIndex/functionalTypes.kt");
    }

    @TestMetadata("generics.kt")
    public void testGenerics() throws Exception {
        runTest("idea/testData/typealiasExpansionIndex/generics.kt");
    }

    @TestMetadata("simpleType.kt")
    public void testSimpleType() throws Exception {
        runTest("idea/testData/typealiasExpansionIndex/simpleType.kt");
    }
}
