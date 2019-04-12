/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.jps.build

import com.intellij.testFramework.UsefulTestCase
import org.jetbrains.jps.builders.BuildTarget
import org.jetbrains.jps.builders.storage.BuildDataPaths
import org.jetbrains.kotlin.config.IncrementalCompilation
import org.jetbrains.kotlin.incremental.KOTLIN_CACHE_DIRECTORY_NAME
import org.jetbrains.kotlin.incremental.storage.BasicMapsOwner
import org.jetbrains.kotlin.incremental.testingUtils.Modification
import org.jetbrains.kotlin.incremental.testingUtils.ModifyContent
import org.jetbrains.kotlin.jps.incremental.KotlinDataContainerTarget
import org.jetbrains.kotlin.jps.targets.KotlinModuleBuildTarget
import org.jetbrains.kotlin.utils.Printer
import java.io.File

abstract class AbstractIncrementalLazyCachesTest : AbstractIncrementalJpsTest() {
    private val expectedCachesFileName: String
        get() = "expected-kotlin-caches.txt"

    private var isICEnabledBackup: Boolean = false

    override fun setUp() {
        super.setUp()
        isICEnabledBackup = IncrementalCompilation.isEnabledForJvm()
        IncrementalCompilation.setIsEnabledForJvm(true)
    }

    override fun tearDown() {
        IncrementalCompilation.setIsEnabledForJvm(isICEnabledBackup)
        super.tearDown()
    }

    override fun doTest(testDataPath: String) {
        super.doTest(testDataPath)

        val actual = dumpKotlinCachesFileNames()
        val expectedFile = File(testDataPath, expectedCachesFileName)
        UsefulTestCase.assertSameLinesWithFile(expectedFile.canonicalPath, actual)
    }

    override fun performAdditionalModifications(modifications: List<Modification>) {
        super.performAdditionalModifications(modifications)

        for (modification in modifications) {
            if (modification !is ModifyContent) continue

            val name = File(modification.path).name

            when {
                name.endsWith("incremental-compilation") -> {
                    IncrementalCompilation.setIsEnabledForJvm(modification.dataFile.readAsBool())
                }
            }
        }
    }

    fun File.readAsBool(): Boolean {
        val content = this.readText()

        return when (content.trim()) {
            "on" -> true
            "off" -> false
            else -> throw IllegalStateException("$this content is expected to be 'on' or 'off'")
        }
    }

    private fun dumpKotlinCachesFileNames(): String {
        val sb = StringBuilder()
        val printer = Printer(sb)
        val chunks = kotlinCompileContext.targetsIndex.chunks
        val dataManager = projectDescriptor.dataManager
        val paths = dataManager.dataPaths

        dumpCachesForTarget(
            printer,
            paths,
            KotlinDataContainerTarget,
            kotlinCompileContext.lookupsCacheAttributesManager.versionManagerForTesting.versionFileForTesting
        )

        data class TargetInChunk(val chunk: KotlinChunk, val target: KotlinModuleBuildTarget<*>)

        val allTargets = chunks.flatMap { chunk ->
            chunk.targets.map { target ->
                TargetInChunk(chunk, target)
            }
        }.sortedBy { it.target.jpsModuleBuildTarget.presentableName }

        allTargets.forEach { (chunk, target) ->
            val metaBuildInfo = chunk.buildMetaInfoFile(target.jpsModuleBuildTarget)
            dumpCachesForTarget(
                printer, paths, target.jpsModuleBuildTarget,
                target.localCacheVersionManager.versionFileForTesting,
                metaBuildInfo,
                subdirectory = KOTLIN_CACHE_DIRECTORY_NAME
            )
        }


        return sb.toString()
    }

    private fun dumpCachesForTarget(
        p: Printer,
        paths: BuildDataPaths,
        target: BuildTarget<*>,
        vararg cacheVersionsFiles: File,
        subdirectory: String? = null
    ) {
        p.println(target)
        p.pushIndent()

        val dataRoot = paths.getTargetDataRoot(target).let { if (subdirectory != null) File(it, subdirectory) else it }
        cacheVersionsFiles
            .filter(File::exists)
            .sortedBy { it.name }
            .forEach { p.println(it.name) }

        kotlinCacheNames(dataRoot).sorted().forEach { p.println(it) }

        p.popIndent()
    }

    private fun kotlinCacheNames(dir: File): List<String> {
        val result = arrayListOf<String>()

        for (file in dir.walk()) {
            if (file.isFile && file.extension == BasicMapsOwner.CACHE_EXTENSION) {
                result.add(file.name)
            }
        }

        return result
    }
}
