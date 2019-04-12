/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.coroutines.js.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext

@PublishedApi
@SinceKotlin("1.3")
internal val EmptyContinuation = Continuation<Any?>(EmptyCoroutineContext) { result ->
    result.getOrThrow()
}