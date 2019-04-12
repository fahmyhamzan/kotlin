/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.jps.build

import com.intellij.openapi.diagnostic.Logger
import com.intellij.util.containers.ContainerUtil
import org.jetbrains.jps.ModuleChunk
import org.jetbrains.jps.builders.BuildRootDescriptor
import org.jetbrains.jps.builders.BuildTarget
import org.jetbrains.jps.builders.java.JavaSourceRootDescriptor
import org.jetbrains.jps.builders.java.dependencyView.Mappings
import org.jetbrains.jps.incremental.CompileContext
import org.jetbrains.jps.incremental.FSOperations
import org.jetbrains.jps.incremental.ModuleBuildTarget
import org.jetbrains.jps.incremental.fs.CompilationRound
import java.io.File
import java.util.HashMap
import kotlin.collections.*

/**
 * Entry point for safely marking files as dirty.
 */
class FSOperationsHelper(
    private val compileContext: CompileContext,
    private val chunk: ModuleChunk,
    private val dirtyFilesHolder: KotlinDirtySourceFilesHolder,
    private val log: Logger
) {
    private val moduleBasedFilter = ModulesBasedFileFilter(compileContext, chunk)

    internal var hasMarkedDirty = false
        private set

    private val buildLogger = compileContext.testingContext?.buildLogger

    fun markChunk(recursively: Boolean, kotlinOnly: Boolean, excludeFiles: Set<File> = setOf()) {
        fun shouldMark(file: File): Boolean {
            if (kotlinOnly && !file.isKotlinSourceFile) return false

            if (file in excludeFiles) return false

            hasMarkedDirty = true
            return true
        }

        if (recursively) {
            FSOperations.markDirtyRecursively(compileContext, CompilationRound.NEXT, chunk, ::shouldMark)
        } else {
            FSOperations.markDirty(compileContext, CompilationRound.NEXT, chunk, ::shouldMark)
        }
    }

    internal fun markFilesForCurrentRound(files: Iterable<File>) {
        files.forEach {
            val root = compileContext.projectDescriptor.buildRootIndex.findJavaRootDescriptor(compileContext, it)
            if (root != null) dirtyFilesHolder.byTarget[root.target]?._markDirty(it, root)
        }

        markFilesImpl(files, currentRound = true) { it.exists() && moduleBasedFilter.accept(it) }
    }

    /**
     * Marks given [files] as dirty for current round and given [target] of [chunk].
     */
    fun markFilesForCurrentRound(target: ModuleBuildTarget, files: Iterable<File>) {
        require(target in chunk.targets)

        val targetDirtyFiles = dirtyFilesHolder.byTarget.getValue(target)
        files.forEach { file ->
            val root = compileContext.projectDescriptor.buildRootIndex
                .findAllParentDescriptors<BuildRootDescriptor>(file, compileContext)
                .single { sourceRoot -> sourceRoot.target == target }

            targetDirtyFiles._markDirty(file, root as JavaSourceRootDescriptor)
        }

        markFilesImpl(files, currentRound = true) { it.exists() }
    }

    fun markFiles(files: Iterable<File>) {
        markFilesImpl(files, currentRound = false) { it.exists() }
    }

    fun markInChunkOrDependents(files: Iterable<File>, excludeFiles: Set<File>) {
        markFilesImpl(files, currentRound = false) {
            it !in excludeFiles && it.exists() && moduleBasedFilter.accept(it)
        }
    }

    private inline fun markFilesImpl(
        files: Iterable<File>,
        currentRound: Boolean,
        shouldMark: (File) -> Boolean
    ) {
        val filesToMark = files.filterTo(HashSet(), shouldMark)
        if (filesToMark.isEmpty()) return

        val compilationRound = if (currentRound) {
            buildLogger?.markedAsDirtyBeforeRound(filesToMark)
            CompilationRound.CURRENT
        } else {
            buildLogger?.markedAsDirtyAfterRound(filesToMark)
            hasMarkedDirty = true
            CompilationRound.NEXT
        }

        for (fileToMark in filesToMark) {
            FSOperations.markDirty(compileContext, compilationRound, fileToMark)
        }
        log.debug("Mark dirty: $filesToMark ($compilationRound)")
    }

    // Based on `JavaBuilderUtil#ModulesBasedFileFilter` from Intellij
    private class ModulesBasedFileFilter(
        private val context: CompileContext,
        chunk: ModuleChunk
    ) : Mappings.DependentFilesFilter {
        private val chunkTargets = chunk.targets
        private val buildRootIndex = context.projectDescriptor.buildRootIndex
        private val buildTargetIndex = context.projectDescriptor.buildTargetIndex
        private val cache = HashMap<BuildTarget<*>, Set<BuildTarget<*>>>()

        override fun accept(file: File): Boolean {
            val rd = buildRootIndex.findJavaRootDescriptor(context, file) ?: return true
            val target = rd.target
            if (target in chunkTargets) return true

            val targetOfFileWithDependencies = cache.getOrPut(target) { buildTargetIndex.getDependenciesRecursively(target, context) }
            return ContainerUtil.intersects(targetOfFileWithDependencies, chunkTargets)
        }

        override fun belongsToCurrentTargetChunk(file: File): Boolean {
            val rd = buildRootIndex.findJavaRootDescriptor(context, file)
            return rd != null && chunkTargets.contains(rd.target)
        }
    }
}
