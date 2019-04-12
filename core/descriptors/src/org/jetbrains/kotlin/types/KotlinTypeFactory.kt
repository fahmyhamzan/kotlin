/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.types

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor
import org.jetbrains.kotlin.resolve.scopes.MemberScope
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker

object KotlinTypeFactory {
    private fun computeMemberScope(constructor: TypeConstructor, arguments: List<TypeProjection>): MemberScope {
        val descriptor = constructor.declarationDescriptor
        return when (descriptor) {
            is TypeParameterDescriptor -> descriptor.getDefaultType().memberScope
            is ClassDescriptor -> {
                if (arguments.isEmpty())
                    descriptor.defaultType.memberScope
                else
                    descriptor.getMemberScope(TypeConstructorSubstitution.create(constructor, arguments))
            }
            is TypeAliasDescriptor -> ErrorUtils.createErrorScope("Scope for abbreviation: ${descriptor.name}", true)
            else -> throw IllegalStateException("Unsupported classifier: $descriptor for constructor: $constructor")
        }
    }

    @JvmStatic
    fun simpleType(
        annotations: Annotations,
        constructor: TypeConstructor,
        arguments: List<TypeProjection>,
        nullable: Boolean
    ): SimpleType {
        if (annotations.isEmpty() && arguments.isEmpty() && !nullable && constructor.declarationDescriptor != null) {
            return constructor.declarationDescriptor!!.defaultType
        }

        return simpleTypeWithNonTrivialMemberScope(
            annotations,
            constructor,
            arguments,
            nullable,
            computeMemberScope(constructor, arguments)
        )
    }

    @JvmStatic
    fun simpleTypeWithNonTrivialMemberScope(
        annotations: Annotations,
        constructor: TypeConstructor,
        arguments: List<TypeProjection>,
        nullable: Boolean,
        memberScope: MemberScope
    ): SimpleType =
        SimpleTypeImpl(constructor, arguments, nullable, memberScope)
            .let {
                if (annotations.isEmpty())
                    it
                else
                    AnnotatedSimpleType(it, annotations)
            }

    @JvmStatic
    fun simpleNotNullType(
        annotations: Annotations,
        descriptor: ClassDescriptor,
        arguments: List<TypeProjection>
    ): SimpleType = simpleType(annotations, descriptor.typeConstructor, arguments, nullable = false)

    @JvmStatic
    fun simpleType(
        baseType: SimpleType,
        annotations: Annotations = baseType.annotations,
        constructor: TypeConstructor = baseType.constructor,
        arguments: List<TypeProjection> = baseType.arguments,
        nullable: Boolean = baseType.isMarkedNullable
    ): SimpleType = simpleType(annotations, constructor, arguments, nullable)

    @JvmStatic
    fun flexibleType(lowerBound: SimpleType, upperBound: SimpleType): UnwrappedType {
        if (lowerBound == upperBound) return lowerBound
        return FlexibleTypeImpl(lowerBound, upperBound)
    }

    @JvmStatic
    fun integerLiteralType(
        annotations: Annotations,
        constructor: IntegerLiteralTypeConstructor,
        nullable: Boolean
    ): SimpleType = simpleTypeWithNonTrivialMemberScope(
        annotations,
        constructor,
        emptyList(),
        nullable,
        ErrorUtils.createErrorScope("Scope for integer literal type", true)
    )
}

private class SimpleTypeImpl(
    override val constructor: TypeConstructor,
    override val arguments: List<TypeProjection>,
    override val isMarkedNullable: Boolean,
    override val memberScope: MemberScope
) : SimpleType() {
    override val annotations: Annotations get() = Annotations.EMPTY

    override fun replaceAnnotations(newAnnotations: Annotations) =
        if (newAnnotations.isEmpty())
            this
        else
            AnnotatedSimpleType(this, newAnnotations)

    override fun makeNullableAsSpecified(newNullability: Boolean) = when {
            newNullability == isMarkedNullable -> this
            newNullability -> NullableSimpleType(this)
            else -> NotNullSimpleType(this)
        }

    init {
        if (memberScope is ErrorUtils.ErrorScope) {
            throw IllegalStateException("SimpleTypeImpl should not be created for error type: $memberScope\n$constructor")
        }
    }
}

abstract class DelegatingSimpleTypeImpl(override val delegate: SimpleType) : DelegatingSimpleType() {
    override fun replaceAnnotations(newAnnotations: Annotations) =
        if (newAnnotations !== annotations)
            AnnotatedSimpleType(this, newAnnotations)
        else
            this

    override fun makeNullableAsSpecified(newNullability: Boolean): SimpleType {
        if (newNullability == isMarkedNullable) return this
        return delegate.makeNullableAsSpecified(newNullability).replaceAnnotations(annotations)
    }
}

private class AnnotatedSimpleType(
    delegate: SimpleType,
    override val annotations: Annotations
) : DelegatingSimpleTypeImpl(delegate)

private class NullableSimpleType(delegate: SimpleType) : DelegatingSimpleTypeImpl(delegate) {
    override val isMarkedNullable: Boolean
        get() = true
}

private class NotNullSimpleType(delegate: SimpleType) : DelegatingSimpleTypeImpl(delegate) {
    override val isMarkedNullable: Boolean
        get() = false
}