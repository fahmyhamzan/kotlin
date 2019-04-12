/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.internal

import com.intellij.openapi.fileTypes.StdFileTypes
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.java.decompiler.IdeaLogger
import org.jetbrains.java.decompiler.main.decompiler.BaseDecompiler
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences
import org.jetbrains.java.decompiler.main.extern.IResultSaver
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.languageVersionSettings
import org.jetbrains.kotlin.idea.actions.canBeDecompiledToJava
import org.jetbrains.kotlin.idea.project.languageVersionSettings
import org.jetbrains.kotlin.idea.util.application.runReadAction
import org.jetbrains.kotlin.psi.KtFile
import java.io.File
import java.util.jar.Manifest

class KotlinDecompilerServiceImpl : KotlinDecompilerService {
    override fun decompile(file: KtFile): String? {
        try {
            val bytecodeMap: Map<File, () -> ByteArray> = runReadAction {
                when {
                    file.canBeDecompiledToJava() -> bytecodeMapForExistingClassfile(file.virtualFile)
                    !file.isCompiled -> bytecodeMapForSourceFile(file)
                    else -> null
                }
            } ?: return null

            val resultSaver = KotlinResultSaver()
            val options = hashMapOf<String, Any>(
                IFernflowerPreferences.REMOVE_BRIDGE to "0"
            )

            val bytecodeProvider = IBytecodeProvider { externalPath, _ ->
                val path = File(FileUtil.toSystemIndependentName(externalPath))
                bytecodeMap[path]?.invoke()
            }

            val decompiler = BaseDecompiler(bytecodeProvider, resultSaver, options, IdeaLogger())
            for (path in bytecodeMap.keys) {
                decompiler.addSpaceEx(path, true)
            }
            decompiler.decompileContext()
            return resultSaver.resultText
        } catch (ex: IdeaLogger.InternalException) {
            throw DecompileFailedException(ex.message ?: "Unknown error", ex)
        }
    }

    private fun bytecodeMapForExistingClassfile(file: VirtualFile): Map<File, () -> ByteArray> {
        val mask = "${file.nameWithoutExtension}$"
        val files =
            mapOf(file.path to file) +
                    file.parent.children.filter {
                        it.nameWithoutExtension.startsWith(mask) && it.fileType === StdFileTypes.CLASS
                    }.map { it.path to it }

        return files.entries.associate {
            Pair(File(it.key), { it.value.contentsToByteArray(false) })
        }
    }

    private fun bytecodeMapForSourceFile(file: KtFile): Map<File, () -> ByteArray> {
        val configuration = CompilerConfiguration().apply {
            languageVersionSettings = file.languageVersionSettings
        }
        val generationState = KotlinBytecodeToolWindow.compileSingleFile(file, configuration) ?: return emptyMap()

        val bytecodeMap = hashMapOf<File, () -> ByteArray>()
        generationState.factory.asList().filter { FileUtilRt.extensionEquals(it.relativePath, "class") }.forEach {
            bytecodeMap[File("/${it.relativePath}").absoluteFile] = { it.asByteArray() }
        }
        return bytecodeMap
    }

    class KotlinResultSaver : IResultSaver {
        private val decompiledText = mutableMapOf<String, String>()

        val resultText: String
            get() {
                decompiledText.values.singleOrNull()?.let { return it }
                return buildString {
                    for ((filename, content) in decompiledText) {
                        appendln("// $filename")
                        append(content)
                    }
                }
            }

        override fun saveFolder(path: String?) {}

        override fun closeArchive(path: String?, archiveName: String?) {}

        override fun copyEntry(source: String?, path: String?, archiveName: String?, entry: String?) {}

        override fun createArchive(path: String?, archiveName: String?, manifest: Manifest?) {}

        override fun saveClassFile(path: String?, qualifiedName: String?, entryName: String?, content: String?, mapping: IntArray?) {
            if (entryName != null && content != null) {
                decompiledText[entryName] = content
            }
        }

        override fun copyFile(source: String?, path: String?, entryName: String?) {}

        override fun saveClassEntry(path: String?, archiveName: String?, qualifiedName: String?, entryName: String?, content: String?) {}

        override fun saveDirEntry(path: String?, archiveName: String?, entryName: String?) {}

    }
}