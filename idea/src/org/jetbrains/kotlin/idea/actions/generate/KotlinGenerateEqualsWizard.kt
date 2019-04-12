/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.actions.generate

import com.intellij.codeInsight.CodeInsightBundle
import com.intellij.codeInsight.CodeInsightSettings
import com.intellij.codeInsight.generation.ui.AbstractGenerateEqualsWizard
import com.intellij.ide.wizard.StepAdapter
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.refactoring.classMembers.AbstractMemberInfoModel
import com.intellij.ui.NonFocusableCheckBox
import com.intellij.util.containers.HashMap
import org.jetbrains.kotlin.idea.core.isInheritable
import org.jetbrains.kotlin.idea.refactoring.memberInfo.KotlinMemberInfo
import org.jetbrains.kotlin.idea.refactoring.memberInfo.KotlinMemberSelectionPanel
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.utils.keysToMap
import javax.swing.JLabel
import javax.swing.JPanel

class KotlinGenerateEqualsWizard(
    project: Project,
    klass: KtClass,
    properties: List<KtNamedDeclaration>,
    needEquals: Boolean,
    needHashCode: Boolean
) : AbstractGenerateEqualsWizard<KtClass, KtNamedDeclaration, KotlinMemberInfo>(
    project, BuilderImpl(klass, properties, needEquals, needHashCode)
) {
    private object MemberInfoModelImpl : AbstractMemberInfoModel<KtNamedDeclaration, KotlinMemberInfo>()

    private class BuilderImpl(
        private val klass: KtClass,
        properties: List<KtNamedDeclaration>,
        needEquals: Boolean,
        needHashCode: Boolean
    ) : AbstractGenerateEqualsWizard.Builder<KtClass, KtNamedDeclaration, KotlinMemberInfo>() {
        private val equalsPanel: KotlinMemberSelectionPanel?
        private val hashCodePanel: KotlinMemberSelectionPanel?

        private val memberInfos = properties.map { createMemberInfo(it) }

        private val membersToHashCode = HashMap(properties.keysToMap { createMemberInfo(it) })

        init {
            equalsPanel = if (needEquals) {
                KotlinMemberSelectionPanel("Choose properties to be included in equals()", memberInfos, null).apply {
                    table.memberInfoModel = MemberInfoModelImpl
                }
            } else null

            hashCodePanel = if (needHashCode) {
                KotlinMemberSelectionPanel("Choose properties to be included in hashCode()", memberInfos, null).apply {
                    table.memberInfoModel = MemberInfoModelImpl
                }
            } else null
        }

        private fun createMemberInfo(it: KtNamedDeclaration) = KotlinMemberInfo(it).apply { isChecked = true }

        override fun getPsiClass() = klass

        override fun getClassFields() = memberInfos

        override fun getFieldsToHashCode() = membersToHashCode

        override fun getFieldsToNonNull() = HashMap<KtNamedDeclaration, KotlinMemberInfo>()

        override fun getEqualsPanel() = equalsPanel

        override fun getHashCodePanel() = hashCodePanel

        override fun getNonNullPanel() = null

        override fun updateHashCodeMemberInfos(equalsMemberInfos: MutableCollection<KotlinMemberInfo>) {
            hashCodePanel?.table?.setMemberInfos(equalsMemberInfos.map { membersToHashCode[it.member] })
        }

        override fun updateNonNullMemberInfos(equalsMemberInfos: MutableCollection<KotlinMemberInfo>?) {

        }
    }

    private object OptionsStep : StepAdapter() {
        private val panel = JPanel(VerticalFlowLayout())

        init {
            with(NonFocusableCheckBox(CodeInsightBundle.message("generate.equals.hashcode.accept.sublcasses"))) {
                isSelected = CodeInsightSettings.getInstance().USE_INSTANCEOF_ON_EQUALS_PARAMETER
                addActionListener { CodeInsightSettings.getInstance().USE_INSTANCEOF_ON_EQUALS_PARAMETER = isSelected }
                panel.add(this)
            }
            panel.add(JLabel(CodeInsightBundle.message("generate.equals.hashcode.accept.sublcasses.explanation")))
        }

        override fun getComponent() = panel
    }

    override fun addSteps() {
        if (myEqualsPanel != null && myClass.isInheritable()) {
            addStep(OptionsStep)
        }
        super.addSteps()
    }

    override fun doOKAction() {
        myEqualsPanel?.let { updateHashCodeMemberInfos(it.table.selectedMemberInfos) }
        super.doOKAction()
    }

    fun getPropertiesForEquals() = myEqualsPanel?.table?.selectedMemberInfos?.map { it.member } ?: emptyList()

    fun getPropertiesForHashCode() = myHashCodePanel?.table?.selectedMemberInfos?.map { it.member } ?: emptyList()
}