/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.codegen.optimization.common

import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode
import org.jetbrains.org.objectweb.asm.tree.InsnList
import org.jetbrains.org.objectweb.asm.tree.MethodNode
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue


class ControlFlowGraph private constructor(private val insns: InsnList) {
    private val edges: Array<MutableList<Int>> = Array(insns.size()) { arrayListOf<Int>() }

    fun getSuccessorsIndices(insn: AbstractInsnNode): List<Int> = getSuccessorsIndices(insns.indexOf(insn))
    fun getSuccessorsIndices(index: Int): List<Int> = edges[index]

    companion object {
        @JvmStatic
        fun build(node: MethodNode): ControlFlowGraph {
            val graph = ControlFlowGraph(node.instructions)

            fun addEdge(from: Int, to: Int) {
                graph.edges[from].add(to)
            }

            object : MethodAnalyzer<BasicValue>("fake", node, OptimizationBasicInterpreter()) {
                override fun visitControlFlowEdge(insn: Int, successor: Int): Boolean {
                    addEdge(insn, successor)
                    return true
                }

                override fun visitControlFlowExceptionEdge(insn: Int, successor: Int): Boolean {
                    addEdge(insn, successor)
                    return true
                }
            }.analyze()

            return graph
        }
    }
}
