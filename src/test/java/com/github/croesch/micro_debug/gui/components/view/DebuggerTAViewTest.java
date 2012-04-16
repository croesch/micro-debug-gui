/*
 * Copyright (C) 2011-2012  Christian Roesch
 * 
 * This file is part of micro-debug-gui.
 * 
 * micro-debug-gui is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * micro-debug-gui is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with micro-debug-gui.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.croesch.micro_debug.gui.components.view;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.commons.Printer;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.i18n.Text;

/**
 * Provides test cases for {@link DebuggerTAView}.
 * 
 * @author croesch
 * @since Date: Apr 16, 2012
 */
public class DebuggerTAViewTest extends DefaultGUITestCase {

  public static DebuggerTAView getPanel(final String name) {
    return GuiActionRunner.execute(new GuiQuery<DebuggerTAView>() {
      @Override
      protected DebuggerTAView executeInEDT() throws Throwable {
        return new DebuggerTAView(name);
      }
    });
  }

  @Test
  public void testPanel() {
    printlnMethodName();

    final DebuggerTAView p = getPanel("printer");
    showInFrame(p);
    final JPanelFixture panel = new JPanelFixture(robot(), p);
    assertThat(panel.component().getName()).isEqualTo("printer");
    panel.scrollPane("printer-scrollpane");
    panel.textBox("printer-ta").requireEmpty();

    Printer.println(":D");
    panel.textBox("printer-ta").requireText(":D" + getLineSeparator());

    Printer.printErrorln("Oups ..");
    panel.textBox("printer-ta")
      .requireText(":D" + getLineSeparator() + Text.ERROR.text("Oups ..") + getLineSeparator());
  }
}
