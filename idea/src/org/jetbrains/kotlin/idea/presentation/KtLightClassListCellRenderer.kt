/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.presentation

import com.intellij.psi.presentation.java.ClassPresentationUtil
import org.jetbrains.kotlin.asJava.classes.KtLightClass
import org.jetbrains.kotlin.name.FqName

class KtLightClassListCellRenderer : KtModuleSpecificListCellRenderer<KtLightClass>() {
    override fun getElementText(element: KtLightClass) = ClassPresentationUtil.getNameForClass(element, false)

    // TODO: correct text for local, anonymous, enum entries ... etc
    override fun getContainerText(element: KtLightClass, name: String) = element.qualifiedName?.let { qName ->
        "(" + FqName(qName).parent().asString() + ")"
    } ?: ""
}
