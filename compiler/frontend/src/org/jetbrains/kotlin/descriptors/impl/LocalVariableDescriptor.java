/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.descriptors.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.descriptors.*;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

public class LocalVariableDescriptor extends VariableDescriptorWithInitializerImpl implements VariableDescriptorWithAccessors {
    private final boolean isDelegated;
    private final boolean isLateInit;
    private LocalVariableAccessorDescriptor.Getter getter;
    private LocalVariableAccessorDescriptor.Setter setter;

    public LocalVariableDescriptor(
            @NotNull DeclarationDescriptor containingDeclaration,
            @NotNull Annotations annotations,
            @NotNull Name name,
            @Nullable KotlinType type,
            boolean mutable,
            boolean isDelegated,
            boolean isLateInit,
            @NotNull SourceElement source
    ) {
        super(containingDeclaration, annotations, name, type, mutable, source);
        this.isDelegated = isDelegated;
        this.isLateInit = isLateInit;
    }

    public LocalVariableDescriptor(
            @NotNull DeclarationDescriptor containingDeclaration,
            @NotNull Annotations annotations,
            @NotNull Name name,
            @Nullable KotlinType type,
            boolean mutable,
            boolean isDelegated,
            @NotNull SourceElement source
    ) {
        this(containingDeclaration, annotations, name, type, mutable, isDelegated, false, source);
    }

    public LocalVariableDescriptor(
            @NotNull DeclarationDescriptor containingDeclaration,
            @NotNull Annotations annotations,
            @NotNull Name name,
            @Nullable KotlinType type,
            @NotNull SourceElement source
    ) {
        this(containingDeclaration, annotations, name, type, false, false, false, source);
    }

    @Override
    public void setOutType(KotlinType outType) {
        super.setOutType(outType);
        if (isDelegated) {
            this.getter = new LocalVariableAccessorDescriptor.Getter(this);
            if (isVar()) {
                this.setter = new LocalVariableAccessorDescriptor.Setter(this);
            }
        }
    }

    @NotNull
    @Override
    public LocalVariableDescriptor substitute(@NotNull TypeSubstitutor substitutor) {
        if (substitutor.isEmpty()) return this;
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        return visitor.visitVariableDescriptor(this, data);
    }

    @NotNull
    @Override
    public Visibility getVisibility() {
        return Visibilities.LOCAL;
    }

    @Nullable
    @Override
    public LocalVariableAccessorDescriptor.Getter getGetter() {
        return getter;
    }

    @Nullable
    @Override
    public LocalVariableAccessorDescriptor.Setter getSetter() {
        return setter;
    }

    // This override is not deprecated because local variables can only come from sources,
    // and we can be sure that they won't be recompiled independently
    @Override
    @SuppressWarnings("deprecation")
    public boolean isDelegated() {
        return isDelegated;
    }

    @Override
    public boolean isLateInit() {
        return isLateInit;
    }
}
