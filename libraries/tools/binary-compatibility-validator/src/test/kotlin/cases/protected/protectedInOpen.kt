/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package cases.protected

public open class PublicOpenClass protected constructor() {
    protected val protectedVal = 1
    protected var protectedVar = 2

    protected fun protectedFun() = protectedVal
}
