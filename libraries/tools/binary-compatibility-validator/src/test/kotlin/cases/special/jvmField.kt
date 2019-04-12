/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package cases.special

public open class JvmFieldsClass {
    @JvmField
    public var publicField = "x"

    @JvmField
    internal var internalField = "y"

    @JvmField
    protected var protectedField = "y"

    public companion object JvmFieldsCompanion {
        @JvmField
        public var publicСField = "x"

        @JvmField
        internal var internalСField = "y"

        @JvmField
        protected var protectedСField = "y"

        public const val publicConst = 1
        internal const val internalConst = 2
        protected const val protectedConst = 3
        private const val privateConst = 4
    }
}

public object JvmFieldsObject {
    @JvmField
    public var publicField = "x"

    @JvmField
    internal var internalField = "y"

    public const val publicConst = 1
    internal const val internalConst = 2
    private const val privateConst = 4
}


@JvmField
public var publicField = "x"

@JvmField
internal var internalField = "y"

public const val publicConst = 1
internal const val internalConst = 2
private const val privateConst = 4
