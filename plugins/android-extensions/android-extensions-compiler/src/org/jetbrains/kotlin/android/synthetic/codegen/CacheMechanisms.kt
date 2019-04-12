/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.android.synthetic.codegen

import kotlinx.android.extensions.CacheImplementation
import org.jetbrains.org.objectweb.asm.Type
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter
import org.jetbrains.kotlin.android.synthetic.codegen.AbstractAndroidExtensionsExpressionCodegenExtension.Companion.PROPERTY_NAME

internal val CacheImplementation.hasCache: Boolean
    get() = this != CacheImplementation.NO_CACHE

interface CacheMechanism {
    /** Push the cache object onto the stack. */
    fun loadCache()

    /** Init cache field. */
    fun initCache()

    /** Clear cache. The cache storage should be on the stack. */
    fun clearCache()

    /** Push the cached view onto the stack, or push `null` if the view is not cached. `Int` id should be on the stack. */
    fun getViewFromCache()

    /** Cache the view. `Int` id should be on the stack. */
    fun putViewToCache(getView: () -> Unit)

    companion object {
        fun getType(cacheImpl: CacheImplementation): Type {
            return Type.getObjectType(when (cacheImpl) {
                CacheImplementation.SPARSE_ARRAY -> "android.util.SparseArray"
                CacheImplementation.HASH_MAP -> HashMap::class.java.canonicalName
                CacheImplementation.NO_CACHE -> throw IllegalArgumentException("Container should support cache")
            }.replace('.', '/'))
        }

        fun get(cacheImpl: CacheImplementation, iv: InstructionAdapter, containerType: Type): CacheMechanism {
            return when (cacheImpl) {
                CacheImplementation.HASH_MAP -> HashMapCacheMechanism(iv, containerType)
                CacheImplementation.SPARSE_ARRAY -> SparseArrayCacheMechanism(iv, containerType)
                CacheImplementation.NO_CACHE -> throw IllegalArgumentException("Container should support cache")
            }
        }
    }
}

internal class HashMapCacheMechanism(
        val iv: InstructionAdapter,
        val containerType: Type
) : CacheMechanism {
    override fun loadCache() {
        iv.load(0, containerType)
        iv.getfield(containerType.internalName, PROPERTY_NAME, "Ljava/util/HashMap;")
    }

    override fun initCache() {
        iv.load(0, containerType)
        iv.anew(Type.getType("Ljava/util/HashMap;"))
        iv.dup()
        iv.invokespecial("java/util/HashMap", "<init>", "()V", false)
        iv.putfield(containerType.internalName, PROPERTY_NAME, "Ljava/util/HashMap;")
    }

    override fun clearCache() {
        iv.invokevirtual("java/util/HashMap", "clear", "()V", false)
    }

    override fun getViewFromCache() {
        iv.invokestatic("java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false)
        iv.invokevirtual("java/util/HashMap", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", false)
    }

    override fun putViewToCache(getView: () -> Unit) {
        iv.invokestatic("java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false)
        getView()
        iv.invokevirtual("java/util/HashMap", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false)
        iv.pop()
    }
}

internal class SparseArrayCacheMechanism(
        val iv: InstructionAdapter,
        val containerType: Type
) : CacheMechanism {
    override fun loadCache() {
        iv.load(0, containerType)
        iv.getfield(containerType.internalName, PROPERTY_NAME, "Landroid/util/SparseArray;")
    }

    override fun initCache() {
        iv.load(0, containerType)
        iv.anew(Type.getType("Landroid/util/SparseArray;"))
        iv.dup()
        iv.invokespecial("android/util/SparseArray", "<init>", "()V", false)
        iv.putfield(containerType.internalName, PROPERTY_NAME, "Landroid/util/SparseArray;")
    }

    override fun clearCache() {
        iv.invokevirtual("android/util/SparseArray", "clear", "()V", false)
    }

    override fun getViewFromCache() {
        iv.invokevirtual("android/util/SparseArray", "get", "(I)Ljava/lang/Object;", false)
    }

    override fun putViewToCache(getView: () -> Unit) {
        getView()
        iv.invokevirtual("android/util/SparseArray", "put", "(ILjava/lang/Object;)V", false)
    }
}