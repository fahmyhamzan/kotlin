/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.navigation;

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
@TestMetadata("idea/testData/navigation/gotoSuper/multiModule")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class KotlinGotoSuperMultiModuleTestGenerated extends AbstractKotlinGotoSuperMultiModuleTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
    }

    @TestMetadata("actualClass")
    public void testActualClass() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/multiModule/actualClass/");
    }

    @TestMetadata("actualFunction")
    public void testActualFunction() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/multiModule/actualFunction/");
    }

    @TestMetadata("actualProperty")
    public void testActualProperty() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/multiModule/actualProperty/");
    }

    public void testAllFilesPresentInMultiModule() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/navigation/gotoSuper/multiModule"), Pattern.compile("^([^\\.]+)$"), TargetBackend.ANY, false);
    }
}
