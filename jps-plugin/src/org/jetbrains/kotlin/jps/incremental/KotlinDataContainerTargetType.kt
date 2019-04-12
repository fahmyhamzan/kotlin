/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.jps.incremental

import org.jetbrains.jps.builders.*
import org.jetbrains.jps.builders.storage.BuildDataPaths
import org.jetbrains.jps.incremental.CompileContext
import org.jetbrains.jps.indices.IgnoredFileIndex
import org.jetbrains.jps.indices.ModuleExcludeIndex
import org.jetbrains.jps.model.JpsModel
import java.io.File

private val KOTLIN_DATA_CONTAINER = "kotlin-data-container"

object KotlinDataContainerTargetType : BuildTargetType<KotlinDataContainerTarget>(KOTLIN_DATA_CONTAINER) {
    override fun computeAllTargets(model: JpsModel): List<KotlinDataContainerTarget> = listOf(KotlinDataContainerTarget)

    override fun createLoader(model: JpsModel): BuildTargetLoader<KotlinDataContainerTarget> =
        object : BuildTargetLoader<KotlinDataContainerTarget>() {
            override fun createTarget(targetId: String): KotlinDataContainerTarget? = KotlinDataContainerTarget
        }
}

// Fake target to store data per project for incremental compilation
object KotlinDataContainerTarget : BuildTarget<BuildRootDescriptor>(KotlinDataContainerTargetType) {
    override fun getId(): String? = KOTLIN_DATA_CONTAINER
    override fun getPresentableName(): String = KOTLIN_DATA_CONTAINER

    override fun computeRootDescriptors(
        model: JpsModel?,
        index: ModuleExcludeIndex?,
        ignoredFileIndex: IgnoredFileIndex?,
        dataPaths: BuildDataPaths?
    ): List<BuildRootDescriptor> = listOf()

    override fun getOutputRoots(context: CompileContext): Collection<File> {
        val dataManager = context.projectDescriptor.dataManager
        val storageRoot = dataManager.dataPaths.dataStorageRoot
        return listOf(File(storageRoot, KOTLIN_DATA_CONTAINER))
    }

    override fun findRootDescriptor(rootId: String?, rootIndex: BuildRootIndex?): BuildRootDescriptor? = null

    override fun computeDependencies(
        targetRegistry: BuildTargetRegistry?,
        outputIndex: TargetOutputIndex?
    ): Collection<BuildTarget<*>>? = listOf()
}
