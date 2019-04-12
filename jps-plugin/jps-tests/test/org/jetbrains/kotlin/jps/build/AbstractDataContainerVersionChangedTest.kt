/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.jps.build

import org.jetbrains.kotlin.incremental.testingUtils.BuildLogFinder
import org.jetbrains.kotlin.jps.targets.KotlinModuleBuildTarget

/**
 * @see [jps-plugin/testData/incremental/cacheVersionChanged/README.md]
 */
abstract class AbstractDataContainerVersionChangedTest : AbstractIncrementalCacheVersionChangedTest() {
    override val buildLogFinder: BuildLogFinder
        get() = BuildLogFinder(isDataContainerBuildLogEnabled = true)

    override fun getVersionManagersToTest(target: KotlinModuleBuildTarget<*>) =
        listOf(kotlinCompileContext.lookupsCacheAttributesManager.versionManagerForTesting)
}