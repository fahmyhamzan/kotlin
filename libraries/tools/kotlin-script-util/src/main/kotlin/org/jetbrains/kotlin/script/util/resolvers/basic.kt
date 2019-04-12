/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.script.util.resolvers

import org.jetbrains.kotlin.script.util.Repository
import org.jetbrains.kotlin.script.util.resolvers.experimental.*
import java.io.File
import java.net.MalformedURLException
import java.net.URL

class DirectResolver : GenericRepositoryWithBridge {
    override fun tryResolve(artifactCoordinates: GenericArtifactCoordinates): Iterable<File>? =
        artifactCoordinates.string.takeUnless(String::isBlank)
            ?.let(::File)?.takeIf { it.exists() && (it.isFile || it.isDirectory) }?.let { listOf(it) }

    override fun tryAddRepository(repositoryCoordinates: GenericRepositoryCoordinates): Boolean = false
}

class FlatLibDirectoryResolver(vararg paths: File) : GenericRepositoryWithBridge {

    private val localRepos = arrayListOf<File>()

    init {
        for (path in paths) {
            if (!path.exists() || !path.isDirectory) throw IllegalArgumentException("Invalid flat lib directory repository path '$path'")
        }
        localRepos.addAll(paths)
    }

    override fun tryResolve(artifactCoordinates: GenericArtifactCoordinates): Iterable<File>? {
        for (path in localRepos) {
            // TODO: add coordinates and wildcard matching
            val res = artifactCoordinates.string.takeUnless(String::isBlank)
                ?.let { File(path, it) }
                ?.takeIf { it.exists() && (it.isFile || it.isDirectory) }
            if (res != null) return listOf(res)
        }
        return null
    }

    override fun tryAddRepository(repositoryCoordinates: GenericRepositoryCoordinates): Boolean {
        val repoDir = repositoryCoordinates.file ?: return false
        localRepos.add(repoDir)
        return true
    }

    companion object {
        fun tryCreate(annotation: Repository): FlatLibDirectoryResolver? = tryCreate(
            BasicRepositoryCoordinates(
                annotation.url.takeUnless(String::isBlank) ?: annotation.value, annotation.id.takeUnless(String::isBlank)
            )
        )

        fun tryCreate(repositoryCoordinates: GenericRepositoryCoordinates): FlatLibDirectoryResolver? =
            repositoryCoordinates.file?.let { FlatLibDirectoryResolver(it) }
    }
}

internal fun String.toRepositoryUrlOrNull(): URL? =
    try {
        URL(this)
    } catch (_: MalformedURLException) {
        null
    }

internal fun String.toRepositoryFileOrNull(): File? =
    File(this).takeIf { it.exists() && it.isDirectory }
