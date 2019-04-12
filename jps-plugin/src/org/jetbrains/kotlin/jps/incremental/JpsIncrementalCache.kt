/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.jps.incremental

import org.jetbrains.jps.builders.storage.BuildDataPaths
import org.jetbrains.jps.builders.storage.StorageProvider
import org.jetbrains.jps.incremental.ModuleBuildTarget
import org.jetbrains.jps.incremental.storage.BuildDataManager
import org.jetbrains.jps.incremental.storage.StorageOwner
import org.jetbrains.kotlin.incremental.IncrementalCacheCommon
import org.jetbrains.kotlin.incremental.IncrementalJsCache
import org.jetbrains.kotlin.incremental.IncrementalJvmCache
import org.jetbrains.kotlin.jps.build.KotlinBuilder
import org.jetbrains.kotlin.jps.targets.KotlinModuleBuildTarget
import java.io.File

interface JpsIncrementalCache : IncrementalCacheCommon, StorageOwner {
    fun addJpsDependentCache(cache: JpsIncrementalCache)
}

class JpsIncrementalJvmCache(
    target: ModuleBuildTarget,
    paths: BuildDataPaths
) : IncrementalJvmCache(paths.getTargetDataRoot(target), target.outputDir), JpsIncrementalCache {
    override fun addJpsDependentCache(cache: JpsIncrementalCache) {
        if (cache is JpsIncrementalJvmCache) {
            addDependentCache(cache)
        }
    }

    override fun debugLog(message: String) {
        KotlinBuilder.LOG.debug(message)
    }
}

class JpsIncrementalJsCache(
    target: ModuleBuildTarget,
    paths: BuildDataPaths
) : IncrementalJsCache(paths.getTargetDataRoot(target)), JpsIncrementalCache {
    override fun addJpsDependentCache(cache: JpsIncrementalCache) {
        if (cache is JpsIncrementalJsCache) {
            addDependentCache(cache)
        }
    }
}

private class KotlinIncrementalStorageProvider(
    private val target: KotlinModuleBuildTarget<*>,
    private val paths: BuildDataPaths
) : StorageProvider<JpsIncrementalCache>() {
    init {
        check(target.hasCaches)
    }

    override fun equals(other: Any?) = other is KotlinIncrementalStorageProvider && target == other.target

    override fun hashCode() = target.hashCode()

    override fun createStorage(targetDataDir: File): JpsIncrementalCache = target.createCacheStorage(paths)
}

fun BuildDataManager.getKotlinCache(target: KotlinModuleBuildTarget<*>?): JpsIncrementalCache? =
    if (target == null || !target.hasCaches) null
    else getStorage(target.jpsModuleBuildTarget, KotlinIncrementalStorageProvider(target, dataPaths))

