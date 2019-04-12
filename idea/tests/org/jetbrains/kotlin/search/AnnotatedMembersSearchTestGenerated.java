/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.search;

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
@TestMetadata("idea/testData/search/annotations")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class AnnotatedMembersSearchTestGenerated extends AbstractAnnotatedMembersSearchTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
    }

    public void testAllFilesPresentInAnnotations() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/search/annotations"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
    }

    @TestMetadata("annotationAliased.kt")
    public void testAnnotationAliased() throws Exception {
        runTest("idea/testData/search/annotations/annotationAliased.kt");
    }

    @TestMetadata("testAmbiguousNestedNonAnnotationClass.kt")
    public void testTestAmbiguousNestedNonAnnotationClass() throws Exception {
        runTest("idea/testData/search/annotations/testAmbiguousNestedNonAnnotationClass.kt");
    }

    @TestMetadata("testAmbiguousNestedPrivateAnnotationClass.kt")
    public void testTestAmbiguousNestedPrivateAnnotationClass() throws Exception {
        runTest("idea/testData/search/annotations/testAmbiguousNestedPrivateAnnotationClass.kt");
    }

    @TestMetadata("testAnnotationsOnClass.kt")
    public void testTestAnnotationsOnClass() throws Exception {
        runTest("idea/testData/search/annotations/testAnnotationsOnClass.kt");
    }

    @TestMetadata("testAnnotationsOnFunction.kt")
    public void testTestAnnotationsOnFunction() throws Exception {
        runTest("idea/testData/search/annotations/testAnnotationsOnFunction.kt");
    }

    @TestMetadata("testAnnotationsOnPropertiesAndParameters.kt")
    public void testTestAnnotationsOnPropertiesAndParameters() throws Exception {
        runTest("idea/testData/search/annotations/testAnnotationsOnPropertiesAndParameters.kt");
    }

    @TestMetadata("testAnnotationsWithParameters.kt")
    public void testTestAnnotationsWithParameters() throws Exception {
        runTest("idea/testData/search/annotations/testAnnotationsWithParameters.kt");
    }

    @TestMetadata("testDefaultImport.kt")
    public void testTestDefaultImport() throws Exception {
        runTest("idea/testData/search/annotations/testDefaultImport.kt");
    }

    @TestMetadata("testNestedClassAsAnnotation.kt")
    public void testTestNestedClassAsAnnotation() throws Exception {
        runTest("idea/testData/search/annotations/testNestedClassAsAnnotation.kt");
    }

    @TestMetadata("testNestedPrivateAnnotationClass.kt")
    public void testTestNestedPrivateAnnotationClass() throws Exception {
        runTest("idea/testData/search/annotations/testNestedPrivateAnnotationClass.kt");
    }

    @TestMetadata("testTypeAlias.kt")
    public void testTestTypeAlias() throws Exception {
        runTest("idea/testData/search/annotations/testTypeAlias.kt");
    }
}
