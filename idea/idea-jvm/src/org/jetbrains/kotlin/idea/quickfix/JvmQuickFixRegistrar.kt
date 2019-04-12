/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.quickfix

import org.jetbrains.kotlin.diagnostics.DiagnosticFactory
import org.jetbrains.kotlin.diagnostics.Errors.*
import org.jetbrains.kotlin.idea.inspections.AddReflectionQuickFix
import org.jetbrains.kotlin.idea.inspections.AddScriptRuntimeQuickFix
import org.jetbrains.kotlin.idea.inspections.AddTestLibQuickFix
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm.NO_REFLECTION_IN_CLASS_PATH

class JvmQuickFixRegistrar : QuickFixContributor {
    override fun registerQuickFixes(quickFixes: QuickFixes) {
        fun DiagnosticFactory<*>.registerFactory(vararg factory: KotlinIntentionActionsFactory) {
            quickFixes.register(this, *factory)
        }

        UNRESOLVED_REFERENCE.registerFactory(AddTestLibQuickFix)

        UNSUPPORTED_FEATURE.registerFactory(EnableUnsupportedFeatureFix)

        EXPERIMENTAL_FEATURE_ERROR.registerFactory(ChangeCoroutineSupportFix)
        EXPERIMENTAL_FEATURE_WARNING.registerFactory(ChangeCoroutineSupportFix)

        EXPERIMENTAL_FEATURE_ERROR.registerFactory(ChangeGeneralLanguageFeatureSupportFix)
        EXPERIMENTAL_FEATURE_WARNING.registerFactory(ChangeGeneralLanguageFeatureSupportFix)

        NO_REFLECTION_IN_CLASS_PATH.registerFactory(AddReflectionQuickFix)

        MISSING_SCRIPT_STANDARD_TEMPLATE.registerFactory(AddScriptRuntimeQuickFix)
    }
}