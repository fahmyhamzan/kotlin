/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.refactoring.pushDown

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.refactoring.HelpID
import com.intellij.refactoring.RefactoringBundle
import com.intellij.refactoring.util.CommonRefactoringUtil
import com.intellij.refactoring.util.RefactoringUIUtil
import org.jetbrains.kotlin.idea.core.isInheritable
import org.jetbrains.kotlin.idea.refactoring.AbstractPullPushMembersHandler
import org.jetbrains.kotlin.idea.refactoring.memberInfo.KotlinMemberInfo
import org.jetbrains.kotlin.idea.refactoring.memberInfo.KotlinMemberInfoStorage
import org.jetbrains.kotlin.idea.refactoring.pullUp.PULL_MEMBERS_UP
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.idea.statistics.KotlinEventTrigger
import org.jetbrains.kotlin.idea.statistics.KotlinStatisticsTrigger

val PUSH_MEMBERS_DOWN = "Push Members Down"

class KotlinPushDownHandler : AbstractPullPushMembersHandler(
        refactoringName = PUSH_MEMBERS_DOWN,
        helpId = HelpID.MEMBERS_PUSH_DOWN,
        wrongPositionMessage = RefactoringBundle.message("the.caret.should.be.positioned.inside.a.class.to.push.members.from")
) {
    companion object {
        val PUSH_DOWN_TEST_HELPER_KEY = "PUSH_DOWN_TEST_HELPER_KEY"
    }

    interface TestHelper {
        fun adjustMembers(members: List<KotlinMemberInfo>): List<KotlinMemberInfo>
    }

    private fun reportFinalClassOrObject(project: Project, editor: Editor?, classOrObject: KtClassOrObject) {
        val message = RefactoringBundle.getCannotRefactorMessage(
                "${RefactoringUIUtil.getDescription(classOrObject, false)} is final".capitalize()
        )
        CommonRefactoringUtil.showErrorHint(project, editor, message, PULL_MEMBERS_UP, HelpID.MEMBERS_PULL_UP)
    }

    override fun invoke(project: Project,
                        editor: Editor?,
                        classOrObject: KtClassOrObject?,
                        member: KtNamedDeclaration?,
                        dataContext: DataContext?) {
        if (classOrObject == null) {
            reportWrongContext(project, editor)
            return
        }

        if (!(classOrObject is KtClass && classOrObject.isInheritable())) {
            reportFinalClassOrObject(project, editor, classOrObject)
            return
        }

        val members = KotlinMemberInfoStorage(classOrObject).getClassMemberInfos(classOrObject).filter { it.member !is KtParameter }
        if (ApplicationManager.getApplication().isUnitTestMode) {
            val helper = dataContext?.getData(PUSH_DOWN_TEST_HELPER_KEY) as TestHelper
            val selectedMembers = helper.adjustMembers(members)
            KotlinPushDownProcessor(project, classOrObject, selectedMembers).run()
        }
        else {
            val manager = PsiManager.getInstance(project)
            members.filter { manager.areElementsEquivalent(it.member, member) }.forEach { it.isChecked = true }
            KotlinPushDownDialog(project, members, classOrObject).show()
        }
    }

    override fun invoke(project: Project, elements: Array<out PsiElement>, dataContext: DataContext?) {
        super.invoke(project, elements, dataContext)
        KotlinStatisticsTrigger.trigger(KotlinEventTrigger.KotlinIdeRefactoringTrigger, this::class.java.name)
    }

    override fun invoke(project: Project, editor: Editor, file: PsiFile, dataContext: DataContext?) {
        super.invoke(project, editor, file, dataContext)
        KotlinStatisticsTrigger.trigger(KotlinEventTrigger.KotlinIdeRefactoringTrigger, this::class.java.name)
    }
}