/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.allopen.ide

import org.jetbrains.kotlin.allopen.AllOpenCommandLineProcessor
import org.jetbrains.kotlin.annotation.plugin.ide.AbstractMavenImportHandler
import org.jetbrains.kotlin.annotation.plugin.ide.AnnotationBasedCompilerPluginSetup.PluginOption
import org.jetbrains.kotlin.utils.PathUtil

class AllOpenMavenProjectImportHandler : AbstractMavenImportHandler() {
    private companion object {
        val ANNOTATION_PARAMETER_PREFIX = "all-open:${AllOpenCommandLineProcessor.ANNOTATION_OPTION.optionName}="
    }

    override val compilerPluginId = AllOpenCommandLineProcessor.PLUGIN_ID
    override val pluginName = "allopen"
    override val mavenPluginArtifactName = "kotlin-maven-allopen"
    override val pluginJarFileFromIdea = PathUtil.kotlinPathsForIdeaPlugin.allOpenPluginJarPath

    override fun getOptions(enabledCompilerPlugins: List<String>, compilerPluginOptions: List<String>): List<PluginOption>? {
        if ("all-open" !in enabledCompilerPlugins && "spring" !in enabledCompilerPlugins) {
            return null
        }

        val annotations = mutableListOf<String>()

        for ((presetName, presetAnnotations) in AllOpenCommandLineProcessor.SUPPORTED_PRESETS) {
            if (presetName in enabledCompilerPlugins) {
                annotations.addAll(presetAnnotations)
            }
        }

        annotations.addAll(compilerPluginOptions.mapNotNull { text ->
            if (!text.startsWith(ANNOTATION_PARAMETER_PREFIX)) return@mapNotNull null
            text.substring(ANNOTATION_PARAMETER_PREFIX.length)
        })

        return annotations.map { PluginOption(AllOpenCommandLineProcessor.ANNOTATION_OPTION.optionName, it) }
    }
}
