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

import javax.swing.Action;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

import com.github.croesch.micro_debug.console.Mic1Interpreter;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.commons.WorkerThread;
import com.github.croesch.micro_debug.gui.components.controller.MainController;
import com.github.croesch.micro_debug.gui.components.controller.MainControllerTest;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link ResetAction}.
 * 
 * @author croesch
 * @since Date: May 13, 2012
 */
public class ResetActionTest extends DefaultGUITestCase {

  private Action action;

  private Mic1 processor;

  private ActionProvider provider;

  @Override
  protected void setUpTestCase() throws Exception {
    final String micFile = getClass().getClassLoader().getResource("mic1/hi.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/hi.ijvm").getPath();
    this.processor = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));
    new Mic1Interpreter(this.processor);

    this.provider = ActionProviderTest.getProvider(this.processor);
    this.action = createAction(this.processor, getWorker(), this.provider);
  }

  public static ResetAction createAction(final Mic1 proc, final WorkerThread thread, final ActionProvider provider) {
    return GuiActionRunner.execute(new GuiQuery<ResetAction>() {
      @Override
      protected ResetAction executeInEDT() throws Throwable {
        final MainController cont = MainControllerTest.createController(proc);
        return new ResetAction(cont, thread, provider);
      }
    });
  }

  @Test
  public void testAction() {
    printlnMethodName();

    assertThat(this.action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_RESET.text());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAction_NullThread() {
    createAction(this.processor, null, this.provider);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAction_NullProvider() {
    createAction(this.processor, getWorker(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAction_NullProcessor() {
    createAction(null, getWorker(), this.provider);
  }
}
