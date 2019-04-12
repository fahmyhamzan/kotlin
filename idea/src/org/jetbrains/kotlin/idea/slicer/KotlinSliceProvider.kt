/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.slicer

import com.intellij.ide.util.treeView.AbstractTreeStructure
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.psi.PsiElement
import com.intellij.slicer.*
import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.idea.caches.resolve.resolveToDescriptorIfAny
import org.jetbrains.kotlin.idea.references.KtReference
import org.jetbrains.kotlin.idea.references.mainReference
import org.jetbrains.kotlin.idea.slicer.compat.PsiElement_N183_NN191
import org.jetbrains.kotlin.idea.util.compat.Nullability
import org.jetbrains.kotlin.idea.util.compat.SliceNullnessAnalyzerBaseEx
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.isPlainWithEscapes
import org.jetbrains.kotlin.psi.psiUtil.parentsWithSelf
import org.jetbrains.kotlin.resolve.lazy.BodyResolveMode
import org.jetbrains.kotlin.types.TypeUtils
import org.jetbrains.kotlin.types.isError
import org.jetbrains.kotlin.types.isNullabilityFlexible

class KotlinSliceProvider : SliceLanguageSupportProvider, SliceUsageTransformer {
    companion object {
        val LEAF_ELEMENT_EQUALITY = object : SliceLeafEquality() {
            override fun substituteElement(element: PsiElement) = (element as? KtReference)?.resolve() ?: element
        }
    }

    class KotlinGroupByNullnessAction(treeBuilder: SliceTreeBuilder) : GroupByNullnessActionBase(treeBuilder) {
        override fun isAvailable() = true
    }

    val leafAnalyzer by lazy { SliceLeafAnalyzer(LEAF_ELEMENT_EQUALITY, this) }
    val nullnessAnalyzer: SliceNullnessAnalyzerBase by lazy {
        object : SliceNullnessAnalyzerBaseEx(LEAF_ELEMENT_EQUALITY, this) {
            override fun checkNullabilityEx(element: PsiElement?): Nullability {
                val types = when (element) {
                    is KtCallableDeclaration -> listOfNotNull((element.resolveToDescriptorIfAny() as? CallableDescriptor)?.returnType)
                    is KtDeclaration -> emptyList()
                    is KtExpression -> listOfNotNull(element.analyze(BodyResolveMode.PARTIAL).getType(element))
                    else -> emptyList()
                }
                return when {
                    types.isEmpty() -> return Nullability.UNKNOWN
                    types.all { KotlinBuiltIns.isNullableNothing(it) } -> Nullability.NULLABLE
                    types.any { it.isError || TypeUtils.isNullableType(it) || it.isNullabilityFlexible() } -> Nullability.UNKNOWN
                    else -> Nullability.NOT_NULL
                }
            }
        }
    }

    override fun createRootUsage(element: PsiElement, params: SliceAnalysisParams) = KotlinSliceUsage(element, params)

    override fun transform(usage: SliceUsage): Collection<SliceUsage>? {
        if (usage is KotlinSliceUsage) return null
        return listOf(KotlinSliceUsage(usage.element, usage.parent, 0, false))
    }

    override fun getExpressionAtCaret(atCaret: PsiElement_N183_NN191, dataFlowToThis: Boolean): KtExpression? {
        // BUNCH: 183
        @Suppress("SENSELESS_COMPARISON")
        if (atCaret == null) return null

        val element =
            atCaret.parentsWithSelf
                .firstOrNull {
                    it is KtProperty ||
                            it is KtParameter ||
                            it is KtDeclarationWithBody ||
                            (it is KtClass && !it.hasExplicitPrimaryConstructor()) ||
                            (it is KtExpression && it !is KtDeclaration)
                }
                ?.let { KtPsiUtil.safeDeparenthesize(it as KtExpression) } ?: return null
        if (dataFlowToThis) {
            if (element is KtConstantExpression) return null
            if (element is KtStringTemplateExpression && element.isPlainWithEscapes()) return null
            if (element is KtClassLiteralExpression) return null
            if (element is KtCallableReferenceExpression) return null
        }
        return element
    }

    override fun getElementForDescription(element: PsiElement): PsiElement {
        return (element as? KtSimpleNameExpression)?.mainReference?.resolve() ?: element
    }

    override fun getRenderer() = KotlinSliceUsageCellRenderer

    override fun startAnalyzeLeafValues(structure: AbstractTreeStructure, finalRunnable: Runnable) {
        leafAnalyzer.startAnalyzeValues(structure, finalRunnable)
    }

    override fun startAnalyzeNullness(structure: AbstractTreeStructure, finalRunnable: Runnable) {
        nullnessAnalyzer.startAnalyzeNullness(structure, finalRunnable)
    }

    override fun registerExtraPanelActions(group: DefaultActionGroup, builder: SliceTreeBuilder) {
        if (builder.dataFlowToThis) {
            group.add(GroupByLeavesAction(builder))
            group.add(KotlinGroupByNullnessAction(builder))
        }
    }
}