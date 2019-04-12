/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.jps.build

inline fun withSystemProperty(property: String, newValue: String?, fn: ()->Unit) {
    val backup = System.getProperty(property)
    setOrClearSysProperty(property, newValue)

    try {
        fn()
    }
    finally {
        setOrClearSysProperty(property, backup)
    }
}


@Suppress("NOTHING_TO_INLINE")
inline fun setOrClearSysProperty(property: String, newValue: String?) {
    if (newValue != null) {
        System.setProperty(property, newValue)
    }
    else {
        System.clearProperty(property)
    }
}