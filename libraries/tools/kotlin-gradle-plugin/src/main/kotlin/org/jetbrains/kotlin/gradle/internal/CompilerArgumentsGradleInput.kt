/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.internal

import org.jetbrains.kotlin.cli.common.arguments.*
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

internal object CompilerArgumentsGradleInput {
    fun <T : CommonToolArguments> createInputsMap(args: T): Map<String, String> {
        @Suppress("UNCHECKED_CAST")
        val argumentProperties =
            args::class.members.mapNotNull { member ->
                (member as? KProperty1<T, *>)?.takeIf { it.annotations.any { ann -> ann is Argument } }
            } +
                    (CommonToolArguments::freeArgs as KProperty1<T, *>)

        val filteredProperties = argumentProperties.filterNot { it in ignoredProperties }

        fun inputItem(property: KProperty1<out T, *>): Pair<String, String> {
            @Suppress("UNCHECKED_CAST")
            val value = (property as KProperty1<T, *>).get(args)
            return property.name to if (value is Array<*>)
                value.asList().toString()
            else
                value.toString()
        }

        return filteredProperties.associate(::inputItem).toSortedMap()
    }

    // We ignore some file properties e.g. to instead include their values into the Gradle file property checks,
    // which, unlike String checks, run with specified path sensitivity;
    private val ignoredProperties = setOf<KProperty<*>>(
        CommonCompilerArguments::verbose, // debug should not lead to rebuild

        K2JVMCompilerArguments::destination, // handled by destinationDir
        K2JVMCompilerArguments::classpath, // handled by classpath of the Gradle tasks
        K2JVMCompilerArguments::friendPaths, // is part of the classpath
        K2JVMCompilerArguments::jdkHome, // JDK can be both placed differently and contain different files on user machines
        K2JVMCompilerArguments::buildFile, // in Gradle build, these XMLs are transient and provide no useful info
        K2JVMCompilerArguments::pluginOptions, // handled specially in the task
        K2JVMCompilerArguments::pluginClasspaths, // handled in the task as classpath
        K2JVMCompilerArguments::javaSourceRoots, // handled in inputs

        K2JSCompilerArguments::outputFile, // already handled by Gradle task property
        K2JSCompilerArguments::libraries, // defined by by classpath and friendDependency of the Gradle task
        K2JSCompilerArguments::sourceMapBaseDirs, // defined by sources
        K2JSCompilerArguments::friendModules, // handled by Gradle task friendDependency property
        K2JSCompilerArguments::pluginOptions, // handled specially in the task
        K2JSCompilerArguments::pluginClasspaths, // handled in the task as classpath

        K2MetadataCompilerArguments::classpath, // handled by classpath of the Gradle task
        K2MetadataCompilerArguments::destination, // handled by destinationDir
        K2MetadataCompilerArguments::pluginOptions, // handled specially in the task
        K2MetadataCompilerArguments::pluginClasspaths, // handled in the task as classpath

        K2JSDceArguments::outputDirectory // handled by destinationDir
    )
}