/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.util

import org.jetbrains.kotlin.load.kotlin.ModuleVisibilityManager
import org.jetbrains.kotlin.modules.Module

class IdeModuleVisibilityManagerImpl() : ModuleVisibilityManager {
    override val chunk: Collection<Module> = emptyList()
    override val friendPaths: Collection<String> = emptyList()
    override fun addModule(module: Module) {}
    override fun addFriendPath(path: String) {}
}
