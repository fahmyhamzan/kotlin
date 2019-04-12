/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.resolve;

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
@TestMetadata("idea/testData/resolve/referenceToJavaWithWrongFileStructure")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class ReferenceToJavaWithWrongFileStructureTestGenerated extends AbstractReferenceToJavaWithWrongFileStructureTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
    }

    public void testAllFilesPresentInReferenceToJavaWithWrongFileStructure() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/resolve/referenceToJavaWithWrongFileStructure"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, false);
    }

    @TestMetadata("ClassStatics.kt")
    public void testClassStatics() throws Exception {
        runTest("idea/testData/resolve/referenceToJavaWithWrongFileStructure/ClassStatics.kt");
    }

    @TestMetadata("SimpleClass.kt")
    public void testSimpleClass() throws Exception {
        runTest("idea/testData/resolve/referenceToJavaWithWrongFileStructure/SimpleClass.kt");
    }
}
