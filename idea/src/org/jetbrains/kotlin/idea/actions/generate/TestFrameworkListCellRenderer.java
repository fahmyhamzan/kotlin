/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.actions.generate;

import com.intellij.testIntegration.TestFramework;

import javax.swing.*;
import java.awt.*;

public class TestFrameworkListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component result = super.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
        if (value == null) return result;

        TestFramework framework = (TestFramework) value;
        setIcon(framework.getIcon());
        setText(framework.getName());
        return result;
    }
}
