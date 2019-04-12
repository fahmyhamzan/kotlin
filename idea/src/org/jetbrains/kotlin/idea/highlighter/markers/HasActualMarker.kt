/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.highlighter.markers

import com.intellij.ide.util.DefaultPsiElementCellRenderer
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.analyzer.ModuleInfo
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.idea.caches.project.ModuleSourceInfo
import org.jetbrains.kotlin.idea.core.isAndroidModule
import org.jetbrains.kotlin.idea.core.toDescriptor
import org.jetbrains.kotlin.idea.util.actualsForExpected
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.resolve.MultiTargetPlatform
import org.jetbrains.kotlin.resolve.descriptorUtil.module
import org.jetbrains.kotlin.resolve.getMultiTargetPlatform

private fun ModuleDescriptor?.getMultiTargetPlatformName(): String? {
    if (this == null) return null
    val moduleInfo = getCapability(ModuleInfo.Capability) as? ModuleSourceInfo
    if (moduleInfo != null && moduleInfo.module.isAndroidModule()) {
        return "Android"
    }
    val platform = getMultiTargetPlatform() ?: return null
    return when (platform) {
        is MultiTargetPlatform.Specific ->
            platform.platform
        MultiTargetPlatform.Common ->
            "common"
    }
}

fun getPlatformActualTooltip(declaration: KtDeclaration): String? {
    val actualDeclarations = declaration.actualsForExpected()
    if (actualDeclarations.isEmpty()) return null

    return actualDeclarations.asSequence()
        .mapNotNull { it.toDescriptor()?.module }
        .groupBy { it.getMultiTargetPlatformName() }
        .filter { (platform, _) -> platform != null }
        .entries
        .joinToString(prefix = "Has actuals in ") { (platform, modules) ->
            val modulesSuffix = if (modules.size <= 1) "" else " (${modules.size} modules)"
            if (platform == null) {
                throw AssertionError("Platform should not be null")
            }
            platform + modulesSuffix
        }
}

fun KtDeclaration.allNavigatableActualDeclarations(): Set<KtDeclaration> =
    actualsForExpected() + findMarkerBoundDeclarations().flatMap { it.actualsForExpected().asSequence() }

class ActualExpectedPsiElementCellRenderer : DefaultPsiElementCellRenderer() {
    override fun getContainerText(element: PsiElement?, name: String?) = ""
}

fun KtDeclaration.navigateToActualTitle() = "Choose actual for $name"

fun KtDeclaration.navigateToActualUsagesTitle() = "Actuals for $name"

fun buildNavigateToActualDeclarationsPopup(element: PsiElement?): NavigationPopupDescriptor? {
    return element?.markerDeclaration?.let {
        val navigatableActualDeclarations = it.allNavigatableActualDeclarations()
        if (navigatableActualDeclarations.isEmpty()) return null
        return NavigationPopupDescriptor(
            navigatableActualDeclarations,
            it.navigateToActualTitle(),
            it.navigateToActualUsagesTitle(),
            ActualExpectedPsiElementCellRenderer()
        )
    }
}