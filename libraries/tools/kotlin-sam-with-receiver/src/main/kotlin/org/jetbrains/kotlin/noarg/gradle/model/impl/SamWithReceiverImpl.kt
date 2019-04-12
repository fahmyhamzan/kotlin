/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.noarg.gradle.model.impl

import org.jetbrains.kotlin.gradle.model.SamWithReceiver
import java.io.Serializable

/**
 * Implementation of the [SamWithReceiver] interface.
 */
data class SamWithReceiverImpl(
    override val name: String,
    override val annotations: List<String>,
    override val presets: List<String>
) : SamWithReceiver, Serializable {

    override val modelVersion = serialVersionUID

    companion object {
        private const val serialVersionUID = 1L
    }
}