/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.resolve.scopes.receivers

import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.resolve.scopes.MemberScope
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.checker.prepareArgumentTypeRegardingCaptureTypes

// this receiver used only for resolution. see subtypes
interface DetailedReceiver

class ReceiverValueWithSmartCastInfo(
    val receiverValue: ReceiverValue,
    val possibleTypes: Set<KotlinType>, // doesn't include receiver.type
    val isStable: Boolean
) : DetailedReceiver {
    override fun toString() = receiverValue.toString()
}

interface QualifierReceiver : Receiver, DetailedReceiver {
    val descriptor: DeclarationDescriptor

    val staticScope: MemberScope

    val classValueReceiver: ReceiverValue?

    // for qualifiers smart cast is impossible
    val classValueReceiverWithSmartCastInfo: ReceiverValueWithSmartCastInfo?
        get() = classValueReceiver?.let { ReceiverValueWithSmartCastInfo(it, emptySet(), true) }
}

fun ReceiverValueWithSmartCastInfo.prepareReceiverRegardingCaptureTypes(): ReceiverValueWithSmartCastInfo {
    val preparedBaseType = prepareArgumentTypeRegardingCaptureTypes(receiverValue.type.unwrap())
    if (preparedBaseType == null && possibleTypes.isEmpty()) return this

    val newPossibleTypes = possibleTypes.mapTo(HashSet<KotlinType>()) { prepareArgumentTypeRegardingCaptureTypes(it.unwrap()) ?: it }
    val newReceiver = if (preparedBaseType != null) receiverValue.replaceType(preparedBaseType) else receiverValue

    return ReceiverValueWithSmartCastInfo(newReceiver, newPossibleTypes, isStable)
}