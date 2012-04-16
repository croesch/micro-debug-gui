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
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.basic.NumberLabel;
import com.github.croesch.micro_debug.mic1.register.Register;

/**
 * Provides test cases for {@link RegisterPanel}.
 * 
 * @author croesch
 * @since Date: Apr 9, 2012
 */
public class RegisterPanelTest extends DefaultGUITestCase {

  public static RegisterPanel getPanel(final String name) {
    return GuiActionRunner.execute(new GuiQuery<RegisterPanel>() {
      @Override
      protected RegisterPanel executeInEDT() throws Throwable {
        return new RegisterPanel(name);
      }
    });
  }

  @Test
  public void testGetCheckBox() {
    printlnMethodName();

    final RegisterPanel p = getPanel("r");
    showInFrame(p);

    final JPanelFixture panel = new JPanelFixture(robot(), p);
    assertThat(panel.checkBox("bpCB-CPP").component()).isEqualTo(p.getCheckBox(Register.CPP));
    assertThat(panel.checkBox("bpCB-H").component()).isEqualTo(p.getCheckBox(Register.H));
    assertThat(panel.checkBox("bpCB-LV").component()).isEqualTo(p.getCheckBox(Register.LV));
    assertThat(panel.checkBox("bpCB-MAR").component()).isEqualTo(p.getCheckBox(Register.MAR));
    assertThat(panel.checkBox("bpCB-MBR").component()).isEqualTo(p.getCheckBox(Register.MBR));
    assertThat(panel.checkBox("bpCB-MBRU").component()).isEqualTo(p.getCheckBox(Register.MBRU));
    assertThat(panel.checkBox("bpCB-MDR").component()).isEqualTo(p.getCheckBox(Register.MDR));
    assertThat(panel.checkBox("bpCB-OPC").component()).isEqualTo(p.getCheckBox(Register.OPC));
    assertThat(panel.checkBox("bpCB-PC").component()).isEqualTo(p.getCheckBox(Register.PC));
    assertThat(panel.checkBox("bpCB-SP").component()).isEqualTo(p.getCheckBox(Register.SP));
    assertThat(panel.checkBox("bpCB-TOS").component()).isEqualTo(p.getCheckBox(Register.TOS));
  }

  @Test
  public void testPanel() {
    printlnMethodName();

    final RegisterPanel p = getPanel("r");
    showInFrame(p);
    final JPanelFixture panel = new JPanelFixture(robot(), p);
    assertThat(panel.component().getName()).isEqualTo("r");
    panel.label("regDesc-CPP").requireText("CPP");
    panel.checkBox("bpCB-CPP").requireNotSelected();
    assertThat(panel.label("regValue-CPP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(Register.CPP
                                                                                                      .getValue());

    panel.label("regDesc-H").requireText("H");
    panel.checkBox("bpCB-H").requireNotSelected();
    assertThat(panel.label("regValue-H").targetCastedTo(NumberLabel.class).getNumber())
      .isEqualTo(Register.H.getValue());

    panel.label("regDesc-LV").requireText("LV");
    panel.checkBox("bpCB-LV").requireNotSelected();
    assertThat(panel.label("regValue-LV").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(Register.LV
                                                                                                     .getValue());

    panel.label("regDesc-MAR").requireText("MAR");
    panel.checkBox("bpCB-MAR").requireNotSelected();
    assertThat(panel.label("regValue-MAR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(Register.MAR
                                                                                                      .getValue());

    panel.label("regDesc-MBR").requireText("MBR");
    panel.checkBox("bpCB-MBR").requireNotSelected();
    assertThat(panel.label("regValue-MBR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(Register.MBR
                                                                                                      .getValue());

    panel.label("regDesc-MBRU").requireText("MBRU");
    panel.checkBox("bpCB-MBRU").requireNotSelected();
    assertThat(panel.label("regValue-MBRU").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(Register.MBRU
                                                                                                       .getValue());

    panel.label("regDesc-MDR").requireText("MDR");
    panel.checkBox("bpCB-MDR").requireNotSelected();
    assertThat(panel.label("regValue-MDR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(Register.MDR
                                                                                                      .getValue());

    panel.label("regDesc-OPC").requireText("OPC");
    panel.checkBox("bpCB-OPC").requireNotSelected();
    assertThat(panel.label("regValue-OPC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(Register.OPC
                                                                                                      .getValue());

    panel.label("regDesc-PC").requireText("PC");
    panel.checkBox("bpCB-PC").requireNotSelected();
    assertThat(panel.label("regValue-PC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(Register.PC
                                                                                                     .getValue());

    panel.label("regDesc-SP").requireText("SP");
    panel.checkBox("bpCB-SP").requireNotSelected();
    assertThat(panel.label("regValue-SP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(Register.SP
                                                                                                     .getValue());

    panel.label("regDesc-TOS").requireText("TOS");
    panel.checkBox("bpCB-TOS").requireNotSelected();
    assertThat(panel.label("regValue-TOS").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(Register.TOS
                                                                                                      .getValue());
  }

  @Test
  public void testUpdate() {
    printlnMethodName();

    final RegisterPanel p = getPanel("r");
    showInFrame(p);
    final JPanelFixture panel = new JPanelFixture(robot(), p);

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

    update(panel);

    assertThat(panel.label("regValue-CPP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(42);
    assertThat(panel.label("regValue-H").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(43);
    assertThat(panel.label("regValue-LV").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(44);
    assertThat(panel.label("regValue-MAR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(45);
    assertThat(panel.label("regValue-MBR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(46);
    assertThat(panel.label("regValue-MBRU").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(46);
    assertThat(panel.label("regValue-MDR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(48);
    assertThat(panel.label("regValue-OPC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(49);
    assertThat(panel.label("regValue-PC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(50);
    assertThat(panel.label("regValue-SP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(51);
    assertThat(panel.label("regValue-TOS").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(52);

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

    assertThat(panel.label("regValue-CPP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(42);
    assertThat(panel.label("regValue-H").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(43);
    assertThat(panel.label("regValue-LV").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(44);
    assertThat(panel.label("regValue-MAR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(45);
    assertThat(panel.label("regValue-MBR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(46);
    assertThat(panel.label("regValue-MBRU").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(46);
    assertThat(panel.label("regValue-MDR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(48);
    assertThat(panel.label("regValue-OPC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(49);
    assertThat(panel.label("regValue-PC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(50);
    assertThat(panel.label("regValue-SP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(51);
    assertThat(panel.label("regValue-TOS").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(52);

    update(panel);

    assertThat(panel.label("regValue-CPP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4711);
    assertThat(panel.label("regValue-H").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4712);
    assertThat(panel.label("regValue-LV").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4713);
    assertThat(panel.label("regValue-MAR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4714);
    assertThat(panel.label("regValue-MBR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(42);
    assertThat(panel.label("regValue-MBRU").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(42);
    assertThat(panel.label("regValue-MDR").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4717);
    assertThat(panel.label("regValue-OPC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4718);
    assertThat(panel.label("regValue-PC").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4719);
    assertThat(panel.label("regValue-SP").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4720);
    assertThat(panel.label("regValue-TOS").targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(4721);
  }

  private void update(final JPanelFixture panel) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        panel.targetCastedTo(RegisterPanel.class).update();
      }
    });
  }
}
