/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.inspections

import com.intellij.codeInspection.IntentionWrapper
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.*
import org.jetbrains.kotlin.asJava.elements.KtLightFieldImpl.KtLightFieldForDeclaration
import org.jetbrains.kotlin.idea.inspections.MayBeConstantInspection.Status.*
import org.jetbrains.kotlin.idea.quickfix.AddConstModifierFix
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.forEachDescendantOfType

class FakeJvmFieldConstantInspection : AbstractKotlinInspection() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : JavaElementVisitor() {
            override fun visitAnnotationParameterList(list: PsiAnnotationParameterList) {
                super.visitAnnotationParameterList(list)

                for (attribute in list.attributes) {
                    val valueExpression = attribute.value as? PsiExpression ?: continue
                    checkExpression(valueExpression, holder)
                }
            }

            override fun visitSwitchLabelStatement(statement: PsiSwitchLabelStatement) {
                super.visitSwitchLabelStatement(statement)

                val valueExpression = statement.caseValue ?: return
                checkExpression(valueExpression, holder)
            }

            override fun visitAssignmentExpression(expression: PsiAssignmentExpression) {
                super.visitAssignmentExpression(expression)

                if (expression.operationTokenType != JavaTokenType.EQ) return
                val leftType = expression.lExpression.type as? PsiPrimitiveType ?: return
                checkAssignmentChildren(expression.rExpression ?: return, leftType, holder)
            }

            override fun visitVariable(variable: PsiVariable) {
                super.visitVariable(variable)

                val leftType = variable.type as? PsiPrimitiveType ?: return
                val initializer = variable.initializer ?: return
                checkAssignmentChildren(initializer, leftType, holder)
            }
        }
    }

    private fun checkAssignmentChildren(right: PsiExpression, leftType: PsiPrimitiveType, holder: ProblemsHolder) {
        if (leftType == PsiType.BOOLEAN || leftType == PsiType.CHAR || leftType == PsiType.VOID) return
        right.forEachDescendantOfType<PsiExpression>(canGoInside = { parentElement ->
            parentElement !is PsiCallExpression && parentElement !is PsiTypeCastExpression
        }) { rightPart ->
            checkExpression(rightPart, holder) { resolvedPropertyType ->
                leftType != resolvedPropertyType && !leftType.isAssignableFrom(resolvedPropertyType)
            }
        }
    }

    private fun checkExpression(
        valueExpression: PsiExpression,
        holder: ProblemsHolder,
        additionalTypeCheck: (PsiType) -> Boolean = { true }
    ) {
        val resolvedLightField = (valueExpression as? PsiReference)?.resolve() as? KtLightFieldForDeclaration ?: return
        val resolvedProperty = resolvedLightField.kotlinOrigin as? KtProperty ?: return
        with(MayBeConstantInspection) {
            if (resolvedProperty.annotationEntries.isEmpty()) return
            val resolvedPropertyStatus = resolvedProperty.getStatus()
            if (resolvedPropertyStatus == JVM_FIELD_MIGHT_BE_CONST ||
                resolvedPropertyStatus == JVM_FIELD_MIGHT_BE_CONST_NO_INITIALIZER ||
                resolvedPropertyStatus == JVM_FIELD_MIGHT_BE_CONST_ERRONEOUS
            ) {
                val resolvedPropertyType = resolvedLightField.type
                if (!additionalTypeCheck(resolvedPropertyType)) return
                val fixes = mutableListOf<LocalQuickFix>()
                if (resolvedPropertyStatus == JVM_FIELD_MIGHT_BE_CONST) {
                    fixes += IntentionWrapper(AddConstModifierFix(resolvedProperty), resolvedProperty.containingFile)
                }
                holder.registerProblem(
                    valueExpression,
                    "Use of non-const Kotlin property as Java constant is incorrect. Will be forbidden in 1.4",
                    ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
                    *fixes.toTypedArray()
                )
            }
        }
    }
}