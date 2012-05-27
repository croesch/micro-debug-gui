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
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.i18n.Text;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link RunAction}.
 * 
 * @author croesch
 * @since Date: May 13, 2012
 */
public class RunActionTest extends DefaultGUITestCase {

  private Action action;

  private Action resetAction;

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
    this.resetAction = ResetActionTest.createAction(this.processor, getWorker(), this.provider);
  }

  public static RunAction createAction(final Mic1 proc, final WorkerThread thread, final ActionProvider provider) {
    return GuiActionRunner.execute(new GuiQuery<RunAction>() {
      @Override
      protected RunAction executeInEDT() throws Throwable {
        return new RunAction(proc, thread, provider);
      }
    });
  }

  @Test
  public void testAction() {
    printlnMethodName();

    assertThat(this.action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_RUN.text());

    perform(this.provider, this.action);
    out.reset();

    perform(this.provider, this.resetAction);

    perform(this.provider, this.action);
    assertThat(out.toString()).isEqualTo(Text.TICKS.text(14) + getLineSeparator());

    out.reset();
    perform(this.provider, this.action);
    assertThat(out.toString()).isEmpty();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAction_NullThread() {
    createAction(this.processor, null, this.provider);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAction_NullProvider() {
    createAction(this.processor, getWorker(), null);
  }
}
