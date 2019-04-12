/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.utils

import org.gradle.api.Project

fun Project.addExtendsFromRelation(extendingConfigurationName: String, extendsFromConfigurationName: String, forced: Boolean = true) {
    if (extendingConfigurationName != extendsFromConfigurationName) {
        if (forced || configurations.findByName(extendingConfigurationName) != null) {
            project.dependencies.add(extendingConfigurationName, project.configurations.getByName(extendsFromConfigurationName))
        }
    }
}