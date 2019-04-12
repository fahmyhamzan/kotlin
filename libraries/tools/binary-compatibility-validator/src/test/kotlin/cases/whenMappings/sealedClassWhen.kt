/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package cases.whenMappings

sealed class SampleSealed {
    class A : SampleSealed()
    class B : SampleSealed()
    class C : SampleSealed()
}

fun SampleSealed.deacronimize() = when (this) {
    is SampleSealed.A -> "Apple"
    is SampleSealed.B -> "Biscuit"
    is SampleSealed.C -> "Cinnamon"
}


inline fun SampleSealed.switch(thenA: () -> Unit, thenB: () -> Unit, thenC: () -> Unit) = when (this) {
    is SampleSealed.C -> thenC()
    is SampleSealed.B -> thenB()
    is SampleSealed.A -> thenA()
}
