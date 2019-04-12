/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.collections

@kotlin.internal.InlineOnly
@Suppress("UNUSED_PARAMETER")
internal inline fun deleteProperty(obj: Any, property: Any) {
    js("delete obj[property]")
}
