/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analyzer

import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.types.ErrorUtils
import java.io.File

open class AnalysisResult protected constructor(
    val bindingContext: BindingContext,
    val moduleDescriptor: ModuleDescriptor,
    val shouldGenerateCode: Boolean = true
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return (other is AnalysisResult && bindingContext == other.bindingContext &&
                moduleDescriptor == other.moduleDescriptor && shouldGenerateCode == other.shouldGenerateCode)
    }

    override fun hashCode(): Int {
        var result = 17
        result = 29 * result + bindingContext.hashCode()
        result = 29 * result + moduleDescriptor.hashCode()
        result = 29 * result + shouldGenerateCode.hashCode()
        return result
    }

    operator fun component1() = bindingContext

    operator fun component2() = moduleDescriptor

    operator fun component3() = shouldGenerateCode

    val error: Throwable
        get() = if (this is InternalError) this.exception else throw IllegalStateException("Should only be called for error analysis result")

    fun isError(): Boolean = this is InternalError || this is CompilationError

    fun throwIfError() {
        when {
            this is InternalError -> throw IllegalStateException("failed to analyze: " + error, error)
            this is CompilationError -> throw CompilationErrorException()
        }
    }

    class CompilationErrorException : RuntimeException()

    private class CompilationError(bindingContext: BindingContext) : AnalysisResult(bindingContext, ErrorUtils.getErrorModule())

    private class InternalError(
        bindingContext: BindingContext,
        val exception: Throwable
    ) : AnalysisResult(bindingContext, ErrorUtils.getErrorModule())

    class RetryWithAdditionalJavaRoots(
        bindingContext: BindingContext,
        moduleDescriptor: ModuleDescriptor,
        val additionalJavaRoots: List<File>,
        val addToEnvironment: Boolean = true
    ) : AnalysisResult(bindingContext, moduleDescriptor)

    companion object {
        val EMPTY: AnalysisResult = success(BindingContext.EMPTY, ErrorUtils.getErrorModule())

        @JvmStatic
        fun success(bindingContext: BindingContext, module: ModuleDescriptor): AnalysisResult {
            return AnalysisResult(bindingContext, module, true)
        }

        @JvmStatic
        fun success(bindingContext: BindingContext, module: ModuleDescriptor, shouldGenerateCode: Boolean): AnalysisResult {
            return AnalysisResult(bindingContext, module, shouldGenerateCode)
        }

        @JvmStatic
        fun internalError(bindingContext: BindingContext, error: Throwable): AnalysisResult {
            return InternalError(bindingContext, error)
        }

        @JvmStatic
        fun compilationError(bindingContext: BindingContext): AnalysisResult {
            return CompilationError(bindingContext)
        }
    }
}
