/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.search.ideaExtensions

import com.intellij.openapi.application.QueryExecutorBase
import com.intellij.openapi.application.ReadActionProcessor
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiReference
import com.intellij.psi.search.searches.MethodReferencesSearch
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.PsiUtil
import org.jetbrains.kotlin.compatibility.ExecutorProcessor
import org.jetbrains.kotlin.idea.references.KtSimpleNameReference
import org.jetbrains.kotlin.idea.search.restrictToKotlinSources
import org.jetbrains.kotlin.idea.util.application.runReadAction
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.psi.psiUtil.getParentOfTypeAndBranch
import org.jetbrains.kotlin.utils.addToStdlib.firstIsInstanceOrNull
import org.jetbrains.kotlin.idea.references.KotlinDefaultAnnotationMethodImplicitReferenceContributor.ReferenceImpl as ImplicitReference

class DefaultAnnotationMethodKotlinImplicitReferenceSearcher :
    QueryExecutorBase<PsiReference, MethodReferencesSearch.SearchParameters>(true) {
    private val PsiMethod.isDefaultAnnotationMethod: Boolean
        get() = PsiUtil.isAnnotationMethod(this) && name == PsiAnnotation.DEFAULT_REFERENCED_METHOD_NAME && parameterList.parametersCount == 0

    private fun createReferenceProcessor(consumer: ExecutorProcessor<PsiReference>) = object : ReadActionProcessor<PsiReference>() {
        override fun processInReadAction(reference: PsiReference): Boolean {
            if (reference !is KtSimpleNameReference) return true
            val annotationEntry = reference.expression.getParentOfTypeAndBranch<KtAnnotationEntry> { typeReference } ?: return true
            val argument = annotationEntry.valueArguments.singleOrNull() as? KtValueArgument ?: return true
            val implicitRef = argument.references.firstIsInstanceOrNull<ImplicitReference>() ?: return true
            return consumer.process(implicitRef)
        }
    }

    override fun processQuery(queryParameters: MethodReferencesSearch.SearchParameters, consumer: ExecutorProcessor<PsiReference>) {
        runReadAction {
            val method = queryParameters.method
            if (!method.isDefaultAnnotationMethod) return@runReadAction null
            val annotationClass = method.containingClass ?: return@runReadAction null
            val searchScope = queryParameters.effectiveSearchScope.restrictToKotlinSources()
            ReferencesSearch.search(annotationClass, searchScope)
        }?.forEach(createReferenceProcessor(consumer))
    }
}
