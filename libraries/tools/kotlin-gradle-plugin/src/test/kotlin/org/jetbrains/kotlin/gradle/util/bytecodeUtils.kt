/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.util

import org.jetbrains.org.objectweb.asm.ClassReader
import org.jetbrains.org.objectweb.asm.util.TraceClassVisitor
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import javax.tools.ToolProvider

fun classFileBytecodeString(classFile: File): String {
    val out = StringWriter()
    val traceVisitor = TraceClassVisitor(PrintWriter(out))
    ClassReader(classFile.readBytes()).accept(traceVisitor, 0)
    return out.toString()
}

fun checkBytecodeContains(classFile: File, vararg strings: String) {
    checkBytecodeContains(classFile, strings.toList())
}

fun checkBytecodeContains(classFile: File, strings: Iterable<String>) {
    val bytecode = classFileBytecodeString(classFile)
    for (string in strings) {
        assert(bytecode.contains(string)) { "Bytecode should contain '$string':\n$bytecode" }
    }
}

fun checkBytecodeNotContains(classFile: File, strings: Iterable<String>) {
    val bytecode = classFileBytecodeString(classFile)
    for (string in strings) {
        assert(!bytecode.contains(string)) { "Bytecode should NOT contain '$string':\n$bytecode" }
    }
}

fun compileSources(sources: Collection<File>, outputDir: File) {
    val compiler = ToolProvider.getSystemJavaCompiler()
    compiler.getStandardFileManager(null, null, null).use { fileManager ->
        val compilationTask =
            compiler.getTask(
                null, fileManager, null, listOf("-d", outputDir.absolutePath), null, fileManager.getJavaFileObjectsFromFiles(sources)
            )

        compilationTask.call()
    }
}