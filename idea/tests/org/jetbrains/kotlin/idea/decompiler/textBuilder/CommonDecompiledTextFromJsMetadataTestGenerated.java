/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.decompiler.textBuilder;

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
@TestMetadata("idea/testData/decompiler/decompiledText")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class CommonDecompiledTextFromJsMetadataTestGenerated extends AbstractCommonDecompiledTextFromJsMetadataTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
    }

    public void testAllFilesPresentInDecompiledText() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
    }

    @TestMetadata("AnnotatedEnumEntry")
    public void testAnnotatedEnumEntry() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/AnnotatedEnumEntry/");
    }

    @TestMetadata("AnnotatedParameterInEnumConstructor")
    public void testAnnotatedParameterInEnumConstructor() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/AnnotatedParameterInEnumConstructor/");
    }

    @TestMetadata("AnnotatedParameterInInnerClassConstructor")
    public void testAnnotatedParameterInInnerClassConstructor() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/AnnotatedParameterInInnerClassConstructor/");
    }

    @TestMetadata("Annotations")
    public void testAnnotations() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/Annotations/");
    }

    @TestMetadata("AnnotationsOnPrimaryCtr")
    public void testAnnotationsOnPrimaryCtr() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/AnnotationsOnPrimaryCtr/");
    }

    @TestMetadata("ClassWithClassObject")
    public void testClassWithClassObject() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/ClassWithClassObject/");
    }

    @TestMetadata("ClassWithNamedClassObject")
    public void testClassWithNamedClassObject() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/ClassWithNamedClassObject/");
    }

    @TestMetadata("Const")
    public void testConst() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/Const/");
    }

    @TestMetadata("DependencyOnNestedClasses")
    public void testDependencyOnNestedClasses() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/DependencyOnNestedClasses/");
    }

    @TestMetadata("Enum")
    public void testEnum() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/Enum/");
    }

    @TestMetadata("FunctionTypes")
    public void testFunctionTypes() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/FunctionTypes/");
    }

    @TestMetadata("FunctionalTypeWithNamedArguments")
    public void testFunctionalTypeWithNamedArguments() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/FunctionalTypeWithNamedArguments/");
    }

    @TestMetadata("Inherited")
    public void testInherited() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/Inherited/");
    }

    @TestMetadata("InnerClasses")
    public void testInnerClasses() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/InnerClasses/");
    }

    @TestMetadata("LocalClassAsTypeWithArgument")
    public void testLocalClassAsTypeWithArgument() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/LocalClassAsTypeWithArgument/");
    }

    @TestMetadata("Modifiers")
    public void testModifiers() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/Modifiers/");
    }

    @TestMetadata("NestedClasses")
    public void testNestedClasses() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/NestedClasses/");
    }

    @TestMetadata("Object")
    public void testObject() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/Object/");
    }

    @TestMetadata("SecondaryConstructors")
    public void testSecondaryConstructors() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/SecondaryConstructors/");
    }

    @TestMetadata("SimpleClass")
    public void testSimpleClass() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/SimpleClass/");
    }

    @TestMetadata("TypeModifiers")
    public void testTypeModifiers() throws Exception {
        runTest("idea/testData/decompiler/decompiledText/TypeModifiers/");
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/AnnotatedEnumEntry")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class AnnotatedEnumEntry extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInAnnotatedEnumEntry() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/AnnotatedEnumEntry"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/AnnotatedParameterInEnumConstructor")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class AnnotatedParameterInEnumConstructor extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInAnnotatedParameterInEnumConstructor() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/AnnotatedParameterInEnumConstructor"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/AnnotatedParameterInInnerClassConstructor")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class AnnotatedParameterInInnerClassConstructor extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInAnnotatedParameterInInnerClassConstructor() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/AnnotatedParameterInInnerClassConstructor"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/Annotations")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class Annotations extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInAnnotations() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/Annotations"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/AnnotationsOnPrimaryCtr")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class AnnotationsOnPrimaryCtr extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInAnnotationsOnPrimaryCtr() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/AnnotationsOnPrimaryCtr"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/ClassWithClassObject")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class ClassWithClassObject extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInClassWithClassObject() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/ClassWithClassObject"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/ClassWithNamedClassObject")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class ClassWithNamedClassObject extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInClassWithNamedClassObject() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/ClassWithNamedClassObject"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/Const")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class Const extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInConst() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/Const"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/DependencyOnNestedClasses")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class DependencyOnNestedClasses extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInDependencyOnNestedClasses() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/DependencyOnNestedClasses"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/Enum")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class Enum extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInEnum() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/Enum"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/FlexibleTypes")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class FlexibleTypes extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInFlexibleTypes() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/FlexibleTypes"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/FunctionTypes")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class FunctionTypes extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInFunctionTypes() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/FunctionTypes"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/FunctionalTypeWithNamedArguments")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class FunctionalTypeWithNamedArguments extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInFunctionalTypeWithNamedArguments() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/FunctionalTypeWithNamedArguments"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/Inherited")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class Inherited extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInInherited() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/Inherited"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/InnerClasses")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class InnerClasses extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInInnerClasses() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/InnerClasses"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/LocalClassAsTypeWithArgument")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class LocalClassAsTypeWithArgument extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInLocalClassAsTypeWithArgument() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/LocalClassAsTypeWithArgument"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/Modifiers")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class Modifiers extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInModifiers() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/Modifiers"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/NestedClasses")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class NestedClasses extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInNestedClasses() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/NestedClasses"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/Object")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class Object extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInObject() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/Object"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/SecondaryConstructors")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class SecondaryConstructors extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInSecondaryConstructors() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/SecondaryConstructors"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/SimpleClass")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class SimpleClass extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInSimpleClass() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/SimpleClass"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }

    @TestMetadata("idea/testData/decompiler/decompiledText/TypeModifiers")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class TypeModifiers extends AbstractCommonDecompiledTextFromJsMetadataTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.JS, testDataFilePath);
        }

        public void testAllFilesPresentInTypeModifiers() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/decompiledText/TypeModifiers"), Pattern.compile("^([^\\.]+)$"), TargetBackend.JS, true);
        }
    }
}
