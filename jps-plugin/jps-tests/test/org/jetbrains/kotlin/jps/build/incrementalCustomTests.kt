/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.jps.build

import org.jetbrains.kotlin.incremental.testingUtils.Modification

class IncrementalRenameModuleTest : AbstractIncrementalJpsTest() {
    fun testRenameModule() {
        doTest("jps-plugin/testData/incremental/custom/renameModule/")
    }

    override fun performAdditionalModifications(modifications: List<Modification>) {
        projectDescriptor.project.modules.forEach { it.name += "Renamed" }
    }
}