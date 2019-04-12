/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.internal.makeBackup

import com.intellij.history.LocalHistory
import com.intellij.openapi.compiler.CompileContext
import com.intellij.openapi.compiler.CompileTask
import com.intellij.openapi.util.Key
import java.util.*

val random = Random()

val HISTORY_LABEL_KEY = Key.create<String>("history label")

class MakeBackupCompileTask : CompileTask {
    override fun execute(context: CompileContext?): Boolean {
        val project = context!!.project!!

        val localHistory = LocalHistory.getInstance()!!
        val label = HISTORY_LABEL_PREFIX + Integer.toHexString(random.nextInt())
        localHistory.putSystemLabel(project, label)

        context.compileScope!!.putUserData(HISTORY_LABEL_KEY, label)

        return true
    }
}
