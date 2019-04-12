/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.psi.stubs.elements;

import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.stubs.KotlinParameterStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinParameterStubImpl;

import java.io.IOException;

public class KtParameterElementType extends KtStubElementType<KotlinParameterStub, KtParameter> {
    public KtParameterElementType(@NotNull @NonNls String debugName) {
        super(debugName, KtParameter.class, KotlinParameterStub.class);
    }

    @NotNull
    @Override
    public KotlinParameterStub createStub(@NotNull KtParameter psi, StubElement parentStub) {
        FqName fqName = psi.getFqName();
        StringRef fqNameRef = StringRef.fromString(fqName != null ? fqName.asString() : null);
        return new KotlinParameterStubImpl(
                (StubElement<?>) parentStub, fqNameRef, StringRef.fromString(psi.getName()),
                psi.isMutable(), psi.hasValOrVar(), psi.hasDefaultValue()
        );
    }

    @Override
    public void serialize(@NotNull KotlinParameterStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        dataStream.writeBoolean(stub.isMutable());
        dataStream.writeBoolean(stub.hasValOrVar());
        dataStream.writeBoolean(stub.hasDefaultValue());
        FqName name = stub.getFqName();
        dataStream.writeName(name != null ? name.asString() : null);
    }

    @NotNull
    @Override
    public KotlinParameterStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        StringRef name = dataStream.readName();
        boolean isMutable = dataStream.readBoolean();
        boolean hasValOrValNode = dataStream.readBoolean();
        boolean hasDefaultValue = dataStream.readBoolean();
        StringRef fqName = dataStream.readName();

        return new KotlinParameterStubImpl((StubElement<?>) parentStub, fqName, name, isMutable, hasValOrValNode, hasDefaultValue);
    }

    @Override
    public void indexStub(@NotNull KotlinParameterStub stub, @NotNull IndexSink sink) {
        StubIndexService.getInstance().indexParameter(stub, sink);
    }
}
