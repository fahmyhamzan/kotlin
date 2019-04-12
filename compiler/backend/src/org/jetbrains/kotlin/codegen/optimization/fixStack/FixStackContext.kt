/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.codegen.optimization.fixStack

import com.intellij.util.SmartList
import com.intellij.util.containers.Stack
import org.jetbrains.kotlin.codegen.inline.isAfterInlineMarker
import org.jetbrains.kotlin.codegen.inline.isBeforeInlineMarker
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsn
import org.jetbrains.kotlin.codegen.pseudoInsns.parsePseudoInsnOrNull
import org.jetbrains.org.objectweb.asm.Opcodes
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode
import org.jetbrains.org.objectweb.asm.tree.MethodNode

internal class FixStackContext(val methodNode: MethodNode) {
    val breakContinueGotoNodes = linkedSetOf<JumpInsnNode>()
    val fakeAlwaysTrueIfeqMarkers = arrayListOf<AbstractInsnNode>()
    val fakeAlwaysFalseIfeqMarkers = arrayListOf<AbstractInsnNode>()

    val isThereAnyTryCatch: Boolean
    val saveStackMarkerForRestoreMarker = insertTryCatchBlocksMarkers(methodNode)
    val restoreStackMarkersForSaveMarker = hashMapOf<AbstractInsnNode, MutableList<AbstractInsnNode>>()

    val openingInlineMethodMarker = hashMapOf<AbstractInsnNode, AbstractInsnNode>()
    var consistentInlineMarkers: Boolean = true; private set

    init {
        isThereAnyTryCatch = saveStackMarkerForRestoreMarker.isNotEmpty()
        for ((restore, save) in saveStackMarkerForRestoreMarker) {
            restoreStackMarkersForSaveMarker.getOrPut(save) { SmartList() }.add(restore)
        }

        val inlineMarkersStack = Stack<AbstractInsnNode>()

        InsnSequence(methodNode.instructions).forEach { insnNode ->
            val pseudoInsn = parsePseudoInsnOrNull(insnNode)
            when {
                pseudoInsn == PseudoInsn.FIX_STACK_BEFORE_JUMP ->
                    visitFixStackBeforeJump(insnNode)
                pseudoInsn == PseudoInsn.FAKE_ALWAYS_TRUE_IFEQ ->
                    visitFakeAlwaysTrueIfeq(insnNode)
                pseudoInsn == PseudoInsn.FAKE_ALWAYS_FALSE_IFEQ ->
                    visitFakeAlwaysFalseIfeq(insnNode)
                isBeforeInlineMarker(insnNode) -> {
                    inlineMarkersStack.push(insnNode)
                }
                isAfterInlineMarker(insnNode) -> {
                    assert(inlineMarkersStack.isNotEmpty()) { "Mismatching after inline method marker at ${indexOf(insnNode)}" }
                    openingInlineMethodMarker[insnNode] = inlineMarkersStack.pop()
                }
            }
        }

        if (inlineMarkersStack.isNotEmpty()) {
            consistentInlineMarkers = false
        }
    }

    private fun visitFixStackBeforeJump(insnNode: AbstractInsnNode) {
        val next = insnNode.next
        assert(next.opcode == Opcodes.GOTO) { "${indexOf(insnNode)}: should be followed by GOTO" }
        breakContinueGotoNodes.add(next as JumpInsnNode)
    }

    private fun visitFakeAlwaysTrueIfeq(insnNode: AbstractInsnNode) {
        assert(insnNode.next.opcode == Opcodes.IFEQ) { "${indexOf(insnNode)}: should be followed by IFEQ" }
        fakeAlwaysTrueIfeqMarkers.add(insnNode)
    }

    private fun visitFakeAlwaysFalseIfeq(insnNode: AbstractInsnNode) {
        assert(insnNode.next.opcode == Opcodes.IFEQ) { "${indexOf(insnNode)}: should be followed by IFEQ" }
        fakeAlwaysFalseIfeqMarkers.add(insnNode)
    }

    private fun indexOf(node: AbstractInsnNode) = methodNode.instructions.indexOf(node)

    fun hasAnyMarkers(): Boolean =
        breakContinueGotoNodes.isNotEmpty() ||
                fakeAlwaysTrueIfeqMarkers.isNotEmpty() ||
                fakeAlwaysFalseIfeqMarkers.isNotEmpty() ||
                isThereAnyTryCatch ||
                openingInlineMethodMarker.isNotEmpty()

    fun isAnalysisRequired(): Boolean =
        breakContinueGotoNodes.isNotEmpty() ||
                isThereAnyTryCatch ||
                openingInlineMethodMarker.isNotEmpty()

}
