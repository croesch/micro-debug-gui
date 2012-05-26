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
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.JTextComponentFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.console.Mic1Interpreter;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.i18n.Text;
import com.github.croesch.micro_debug.mic1.Mic1;
import com.github.croesch.micro_debug.mic1.register.Register;
import com.github.croesch.micro_debug.settings.Settings;

/**
 * Provides test cases for {@link StepAction}.
 * 
 * @author croesch
 * @since Date: May 13, 2012
 */
public class StepActionTest extends DefaultGUITestCase {

  private StepAction action;

  private Mic1 processor;

  private JTextComponent txtComponent;

  private JTextComponentFixture txtFixture;

  @Override
  protected void setUpTestCase() throws Exception {
    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    this.processor = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));
    new Mic1Interpreter(this.processor);

    this.action = createAction(this.processor);

    this.txtComponent = GuiActionRunner.execute(new GuiQuery<JTextComponent>() {
      @Override
      protected JTextComponent executeInEDT() throws Throwable {
        final JFrame f = new JFrame();
        final JTextField jTextField = new JTextField(20);
        f.add(jTextField);
        f.setSize(100, 50);
        f.setVisible(true);
        return jTextField;
      }
    });
    this.txtFixture = new JTextComponentFixture(robot(), this.txtComponent);
  }

  public static StepAction createAction(final Mic1 proc) {
    return GuiActionRunner.execute(new GuiQuery<StepAction>() {
      @Override
      protected StepAction executeInEDT() throws Throwable {
        return new StepAction(proc, DefaultGUITestCase.getWorker());
      }
    });
  }

  @Test
  public void testAction() {
    printlnMethodName();

    this.txtFixture.enterText("2");
    assertThat(this.action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_STEP.text());

    assertThat(Register.PC.getValue()).isEqualTo(Settings.MIC1_REGISTER_PC_DEFVAL.getValue());

    perform(this.action);
    assertThat(Register.PC.getValue()).isEqualTo(0);
    assertTicksDoneAndResetPrintStream(3);

    this.action.setTextComponent(this.txtComponent);

    perform(this.action);
    assertThat(Register.PC.getValue()).isEqualTo(3);
    assertTicksDoneAndResetPrintStream(7);

    this.txtFixture.requireText("2");
    this.txtFixture.deleteText();

    perform(this.action);
    assertThat(Register.PC.getValue()).isEqualTo(5);
    assertTicksDoneAndResetPrintStream(7);

    this.txtFixture.enterText("1");

    perform(this.action);
    assertThat(Register.PC.getValue()).isEqualTo(7);
    assertTicksDoneAndResetPrintStream(7);

    this.txtFixture.requireText("1");
    this.txtFixture.deleteText();
    this.txtFixture.enterText("null");

    perform(this.action);
    this.txtFixture.requireEmpty();
    assertThat(out.toString()).isEqualTo(Text.ERROR.text(Text.INVALID_NUMBER.text("null")) + getLineSeparator());
    out.reset();

    perform(this.action);
    assertThat(Register.PC.getValue()).isEqualTo(9);
    assertTicksDoneAndResetPrintStream(4);

    this.txtFixture.enterText("0");
    perform(this.action);
    assertThat(Register.PC.getValue()).isEqualTo(9);
    assertThat(out.toString()).isEmpty();

    this.txtFixture.deleteText();

    perform(this.action);
    assertThat(Register.PC.getValue()).isEqualTo(10);
    assertTicksDoneAndResetPrintStream(9);

    perform(this.action);
    assertThat(Register.PC.getValue()).isEqualTo(13);
    assertTicksDoneAndResetPrintStream(8);

    perform(this.action);
    assertThat(Register.PC.getValue()).isEqualTo(71);
    assertTicksDoneAndResetPrintStream(23);
  }
}
