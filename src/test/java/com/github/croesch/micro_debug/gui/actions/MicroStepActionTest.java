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

/**
 * Provides test cases for {@link MicroStepAction}.
 * 
 * @author croesch
 * @since Date: May 13, 2012
 */
public class MicroStepActionTest extends DefaultGUITestCase {

  private MicroStepAction action;

  private Action resetAction;

  private Mic1 processor;

  private JTextComponent txtComponent;

  private JTextComponentFixture txtFixture;

  @Override
  protected void setUpTestCase() throws Exception {
    final String micFile = getClass().getClassLoader().getResource("mic1/hi.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/hi.ijvm").getPath();
    this.processor = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));
    new Mic1Interpreter(this.processor);

    this.action = GuiActionRunner.execute(new GuiQuery<MicroStepAction>() {
      @Override
      protected MicroStepAction executeInEDT() throws Throwable {
        return new MicroStepAction(MicroStepActionTest.this.processor);
      }
    });
    this.resetAction = GuiActionRunner.execute(new GuiQuery<ResetAction>() {
      @Override
      protected ResetAction executeInEDT() throws Throwable {
        return new ResetAction(MicroStepActionTest.this.processor);
      }
    });

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

  @Test
  public void testAction() {
    printlnMethodName();

    assertThat(this.action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_MICRO_STEP.text());

    this.txtFixture.enterText("20");

    perform(this.action);
    assertThat(Register.MAR.getValue()).isZero();
    assertThat(Register.PC.getValue()).isZero();
    assertThat(this.processor.isHaltInstruction()).isFalse();
    assertThat(out.toString()).isEqualTo(Text.TICKS.text(1) + getLineSeparator());
    out.reset();

    perform(this.resetAction);

    perform(this.action);
    assertThat(Register.MAR.getValue()).isZero();
    assertThat(Register.PC.getValue()).isZero();
    assertThat(this.processor.isHaltInstruction()).isFalse();
    assertThat(out.toString()).isEqualTo(Text.TICKS.text(1) + getLineSeparator());
    out.reset();

    this.action.setTextComponent(this.txtComponent);
    this.txtFixture.deleteText();
    this.txtFixture.enterText("zwei");
    perform(this.action);
    this.txtFixture.requireEmpty();
    assertThat(Register.MAR.getValue()).isZero();
    assertThat(Register.PC.getValue()).isZero();
    assertThat(this.processor.isHaltInstruction()).isFalse();
    assertThat(out.toString()).isEqualTo(Text.ERROR.text(Text.INVALID_NUMBER.text("zwei")) + getLineSeparator());
    out.reset();

    perform(this.action);
    this.txtFixture.requireEmpty();
    assertThat(Register.MAR.getValue()).isZero();
    assertThat(Register.PC.getValue()).isZero();
    assertThat(Register.LV.getValue()).isEqualTo(-1);
    assertThat(Register.H.getValue()).isEqualTo(-1);
    assertThat(this.processor.isHaltInstruction()).isFalse();
    assertThat(out.toString()).isEqualTo(Text.TICKS.text(1) + getLineSeparator());
    out.reset();

    this.txtFixture.deleteText();
    this.txtFixture.enterText("20");

    perform(this.resetAction);

    perform(this.action);
    this.txtFixture.requireText("20");
    assertThat(Register.MDR.getValue()).isEqualTo('\n');
    assertThat(Register.MAR.getValue()).isEqualTo(-3);
    assertThat(Register.PC.getValue()).isEqualTo(3);
    assertThat(Register.LV.getValue()).isEqualTo(-2);
    assertThat(Register.H.getValue()).isEqualTo(-1);
    assertThat(this.processor.isHaltInstruction()).isTrue();
    assertThat(out.toString()).isEqualTo(Text.TICKS.text(14) + getLineSeparator());
    out.reset();

    this.action.setTextComponent(null);
    perform(this.action);
    assertThat(Register.MDR.getValue()).isEqualTo('\n');
    assertThat(Register.MAR.getValue()).isEqualTo(-3);
    assertThat(Register.PC.getValue()).isEqualTo(3);
    assertThat(Register.LV.getValue()).isEqualTo(-2);
    assertThat(Register.H.getValue()).isEqualTo(-1);
    assertThat(this.processor.isHaltInstruction()).isTrue();
    assertThat(out.toString()).isEmpty();
    out.reset();

    perform(this.resetAction);

    this.action.setTextComponent(this.txtComponent);
    this.txtFixture.deleteText();
    this.txtFixture.enterText("2");
    perform(this.action);
    assertThat(Register.MAR.getValue()).isZero();
    assertThat(Register.PC.getValue()).isZero();
    assertThat(Register.LV.getValue()).isEqualTo(-1);
    assertThat(Register.H.getValue()).isEqualTo(-1);
    assertThat(this.processor.isHaltInstruction()).isFalse();
    assertThat(out.toString()).isEqualTo(Text.TICKS.text(2) + getLineSeparator());
  }
}
