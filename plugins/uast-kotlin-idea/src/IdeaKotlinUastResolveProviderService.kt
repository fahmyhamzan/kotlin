/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.uast.kotlin.internal

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootModificationTracker
import com.intellij.psi.PsiElement
import com.intellij.psi.util.CachedValueProvider.Result
import com.intellij.psi.util.CachedValuesManager
import org.jetbrains.kotlin.codegen.ClassBuilderMode
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper
import org.jetbrains.kotlin.config.LanguageVersionSettings
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.idea.caches.resolve.getResolutionFacade
import org.jetbrains.kotlin.idea.core.resolveCandidates
import org.jetbrains.kotlin.idea.project.TargetPlatformDetector
import org.jetbrains.kotlin.idea.project.languageVersionSettings
import org.jetbrains.kotlin.idea.util.module
import org.jetbrains.kotlin.load.java.JvmAbi
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.calls.callUtil.getCall
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatform
import org.jetbrains.kotlin.resolve.lazy.BodyResolveMode
import org.jetbrains.uast.kotlin.KotlinUastResolveProviderService

class IdeaKotlinUastResolveProviderService : KotlinUastResolveProviderService {
    override fun getBindingContext(element: KtElement) = element.analyze(BodyResolveMode.PARTIAL)

    override fun getTypeMapper(element: KtElement): KotlinTypeMapper? {
        return KotlinTypeMapper(
            getBindingContext(element), ClassBuilderMode.LIGHT_CLASSES,
            JvmAbi.DEFAULT_MODULE_NAME, element.languageVersionSettings
        )
    }

    override fun isJvmElement(psiElement: PsiElement): Boolean {
        if (allModulesSupportJvm(psiElement.project)) return true

        val containingFile = psiElement.containingFile
        if (containingFile is KtFile) {
            return TargetPlatformDetector.getPlatform(containingFile) is JvmPlatform
        }

        val module = psiElement.module
        return module == null || TargetPlatformDetector.getPlatform(module) is JvmPlatform
    }

    override fun getLanguageVersionSettings(element: KtElement): LanguageVersionSettings {
        return element.languageVersionSettings
    }

    override fun getReferenceVariants(ktElement: KtElement, nameHint: String): Sequence<DeclarationDescriptor> {
        val resolutionFacade = ktElement.getResolutionFacade()
        val bindingContext = ktElement.analyze()
        val call = ktElement.getCall(bindingContext) ?: return emptySequence()
        return call.resolveCandidates(bindingContext, resolutionFacade).map { it.candidateDescriptor }.asSequence()
    }

    private fun allModulesSupportJvm(project: Project): Boolean =
        CachedValuesManager.getManager(project)
            .getCachedValue(project, {
                Result.create(
                    ModuleManager.getInstance(project).modules.all { module ->
                        TargetPlatformDetector.getPlatform(module) is JvmPlatform
                    },
                    ProjectRootModificationTracker.getInstance(project)
                )
            })

}
