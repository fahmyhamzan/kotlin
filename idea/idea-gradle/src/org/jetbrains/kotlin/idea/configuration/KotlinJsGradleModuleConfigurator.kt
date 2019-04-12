/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.configuration

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.idea.util.module
import org.jetbrains.kotlin.idea.versions.MAVEN_JS_STDLIB_ID
import org.jetbrains.kotlin.js.resolve.JsPlatform
import org.jetbrains.kotlin.resolve.TargetPlatform

class KotlinJsGradleModuleConfigurator : KotlinWithGradleConfigurator() {
    override val name: String = "gradle-js"
    override val presentableText: String = "JavaScript with Gradle"
    override val targetPlatform: TargetPlatform = JsPlatform
    override val kotlinPluginName: String = KOTLIN_JS
    override fun getKotlinPluginExpression(forKotlinDsl: Boolean): String =
        if (forKotlinDsl) "id(\"kotlin2js\")" else "id 'kotlin2js'"
    override fun getMinimumSupportedVersion() = "1.1.0"
    override fun getStdlibArtifactName(sdk: Sdk?, version: String): String = MAVEN_JS_STDLIB_ID

    override fun addElementsToFile(file: PsiFile, isTopLevelProjectFile: Boolean, version: String): Boolean {
        val gradleVersion = fetchGradleVersion(file)

        if (getManipulator(file).useNewSyntax(kotlinPluginName, gradleVersion)) {
            val settingsPsiFile = if (isTopLevelProjectFile) {
                file.module?.getTopLevelBuildScriptSettingsPsiFile()
            } else {
                file.module?.getBuildScriptSettingsPsiFile()
            }
            if (settingsPsiFile != null) {
                getManipulator(settingsPsiFile).addResolutionStrategy("kotlin2js")
            }
        }

        return super.addElementsToFile(file, isTopLevelProjectFile, version)
    }

    companion object {
        val KOTLIN_JS = "kotlin2js"
    }
}
