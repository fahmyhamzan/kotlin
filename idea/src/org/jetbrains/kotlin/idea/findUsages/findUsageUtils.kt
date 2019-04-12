/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.findUsages

import com.intellij.find.findUsages.FindUsagesManager
import com.intellij.find.findUsages.FindUsagesOptions
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.light.LightMemberReference
import com.intellij.psi.search.LocalSearchScope
import com.intellij.psi.search.SearchScope
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.usageView.UsageInfo
import com.intellij.usages.UsageViewManager
import com.intellij.util.Query
import org.jetbrains.kotlin.asJava.toLightClass
import org.jetbrains.kotlin.asJava.toLightElements
import org.jetbrains.kotlin.idea.references.KtReference
import org.jetbrains.kotlin.psi.KtConstructor
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtPsiUtil
import org.jetbrains.kotlin.utils.SmartList

fun PsiElement.processAllExactUsages(
    options: () -> FindUsagesOptions,
    processor: (UsageInfo) -> Unit
) {
    fun elementsToCheckReferenceAgainst(reference: PsiReference): List<PsiElement> {
        if (reference is KtReference || this !is KtDeclaration) return listOf(this)
        return SmartList<PsiElement>().also { list ->
            list += this
            list += toLightElements()
            if (this is KtConstructor<*>) {
                getContainingClassOrObject().toLightClass()?.let { list += it }
            }
        }
    }

    val project = project
    FindUsagesManager(project, UsageViewManager.getInstance(project))
        .getFindUsagesHandler(this, true)
        ?.processElementUsages(
            this,
            { usageInfo ->
                val reference = usageInfo.reference ?: return@processElementUsages true
                if (reference is LightMemberReference || elementsToCheckReferenceAgainst(reference).any { reference.isReferenceTo(it) }) {
                    processor(usageInfo)
                }
                true
            },
            options()
        )
}

fun KtDeclaration.processAllUsages(
    options: FindUsagesOptions,
    processor: (UsageInfo) -> Unit
) {
    val findUsagesHandler = KotlinFindUsagesHandlerFactory(project).createFindUsagesHandler(this, true)
    findUsagesHandler.processElementUsages(
        this,
        {
            processor(it)
            true
        },
        options
    )
}

object ReferencesSearchScopeHelper {
    fun search(declaration: KtDeclaration, defaultScope: SearchScope? = null): Query<PsiReference> {
        val enclosingElement = KtPsiUtil.getEnclosingElementForLocalDeclaration(declaration)
        return when {
            enclosingElement != null -> ReferencesSearch.search(declaration, LocalSearchScope(enclosingElement))
            defaultScope != null -> ReferencesSearch.search(declaration, defaultScope)
            else -> ReferencesSearch.search(declaration)
        }
    }
}