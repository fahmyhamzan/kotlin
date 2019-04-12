/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.highlighter

import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.diagnostics.rendering.MultiplatformDiagnosticRenderingMode
import org.jetbrains.kotlin.diagnostics.rendering.RenderingContext

object IdeMultiplatformDiagnosticRenderingMode : MultiplatformDiagnosticRenderingMode() {
    override fun newLine(sb: StringBuilder) {
        sb.append("<br/>")
    }

    override fun renderList(sb: StringBuilder, elements: List<() -> Unit>) {
        sb.append("<ul>")
        for (element in elements) {
            sb.append("<li>")
            element()
            sb.append("</li>")
        }
        sb.append("</ul>")
    }

    override fun renderDescriptor(sb: StringBuilder, descriptor: DeclarationDescriptor, context: RenderingContext, indent: String) {
        sb.append(IdeRenderers.HTML.render(descriptor, context))
    }
}
