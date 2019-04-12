/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.reflect.jvm.internal;

/* package */ class Util {
    @SuppressWarnings("unchecked")
    public static Object getEnumConstantByName(Class<? extends Enum<?>> enumClass, String name) {
        // This is a workaround for KT-5191. Enum#valueOf cannot be called in Kotlin
        return Enum.valueOf((Class) enumClass, name);
    }
}
