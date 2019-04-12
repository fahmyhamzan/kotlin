/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.refactoring.changeSignature

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtPsiFactory

enum class KotlinValVar(val keywordName: String) {
    None("none") {
        override fun createKeyword(factory: KtPsiFactory) = null
    },
    Val("val") {
        override fun createKeyword(factory: KtPsiFactory) = factory.createValKeyword()
    },
    Var("var"){
        override fun createKeyword(factory: KtPsiFactory) = factory.createVarKeyword()
    };

    override fun toString(): String = keywordName

    abstract fun createKeyword(factory: KtPsiFactory): PsiElement?
}

fun PsiElement?.toValVar(): KotlinValVar {
    return when {
        this == null -> KotlinValVar.None
        node.elementType == KtTokens.VAL_KEYWORD -> KotlinValVar.Val
        node.elementType == KtTokens.VAR_KEYWORD -> KotlinValVar.Var
        else -> throw IllegalArgumentException("Unknown val/var token: " + text)
    }
}

fun KtParameter.setValOrVar(valOrVar: KotlinValVar): PsiElement? {
    val newKeyword = valOrVar.createKeyword(KtPsiFactory(this))
    val currentKeyword = valOrVarKeyword

    if (currentKeyword != null) {
        return if (newKeyword == null) {
            currentKeyword.delete()
            null
        }
        else {
            currentKeyword.replace(newKeyword)
        }
    }

    if (newKeyword == null) return null

    nameIdentifier?.let { return addBefore(newKeyword, it) }
    modifierList?.let { return addAfter(newKeyword, it) }
    return addAfter(newKeyword, null)
}