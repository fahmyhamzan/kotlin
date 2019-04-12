/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.jps.build

import com.intellij.openapi.util.io.FileUtil
import org.jetbrains.jps.model.JpsDummyElement
import org.jetbrains.jps.model.JpsModuleRootModificationUtil
import org.jetbrains.jps.model.JpsProject
import org.jetbrains.jps.model.java.JpsJavaDependencyScope
import org.jetbrains.jps.model.java.JpsJavaLibraryType
import org.jetbrains.jps.model.java.JpsJavaSdkType
import org.jetbrains.jps.model.library.JpsLibrary
import org.jetbrains.jps.model.library.JpsOrderRootType
import org.jetbrains.jps.model.library.sdk.JpsSdk
import org.jetbrains.jps.model.module.JpsModule
import org.jetbrains.jps.util.JpsPathUtil
import org.jetbrains.kotlin.codegen.forTestCompile.ForTestCompileRuntime
import org.jetbrains.kotlin.utils.PathUtil

import java.io.File
import java.io.IOException

abstract class AbstractKotlinJpsBuildTestCase : BaseKotlinJpsBuildTestCase() {
    protected lateinit var workDir: File

    @Throws(IOException::class)
    override fun doGetProjectDir(): File? {
        return workDir
    }

    override fun addJdk(name: String, path: String?): JpsSdk<JpsDummyElement> {
        val homePath = System.getProperty("java.home")
        val versionString = System.getProperty("java.version")
        val jdk = myModel.global.addSdk(name, homePath, versionString, JpsJavaSdkType.INSTANCE)
        jdk.addRoot(JpsPathUtil.pathToUrl(path), JpsOrderRootType.COMPILED)
        return jdk.properties
    }

    protected fun addKotlinMockRuntimeDependency(): JpsLibrary {
        return addDependency(KotlinJpsLibrary.MockRuntime)
    }

    protected fun addKotlinStdlibDependency(): JpsLibrary {
        return addDependency(KotlinJpsLibrary.JvmStdLib)
    }

    protected fun addKotlinJavaScriptStdlibDependency(): JpsLibrary {
        return addDependency(KotlinJpsLibrary.JsStdLib)
    }

    private fun addDependency(library: KotlinJpsLibrary): JpsLibrary {
        return addDependency(myProject, library)
    }

    protected fun addDependency(libraryName: String, libraryFile: File): JpsLibrary {
        return addDependency(myProject, libraryName, libraryFile)
    }

    companion object {
        val TEST_DATA_PATH = "jps-plugin/testData/"

        @Throws(IOException::class)
        @JvmStatic
        protected fun copyTestDataToTmpDir(testDataDir: File): File {
            assert(testDataDir.exists()) { "Cannot find source folder " + testDataDir.absolutePath }
            val tmpDir = FileUtil.createTempDirectory("jps-build", null)
            FileUtil.copyDir(testDataDir, tmpDir)
            return tmpDir
        }

        @JvmStatic
        protected fun addKotlinStdlibDependency(modules: Collection<JpsModule>, exported: Boolean = false): JpsLibrary {
            return addDependency(modules, KotlinJpsLibrary.JvmStdLib, exported)
        }

        @JvmStatic
        private fun addDependency(project: JpsProject, library: KotlinJpsLibrary, exported: Boolean = false): JpsLibrary {
            return addDependency(JpsJavaDependencyScope.COMPILE, project.modules, exported, library.id, *library.roots)
        }

        @JvmStatic
        private fun addDependency(modules: Collection<JpsModule>, library: KotlinJpsLibrary, exported: Boolean = false): JpsLibrary {
            return addDependency(JpsJavaDependencyScope.COMPILE, modules, exported, library.id, *library.roots)
        }

        @JvmStatic
        private fun addDependency(project: JpsProject, libraryName: String, libraryFile: File): JpsLibrary {
            return addDependency(JpsJavaDependencyScope.COMPILE, project.modules, false, libraryName, libraryFile)
        }

        @JvmStatic
        protected fun addDependency(
            type: JpsJavaDependencyScope,
            modules: Collection<JpsModule>,
            exported: Boolean,
            libraryName: String,
            vararg file: File
        ): JpsLibrary {
            val library = modules.iterator().next().project.addLibrary(libraryName, JpsJavaLibraryType.INSTANCE)

            for (fileRoot in file) {
                library.addRoot(fileRoot, JpsOrderRootType.COMPILED)
            }

            for (module in modules) {
                JpsModuleRootModificationUtil.addDependency(module, library, type, exported)
            }
            return library
        }
    }
}
