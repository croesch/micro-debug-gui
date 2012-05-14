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
package com.github.croesch.micro_debug.gui.actions;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.Action;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Provides test cases for {@link HelpAction}.
 * 
 * @author croesch
 * @since Date: May 14, 2012
 */
public class HelpActionTest extends DefaultGUITestCase {

  private Action action;

  @Override
  protected void setUpTestCase() throws Exception {
    this.action = createAction();
  }

  public static HelpAction createAction() {
    return GuiActionRunner.execute(new GuiQuery<HelpAction>() {
      @Override
      protected HelpAction executeInEDT() throws Throwable {
        return new HelpAction();
      }
    });
  }

  @Test
  public void testAction() {
    printlnMethodName();

    assertThat(this.action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_HELP.text());
  }
}
