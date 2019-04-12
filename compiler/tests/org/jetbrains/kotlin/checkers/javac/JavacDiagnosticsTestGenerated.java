/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.checkers.javac;

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
@RunWith(JUnit3RunnerWithInners.class)
public class JavacDiagnosticsTestGenerated extends AbstractJavacDiagnosticsTest {
    @TestMetadata("compiler/testData/javac/diagnostics/tests")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class Tests extends AbstractJavacDiagnosticsTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
        }

        public void testAllFilesPresentInTests() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
        }

        @TestMetadata("Annotations.kt")
        public void testAnnotations() throws Exception {
            runTest("compiler/testData/javac/diagnostics/tests/Annotations.kt");
        }

        @TestMetadata("compiler/testData/javac/diagnostics/tests/imports")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class Imports extends AbstractJavacDiagnosticsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInImports() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests/imports"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("AllUnderImportsAmbiguity.kt")
            public void testAllUnderImportsAmbiguity() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/AllUnderImportsAmbiguity.kt");
            }

            @TestMetadata("AllUnderImportsLessPriority.kt")
            public void testAllUnderImportsLessPriority() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/AllUnderImportsLessPriority.kt");
            }

            @TestMetadata("ClassImportsConflicting.kt")
            public void testClassImportsConflicting() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/ClassImportsConflicting.kt");
            }

            @TestMetadata("CurrentPackageAndAllUnderImport.kt")
            public void testCurrentPackageAndAllUnderImport() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/CurrentPackageAndAllUnderImport.kt");
            }

            @TestMetadata("CurrentPackageAndExplicitImport.kt")
            public void testCurrentPackageAndExplicitImport() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/CurrentPackageAndExplicitImport.kt");
            }

            @TestMetadata("CurrentPackageAndExplicitNestedImport.kt")
            public void testCurrentPackageAndExplicitNestedImport() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/CurrentPackageAndExplicitNestedImport.kt");
            }

            @TestMetadata("CurrentPackageAndNestedAsteriskImport.kt")
            public void testCurrentPackageAndNestedAsteriskImport() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/CurrentPackageAndNestedAsteriskImport.kt");
            }

            @TestMetadata("ImportGenericVsPackage.kt")
            public void testImportGenericVsPackage() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/ImportGenericVsPackage.kt");
            }

            @TestMetadata("ImportProtectedClass.kt")
            public void testImportProtectedClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/ImportProtectedClass.kt");
            }

            @TestMetadata("ImportTwoTimes.kt")
            public void testImportTwoTimes() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/ImportTwoTimes.kt");
            }

            @TestMetadata("ImportTwoTimesStar.kt")
            public void testImportTwoTimesStar() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/ImportTwoTimesStar.kt");
            }

            @TestMetadata("NestedAndTopLevelClassClash.kt")
            public void testNestedAndTopLevelClassClash() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/NestedAndTopLevelClassClash.kt");
            }

            @TestMetadata("NestedClassClash.kt")
            public void testNestedClassClash() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/NestedClassClash.kt");
            }

            @TestMetadata("PackageExplicitAndStartImport.kt")
            public void testPackageExplicitAndStartImport() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/PackageExplicitAndStartImport.kt");
            }

            @TestMetadata("PackagePrivateAndPublicNested.kt")
            public void testPackagePrivateAndPublicNested() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/PackagePrivateAndPublicNested.kt");
            }

            @TestMetadata("TopLevelClassVsPackage.kt")
            public void testTopLevelClassVsPackage() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/TopLevelClassVsPackage.kt");
            }

            @TestMetadata("TopLevelClassVsPackage2.kt")
            public void testTopLevelClassVsPackage2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/TopLevelClassVsPackage2.kt");
            }
        }

        @TestMetadata("compiler/testData/javac/diagnostics/tests/inheritance")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class Inheritance extends AbstractJavacDiagnosticsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInInheritance() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests/inheritance"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("IheritanceOfInner.kt")
            public void testIheritanceOfInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/IheritanceOfInner.kt");
            }

            @TestMetadata("InheritanceAmbiguity.kt")
            public void testInheritanceAmbiguity() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceAmbiguity.kt");
            }

            @TestMetadata("InheritanceAmbiguity2.kt")
            public void testInheritanceAmbiguity2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceAmbiguity2.kt");
            }

            @TestMetadata("InheritanceAmbiguity3.kt")
            public void testInheritanceAmbiguity3() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceAmbiguity3.kt");
            }

            @TestMetadata("InheritanceAmbiguity4.kt")
            public void testInheritanceAmbiguity4() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceAmbiguity4.kt");
            }

            @TestMetadata("InheritanceWithKotlin.kt")
            public void testInheritanceWithKotlin() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceWithKotlin.kt");
            }

            @TestMetadata("InheritanceWithKotlinClasses.kt")
            public void testInheritanceWithKotlinClasses() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceWithKotlinClasses.kt");
            }

            @TestMetadata("InheritedInner.kt")
            public void testInheritedInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritedInner.kt");
            }

            @TestMetadata("InheritedInner2.kt")
            public void testInheritedInner2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritedInner2.kt");
            }

            @TestMetadata("InheritedInnerAndSupertypeWithSameName.kt")
            public void testInheritedInnerAndSupertypeWithSameName() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritedInnerAndSupertypeWithSameName.kt");
            }

            @TestMetadata("InheritedInnerUsageInInner.kt")
            public void testInheritedInnerUsageInInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritedInnerUsageInInner.kt");
            }

            @TestMetadata("InheritedKotlinInner.kt")
            public void testInheritedKotlinInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritedKotlinInner.kt");
            }

            @TestMetadata("InnerAndInheritedInner.kt")
            public void testInnerAndInheritedInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InnerAndInheritedInner.kt");
            }

            @TestMetadata("ManyInheritedClasses.kt")
            public void testManyInheritedClasses() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/ManyInheritedClasses.kt");
            }

            @TestMetadata("NoAmbiguity.kt")
            public void testNoAmbiguity() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/NoAmbiguity.kt");
            }

            @TestMetadata("NoAmbiguity2.kt")
            public void testNoAmbiguity2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/NoAmbiguity2.kt");
            }

            @TestMetadata("SameInnersInSupertypeAndSupertypesSupertype.kt")
            public void testSameInnersInSupertypeAndSupertypesSupertype() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/SameInnersInSupertypeAndSupertypesSupertype.kt");
            }

            @TestMetadata("SuperTypeWithSameInner.kt")
            public void testSuperTypeWithSameInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/SuperTypeWithSameInner.kt");
            }

            @TestMetadata("SupertypeInnerAndTypeParameterWithSameNames.kt")
            public void testSupertypeInnerAndTypeParameterWithSameNames() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/SupertypeInnerAndTypeParameterWithSameNames.kt");
            }
        }

        @TestMetadata("compiler/testData/javac/diagnostics/tests/inners")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class Inners extends AbstractJavacDiagnosticsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInInners() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests/inners"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("ComplexCase.kt")
            public void testComplexCase() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/ComplexCase.kt");
            }

            @TestMetadata("ComplexCase2.kt")
            public void testComplexCase2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/ComplexCase2.kt");
            }

            @TestMetadata("CurrentPackageAndInner.kt")
            public void testCurrentPackageAndInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/CurrentPackageAndInner.kt");
            }

            @TestMetadata("ImportThriceNestedClass.kt")
            public void testImportThriceNestedClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/ImportThriceNestedClass.kt");
            }

            @TestMetadata("InnerInInner.kt")
            public void testInnerInInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/InnerInInner.kt");
            }

            @TestMetadata("Nested.kt")
            public void testNested() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/Nested.kt");
            }

            @TestMetadata("ThriceNestedClass.kt")
            public void testThriceNestedClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/ThriceNestedClass.kt");
            }
        }

        @TestMetadata("compiler/testData/javac/diagnostics/tests/qualifiedExpression")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class QualifiedExpression extends AbstractJavacDiagnosticsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInQualifiedExpression() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests/qualifiedExpression"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("GenericClassVsPackage.kt")
            public void testGenericClassVsPackage() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/qualifiedExpression/GenericClassVsPackage.kt");
            }

            @TestMetadata("PackageVsClass.kt")
            public void testPackageVsClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/qualifiedExpression/PackageVsClass.kt");
            }

            @TestMetadata("PackageVsClass2.kt")
            public void testPackageVsClass2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/qualifiedExpression/PackageVsClass2.kt");
            }

            @TestMetadata("PackageVsRootClass.kt")
            public void testPackageVsRootClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/qualifiedExpression/PackageVsRootClass.kt");
            }

            @TestMetadata("visibleClassVsQualifiedClass.kt")
            public void testVisibleClassVsQualifiedClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/qualifiedExpression/visibleClassVsQualifiedClass.kt");
            }
        }

        @TestMetadata("compiler/testData/javac/diagnostics/tests/typeParameters")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class TypeParameters extends AbstractJavacDiagnosticsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInTypeParameters() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests/typeParameters"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("Clash.kt")
            public void testClash() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/Clash.kt");
            }

            @TestMetadata("ComplexCase.kt")
            public void testComplexCase() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/ComplexCase.kt");
            }

            @TestMetadata("InheritedInnerAndTypeParameterWithSameNames.kt")
            public void testInheritedInnerAndTypeParameterWithSameNames() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/InheritedInnerAndTypeParameterWithSameNames.kt");
            }

            @TestMetadata("InnerWithTypeParameter.kt")
            public void testInnerWithTypeParameter() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/InnerWithTypeParameter.kt");
            }

            @TestMetadata("NestedWithInner.kt")
            public void testNestedWithInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/NestedWithInner.kt");
            }

            @TestMetadata("SeveralInnersWithTypeParameters.kt")
            public void testSeveralInnersWithTypeParameters() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/SeveralInnersWithTypeParameters.kt");
            }

            @TestMetadata("TypeParametersInInnerAndOuterWithSameNames.kt")
            public void testTypeParametersInInnerAndOuterWithSameNames() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/TypeParametersInInnerAndOuterWithSameNames.kt");
            }
        }
    }

    @TestMetadata("compiler/testData/javac/diagnostics/tests")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class TestsWithoutJavac extends AbstractJavacDiagnosticsTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTestWithoutJavacWrapper, TargetBackend.ANY, testDataFilePath);
        }

        public void testAllFilesPresentInTestsWithoutJavac() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
        }

        @TestMetadata("Annotations.kt")
        public void testAnnotations() throws Exception {
            runTest("compiler/testData/javac/diagnostics/tests/Annotations.kt");
        }

        @TestMetadata("compiler/testData/javac/diagnostics/tests/imports")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class Imports extends AbstractJavacDiagnosticsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTestWithoutJavacWrapper, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInImports() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests/imports"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("AllUnderImportsAmbiguity.kt")
            public void testAllUnderImportsAmbiguity() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/AllUnderImportsAmbiguity.kt");
            }

            @TestMetadata("AllUnderImportsLessPriority.kt")
            public void testAllUnderImportsLessPriority() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/AllUnderImportsLessPriority.kt");
            }

            @TestMetadata("ClassImportsConflicting.kt")
            public void testClassImportsConflicting() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/ClassImportsConflicting.kt");
            }

            @TestMetadata("CurrentPackageAndAllUnderImport.kt")
            public void testCurrentPackageAndAllUnderImport() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/CurrentPackageAndAllUnderImport.kt");
            }

            @TestMetadata("CurrentPackageAndExplicitImport.kt")
            public void testCurrentPackageAndExplicitImport() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/CurrentPackageAndExplicitImport.kt");
            }

            @TestMetadata("CurrentPackageAndExplicitNestedImport.kt")
            public void testCurrentPackageAndExplicitNestedImport() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/CurrentPackageAndExplicitNestedImport.kt");
            }

            @TestMetadata("CurrentPackageAndNestedAsteriskImport.kt")
            public void testCurrentPackageAndNestedAsteriskImport() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/CurrentPackageAndNestedAsteriskImport.kt");
            }

            @TestMetadata("ImportGenericVsPackage.kt")
            public void testImportGenericVsPackage() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/ImportGenericVsPackage.kt");
            }

            @TestMetadata("ImportProtectedClass.kt")
            public void testImportProtectedClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/ImportProtectedClass.kt");
            }

            @TestMetadata("ImportTwoTimes.kt")
            public void testImportTwoTimes() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/ImportTwoTimes.kt");
            }

            @TestMetadata("ImportTwoTimesStar.kt")
            public void testImportTwoTimesStar() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/ImportTwoTimesStar.kt");
            }

            @TestMetadata("NestedAndTopLevelClassClash.kt")
            public void testNestedAndTopLevelClassClash() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/NestedAndTopLevelClassClash.kt");
            }

            @TestMetadata("NestedClassClash.kt")
            public void testNestedClassClash() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/NestedClassClash.kt");
            }

            @TestMetadata("PackageExplicitAndStartImport.kt")
            public void testPackageExplicitAndStartImport() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/PackageExplicitAndStartImport.kt");
            }

            @TestMetadata("PackagePrivateAndPublicNested.kt")
            public void testPackagePrivateAndPublicNested() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/PackagePrivateAndPublicNested.kt");
            }

            @TestMetadata("TopLevelClassVsPackage.kt")
            public void testTopLevelClassVsPackage() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/TopLevelClassVsPackage.kt");
            }

            @TestMetadata("TopLevelClassVsPackage2.kt")
            public void testTopLevelClassVsPackage2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/imports/TopLevelClassVsPackage2.kt");
            }
        }

        @TestMetadata("compiler/testData/javac/diagnostics/tests/inheritance")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class Inheritance extends AbstractJavacDiagnosticsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTestWithoutJavacWrapper, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInInheritance() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests/inheritance"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("IheritanceOfInner.kt")
            public void testIheritanceOfInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/IheritanceOfInner.kt");
            }

            @TestMetadata("InheritanceAmbiguity.kt")
            public void testInheritanceAmbiguity() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceAmbiguity.kt");
            }

            @TestMetadata("InheritanceAmbiguity2.kt")
            public void testInheritanceAmbiguity2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceAmbiguity2.kt");
            }

            @TestMetadata("InheritanceAmbiguity3.kt")
            public void testInheritanceAmbiguity3() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceAmbiguity3.kt");
            }

            @TestMetadata("InheritanceAmbiguity4.kt")
            public void testInheritanceAmbiguity4() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceAmbiguity4.kt");
            }

            @TestMetadata("InheritanceWithKotlin.kt")
            public void testInheritanceWithKotlin() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceWithKotlin.kt");
            }

            @TestMetadata("InheritanceWithKotlinClasses.kt")
            public void testInheritanceWithKotlinClasses() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritanceWithKotlinClasses.kt");
            }

            @TestMetadata("InheritedInner.kt")
            public void testInheritedInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritedInner.kt");
            }

            @TestMetadata("InheritedInner2.kt")
            public void testInheritedInner2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritedInner2.kt");
            }

            @TestMetadata("InheritedInnerAndSupertypeWithSameName.kt")
            public void testInheritedInnerAndSupertypeWithSameName() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritedInnerAndSupertypeWithSameName.kt");
            }

            @TestMetadata("InheritedInnerUsageInInner.kt")
            public void testInheritedInnerUsageInInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritedInnerUsageInInner.kt");
            }

            @TestMetadata("InheritedKotlinInner.kt")
            public void testInheritedKotlinInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InheritedKotlinInner.kt");
            }

            @TestMetadata("InnerAndInheritedInner.kt")
            public void testInnerAndInheritedInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/InnerAndInheritedInner.kt");
            }

            @TestMetadata("ManyInheritedClasses.kt")
            public void testManyInheritedClasses() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/ManyInheritedClasses.kt");
            }

            @TestMetadata("NoAmbiguity.kt")
            public void testNoAmbiguity() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/NoAmbiguity.kt");
            }

            @TestMetadata("NoAmbiguity2.kt")
            public void testNoAmbiguity2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/NoAmbiguity2.kt");
            }

            @TestMetadata("SameInnersInSupertypeAndSupertypesSupertype.kt")
            public void testSameInnersInSupertypeAndSupertypesSupertype() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/SameInnersInSupertypeAndSupertypesSupertype.kt");
            }

            @TestMetadata("SuperTypeWithSameInner.kt")
            public void testSuperTypeWithSameInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/SuperTypeWithSameInner.kt");
            }

            @TestMetadata("SupertypeInnerAndTypeParameterWithSameNames.kt")
            public void testSupertypeInnerAndTypeParameterWithSameNames() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inheritance/SupertypeInnerAndTypeParameterWithSameNames.kt");
            }
        }

        @TestMetadata("compiler/testData/javac/diagnostics/tests/inners")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class Inners extends AbstractJavacDiagnosticsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTestWithoutJavacWrapper, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInInners() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests/inners"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("ComplexCase.kt")
            public void testComplexCase() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/ComplexCase.kt");
            }

            @TestMetadata("ComplexCase2.kt")
            public void testComplexCase2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/ComplexCase2.kt");
            }

            @TestMetadata("CurrentPackageAndInner.kt")
            public void testCurrentPackageAndInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/CurrentPackageAndInner.kt");
            }

            @TestMetadata("ImportThriceNestedClass.kt")
            public void testImportThriceNestedClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/ImportThriceNestedClass.kt");
            }

            @TestMetadata("InnerInInner.kt")
            public void testInnerInInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/InnerInInner.kt");
            }

            @TestMetadata("Nested.kt")
            public void testNested() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/Nested.kt");
            }

            @TestMetadata("ThriceNestedClass.kt")
            public void testThriceNestedClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/inners/ThriceNestedClass.kt");
            }
        }

        @TestMetadata("compiler/testData/javac/diagnostics/tests/qualifiedExpression")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class QualifiedExpression extends AbstractJavacDiagnosticsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTestWithoutJavacWrapper, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInQualifiedExpression() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests/qualifiedExpression"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("GenericClassVsPackage.kt")
            public void testGenericClassVsPackage() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/qualifiedExpression/GenericClassVsPackage.kt");
            }

            @TestMetadata("PackageVsClass.kt")
            public void testPackageVsClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/qualifiedExpression/PackageVsClass.kt");
            }

            @TestMetadata("PackageVsClass2.kt")
            public void testPackageVsClass2() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/qualifiedExpression/PackageVsClass2.kt");
            }

            @TestMetadata("PackageVsRootClass.kt")
            public void testPackageVsRootClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/qualifiedExpression/PackageVsRootClass.kt");
            }

            @TestMetadata("visibleClassVsQualifiedClass.kt")
            public void testVisibleClassVsQualifiedClass() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/qualifiedExpression/visibleClassVsQualifiedClass.kt");
            }
        }

        @TestMetadata("compiler/testData/javac/diagnostics/tests/typeParameters")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class TypeParameters extends AbstractJavacDiagnosticsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTestWithoutJavacWrapper, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInTypeParameters() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/javac/diagnostics/tests/typeParameters"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("Clash.kt")
            public void testClash() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/Clash.kt");
            }

            @TestMetadata("ComplexCase.kt")
            public void testComplexCase() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/ComplexCase.kt");
            }

            @TestMetadata("InheritedInnerAndTypeParameterWithSameNames.kt")
            public void testInheritedInnerAndTypeParameterWithSameNames() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/InheritedInnerAndTypeParameterWithSameNames.kt");
            }

            @TestMetadata("InnerWithTypeParameter.kt")
            public void testInnerWithTypeParameter() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/InnerWithTypeParameter.kt");
            }

            @TestMetadata("NestedWithInner.kt")
            public void testNestedWithInner() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/NestedWithInner.kt");
            }

            @TestMetadata("SeveralInnersWithTypeParameters.kt")
            public void testSeveralInnersWithTypeParameters() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/SeveralInnersWithTypeParameters.kt");
            }

            @TestMetadata("TypeParametersInInnerAndOuterWithSameNames.kt")
            public void testTypeParametersInInnerAndOuterWithSameNames() throws Exception {
                runTest("compiler/testData/javac/diagnostics/tests/typeParameters/TypeParametersInInnerAndOuterWithSameNames.kt");
            }
        }
    }
}
