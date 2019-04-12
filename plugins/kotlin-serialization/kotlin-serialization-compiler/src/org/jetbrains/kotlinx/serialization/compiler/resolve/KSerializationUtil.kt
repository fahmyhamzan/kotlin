/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlinx.serialization.compiler.resolve

import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.annotations.Annotated
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.ValueArgument
import org.jetbrains.kotlin.resolve.constants.KClassValue
import org.jetbrains.kotlin.resolve.descriptorUtil.classId
import org.jetbrains.kotlin.resolve.descriptorUtil.module
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationDescriptor
import org.jetbrains.kotlin.resolve.scopes.getDescriptorsFiltered
import org.jetbrains.kotlin.types.*
import org.jetbrains.kotlin.types.typeUtil.isSubtypeOf
import org.jetbrains.kotlin.types.typeUtil.supertypes
import org.jetbrains.kotlinx.serialization.compiler.backend.jvm.contextSerializerId
import org.jetbrains.kotlinx.serialization.compiler.backend.jvm.enumSerializerId
import org.jetbrains.kotlinx.serialization.compiler.backend.jvm.polymorphicSerializerId
import org.jetbrains.kotlinx.serialization.compiler.resolve.SerialEntityNames.KSERIALIZER_CLASS
import org.jetbrains.kotlinx.serialization.compiler.resolve.SerializationAnnotations.serialInfoFqName
import org.jetbrains.kotlinx.serialization.compiler.resolve.SerializationPackages.packageFqName

internal fun isAllowedToHaveAutoGeneratedSerializerMethods(
    classDescriptor: ClassDescriptor,
    serializableClassDescriptor: ClassDescriptor
): Boolean {
    // don't generate automatically anything for enums or interfaces or other strange things
    if (serializableClassDescriptor.kind != ClassKind.CLASS) return false
    // it is either GeneratedSerializer implementation
    // or user implementation which does not have type parameters (to be able to correctly initialize descriptor)
    return classDescriptor.typeConstructor.supertypes.any(::isGeneratedKSerializer) ||
            (classDescriptor.typeConstructor.supertypes.any(::isKSerializer) && classDescriptor.declaredTypeParameters.isEmpty())
}

internal fun isKSerializer(type: KotlinType?): Boolean =
    type != null && KotlinBuiltIns.isConstructedFromGivenClass(type, SerialEntityNames.KSERIALIZER_NAME_FQ)

internal fun isGeneratedKSerializer(type: KotlinType?): Boolean =
    type != null && KotlinBuiltIns.isConstructedFromGivenClass(type, SerialEntityNames.GENERATED_SERIALIZER_FQ)

internal fun ClassDescriptor.getGeneratedSerializerDescriptor(): ClassDescriptor =
    module.getClassFromInternalSerializationPackage(SerialEntityNames.GENERATED_SERIALIZER_CLASS.identifier)


internal fun ClassDescriptor.createSerializerTypeFor(argument: SimpleType, baseSerializerInterface: FqName): SimpleType {
    val projectionType = Variance.INVARIANT
    val types = listOf(TypeProjectionImpl(projectionType, argument))
    val descriptor = module.findClassAcrossModuleDependencies(ClassId.topLevel(baseSerializerInterface))
        ?: throw IllegalArgumentException("Can't locate $baseSerializerInterface")
    return KotlinTypeFactory.simpleNotNullType(Annotations.EMPTY, descriptor, types)
}

internal fun extractKSerializerArgumentFromImplementation(implementationClass: ClassDescriptor): KotlinType? {
    val supertypes = implementationClass.typeConstructor.supertypes
    val kSerializerSupertype = supertypes.find { isGeneratedKSerializer(it) }
        ?: supertypes.find { isKSerializer(it) }
        ?: return null
    return kSerializerSupertype.arguments.first().type
}

internal val DeclarationDescriptor.serializableWith: KotlinType?
    get() = annotations.serializableWith(module)

internal fun Annotations.serializableWith(module: ModuleDescriptor): KotlinType? =
    this.findAnnotationKotlinTypeValue(SerializationAnnotations.serializableAnnotationFqName, module,"with")

internal val DeclarationDescriptor.serializerForClass: KotlinType?
    get() = annotations.findAnnotationKotlinTypeValue(SerializationAnnotations.serializerAnnotationFqName, module, "forClass")

internal val ClassDescriptor.isSerialInfoAnnotation: Boolean
    get() = annotations.hasAnnotation(serialInfoFqName)

internal val Annotations.serialNameValue: String?
    get() = findAnnotationConstantValue(SerializationAnnotations.serialNameAnnotationFqName, "value")

internal val Annotations.serialRequired: Boolean
    get() = hasAnnotation(SerializationAnnotations.requiredAnnotationFqName)

