/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.stubindex;

import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.psi.KtNamedFunction;

import java.util.Collection;

public class KotlinFunctionShortNameIndex extends StringStubIndexExtension<KtNamedFunction> {
    private static final StubIndexKey<String, KtNamedFunction> KEY = KotlinIndexUtil.createIndexKey(KotlinFunctionShortNameIndex.class);

    private static final KotlinFunctionShortNameIndex ourInstance = new KotlinFunctionShortNameIndex();

    public static KotlinFunctionShortNameIndex getInstance() {
        return ourInstance;
    }

    private KotlinFunctionShortNameIndex() {}

    @NotNull
    @Override
    public StubIndexKey<String, KtNamedFunction> getKey() {
        return KEY;
    }

    @NotNull
    @Override
    public Collection<KtNamedFunction> get(@NotNull String s, @NotNull Project project, @NotNull GlobalSearchScope scope) {
        return StubIndex.getElements(KEY, s, project, scope, KtNamedFunction.class);
    }
}
