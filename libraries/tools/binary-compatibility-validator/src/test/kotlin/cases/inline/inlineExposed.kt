/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package cases.inline

@PublishedApi
internal fun exposedForInline() {}

@PublishedApi
internal class InternalClassExposed
    @PublishedApi
    internal constructor() {

    @PublishedApi
    internal fun funExposed() {}

    // TODO: Cover unsupported cases: requires correctly reflecting annotations from properties
    /*
    @PublishedApi
    internal var propertyExposed: String? = null

    @JvmField
    @PublishedApi
    internal var fieldExposed: String? = null
    */

}
