/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.types.impl

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.types.FirDynamicTypeRef

class FirDynamicTypeRefImpl(
    session: FirSession,
    psi: PsiElement?,
    isNullable: Boolean
) : FirAbstractAnnotatedTypeRef(session, psi, isNullable), FirDynamicTypeRef