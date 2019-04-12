/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.android.intention

import com.intellij.codeInsight.intention.HighPriorityAction
import com.intellij.openapi.editor.Editor
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.util.AndroidBundle
import org.jetbrains.kotlin.android.canAddParcelable
import org.jetbrains.kotlin.android.implementParcelable
import org.jetbrains.kotlin.android.insideBody
import org.jetbrains.kotlin.android.isParcelize
import org.jetbrains.kotlin.idea.intentions.SelfTargetingIntention
import org.jetbrains.kotlin.psi.KtClass


class ImplementParcelableAction :
        SelfTargetingIntention<KtClass>(KtClass::class.java, AndroidBundle.message("implement.parcelable.intention.text")),
        HighPriorityAction {
    override fun isApplicableTo(element: KtClass, caretOffset: Int): Boolean =
            AndroidFacet.getInstance(element) != null &&
            !element.insideBody(caretOffset) &&
            !element.isParcelize() &&
            element.canAddParcelable()

    override fun applyTo(element: KtClass, editor: Editor?) {
        element.implementParcelable()
    }

    override fun startInWriteAction(): Boolean = true
}