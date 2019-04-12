/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.noarg

import com.intellij.mock.MockProject
import org.jetbrains.kotlin.codegen.extensions.ExpressionCodegenExtension
import org.jetbrains.kotlin.compiler.plugin.*
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.container.StorageComponentContainer
import org.jetbrains.kotlin.container.useInstance
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.extensions.StorageComponentContainerContributor
import org.jetbrains.kotlin.noarg.NoArgCommandLineProcessor.Companion.SUPPORTED_PRESETS
import org.jetbrains.kotlin.noarg.NoArgConfigurationKeys.ANNOTATION
import org.jetbrains.kotlin.noarg.NoArgConfigurationKeys.INVOKE_INITIALIZERS
import org.jetbrains.kotlin.noarg.NoArgConfigurationKeys.PRESET
import org.jetbrains.kotlin.noarg.diagnostic.CliNoArgDeclarationChecker
import org.jetbrains.kotlin.resolve.TargetPlatform
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatform

object NoArgConfigurationKeys {
    val ANNOTATION: CompilerConfigurationKey<List<String>> =
            CompilerConfigurationKey.create("annotation qualified name")

    val PRESET: CompilerConfigurationKey<List<String>> = CompilerConfigurationKey.create("annotation preset")

    val INVOKE_INITIALIZERS: CompilerConfigurationKey<Boolean> = CompilerConfigurationKey.create(
            "invoke instance initializers in a no-arg constructor")
}

class NoArgCommandLineProcessor : CommandLineProcessor {
    companion object {
        val SUPPORTED_PRESETS = mapOf(
                "jpa" to listOf("javax.persistence.Entity", "javax.persistence.Embeddable", "javax.persistence.MappedSuperclass"))

        val ANNOTATION_OPTION = CliOption("annotation", "<fqname>", "Annotation qualified names",
                                          required = false, allowMultipleOccurrences = true)

        val PRESET_OPTION = CliOption("preset", "<name>", "Preset name (${SUPPORTED_PRESETS.keys.joinToString()})",
                                      required = false, allowMultipleOccurrences = true)

        val INVOKE_INITIALIZERS_OPTION = CliOption("invokeInitializers", "true/false",
                                                   "Invoke instance initializers in a no-arg constructor",
                                                   required = false, allowMultipleOccurrences = false)

        val PLUGIN_ID = "org.jetbrains.kotlin.noarg"
    }

    override val pluginId = PLUGIN_ID
    override val pluginOptions = listOf(ANNOTATION_OPTION, PRESET_OPTION, INVOKE_INITIALIZERS_OPTION)

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) = when (option) {
        ANNOTATION_OPTION -> configuration.appendList(ANNOTATION, value)
        PRESET_OPTION -> configuration.appendList(PRESET, value)
        INVOKE_INITIALIZERS_OPTION -> configuration.put(INVOKE_INITIALIZERS, value == "true")
        else -> throw CliOptionProcessingException("Unknown option: ${option.optionName}")
    }
}

class NoArgComponentRegistrar : ComponentRegistrar {
    override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
        val annotations = configuration.get(ANNOTATION)?.toMutableList() ?: mutableListOf()
        configuration.get(PRESET)?.forEach { preset ->
            SUPPORTED_PRESETS[preset]?.let { annotations += it }
        }
        if (annotations.isEmpty()) return

        StorageComponentContainerContributor.registerExtension(project, CliNoArgComponentContainerContributor(annotations))

        val invokeInitializers = configuration[INVOKE_INITIALIZERS] ?: false
        ExpressionCodegenExtension.registerExtension(project, NoArgExpressionCodegenExtension(invokeInitializers))
    }
}

class CliNoArgComponentContainerContributor(val annotations: List<String>) : StorageComponentContainerContributor {
    override fun registerModuleComponents(
            container: StorageComponentContainer, platform: TargetPlatform, moduleDescriptor: ModuleDescriptor
    ) {
        if (platform != JvmPlatform) return

        container.useInstance(CliNoArgDeclarationChecker(annotations))
    }
}