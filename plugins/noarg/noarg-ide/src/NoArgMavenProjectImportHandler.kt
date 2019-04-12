/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.noarg.ide

import org.jetbrains.kotlin.annotation.plugin.ide.AbstractMavenImportHandler
import org.jetbrains.kotlin.annotation.plugin.ide.AnnotationBasedCompilerPluginSetup.PluginOption
import org.jetbrains.kotlin.noarg.NoArgCommandLineProcessor
import org.jetbrains.kotlin.utils.PathUtil

class NoArgMavenProjectImportHandler : AbstractMavenImportHandler() {
    private companion object {
        val ANNOTATATION_PARAMETER_PREFIX = "no-arg:${NoArgCommandLineProcessor.ANNOTATION_OPTION.optionName}="
        val INVOKEINITIALIZERS_PARAMETER_PREFIX = "no-arg:${NoArgCommandLineProcessor.INVOKE_INITIALIZERS_OPTION.optionName}="
    }

    override val compilerPluginId = NoArgCommandLineProcessor.PLUGIN_ID
    override val pluginName = "noarg"
    override val mavenPluginArtifactName = "kotlin-maven-noarg"
    override val pluginJarFileFromIdea = PathUtil.kotlinPathsForIdeaPlugin.noArgPluginJarPath

    override fun getOptions(enabledCompilerPlugins: List<String>, compilerPluginOptions: List<String>): List<PluginOption>? {
        if ("no-arg" !in enabledCompilerPlugins && "jpa" !in enabledCompilerPlugins) {
            return null
        }

        val annotations = mutableListOf<String>()
        for ((presetName, presetAnnotations) in NoArgCommandLineProcessor.SUPPORTED_PRESETS) {
            if (presetName in enabledCompilerPlugins) {
                annotations.addAll(presetAnnotations)
            }
        }

        annotations.addAll(compilerPluginOptions.mapNotNull { text ->
            if (!text.startsWith(ANNOTATATION_PARAMETER_PREFIX)) return@mapNotNull null
            text.substring(ANNOTATATION_PARAMETER_PREFIX.length)
        })

        val options = annotations.mapTo(mutableListOf()) { PluginOption(NoArgCommandLineProcessor.ANNOTATION_OPTION.optionName, it) }

        val invokeInitializerOptionValue = compilerPluginOptions
                .firstOrNull { it.startsWith(INVOKEINITIALIZERS_PARAMETER_PREFIX) }
                ?.drop(INVOKEINITIALIZERS_PARAMETER_PREFIX.length) == "true"

        if (invokeInitializerOptionValue) {
            options.add(PluginOption(NoArgCommandLineProcessor.INVOKE_INITIALIZERS_OPTION.optionName, "true"))
        }

        return options
    }
}
