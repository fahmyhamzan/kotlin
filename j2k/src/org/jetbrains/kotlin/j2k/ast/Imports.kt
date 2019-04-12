/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.j2k.ast

import org.jetbrains.kotlin.j2k.CodeBuilder
import org.jetbrains.kotlin.j2k.append

class Import(val name: String) : Element() {
    override fun generateCode(builder: CodeBuilder) {
        builder append "import " append name
    }
}

class ImportList(var imports: List<Import>) : Element() {
    override val isEmpty: Boolean
        get() = imports.isEmpty()

    override fun generateCode(builder: CodeBuilder) {
        builder.append(imports, "\n")
    }
}
