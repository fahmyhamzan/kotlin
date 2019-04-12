/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.test.testFramework

import com.intellij.mock.MockProject
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project

interface ProjectEx : Project {
    fun init()

    fun setProjectName(name: String)
}

class MockProjectEx(parentDisposable: Disposable) : MockProject(if (ApplicationManager.getApplication() != null) ApplicationManager.getApplication().picoContainer else null, parentDisposable), ProjectEx {
    override fun setProjectName(name: String) {
    }

    override fun init() {
    }
}