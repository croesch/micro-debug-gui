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

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.Action;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

import com.github.croesch.micro_debug.error.MacroFileFormatException;
import com.github.croesch.micro_debug.error.MicroFileFormatException;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.controller.MainController;
import com.github.croesch.micro_debug.gui.components.controller.MainControllerTest;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link InterruptAction}.
 * 
 * @author croesch
 * @since Date: Jun 2, 2012
 */
public class InterruptActionTest extends DefaultGUITestCase {

  public static InterruptAction createAction(final MainController controller) {
    return GuiActionRunner.execute(new GuiQuery<InterruptAction>() {
      @Override
      protected InterruptAction executeInEDT() throws Throwable {
        return new InterruptAction(controller);
      }
    });
  }

  @Test
  public void testAction() throws InterruptedException, MacroFileFormatException, MicroFileFormatException,
                          FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/hi.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/hi.ijvm").getPath();
    final Mic1 processor = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final Action action = new InterruptAction(MainControllerTest.createController(processor));
    assertThat(action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_INTERRUPT.text());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testController_Null() {
    createAction(null);
  }
}
