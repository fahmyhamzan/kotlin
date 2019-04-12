/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.compatibility

import com.intellij.util.Processor

/**
 * Processor<T> till IDEA 181 and Processor<in T> since 182.
 * BUNCH: 181
 */
typealias ExecutorProcessor<T> = Processor<in T>