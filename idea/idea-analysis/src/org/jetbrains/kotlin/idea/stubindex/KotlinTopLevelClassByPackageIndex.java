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
import org.jetbrains.kotlin.psi.KtClassOrObject;

import java.util.Collection;

public class KotlinTopLevelClassByPackageIndex extends StringStubIndexExtension<KtClassOrObject> {
    private static final StubIndexKey<String, KtClassOrObject> KEY = KotlinIndexUtil.createIndexKey(KotlinTopLevelClassByPackageIndex.class);

    private static final KotlinTopLevelClassByPackageIndex ourInstance = new KotlinTopLevelClassByPackageIndex();

    public static KotlinTopLevelClassByPackageIndex getInstance() {
        return ourInstance;
    }

    private KotlinTopLevelClassByPackageIndex() {}

    @NotNull
    @Override
    public StubIndexKey<String, KtClassOrObject> getKey() {
        return KEY;
    }

    @NotNull
    @Override
    public Collection<KtClassOrObject> get(@NotNull String fqName, @NotNull Project project, @NotNull GlobalSearchScope scope) {
        return StubIndex.getElements(KEY, fqName, project, scope, KtClassOrObject.class);
    }
}
