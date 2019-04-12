/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package foo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Bar {
    @Inject
    public Bar(){
    }
}
