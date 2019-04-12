/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.test.util;

import com.google.common.collect.Lists;
import com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.descriptors.*;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.lazy.LazyEntity;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.KotlinTestWithEnvironment;
import org.jetbrains.kotlin.utils.Printer;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class RecursiveDescriptorProcessorTest extends KotlinTestWithEnvironment {
    @Override
    protected KotlinCoreEnvironment createEnvironment() {
        return KotlinTestUtils.createEnvironmentWithMockJdkAndIdeaAnnotations(getTestRootDisposable());
    }

    public void testRecursive() throws Exception {
        File ktFile = new File("compiler/testData/recursiveProcessor/declarations.kt");
        File txtFile = new File("compiler/testData/recursiveProcessor/declarations.txt");
        String text = FileUtil.loadFile(ktFile, true);
        KtFile jetFile = KotlinTestUtils.createFile("declarations.kt", text, getEnvironment().getProject());
        AnalysisResult result = KotlinTestUtils.analyzeFile(jetFile, getEnvironment());
        PackageViewDescriptor testPackage = result.getModuleDescriptor().getPackage(FqName.topLevel(Name.identifier("test")));

        List<String> descriptors = recursivelyCollectDescriptors(testPackage);

        StringBuilder builder = new StringBuilder();
        Printer p = new Printer(builder);
        for (String descriptor : descriptors) {
            p.println(descriptor);
        }
        String actualText = builder.toString();

        if (!txtFile.exists()) {
            FileUtil.writeToFile(txtFile, actualText);
            fail("Test data file did not exist and was created from the results of the test: " + txtFile);
        }

        assertSameLinesWithFile(txtFile.getAbsolutePath(), actualText);
    }

    private static Class closestInterface(Class<?> aClass) {
        if (aClass == null) return null;
        if (aClass.isInterface() && aClass != LazyEntity.class) return aClass;

        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            if (anInterface != LazyEntity.class) return anInterface;
        }

        return closestInterface(aClass.getSuperclass());
    }

    private static List<String> recursivelyCollectDescriptors(PackageViewDescriptor testPackage) {
        List<String> lines = Lists.newArrayList();
        RecursiveDescriptorProcessor.process(testPackage, null, new DeclarationDescriptorVisitor<Boolean, Void>() {

            private void add(DeclarationDescriptor descriptor) {
                add(descriptor.getName().asString(), descriptor);
            }

            private void add(String name, DeclarationDescriptor descriptor) {
                lines.add(name + ":" + closestInterface(descriptor.getClass()).getSimpleName());
            }

            private void addCallable(CallableMemberDescriptor descriptor) {
                String prefix = descriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE ? "fake " : "";
                add(prefix + descriptor.getContainingDeclaration().getName() + "." + descriptor.getName(), descriptor);
            }

            @Override
            public Boolean visitPackageFragmentDescriptor(PackageFragmentDescriptor descriptor, Void data) {
                add(descriptor);
                return true;
            }

            @Override
            public Boolean visitPackageViewDescriptor(PackageViewDescriptor descriptor, Void data) {
                add(descriptor);
                return true;
            }

            @Override
            public Boolean visitVariableDescriptor(VariableDescriptor descriptor, Void data) {
                add(descriptor);
                return true;
            }

            @Override
            public Boolean visitFunctionDescriptor(FunctionDescriptor descriptor, Void data) {
                addCallable(descriptor);
                return true;
            }

            @Override
            public Boolean visitTypeParameterDescriptor(TypeParameterDescriptor descriptor, Void data) {
                add(descriptor);
                return true;
            }

            @Override
            public Boolean visitClassDescriptor(ClassDescriptor descriptor, Void data) {
                add(descriptor);
                return true;
            }

            @Override
            public Boolean visitTypeAliasDescriptor(TypeAliasDescriptor descriptor, Void data) {
                add(descriptor);
                return true;
            }

            @Override
            public Boolean visitModuleDeclaration(ModuleDescriptor descriptor, Void data) {
                add(descriptor);
                return true;
            }

            @Override
            public Boolean visitConstructorDescriptor(
                    ConstructorDescriptor constructorDescriptor, Void data
            ) {
                add(constructorDescriptor.getContainingDeclaration().getName() + ".<init>()", constructorDescriptor);
                return true;
            }

            @Override
            public Boolean visitScriptDescriptor(ScriptDescriptor scriptDescriptor, Void data) {
                add(scriptDescriptor);
                return true;
            }

            @Override
            public Boolean visitPropertyDescriptor(PropertyDescriptor descriptor, Void data) {
                addCallable(descriptor);
                return true;
            }

            @Override
            public Boolean visitValueParameterDescriptor(
                    ValueParameterDescriptor descriptor, Void data
            ) {
                add(descriptor);
                return true;
            }

            @Override
            public Boolean visitPropertyGetterDescriptor(
                    PropertyGetterDescriptor descriptor, Void data
            ) {
                addCallable(descriptor);
                return true;
            }

            @Override
            public Boolean visitPropertySetterDescriptor(
                    PropertySetterDescriptor descriptor, Void data
            ) {
                addCallable(descriptor);
                return true;
            }

            @Override
            public Boolean visitReceiverParameterDescriptor(
                    ReceiverParameterDescriptor descriptor, Void data
            ) {
                add(descriptor.getContainingDeclaration().getName() + ".this", descriptor);
                return true;
            }
        });
        Collections.sort(lines);
        return lines;
    }
}
