/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.liveTemplates.macro

import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.descriptors.VariableDescriptor
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.resolve.BindingContext

class AnyVariableMacro : BaseKotlinVariableMacro<Unit>() {
    override fun getName() = "kotlinAnyVariable"
    override fun getPresentableName() = "kotlinAnyVariable()"

    override fun initState(contextElement: KtElement, bindingContext: BindingContext) {
    }

    override fun isSuitable(variableDescriptor: VariableDescriptor, project: Project, state: Unit) = true
}
