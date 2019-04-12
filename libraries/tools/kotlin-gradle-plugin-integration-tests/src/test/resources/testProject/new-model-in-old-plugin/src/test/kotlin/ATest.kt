/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package com.example

import kotlin.test.*

class ATest {
    @Test
    fun testF() {
        val f = A().f()
        assertEquals("hello", f)
    }
}
