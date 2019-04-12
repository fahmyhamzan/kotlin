/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package my.pack.name

import common
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class CommonTest {
    @Test
    fun test1() = assertEquals(common(), 1)

    @Test
    @Ignore
    fun test2() = assertEquals(common(), 2)

    @Test
    fun test3() = assertEquals(common(), 3)

    @Ignore
    class InnerIgnored {
        @Test
        fun test4() = assertEquals(common(), 1)

        @Test
        @Ignore
        fun test5() = assertEquals(common(), 2)

        @Test
        fun test6() = assertEquals(common(), 3)
    }
}