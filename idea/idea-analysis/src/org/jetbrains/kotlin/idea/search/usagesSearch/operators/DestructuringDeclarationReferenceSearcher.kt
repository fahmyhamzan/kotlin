/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.search.usagesSearch.operators

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.search.SearchRequestCollector
import com.intellij.psi.search.SearchScope
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.compatibility.ExecutorProcessor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.idea.references.KtDestructuringDeclarationReference
import org.jetbrains.kotlin.idea.search.ideaExtensions.KotlinReferencesSearchOptions
import org.jetbrains.kotlin.idea.search.usagesSearch.dataClassComponentFunction
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.utils.addToStdlib.firstIsInstance

class DestructuringDeclarationReferenceSearcher(
    targetDeclaration: PsiElement,
    private val componentIndex: Int,
    searchScope: SearchScope,
    consumer: ExecutorProcessor<PsiReference>,
    optimizer: SearchRequestCollector,
    options: KotlinReferencesSearchOptions
) : OperatorReferenceSearcher<KtDestructuringDeclaration>(
    targetDeclaration,
    searchScope,
    consumer,
    optimizer,
    options,
    wordsToSearch = listOf("(")
) {

    override fun resolveTargetToDescriptor(): FunctionDescriptor? {
        return if (targetDeclaration is KtParameter) {
            targetDeclaration.dataClassComponentFunction()
        } else {
            super.resolveTargetToDescriptor()
        }
    }

    override fun extractReference(element: KtElement): PsiReference? {
        val destructuringDeclaration = element as? KtDestructuringDeclaration ?: return null
        val entries = destructuringDeclaration.entries
        if (entries.size < componentIndex) return null
        return entries[componentIndex - 1].references.firstIsInstance<KtDestructuringDeclarationReference>()
    }

    override fun isReferenceToCheck(ref: PsiReference) = ref is KtDestructuringDeclarationReference

    override fun processPossibleReceiverExpression(expression: KtExpression) {
        val parent = expression.parent
        val destructuringDeclaration = when (parent) {
            is KtDestructuringDeclaration -> parent

            is KtContainerNode -> {
                if (parent.node.elementType == KtNodeTypes.LOOP_RANGE) {
                    (parent.parent as KtForExpression).destructuringDeclaration
                } else {
                    null
                }
            }

            else -> null
        }

        if (destructuringDeclaration != null) {
            processReferenceElement(destructuringDeclaration)
        }
    }
}