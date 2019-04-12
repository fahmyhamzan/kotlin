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
@TestMetadata("compiler/testData/foreignAnnotations/tests")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class JavacForeignAnnotationsTestGenerated extends AbstractJavacForeignAnnotationsTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
    }

    public void testAllFilesPresentInTests() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/foreignAnnotations/tests"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
    }

    @TestMetadata("androidRecently.kt")
    public void testAndroidRecently() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/androidRecently.kt");
    }

    @TestMetadata("androidSdk.kt")
    public void testAndroidSdk() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/androidSdk.kt");
    }

    @TestMetadata("android_support.kt")
    public void testAndroid_support() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/android_support.kt");
    }

    @TestMetadata("androidx.kt")
    public void testAndroidx() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/androidx.kt");
    }

    @TestMetadata("aosp.kt")
    public void testAosp() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/aosp.kt");
    }

    @TestMetadata("checkerFramework.kt")
    public void testCheckerFramework() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/checkerFramework.kt");
    }

    @TestMetadata("eclipse.kt")
    public void testEclipse() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/eclipse.kt");
    }

    @TestMetadata("findBugsSimple.kt")
    public void testFindBugsSimple() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/findBugsSimple.kt");
    }

    @TestMetadata("irrelevantQualifierNicknames.kt")
    public void testIrrelevantQualifierNicknames() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/irrelevantQualifierNicknames.kt");
    }

    @TestMetadata("lombokSimple.kt")
    public void testLombokSimple() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/lombokSimple.kt");
    }

    @TestMetadata("rxjava.kt")
    public void testRxjava() throws Exception {
        runTest("compiler/testData/foreignAnnotations/tests/rxjava.kt");
    }

    @TestMetadata("compiler/testData/foreignAnnotations/tests/jsr305")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class Jsr305 extends AbstractJavacForeignAnnotationsTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
        }

        public void testAllFilesPresentInJsr305() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/foreignAnnotations/tests/jsr305"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
        }

        @TestMetadata("nonNullNever.kt")
        public void testNonNullNever() throws Exception {
            runTest("compiler/testData/foreignAnnotations/tests/jsr305/nonNullNever.kt");
        }

        @TestMetadata("nullabilityNicknames.kt")
        public void testNullabilityNicknames() throws Exception {
            runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityNicknames.kt");
        }

        @TestMetadata("simple.kt")
        public void testSimple() throws Exception {
            runTest("compiler/testData/foreignAnnotations/tests/jsr305/simple.kt");
        }

        @TestMetadata("strange.kt")
        public void testStrange() throws Exception {
            runTest("compiler/testData/foreignAnnotations/tests/jsr305/strange.kt");
        }

        @TestMetadata("compiler/testData/foreignAnnotations/tests/jsr305/ignore")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class Ignore extends AbstractJavacForeignAnnotationsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInIgnore() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/foreignAnnotations/tests/jsr305/ignore"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("parametersAreNonnullByDefault.kt")
            public void testParametersAreNonnullByDefault() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/ignore/parametersAreNonnullByDefault.kt");
            }
        }

        @TestMetadata("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class NullabilityWarnings extends AbstractJavacForeignAnnotationsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInNullabilityWarnings() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("elvis.kt")
            public void testElvis() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/elvis.kt");
            }

            @TestMetadata("localInference.kt")
            public void testLocalInference() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/localInference.kt");
            }

            @TestMetadata("nullabilityGenerics.kt")
            public void testNullabilityGenerics() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/nullabilityGenerics.kt");
            }

            @TestMetadata("nullabilityNicknames.kt")
            public void testNullabilityNicknames() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/nullabilityNicknames.kt");
            }

            @TestMetadata("safeCalls.kt")
            public void testSafeCalls() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/safeCalls.kt");
            }

            @TestMetadata("simple.kt")
            public void testSimple() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/simple.kt");
            }

            @TestMetadata("strange.kt")
            public void testStrange() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/strange.kt");
            }

            @TestMetadata("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes")
            @TestDataPath("$PROJECT_ROOT")
            @RunWith(JUnit3RunnerWithInners.class)
            public static class FromPlatformTypes extends AbstractJavacForeignAnnotationsTest {
                private void runTest(String testDataFilePath) throws Exception {
                    KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
                }

                public void testAllFilesPresentInFromPlatformTypes() throws Exception {
                    KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
                }

                @TestMetadata("arithmetic.kt")
                public void testArithmetic() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/arithmetic.kt");
                }

                @TestMetadata("array.kt")
                public void testArray() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/array.kt");
                }

                @TestMetadata("assignToVar.kt")
                public void testAssignToVar() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/assignToVar.kt");
                }

                @TestMetadata("conditions.kt")
                public void testConditions() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/conditions.kt");
                }

                @TestMetadata("dataFlowInfo.kt")
                public void testDataFlowInfo() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/dataFlowInfo.kt");
                }

                @TestMetadata("defaultParameters.kt")
                public void testDefaultParameters() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/defaultParameters.kt");
                }

                @TestMetadata("delegatedProperties.kt")
                public void testDelegatedProperties() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/delegatedProperties.kt");
                }

                @TestMetadata("delegation.kt")
                public void testDelegation() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/delegation.kt");
                }

                @TestMetadata("derefenceExtension.kt")
                public void testDerefenceExtension() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/derefenceExtension.kt");
                }

                @TestMetadata("derefenceMember.kt")
                public void testDerefenceMember() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/derefenceMember.kt");
                }

                @TestMetadata("expectedType.kt")
                public void testExpectedType() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/expectedType.kt");
                }

                @TestMetadata("for.kt")
                public void testFor() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/for.kt");
                }

                @TestMetadata("functionArguments.kt")
                public void testFunctionArguments() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/functionArguments.kt");
                }

                @TestMetadata("invoke.kt")
                public void testInvoke() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/invoke.kt");
                }

                @TestMetadata("kt6829.kt")
                public void testKt6829() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/kt6829.kt");
                }

                @TestMetadata("multiDeclaration.kt")
                public void testMultiDeclaration() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/multiDeclaration.kt");
                }

                @TestMetadata("passToJava.kt")
                public void testPassToJava() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/passToJava.kt");
                }

                @TestMetadata("primitiveArray.kt")
                public void testPrimitiveArray() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/primitiveArray.kt");
                }

                @TestMetadata("throw.kt")
                public void testThrow() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/throw.kt");
                }

                @TestMetadata("uselessElvisRightIsNull.kt")
                public void testUselessElvisRightIsNull() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/fromPlatformTypes/uselessElvisRightIsNull.kt");
                }
            }

            @TestMetadata("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/typeQualifierDefault")
            @TestDataPath("$PROJECT_ROOT")
            @RunWith(JUnit3RunnerWithInners.class)
            public static class TypeQualifierDefault extends AbstractJavacForeignAnnotationsTest {
                private void runTest(String testDataFilePath) throws Exception {
                    KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
                }

                public void testAllFilesPresentInTypeQualifierDefault() throws Exception {
                    KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/typeQualifierDefault"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
                }

                @TestMetadata("equalsOnNonNull.kt")
                public void testEqualsOnNonNull() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/typeQualifierDefault/equalsOnNonNull.kt");
                }

                @TestMetadata("fieldsAreNullable.kt")
                public void testFieldsAreNullable() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/typeQualifierDefault/fieldsAreNullable.kt");
                }

                @TestMetadata("nullabilityFromOverridden.kt")
                public void testNullabilityFromOverridden() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/typeQualifierDefault/nullabilityFromOverridden.kt");
                }

                @TestMetadata("overridingDefaultQualifier.kt")
                public void testOverridingDefaultQualifier() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/typeQualifierDefault/overridingDefaultQualifier.kt");
                }

                @TestMetadata("parametersAreNonnullByDefault.kt")
                public void testParametersAreNonnullByDefault() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/typeQualifierDefault/parametersAreNonnullByDefault.kt");
                }

                @TestMetadata("parametersAreNonnullByDefaultPackage.kt")
                public void testParametersAreNonnullByDefaultPackage() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/typeQualifierDefault/parametersAreNonnullByDefaultPackage.kt");
                }

                @TestMetadata("springNullable.kt")
                public void testSpringNullable() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/typeQualifierDefault/springNullable.kt");
                }

                @TestMetadata("springNullablePackage.kt")
                public void testSpringNullablePackage() throws Exception {
                    runTest("compiler/testData/foreignAnnotations/tests/jsr305/nullabilityWarnings/typeQualifierDefault/springNullablePackage.kt");
                }
            }
        }

        @TestMetadata("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class TypeQualifierDefault extends AbstractJavacForeignAnnotationsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInTypeQualifierDefault() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("fieldsAreNullable.kt")
            public void testFieldsAreNullable() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault/fieldsAreNullable.kt");
            }

            @TestMetadata("forceFlexibility.kt")
            public void testForceFlexibility() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault/forceFlexibility.kt");
            }

            @TestMetadata("forceFlexibleOverOverrides.kt")
            public void testForceFlexibleOverOverrides() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault/forceFlexibleOverOverrides.kt");
            }

            @TestMetadata("nullabilityFromOverridden.kt")
            public void testNullabilityFromOverridden() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault/nullabilityFromOverridden.kt");
            }

            @TestMetadata("overridingDefaultQualifier.kt")
            public void testOverridingDefaultQualifier() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault/overridingDefaultQualifier.kt");
            }

            @TestMetadata("parametersAreNonnullByDefault.kt")
            public void testParametersAreNonnullByDefault() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault/parametersAreNonnullByDefault.kt");
            }

            @TestMetadata("parametersAreNonnullByDefaultPackage.kt")
            public void testParametersAreNonnullByDefaultPackage() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault/parametersAreNonnullByDefaultPackage.kt");
            }

            @TestMetadata("springNullable.kt")
            public void testSpringNullable() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault/springNullable.kt");
            }

            @TestMetadata("springNullablePackage.kt")
            public void testSpringNullablePackage() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305/typeQualifierDefault/springNullablePackage.kt");
            }
        }
    }

    @TestMetadata("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class Jsr305NullabilityWarnings extends AbstractJavacForeignAnnotationsTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
        }

        public void testAllFilesPresentInJsr305NullabilityWarnings() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
        }

        @TestMetadata("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration")
        @TestDataPath("$PROJECT_ROOT")
        @RunWith(JUnit3RunnerWithInners.class)
        public static class Migration extends AbstractJavacForeignAnnotationsTest {
            private void runTest(String testDataFilePath) throws Exception {
                KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
            }

            public void testAllFilesPresentInMigration() throws Exception {
                KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
            }

            @TestMetadata("customMigration.kt")
            public void testCustomMigration() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration/customMigration.kt");
            }

            @TestMetadata("globalIgnore.kt")
            public void testGlobalIgnore() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration/globalIgnore.kt");
            }

            @TestMetadata("globalWarningMigrationIgnore.kt")
            public void testGlobalWarningMigrationIgnore() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration/globalWarningMigrationIgnore.kt");
            }

            @TestMetadata("migrationError.kt")
            public void testMigrationError() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration/migrationError.kt");
            }

            @TestMetadata("migrationIgnore.kt")
            public void testMigrationIgnore() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration/migrationIgnore.kt");
            }

            @TestMetadata("migrationWarning.kt")
            public void testMigrationWarning() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration/migrationWarning.kt");
            }

            @TestMetadata("overrideConflicts.kt")
            public void testOverrideConflicts() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration/overrideConflicts.kt");
            }

            @TestMetadata("specialCollision.kt")
            public void testSpecialCollision() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration/specialCollision.kt");
            }

            @TestMetadata("stateRefinement.kt")
            public void testStateRefinement() throws Exception {
                runTest("compiler/testData/foreignAnnotations/tests/jsr305NullabilityWarnings/migration/stateRefinement.kt");
            }
        }
    }

    @TestMetadata("compiler/testData/foreignAnnotations/tests/typeQualifierDefault")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class TypeQualifierDefault extends AbstractJavacForeignAnnotationsTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, TargetBackend.ANY, testDataFilePath);
        }

        public void testAllFilesPresentInTypeQualifierDefault() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("compiler/testData/foreignAnnotations/tests/typeQualifierDefault"), Pattern.compile("^(.+)\\.kt$"), TargetBackend.ANY, true);
        }

        @TestMetadata("defaultAndNicknameMigrationPolicy.kt")
        public void testDefaultAndNicknameMigrationPolicy() throws Exception {
            runTest("compiler/testData/foreignAnnotations/tests/typeQualifierDefault/defaultAndNicknameMigrationPolicy.kt");
        }
    }
}
