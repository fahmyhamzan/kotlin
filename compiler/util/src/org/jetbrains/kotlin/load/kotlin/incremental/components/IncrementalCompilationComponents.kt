/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.load.kotlin.incremental.components

import org.jetbrains.kotlin.modules.TargetId

interface IncrementalCompilationComponents {
    fun getIncrementalCache(target: TargetId): IncrementalCache
}
