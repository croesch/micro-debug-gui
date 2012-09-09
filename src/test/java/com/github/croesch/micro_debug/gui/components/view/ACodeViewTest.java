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

import javax.swing.AbstractAction;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Code superclass to avoid code duplication in test classes.
 * 
 * @author croesch
 * @since Date: Sep 9, 2012
 */

public class ACodeViewTest extends DefaultGUITestCase {

  protected void setAction(final ACodeView view, final AbstractAction action) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        view.setStepAction(action);
      }
    });
  }

  protected void setActionEnabled(final AbstractAction action, final boolean enabled) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        action.setEnabled(enabled);
      }
    });
  }

  protected void highlight(final ACodeView p, final int line) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        p.highlight(line);
      }
    });
  }
}
