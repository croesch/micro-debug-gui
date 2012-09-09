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

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

import org.fest.swing.core.MouseButton;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.commons.Printer;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.MainFrame;
import com.github.croesch.micro_debug.gui.components.MainFrameTest;
import com.github.croesch.micro_debug.gui.components.code.ACodeArea;
import com.github.croesch.micro_debug.gui.components.code.RulerTest;
import com.github.croesch.micro_debug.gui.components.view.ACodeView;
import com.github.croesch.micro_debug.gui.components.view.MainView;
import com.github.croesch.micro_debug.gui.settings.BooleanSettings;
import com.github.croesch.micro_debug.i18n.Text;
import com.github.croesch.micro_debug.mic1.Mic1;
import com.github.croesch.micro_debug.mic1.register.Register;

/**
 * Provides test cases for {@link MainController}.
 * 
 * @author croesch
 * @since Date: Jun 2, 2012
 */
public class MainControllerTest extends DefaultGUITestCase {

  private FrameFixture frame;

  private MainController controller;

  public static MainController createController(final Mic1 processor) {
    return GuiActionRunner.execute(new GuiQuery<MainController>() {
      @Override
      protected MainController executeInEDT() throws Throwable {
        final BreakpointManager bpm = new BreakpointManager();
        final MainView view = new MainView("view", processor, bpm);
        Printer.setPrintStream(new PrintStream(out));
        return new MainController(processor, view, bpm, BooleanSettings.UPDATE_AFTER_EACH_TICK.value());
      }
    });
  }

  @Override
  protected void setUpTestCase() throws Exception {
    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final MainFrame frame = MainFrameTest.getFrame(proc);
    this.controller = frame.getController();
    this.frame = new FrameFixture(robot(), frame);
    this.frame.show();
  }

  @Test
  public void testMicroBreakpoint() throws BadLocationException {
    printlnMethodName();

    final JPanel r = this.frame.panel("microCode-ruler").component();
    final ACodeArea ta = (ACodeArea) this.frame.panel("microCode").textBox("microCode-code-ta").component();
    robot().click(r, new Point(0, RulerTest.getYOfLine(ta, 2)), MouseButton.LEFT_BUTTON, 2);
    assertThat(this.controller.getBpm().isMicroBreakpoint(2)).isTrue();

    this.frame.menuItem("run").click();
    this.frame.textBox("printer-ta").requireText(Text.TICKS.text(1) + getLineSeparator());
  }

  @Test
  public void testMacroBreakpoint() throws BadLocationException {
    printlnMethodName();

    final JPanel r = this.frame.panel("macroCode-ruler").component();
    final ACodeView view = (ACodeView) this.frame.panel("macroCode").component();
    final ACodeArea ta = (ACodeArea) this.frame.panel("macroCode").textBox("macroCode-code-ta").component();
    robot().click(r, new Point(0, RulerTest.getYOfLine(ta, 2)), MouseButton.LEFT_BUTTON, 2);
    assertThat(this.controller.getBpm().isMacroBreakpoint(view.getLineNumberMapper().getLineForNumber(2))).isTrue();

    this.frame.menuItem("run").click();
    this.frame.textBox("printer-ta").requireText(Text.TICKS.text(10) + getLineSeparator());

    robot().click(r, new Point(0, RulerTest.getYOfLine(ta, 4)), MouseButton.LEFT_BUTTON, 2);
    assertThat(this.controller.getBpm().isMacroBreakpoint(view.getLineNumberMapper().getLineForNumber(2))).isTrue();
    assertThat(this.controller.getBpm().isMacroBreakpoint(view.getLineNumberMapper().getLineForNumber(4))).isTrue();

    robot().click(r, new Point(0, RulerTest.getYOfLine(ta, 2)), MouseButton.LEFT_BUTTON, 2);
    assertThat(this.controller.getBpm().isMacroBreakpoint(view.getLineNumberMapper().getLineForNumber(2))).isFalse();
    assertThat(this.controller.getBpm().isMacroBreakpoint(view.getLineNumberMapper().getLineForNumber(4))).isTrue();

    this.frame.menuItem("run").click();
    this.frame.textBox("printer-ta").requireText(Text.TICKS.text(10) + getLineSeparator() + Text.TICKS.text(14)
                                                         + getLineSeparator());
  }

  @Test
  public void testRegisterBreakpoint() throws BadLocationException {
    printlnMethodName();

    final JPanelFixture r = this.frame.panel("register");
    r.label("regDesc-MAR").requireText("MAR");
    r.label("regValue-MAR").requireText("0x0");
    r.label("regDesc-SP").requireText("SP");
    r.label("regValue-SP").requireText("0xC000");

    assertThat(this.controller.getBpm().isRegisterBreakpoint(Register.MAR)).isFalse();
    assertThat(this.controller.getBpm().isRegisterBreakpoint(Register.SP)).isFalse();
    r.checkBox("bpCB-MAR").click();
    r.checkBox("bpCB-SP").click();
    assertThat(this.controller.getBpm().isRegisterBreakpoint(Register.MAR)).isTrue();
    assertThat(this.controller.getBpm().isRegisterBreakpoint(Register.SP)).isTrue();

    this.frame.menuItem("run").click();
    this.frame.textBox("printer-ta").requireText(Text.TICKS.text(4) + getLineSeparator());
    r.label("regValue-MAR").requireText("0x0");
    r.label("regValue-SP").requireText("0xC000");

    this.frame.menuItem("run").click();
    this.frame.textBox("printer-ta").requireText(Text.TICKS.text(4) + getLineSeparator() + Text.TICKS.text(4)
                                                         + getLineSeparator());
    r.label("regValue-MAR").requireText("0xC001");
    r.label("regValue-SP").requireText("0xC001");

    this.frame.menuItem("micro-step").click();
    this.frame.textBox("printer-ta")
              .requireText(Text.TICKS.text(4) + getLineSeparator() + Text.TICKS.text(4) + getLineSeparator()
                                   + Text.TICKS.text(1) + getLineSeparator());
    r.label("regValue-MAR").requireText("0xC002");
    r.label("regValue-SP").requireText("0xC002");

    assertThat(this.controller.getBpm().isRegisterBreakpoint(Register.MAR)).isTrue();
    assertThat(this.controller.getBpm().isRegisterBreakpoint(Register.SP)).isTrue();
  }

  @Test
  public void testInterrupt() throws BadLocationException {
    printlnMethodName();

    this.frame.menuItem("interrupt").requireDisabled();
    this.frame.menuItem("run").click();
    this.frame.menuItem("interrupt").requireEnabled();

    this.frame.textBox("input-tf").enterText("17");
    this.frame.textBox("printer-ta").requireEmpty();

    this.frame.menuItem("interrupt").click();
    this.frame.menuItem("interrupt").requireDisabled();
    this.frame.textBox("input-tf").pressAndReleaseKeys(KeyEvent.VK_ENTER);

    this.frame.textBox("printer-ta").requireText(Text.TICKS.text(83) + getLineSeparator());
    this.frame.textBox("input-tf").requireEmpty();

    this.frame.menuItem("reset").click();
    this.frame.menuItem("run").click();
    this.frame.menuItem("interrupt").click();
    this.frame.textBox("input-tf").enterText("17");
    this.frame.textBox("input-tf").pressAndReleaseKeys(KeyEvent.VK_ENTER);
  }
}
