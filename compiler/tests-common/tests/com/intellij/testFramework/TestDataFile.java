/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */
package com.intellij.testFramework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the annotated parameter as referencing a file in the testdata directory.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER})
public @interface TestDataFile {
}
