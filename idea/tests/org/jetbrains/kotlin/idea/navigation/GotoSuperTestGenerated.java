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
@TestMetadata("idea/testData/navigation/gotoSuper")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class GotoSuperTestGenerated extends AbstractGotoSuperTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
    }

    public void testAllFilesPresentInGotoSuper() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/navigation/gotoSuper"), Pattern.compile("^(.+)\\.test$"), TargetBackend.ANY, false);
    }

    @TestMetadata("BadPositionLambdaParameter.test")
    public void testBadPositionLambdaParameter() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/BadPositionLambdaParameter.test");
    }

    @TestMetadata("ClassSimple.test")
    public void testClassSimple() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/ClassSimple.test");
    }

    @TestMetadata("DelegatedFun.test")
    public void testDelegatedFun() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/DelegatedFun.test");
    }

    @TestMetadata("DelegatedProperty.test")
    public void testDelegatedProperty() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/DelegatedProperty.test");
    }

    @TestMetadata("FakeOverrideFun.test")
    public void testFakeOverrideFun() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/FakeOverrideFun.test");
    }

    @TestMetadata("FakeOverrideFunWithMostRelevantImplementation.test")
    public void testFakeOverrideFunWithMostRelevantImplementation() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/FakeOverrideFunWithMostRelevantImplementation.test");
    }

    @TestMetadata("FakeOverrideProperty.test")
    public void testFakeOverrideProperty() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/FakeOverrideProperty.test");
    }

    @TestMetadata("FunctionSimple.test")
    public void testFunctionSimple() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/FunctionSimple.test");
    }

    @TestMetadata("ObjectSimple.test")
    public void testObjectSimple() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/ObjectSimple.test");
    }

    @TestMetadata("PropertySimple.test")
    public void testPropertySimple() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/PropertySimple.test");
    }

    @TestMetadata("SuperWithNativeToGenericMapping.test")
    public void testSuperWithNativeToGenericMapping() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/SuperWithNativeToGenericMapping.test");
    }

    @TestMetadata("TraitSimple.test")
    public void testTraitSimple() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/TraitSimple.test");
    }

    @TestMetadata("TypeAliasInSuperType.test")
    public void testTypeAliasInSuperType() throws Exception {
        runTest("idea/testData/navigation/gotoSuper/TypeAliasInSuperType.test");
    }
}
