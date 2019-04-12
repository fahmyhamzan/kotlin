/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package foo

class TestKotlinClass {
    fun numberFromMainKotlin(): Int = MainKotlinClass().number()

    fun numberFromMainJava(): Int = MainJavaClass().number()

    fun numberFromTestJava(): Int = TestJavaClass().number()

    fun number(): Int = 0
}