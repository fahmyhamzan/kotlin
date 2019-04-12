/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.android.synthetic.test

import com.intellij.openapi.util.io.FileUtil
import com.intellij.util.ArrayUtil
import com.intellij.util.Processor
import org.jetbrains.kotlin.cli.jvm.config.jvmClasspathRoots
import org.jetbrains.kotlin.codegen.AbstractBlackBoxCodegenTest
import org.jetbrains.kotlin.codegen.CodegenTestFiles
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.test.ConfigurationKind
import org.jetbrains.kotlin.test.KotlinTestUtils
import org.jetbrains.kotlin.test.TestJdkKind
import java.io.File
import java.net.URL
import java.util.*
import java.util.regex.Pattern

abstract class AbstractAndroidBoxTest : AbstractBlackBoxCodegenTest() {

    private fun createAndroidAPIEnvironment(path: String) {
        return createEnvironmentForConfiguration(KotlinTestUtils.newConfiguration(ConfigurationKind.ALL, TestJdkKind.ANDROID_API), path)
    }

    private fun createFakeAndroidEnvironment(path: String) {
        return createEnvironmentForConfiguration(KotlinTestUtils.newConfiguration(ConfigurationKind.ALL, TestJdkKind.MOCK_JDK), path)
    }

    private fun createEnvironmentForConfiguration(configuration: CompilerConfiguration, path: String) {
        val layoutPaths = File(path).listFiles { it -> it.name.startsWith("layout") && it.isDirectory }!!.map { "$path${it.name}/" }
        myEnvironment = createTestEnvironment(configuration, layoutPaths)
    }

    fun doCompileAgainstAndroidSdkTest(path: String) {
        createAndroidAPIEnvironment(path)
        doMultiFileTest(path)
    }

    fun doFakeInvocationTest(path: String) {
        if (needsInvocationTest(path)) {
            createFakeAndroidEnvironment(path)
            doMultiFileTest(path, getFakeFiles(path))
        }
    }

    override fun getClassPathURLs(): Array<URL> {
        return myEnvironment.configuration.jvmClasspathRoots.map { it.toURI().toURL() }.toTypedArray()
    }

    private fun getFakeFiles(path: String): Collection<String> {
        return FileUtil.findFilesByMask(Pattern.compile("^Fake.*\\.kt$"), File(path.replace(getTestName(true), ""))).map { relativePath(it) }
    }

    private fun needsInvocationTest(path: String): Boolean {
        return !FileUtil.findFilesByMask(Pattern.compile("^0.kt$"), File(path)).isEmpty()
    }

    override fun codegenTestBasePath(): String {
        return "plugins/android-extensions/android-extensions-compiler/testData/codegen/"
    }

    private fun doMultiFileTest(path: String, additionalFiles: Collection<String>? = null) {
        val files = mutableListOf<String>()
        FileUtil.processFilesRecursively(File(path), object : Processor<File> {
            override fun process(file: File?): Boolean {
                when (file!!.name) {
                    "1.kt" -> {
                        if (additionalFiles == null) files.add(relativePath(file))
                    }
                    "0.kt" -> {
                        if (additionalFiles != null) files.add(relativePath(file))
                    }
                    else -> {
                        if (file.name.endsWith(".kt")) files.add(relativePath(file))
                    }
                }
                return true
            }
        })

        for (file in File("plugins/android-extensions/android-extensions-runtime/src").walk()) {
            if (file.extension == "kt") files += relativePath(file.absoluteFile)
        }

        Collections.sort(files)
        if (additionalFiles != null) {
            files.addAll(additionalFiles)
        }
        myFiles = CodegenTestFiles.create(
                myEnvironment!!.project,
                ArrayUtil.toStringArray(files),
                KotlinTestUtils.getHomeDirectory() + "/plugins/android-extensions/android-extensions-compiler/testData"
        )
        blackBox(true)
    }
}
