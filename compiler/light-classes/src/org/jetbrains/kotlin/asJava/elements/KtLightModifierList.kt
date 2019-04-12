/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.asJava.elements

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiModifierList
import com.intellij.psi.PsiModifierListOwner
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport
import org.jetbrains.kotlin.asJava.builder.LightMemberOriginForDeclaration
import org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration
import org.jetbrains.kotlin.asJava.classes.KtUltraLightElementWithNullabilityAnnotation
import org.jetbrains.kotlin.asJava.classes.KtUltraLightNullabilityAnnotation
import org.jetbrains.kotlin.asJava.classes.lazyPub
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.getParentOfType
import org.jetbrains.kotlin.psi.psiUtil.isPropertyParameter
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.jvm.annotations.JVM_DEFAULT_FQ_NAME
import org.jetbrains.kotlin.resolve.source.getPsi

abstract class KtLightModifierList<out T : KtLightElement<KtModifierListOwner, PsiModifierListOwner>>(
    protected val owner: T
) : KtLightElementBase(owner), PsiModifierList, KtLightElement<KtModifierList, PsiModifierList> {
    override val clsDelegate by lazyPub { owner.clsDelegate.modifierList!! }
    private val _annotations by lazyPub { computeAnnotations() }

    override val kotlinOrigin: KtModifierList?
        get() = owner.kotlinOrigin?.modifierList

    override fun getParent() = owner

    override fun hasExplicitModifier(name: String) = hasModifierProperty(name)

    override fun setModifierProperty(name: String, value: Boolean) = clsDelegate.setModifierProperty(name, value)
    override fun checkSetModifierProperty(name: String, value: Boolean) = clsDelegate.checkSetModifierProperty(name, value)
    override fun addAnnotation(qualifiedName: String) = clsDelegate.addAnnotation(qualifiedName)

    override fun getApplicableAnnotations(): Array<out PsiAnnotation> = annotations

    override fun getAnnotations(): Array<out PsiAnnotation> = _annotations.toTypedArray()
    override fun findAnnotation(qualifiedName: String) = _annotations.firstOrNull { it.fqNameMatches(qualifiedName) }

    override fun isEquivalentTo(another: PsiElement?) =
        another is KtLightModifierList<*> && owner == another.owner

    override fun isWritable() = false

    override fun toString() = "Light modifier list of $owner"

    private fun computeAnnotations(): List<KtLightAbstractAnnotation> {
        val annotationsForEntries =
            owner.givenAnnotations ?: lightAnnotationsForEntries(this)
        val modifierListOwner = parent
        if (modifierListOwner is KtLightClassForSourceDeclaration && modifierListOwner.isAnnotationType) {
            val sourceAnnotationNames = annotationsForEntries.mapTo(mutableSetOf()) { it.qualifiedName }
            val specialAnnotationsOnAnnotationClass = modifierListOwner.clsDelegate.modifierList?.annotations.orEmpty().filter {
                it.qualifiedName !in sourceAnnotationNames
            }.map { KtLightNonSourceAnnotation(this, it) }
            return annotationsForEntries + specialAnnotationsOnAnnotationClass
        }
        if ((modifierListOwner is KtLightMember<*> && modifierListOwner !is KtLightFieldImpl.KtLightEnumConstant)
            || modifierListOwner is LightParameter
        ) {
            val nullabilityAnnotation = when (modifierListOwner) {
                is KtUltraLightElementWithNullabilityAnnotation<*, *> -> KtUltraLightNullabilityAnnotation(modifierListOwner, this)
                else -> KtLightNullabilityAnnotation(modifierListOwner as KtLightElement<*, PsiModifierListOwner>, this)
            }

            return annotationsForEntries + listOf(nullabilityAnnotation)
        }
        return annotationsForEntries
    }

}

