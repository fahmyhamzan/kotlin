/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.stubindex.resolve

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.analyzer.ModuleInfo
import org.jetbrains.kotlin.idea.caches.project.IdeaModuleInfo
import org.jetbrains.kotlin.idea.caches.project.ModuleOrigin
import org.jetbrains.kotlin.idea.stubindex.KotlinSourceFilterScope
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactoryService
import org.jetbrains.kotlin.storage.StorageManager

class PluginDeclarationProviderFactoryService : DeclarationProviderFactoryService() {

    override fun create(
        project: Project,
        storageManager: StorageManager,
        syntheticFiles: Collection<KtFile>,
        filesScope: GlobalSearchScope,
        moduleInfo: ModuleInfo
    ): DeclarationProviderFactory {
        if (syntheticFiles.isEmpty() && (moduleInfo as IdeaModuleInfo).moduleOrigin != ModuleOrigin.MODULE) {
            // No actual source declarations for libraries
            // Even in case of libraries sources they should be obtained through the classpath with subsequent decompiling
            // Anyway, we'll filter them out with `KotlinSourceFilterScope.sources` call below
            return DeclarationProviderFactory.EMPTY
        }

        return PluginDeclarationProviderFactory(
            project,
            KotlinSourceFilterScope.projectSources(filesScope, project),
            storageManager,
            syntheticFiles,
            moduleInfo
        )
    }
}