internal val Annotations.serialTransient: Boolean
    get() = hasAnnotation(SerializationAnnotations.serialTransientFqName)

// ----------------------------------------

val KotlinType?.toClassDescriptor: ClassDescriptor?
    @JvmName("toClassDescriptor")
    get() = this?.constructor?.declarationDescriptor as? ClassDescriptor

internal val ClassDescriptor.shouldHaveGeneratedMethodsInCompanion: Boolean
    get() = this.kind == ClassKind.CLASS && annotations.hasAnnotation(SerializationAnnotations.serializableAnnotationFqName)

internal val ClassDescriptor.isInternalSerializable: Boolean //todo normal checking
    get() {
        if (kind != ClassKind.CLASS) return false
        return hasSerializableAnnotationWithoutArgs
    }

internal val ClassDescriptor.hasSerializableAnnotationWithoutArgs: Boolean
    get() {
        if (!annotations.hasAnnotation(SerializationAnnotations.serializableAnnotationFqName)) return false
        // If provided descriptor is lazy, carefully look at psi in order not to trigger full resolve which may be recursive.
        // Otherwise, this descriptor is deserialized from another module and it is OK to check value right away.
        val lazyDesc = annotations.findAnnotation(SerializationAnnotations.serializableAnnotationFqName)
                as? LazyAnnotationDescriptor ?: return (serializableWith == null)
        val psi = lazyDesc.annotationEntry
        return psi.valueArguments.isEmpty()
    }

// For abstract classes marked with @Serializable,
// methods are generated anyway although they shouldn't have
// generated $serializer and use Polymorphic one.
internal fun isAbstractSerializableClass(serializableDescriptor: ClassDescriptor): Boolean =
    serializableDescriptor.isInternalSerializable && serializableDescriptor.modality == Modality.ABSTRACT

internal fun ClassDescriptor.polymorphicSerializerIfApplicableAutomatically(): ClassDescriptor? = if (
    isAbstractSerializableClass(this)
    || kind == ClassKind.INTERFACE
) module.getClassFromSerializationPackage(SpecialBuiltins.polymorphicSerializer)
else null

// serializer that was declared for this type
internal val ClassDescriptor?.classSerializer: ClassDescriptor?
    get() = this?.let {
        // serializer annotation on class?
        serializableWith?.let { return it.toClassDescriptor }
        // companion object serializer?
        if (hasCompanionObjectAsSerializer) return companionObjectDescriptor
        // can infer @Poly?
        polymorphicSerializerIfApplicableAutomatically()?.let { return it }
        // default serializable?
        if (isInternalSerializable) {
            // $serializer nested class
            return this.unsubstitutedMemberScope
                .getDescriptorsFiltered(nameFilter = { it == SerialEntityNames.SERIALIZER_CLASS_NAME })
                .filterIsInstance<ClassDescriptor>().singleOrNull()
        }
        return null
    }

internal val ClassDescriptor.hasCompanionObjectAsSerializer: Boolean
    get() = companionObjectDescriptor?.serializerForClass == this.defaultType

internal fun ClassDescriptor.isSerializerWhichRequiersKClass() = classId in setOf(enumSerializerId, contextSerializerId, polymorphicSerializerId)

internal fun checkSerializerNullability(classType: KotlinType, serializerType: KotlinType): KotlinType {
    val castedToKSerial = requireNotNull(
        serializerType.supertypes().find { isKSerializer(it) },
        { "${KSERIALIZER_CLASS} is not a supertype of $serializerType" }
    )
    if (!classType.isMarkedNullable && castedToKSerial.arguments.first().type.isMarkedNullable)
        throw IllegalStateException("Can't serialize non-nullable field of type ${classType} with nullable serializer ${serializerType}")
    return serializerType
}

// returns only user-overriden Serializer
internal val KotlinType.overridenSerializer: KotlinType?
    get() {
        val desc = this.toClassDescriptor ?: return null
        (desc.serializableWith)?.let { return checkSerializerNullability(this, it) }
        if (desc.annotations.hasAnnotation(SerializationAnnotations.polymorphicFqName))
            return desc.module.getClassFromSerializationPackage(SpecialBuiltins.polymorphicSerializer).defaultType
        return null
    }

internal val KotlinType.genericIndex: Int?
    get() = (this.constructor.declarationDescriptor as? TypeParameterDescriptor)?.index

internal fun getSerializableClassDescriptorByCompanion(thisDescriptor: ClassDescriptor): ClassDescriptor? {
    if (!thisDescriptor.isCompanionObject) return null
    val classDescriptor = (thisDescriptor.containingDeclaration as? ClassDescriptor) ?: return null
    if (!classDescriptor.shouldHaveGeneratedMethodsInCompanion) return null
    return classDescriptor
}

