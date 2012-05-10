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
package com.github.croesch.micro_debug.gui.components.controller;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.basic.NumberLabel;
import com.github.croesch.micro_debug.gui.components.view.RegisterPanel;
import com.github.croesch.micro_debug.gui.components.view.RegisterPanelTest;
import com.github.croesch.micro_debug.mic1.controlstore.MicroInstruction;
import com.github.croesch.micro_debug.mic1.controlstore.MicroInstructionReader;
import com.github.croesch.micro_debug.mic1.register.Register;

/**
 * Contains test cases for the {@link RegisterController}.
 * 
 * @author croesch
 * @since Date: Apr 11, 2012
 */
public class RegisterControllerTest extends DefaultGUITestCase {

  private JPanelFixture panel;

  private BreakpointManager bpm;

  private RegisterController controller;

  public static JFrame showInFrame(final RegisterPanel panel) {
    return GuiActionRunner.execute(new GuiQuery<JFrame>() {

      @Override
      protected JFrame executeInEDT() {
        final JFrame f = new JFrame();
        f.setLayout(new MigLayout("fill"));
        f.add(panel, "grow");
        return f;
      }
    });
  }

  @Override
  protected void setUpTestCase() throws Exception {
    this.bpm = new BreakpointManager();
    final RegisterPanel p = RegisterPanelTest.getPanel("panel");
    new FrameFixture(robot(), showInFrame(p)).show(new Dimension(300, 500));
    this.controller = new RegisterController(p, this.bpm);
    this.panel = new JPanelFixture(robot(), p);
  }

  @Test
  public void testSettingBreakpoints() throws IOException {
    printlnMethodName();

    slowDownRobot();

    this.panel.checkBox("bpCB-TOS").check();
    // instruction writes only TOS
    MicroInstruction in = MicroInstructionReader.read(new ByteArrayInputStream(new byte[] { 0, 0, (byte) 0x02, 0, 0 }));
    assertThat(this.bpm.isBreakpoint(0, 0, null, in)).isTrue();
    this.panel.checkBox("bpCB-TOS").doubleClick();
    assertThat(this.bpm.isBreakpoint(0, 0, null, in)).isTrue();
    this.panel.checkBox("bpCB-TOS").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, null, in)).isFalse();

    this.panel.checkBox("bpCB-TOS").check();
    // instruction writes all registers
    in = MicroInstructionReader.read(new ByteArrayInputStream(new byte[] { 0, 0, (byte) 0xFF, (byte) 0xFF, 0 }));
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-CPP").check();
    this.panel.checkBox("bpCB-H").check();
    this.panel.checkBox("bpCB-LV").check();
    this.panel.checkBox("bpCB-MAR").check();
    this.panel.checkBox("bpCB-MBR").check();
    this.panel.checkBox("bpCB-MBRU").check();
    this.panel.checkBox("bpCB-MDR").check();
    this.panel.checkBox("bpCB-PC").check();
    this.panel.checkBox("bpCB-SP").check();
    this.panel.checkBox("bpCB-OPC").check();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-CPP").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-TOS").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-H").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-LV").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-MAR").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-MBR").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-MBRU").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-MDR").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-PC").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-SP").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
    this.panel.checkBox("bpCB-OPC").uncheck();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isFalse();

    this.panel.checkBox("bpCB-OPC").doubleClick();
    assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isFalse();

    for (final Register r : Register.values()) {
      this.panel.checkBox("bpCB-" + r.name()).check();
      assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isTrue();
      this.panel.checkBox("bpCB-" + r.name()).uncheck();
      assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isFalse();
      this.panel.checkBox("bpCB-" + r.name()).doubleClick();
      assertThat(this.bpm.isBreakpoint(0, 0, in, in)).isFalse();
    }
  }

  @Test
  public void testStateChanged_Null() {
    printlnMethodName();

    this.controller.stateChanged(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCstr_NullView() {
    new RegisterController(null, new BreakpointManager());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCstr_NullBpm() {
    new RegisterController(RegisterPanelTest.getPanel("panel"), null);
  }

  @Test
  public void testUpdate() {
    printlnMethodName();

    Register.CPP.setValue(42);
    Register.H.setValue(43);
    Register.LV.setValue(44);
    Register.MAR.setValue(45);
    Register.MBR.setValue(46);
    Register.MDR.setValue(48);
    Register.OPC.setValue(49);
    Register.PC.setValue(50);
    Register.SP.setValue(51);
    Register.TOS.setValue(52);

    update();

    assertThat(this.panel.label("regValue-CPP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(42);
    assertThat(this.panel.label("regValue-H").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(43);
    assertThat(this.panel.label("regValue-LV").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(44);
    assertThat(this.panel.label("regValue-MAR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(45);
    assertThat(this.panel.label("regValue-MBR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(46);
    assertThat(this.panel.label("regValue-MBRU").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(46);
    assertThat(this.panel.label("regValue-MDR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(48);
    assertThat(this.panel.label("regValue-OPC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(49);
    assertThat(this.panel.label("regValue-PC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(50);
    assertThat(this.panel.label("regValue-SP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(51);
    assertThat(this.panel.label("regValue-TOS").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(52);

    Register.CPP.setValue(4711);
    Register.H.setValue(4712);
    Register.LV.setValue(4713);
    Register.MAR.setValue(4714);
    Register.MBR.setValue(42);
    Register.MDR.setValue(4717);
    Register.OPC.setValue(4718);
    Register.PC.setValue(4719);
    Register.SP.setValue(4720);
    Register.TOS.setValue(4721);

    assertThat(this.panel.label("regValue-CPP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(42);
    assertThat(this.panel.label("regValue-H").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(43);
    assertThat(this.panel.label("regValue-LV").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(44);
    assertThat(this.panel.label("regValue-MAR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(45);
    assertThat(this.panel.label("regValue-MBR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(46);
    assertThat(this.panel.label("regValue-MBRU").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(46);
    assertThat(this.panel.label("regValue-MDR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(48);
    assertThat(this.panel.label("regValue-OPC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(49);
    assertThat(this.panel.label("regValue-PC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(50);
    assertThat(this.panel.label("regValue-SP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(51);
    assertThat(this.panel.label("regValue-TOS").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(52);

    update();

    assertThat(this.panel.label("regValue-CPP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4711);
    assertThat(this.panel.label("regValue-H").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4712);
    assertThat(this.panel.label("regValue-LV").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4713);
    assertThat(this.panel.label("regValue-MAR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4714);
    assertThat(this.panel.label("regValue-MBR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(42);
    assertThat(this.panel.label("regValue-MBRU").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(42);
    assertThat(this.panel.label("regValue-MDR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4717);
    assertThat(this.panel.label("regValue-OPC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4718);
    assertThat(this.panel.label("regValue-PC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4719);
    assertThat(this.panel.label("regValue-SP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4720);
    assertThat(this.panel.label("regValue-TOS").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4721);
  }

  private void update() {
    GuiActionRunner.execute(new GuiTask() {

      @Override
      protected void executeInEDT() throws Throwable {
        RegisterControllerTest.this.controller.performViewUpdate();
      }
    });
  }
}
