/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.highlighter.markers

import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.CommonClassNames
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.asJava.LightClassUtil
import org.jetbrains.kotlin.asJava.toLightClass
import org.jetbrains.kotlin.idea.caches.lightClasses.KtFakeLightClass
import org.jetbrains.kotlin.idea.caches.lightClasses.KtFakeLightMethod
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import java.util.*

fun collectContainingClasses(methods: Collection<PsiMethod>): Set<PsiClass> {
    val classes = HashSet<PsiClass>()
    for (method in methods) {
        ProgressManager.checkCanceled()
        val parentClass = method.containingClass
        if (parentClass != null && CommonClassNames.JAVA_LANG_OBJECT != parentClass.qualifiedName) {
            classes.add(parentClass)
        }
    }

    return classes
}

internal tailrec fun getPsiClass(element: PsiElement?): PsiClass? {
    return when {
        element == null -> null
        element is PsiClass -> element
        element is KtClass -> element.toLightClass() ?: KtFakeLightClass(element)
        element.parent is KtClass -> getPsiClass(element.parent)
        else -> null
    }
}

internal fun getPsiMethod(element: PsiElement?): PsiMethod? {
    val parent = element?.parent
    return when {
        element == null -> null
        element is PsiMethod -> element
        parent is KtNamedFunction || parent is KtSecondaryConstructor ->
            LightClassUtil.getLightClassMethod(parent as KtFunction) ?: KtFakeLightMethod.get(parent)
        else -> null
    }
}
