/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.core.util

import com.intellij.openapi.project.Project
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import kotlin.reflect.KProperty

operator fun <T> CachedValue<T>.getValue(o: Any, property: KProperty<*>): T = value

fun <T> CachedValue(project: Project, trackValue: Boolean = false, provider: () -> CachedValueProvider.Result<T>) =
    CachedValuesManager.getManager(project).createCachedValue(provider, trackValue)
