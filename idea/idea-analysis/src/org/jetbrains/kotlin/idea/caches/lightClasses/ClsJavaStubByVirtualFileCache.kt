/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.caches.lightClasses

import com.intellij.ide.highlighter.JavaClassFileType
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.impl.compiled.ClsFileImpl
import com.intellij.psi.impl.java.stubs.PsiJavaFileStub
import com.intellij.psi.impl.java.stubs.impl.PsiJavaFileStubImpl
import com.intellij.util.cls.ClsFormatException
import com.intellij.util.containers.ContainerUtil
import java.io.IOException

class ClsJavaStubByVirtualFileCache {
    private class CachedJavaStub(val modificationStamp: Long, val javaFileStub: PsiJavaFileStubImpl)

    private val cache = ContainerUtil.createConcurrentWeakKeySoftValueMap<VirtualFile, CachedJavaStub>()

    fun get(classFile: VirtualFile): PsiJavaFileStubImpl? {
        val cached = cache.get(classFile)
        val fileModificationStamp = classFile.modificationStamp
        if (cached != null && cached.modificationStamp == fileModificationStamp) {
            return cached.javaFileStub
        }
        val stub = createStub(classFile) as PsiJavaFileStubImpl? ?: return null
        cache.put(classFile, CachedJavaStub(fileModificationStamp, stub))
        return stub
    }

    private fun createStub(file: VirtualFile): PsiJavaFileStub? {
        if (file.fileType !== JavaClassFileType.INSTANCE) return null

        try {
            return ClsFileImpl.buildFileStub(file, file.contentsToByteArray(false))
        } catch (e: ClsFormatException) {
            LOG.error("Failed to build java cls class for " + file.canonicalPath!!, e)
        } catch (e: IOException) {
            LOG.error("Failed to build java cls class for " + file.canonicalPath!!, e)
        }

        return null
    }

    companion object {
        private val LOG = Logger.getInstance(ClsJavaStubByVirtualFileCache::class.java)

        fun getInstance(project: Project): ClsJavaStubByVirtualFileCache {
            return ServiceManager.getService(project, ClsJavaStubByVirtualFileCache::class.java)
        }
    }
}