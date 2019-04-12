/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.cfg.pseudocode.instructions

import org.jetbrains.kotlin.psi.KtElement

abstract class InstructionWithNext(
    element: KtElement,
    blockScope: BlockScope
) : KtElementInstructionImpl(element, blockScope) {
    var next: Instruction? = null
        set(value) {
            field = outgoingEdgeTo(value)
        }

    override val nextInstructions: Collection<Instruction>
        get() = listOfNotNull(next)
}
