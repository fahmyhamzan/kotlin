/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.codegen.inline;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Opcodes;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

import java.util.ArrayList;
import java.util.List;

import static org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt.GENERATE_SMAP;
import static org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt.getLoadStoreArgSize;

public class InlineAdapter extends InstructionAdapter {
    private final SourceMapper sourceMapper;
    private final List<CatchBlock> blocks = new ArrayList<>();

    private boolean isLambdaInlining = false;
    private int nextLocalIndex = 0;
    private int nextLocalIndexBeforeInline = -1;

    public InlineAdapter(@NotNull MethodVisitor mv, int localsSize, @NotNull SourceMapper sourceMapper) {
        super(Opcodes.API_VERSION, mv);
        this.nextLocalIndex = localsSize;
        this.sourceMapper = sourceMapper;
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        super.visitIincInsn(var, increment);
        updateIndex(var, 1);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
        updateIndex(var, getLoadStoreArgSize(opcode));
    }

    private void updateIndex(int var, int varSize) {
        int newIndex = var + varSize;
        if (newIndex > nextLocalIndex) {
            nextLocalIndex = newIndex;
        }
    }

    public int getNextLocalIndex() {
        return nextLocalIndex;
    }

    public void setLambdaInlining(boolean isInlining) {
        this.isLambdaInlining = isInlining;
        if (isInlining) {
            nextLocalIndexBeforeInline = nextLocalIndex;
        }
        else {
            nextLocalIndex = nextLocalIndexBeforeInline;
        }
    }

    @Override
    public void visitTryCatchBlock(@NotNull Label start, @NotNull Label end, @NotNull Label handler, @Nullable String type) {
        if (!isLambdaInlining) {
            blocks.add(new CatchBlock(start, end, handler, type));
        }
        else {
            super.visitTryCatchBlock(start, end, handler, type);
        }
    }

    @Override
    public void visitLineNumber(int line, @NotNull Label start) {
        if (GENERATE_SMAP) {
            line = sourceMapper.mapLineNumber(line);
        }
        //skip not mapped lines
        if (line >= 0) {
            super.visitLineNumber(line, start);
        }
    }

    @Override
    public void visitMaxs(int stack, int locals) {
        for (CatchBlock b : blocks) {
            super.visitTryCatchBlock(b.start, b.end, b.handler, b.type);
        }
        super.visitMaxs(stack, locals);
    }

    private static class CatchBlock {
        private final Label start;
        private final Label end;
        private final Label handler;
        private final String type;

        public CatchBlock(@NotNull Label start, @NotNull Label end, @NotNull Label handler, @Nullable String type) {
            this.start = start;
            this.end = end;
            this.handler = handler;
            this.type = type;
        }
    }
}
