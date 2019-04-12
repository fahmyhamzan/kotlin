/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.javac.wrappers.trees

import com.sun.tools.javac.tree.JCTree
import org.jetbrains.kotlin.descriptors.Visibilities
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedArrayAnnotationArgument
import org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedReferenceAnnotationArgument
import org.jetbrains.kotlin.load.java.JavaVisibilities
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import javax.lang.model.element.Modifier

internal val JCTree.JCModifiers.isAbstract: Boolean
    get() = Modifier.ABSTRACT in getFlags()

internal val JCTree.JCModifiers.isFinal: Boolean
    get() = Modifier.FINAL in getFlags()

internal val JCTree.JCModifiers.isStatic: Boolean
    get() = Modifier.STATIC in getFlags()

internal val JCTree.JCModifiers.hasDefaultModifier: Boolean
    get() = Modifier.DEFAULT in getFlags()

internal val JCTree.JCModifiers.visibility: Visibility
    get() = getFlags().let {
        when {
            Modifier.PUBLIC in it -> Visibilities.PUBLIC
            Modifier.PRIVATE in it -> Visibilities.PRIVATE
            Modifier.PROTECTED in it -> {
                if (Modifier.STATIC in it) JavaVisibilities.PROTECTED_STATIC_VISIBILITY
                else JavaVisibilities.PROTECTED_AND_PACKAGE
            }
            else -> JavaVisibilities.PACKAGE_VISIBILITY
        }
    }

internal fun JCTree.annotations(): Collection<JCTree.JCAnnotation> = when (this) {
    is JCTree.JCMethodDecl -> mods?.annotations
    is JCTree.JCClassDecl -> mods?.annotations
    is JCTree.JCVariableDecl -> mods?.annotations
    is JCTree.JCTypeParameter -> annotations
    else -> null
} ?: emptyList<JCTree.JCAnnotation>()

fun Collection<JavaAnnotation>.filterTypeAnnotations(): Collection<JavaAnnotation> {
    val filteredAnnotations = arrayListOf<JavaAnnotation>()
    val targetClassId = ClassId(FqName("java.lang.annotation"), Name.identifier("Target"))
    for (annotation in this) {
        val annotationClass = annotation.resolve()
        val targetAnnotation = annotationClass?.annotations?.find { it.classId == targetClassId } ?: continue
        val elementTypeArg = targetAnnotation.arguments.firstOrNull() ?: continue

        when (elementTypeArg) {
            is SymbolBasedArrayAnnotationArgument -> {
                elementTypeArg.args.find { (it as? SymbolBasedReferenceAnnotationArgument)?.element?.simpleName?.contentEquals("TYPE_USE") ?: false }
                        ?.let { filteredAnnotations.add(annotation) }
            }
            is SymbolBasedReferenceAnnotationArgument -> {
                elementTypeArg.element.simpleName.takeIf { it.contentEquals("TYPE_USE") }
                        ?.let { filteredAnnotations.add(annotation) }
            }
            is TreeBasedArrayAnnotationArgument -> {
                elementTypeArg.args.find {
                    (it as? TreeBasedReferenceAnnotationArgument)?.entryName?.asString() == "TYPE_USE"
                }?.let { filteredAnnotations.add(annotation) }
            }
            is TreeBasedReferenceAnnotationArgument -> {
                if (elementTypeArg.entryName?.asString() == "TYPE_USE") {
                    filteredAnnotations.add(annotation)
                }
            }
        }
    }

    return filteredAnnotations
}