internal fun getSerializableClassDescriptorBySerializer(serializerDescriptor: ClassDescriptor): ClassDescriptor? {
    val serializerForClass = serializerDescriptor.serializerForClass
    if (serializerForClass != null) return serializerForClass.toClassDescriptor
    if (serializerDescriptor.name !in setOf(
            SerialEntityNames.SERIALIZER_CLASS_NAME,
            SerialEntityNames.GENERATED_SERIALIZER_CLASS
        )
    ) return null
    val classDescriptor = (serializerDescriptor.containingDeclaration as? ClassDescriptor) ?: return null
    if (!classDescriptor.isInternalSerializable) return null
    return classDescriptor
}

internal fun ClassDescriptor.checkSerializableClassPropertyResult(prop: PropertyDescriptor): Boolean =
        prop.returnType!!.isSubtypeOf(getClassFromSerializationPackage(SerialEntityNames.SERIAL_DESCRIPTOR_CLASS).toSimpleType(false)) // todo: cache lookup

// todo: serialization: do an actual check better that just number of parameters
internal fun ClassDescriptor.checkSaveMethodParameters(parameters: List<ValueParameterDescriptor>): Boolean =
        parameters.size == 2

internal fun ClassDescriptor.checkSaveMethodResult(type: KotlinType): Boolean =
        KotlinBuiltIns.isUnit(type)

// todo: serialization: do an actual check better that just number of parameters
internal fun ClassDescriptor.checkLoadMethodParameters(parameters: List<ValueParameterDescriptor>): Boolean =
        parameters.size == 1

internal fun ClassDescriptor.checkLoadMethodResult(type: KotlinType): Boolean =
    getSerializableClassDescriptorBySerializer(this)?.defaultType == type

// ----------------

inline fun <reified R> Annotations.findAnnotationConstantValue(annotationFqName: FqName, property: String): R? =
    findAnnotation(annotationFqName)?.let { annotation ->
        annotation.allValueArguments.entries.singleOrNull { it.key.asString() == property }?.value?.value
    } as? R

internal fun Annotations.findAnnotationKotlinTypeValue(
    annotationFqName: FqName,
    moduleForResolve: ModuleDescriptor,
    property: String
): KotlinType? =
    findAnnotation(annotationFqName)?.let { annotation ->
        val maybeKClass = annotation.allValueArguments.entries.singleOrNull { it.key.asString() == property }?.value as? KClassValue
        maybeKClass?.getArgumentType(moduleForResolve)
    }

// Search utils

internal fun ClassDescriptor.getKSerializerConstructorMarker(): ClassDescriptor =
        module.findClassAcrossModuleDependencies(ClassId(packageFqName, SerialEntityNames.SERIAL_CTOR_MARKER_NAME))!!

internal fun ModuleDescriptor.getClassFromInternalSerializationPackage(classSimpleName: String) =
    getFromPackage(SerializationPackages.internalPackageFqName, classSimpleName)

internal fun ModuleDescriptor.getClassFromSerializationPackage(classSimpleName: String) =
    getFromPackage(SerializationPackages.packageFqName, classSimpleName)

private fun ModuleDescriptor.getFromPackage(packageFqName: FqName, classSimpleName: String) = requireNotNull(
    findClassAcrossModuleDependencies(
        ClassId(
            packageFqName,
            Name.identifier(classSimpleName)
        )
    )
) { "Can't locate class $classSimpleName from package $packageFqName" }

internal fun ClassDescriptor.getClassFromSerializationPackage(classSimpleName: String) =
        requireNotNull(module.findClassAcrossModuleDependencies(ClassId(packageFqName, Name.identifier(classSimpleName)))) {"Can't locate class $classSimpleName"}

internal fun ClassDescriptor.getClassFromInternalSerializationPackage(classSimpleName: String) =
    module.getClassFromInternalSerializationPackage(classSimpleName)

fun ClassDescriptor.toSimpleType(nullable: Boolean = true) = KotlinTypeFactory.simpleType(Annotations.EMPTY, this.typeConstructor, emptyList(), nullable)

internal fun Annotated.annotationsWithArguments(): List<Triple<ClassDescriptor, List<ValueArgument>, List<ValueParameterDescriptor>>> =
    annotations.asSequence()
        .filter { it.type.toClassDescriptor?.isSerialInfoAnnotation == true }
        .filterIsInstance<LazyAnnotationDescriptor>()
        .mapNotNull { annDesc ->
            annDesc.type.toClassDescriptor?.let {
                Triple(it, annDesc.annotationEntry.valueArguments, it.unsubstitutedPrimaryConstructor?.valueParameters.orEmpty())
            }
        }
        .toList()
