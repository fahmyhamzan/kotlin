/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.conversion.copy

import com.intellij.psi.PsiElement
import java.util.*


class ElementAndTextList() {
    private val elementsAndTexts = ArrayList<Any>()

    constructor(elements: List<Any>) : this() {
        elementsAndTexts.addAll(elements.filter { it is PsiElement || it is String })
    }

    fun add(a: String) = elementsAndTexts.add(a)

    fun add(a: PsiElement) = elementsAndTexts.add(a)

    operator fun plusAssign(other: String) = plusAssign(other as Any)

    operator fun plusAssign(other: PsiElement) = plusAssign(other as Any)

    private fun plusAssign(a: Any) {
        elementsAndTexts.add(a)
    }

    operator fun plusAssign(other: Collection<PsiElement>): Unit {
        elementsAndTexts.addAll(other)
    }

    operator fun plus(other: ElementAndTextList): ElementAndTextList {
        val newList = ElementAndTextList()
        newList.elementsAndTexts.addAll(this.elementsAndTexts)
        newList.elementsAndTexts.addAll(other.elementsAndTexts)
        return newList
    }

    fun toList(): List<Any> = elementsAndTexts.toList()

    fun process(processor: ElementsAndTextsProcessor) {
        elementsAndTexts.forEach {
            when (it) {
                is PsiElement -> processor.processElement(it)
                is String -> processor.processText(it)
            }
        }
    }
}

interface ElementsAndTextsProcessor {
    fun processElement(element: PsiElement)
    fun processText(string: String)
}