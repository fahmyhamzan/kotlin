/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.jvm.lower

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
import org.jetbrains.kotlin.name.Name

interface LoweredFunction: FunctionDescriptor

class LoweredFunctionImpl(containingDeclaration: DeclarationDescriptor,
                          original: FunctionDescriptor,
                          annotations: Annotations,
                          name: Name,
                          kind: CallableMemberDescriptor.Kind,
                          source: SourceElement) :
        FunctionDescriptorImpl(containingDeclaration, original, annotations, name, kind, source), LoweredFunction {
    override fun getDispatchReceiverParameter(): ReceiverParameterDescriptor? {
        return super.getDispatchReceiverParameter()
    }

    override fun createSubstitutedCopy(newOwner: DeclarationDescriptor, original: FunctionDescriptor?, kind: CallableMemberDescriptor.Kind, newName: Name?, annotations: Annotations, source: SourceElement): FunctionDescriptorImpl {
        TODO("not implemented")
    }
}