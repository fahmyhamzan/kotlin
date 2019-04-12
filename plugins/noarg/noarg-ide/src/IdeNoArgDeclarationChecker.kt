/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.noarg.ide

import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootModificationTracker
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.util.containers.ContainerUtil
import org.jetbrains.kotlin.annotation.plugin.ide.getSpecialAnnotations
import org.jetbrains.kotlin.noarg.NoArgCommandLineProcessor.Companion.ANNOTATION_OPTION
import org.jetbrains.kotlin.noarg.NoArgCommandLineProcessor.Companion.PLUGIN_ID
import org.jetbrains.kotlin.noarg.diagnostic.AbstractNoArgDeclarationChecker
import org.jetbrains.kotlin.psi.KtModifierListOwner
import java.util.concurrent.ConcurrentMap

class IdeNoArgDeclarationChecker(val project: Project) : AbstractNoArgDeclarationChecker() {
    private companion object {
        val ANNOTATION_OPTION_PREFIX = "plugin:$PLUGIN_ID:${ANNOTATION_OPTION.optionName}="
    }

    private val cache: CachedValue<ConcurrentMap<Module, List<String>>> = cachedValue(project) {
        CachedValueProvider.Result.create(
            ContainerUtil.createConcurrentWeakMap<Module, List<String>>(),
            ProjectRootModificationTracker.getInstance(project)
        )
    }

    override fun getAnnotationFqNames(modifierListOwner: KtModifierListOwner?): List<String> {
        if (modifierListOwner == null) return emptyList()
        val module = ModuleUtilCore.findModuleForPsiElement(modifierListOwner) ?: return emptyList()

        return cache.value.getOrPut(module) { module.getSpecialAnnotations(ANNOTATION_OPTION_PREFIX) }
    }

    private fun <T> cachedValue(project: Project, result: () -> CachedValueProvider.Result<T>): CachedValue<T> {
        return CachedValuesManager.getManager(project).createCachedValue(result, false)
    }
}