/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package kotlin.js

// Creates IntRange for {Byte, Short, Int}.rangeTo(x: {Byte, Short, Int})
fun numberRangeToNumber(start: dynamic, endInclusive: dynamic) =
    IntRange(start, endInclusive)

// Create LongRange for {Byte, Short, Int}.rangeTo(x: Long)
// Long.rangeTo(x: *) should be implemented in Long class
fun numberRangeToLong(start: dynamic, endInclusive: dynamic) =
    LongRange(numberToLong(start), endInclusive)