open class KtLightSimpleModifierList(
    owner: KtLightElement<KtModifierListOwner, PsiModifierListOwner>, private val modifiers: Set<String>
) : KtLightModifierList<KtLightElement<KtModifierListOwner, PsiModifierListOwner>>(owner) {
    override fun hasModifierProperty(name: String) = name in modifiers

    override fun copy() = KtLightSimpleModifierList(owner, modifiers)
}

private fun lightAnnotationsForEntries(lightModifierList: KtLightModifierList<*>): List<KtLightAnnotationForSourceEntry> {
    val lightModifierListOwner = lightModifierList.parent

    if (!isFromSources(lightModifierList)) return emptyList()

    val annotatedKtDeclaration = lightModifierListOwner.kotlinOrigin as? KtDeclaration

    if (annotatedKtDeclaration == null || !annotatedKtDeclaration.isValid || !hasAnnotationsInSource(annotatedKtDeclaration)) {
        return emptyList()
    }

    return getAnnotationDescriptors(annotatedKtDeclaration, lightModifierListOwner)
        .mapNotNull { descriptor ->
            val fqName = descriptor.fqName?.asString() ?: return@mapNotNull null
            val entry = descriptor.source.getPsi() as? KtAnnotationEntry ?: return@mapNotNull null
            Pair(fqName, entry)
        }
        .groupBy({ it.first }) { it.second }
        .flatMap { (fqName, entries) ->
            entries.mapIndexed { index, entry ->
                KtLightAnnotationForSourceEntry(fqName, entry, lightModifierList) {
                    lightModifierList.clsDelegate.annotations.filter { it.qualifiedName == fqName }.getOrNull(index)
                        ?: KtLightNonExistentAnnotation(lightModifierList)
                }
            }
        }
}

fun isFromSources(lightElement: KtLightElement<*, *>): Boolean {
    if (lightElement is KtLightClassForSourceDeclaration) return true
    if (lightElement.parent is KtLightClassForSourceDeclaration) return true

    val ktLightMember = lightElement.getParentOfType<KtLightMember<*>>(false) ?: return true // hope it will never happen
    if (ktLightMember.lightMemberOrigin !is LightMemberOriginForDeclaration) return false
    return true
}

private fun getAnnotationDescriptors(declaration: KtAnnotated, annotatedLightElement: KtLightElement<*, *>): List<AnnotationDescriptor> {
    val context = LightClassGenerationSupport.getInstance(declaration.project).analyze(declaration)

    val descriptor = if (declaration is KtParameter && declaration.isPropertyParameter()) {
        if (annotatedLightElement is LightParameter && annotatedLightElement.method.isConstructor)
            context[BindingContext.VALUE_PARAMETER, declaration]
        else
            context[BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, declaration]
    } else {
        context[BindingContext.DECLARATION_TO_DESCRIPTOR, declaration]
    }

    val annotatedDescriptor = when {
        descriptor is ClassDescriptor && annotatedLightElement is KtLightMethod && annotatedLightElement.isConstructor ->
            descriptor.unsubstitutedPrimaryConstructor
        descriptor !is PropertyDescriptor -> descriptor
        annotatedLightElement is KtLightFieldImpl.KtLightEnumConstant -> descriptor
        annotatedLightElement is KtLightField -> descriptor.backingField
        annotatedLightElement !is KtLightMethod -> descriptor
        annotatedLightElement.isGetter -> descriptor.getter
        annotatedLightElement.isSetter -> descriptor.setter
        else -> descriptor
    } ?: return emptyList()

    val annotations = annotatedDescriptor.annotations.toMutableList()

    if (descriptor is PropertyDescriptor) {
        val jvmDefault = descriptor.annotations.findAnnotation(JVM_DEFAULT_FQ_NAME)
        if (jvmDefault != null) {
            annotations.add(jvmDefault)
        }
    }

    return annotations
}

private fun hasAnnotationsInSource(declaration: KtAnnotated): Boolean {
    if (declaration.annotationEntries.isNotEmpty()) {
        return true
    }

    if (declaration is KtProperty) {
        return declaration.accessors.any { hasAnnotationsInSource(it) }
    }

    return false
}

