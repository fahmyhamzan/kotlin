/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.uast.test.kotlin

import org.jetbrains.uast.UFile
import org.jetbrains.uast.test.common.kotlin.ResolveTestBase
import org.junit.Test

class KotlinUastResolveTest : AbstractKotlinUastTest(), ResolveTestBase {
    override fun check(testName: String, file: UFile) {
        super.check(testName, file)
    }

    @Test fun testMethodReference() = doTest("MethodReference")
}
