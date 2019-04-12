/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.resolve.jvm.jvmSignature;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

import java.util.List;

public class JvmMethodGenericSignature extends JvmMethodSignature {
    private final String genericsSignature;

    public JvmMethodGenericSignature(
            @NotNull Method asmMethod,
            @NotNull List<JvmMethodParameterSignature> valueParameters,
            @Nullable String genericsSignature
    ) {
        super(asmMethod, valueParameters);
        this.genericsSignature = genericsSignature;
    }

    @Nullable
    public String getGenericsSignature() {
        return genericsSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JvmMethodGenericSignature)) return false;

        JvmMethodGenericSignature that = (JvmMethodGenericSignature) o;

        return super.equals(that) &&
               (genericsSignature == null ? that.genericsSignature == null : genericsSignature.equals(that.genericsSignature));
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (genericsSignature != null ? genericsSignature.hashCode() : 0);
        return result;
    }
}
