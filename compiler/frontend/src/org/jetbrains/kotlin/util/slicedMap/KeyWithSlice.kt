/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.util.slicedMap

import com.intellij.openapi.util.Key

abstract class KeyWithSlice<K, V, out Slice : ReadOnlySlice<K, V>>(debugName: String) : Key<V>(debugName) {
    abstract val slice: Slice
}
