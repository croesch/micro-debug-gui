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
package com.github.croesch.micro_debug.gui.components;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.error.MacroFileFormatException;
import com.github.croesch.micro_debug.error.MicroFileFormatException;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.code.ACodeArea;
import com.github.croesch.micro_debug.gui.components.code.ACodeAreaTest;
import com.github.croesch.micro_debug.gui.components.code.LineNumberLabelTest;
import com.github.croesch.micro_debug.gui.components.controller.CodeControllerTest;
import com.github.croesch.micro_debug.gui.components.view.MacroCodeView;
import com.github.croesch.micro_debug.gui.components.view.MicroCodeView;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.IntegerSettings;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;
import com.github.croesch.micro_debug.mic1.Mic1;
import com.github.croesch.micro_debug.mic1.controlstore.MicroInstruction;
import com.github.croesch.micro_debug.mic1.controlstore.MicroInstructionReader;

/**
 * Provides test cases for {@link MainFrame}.
 * 
 * @author croesch
 * @since Date: Apr 8, 2012
 */
public class MainFrameTest extends DefaultGUITestCase {

  public static MainFrame getFrame(final Mic1 processor) {
    return GuiActionRunner.execute(new GuiQuery<MainFrame>() {

      @Override
      protected MainFrame executeInEDT() throws Throwable {
        return new MainFrame(processor);
      }
    });
  }

  @Test
  public void testFrame() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final FrameFixture frame = new FrameFixture(robot(), getFrame(proc));
    frame.show();
    assertThat(frame.component().isResizable()).isTrue();
    assertThat(frame.component().getTitle()).isEqualTo(GuiText.GUI_MAIN_TITLE.text(InternalSettings.NAME,
                                                                                   InternalSettings.VERSION));

    assertThat(frame.component().getHeight()).isEqualTo(IntegerSettings.MAIN_FRAME_HEIGHT.getValue());
    assertThat(frame.component().getWidth()).isEqualTo(IntegerSettings.MAIN_FRAME_WIDTH.getValue());

    assertThat(frame.splitPane("main-view").component().getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(frame.splitPane("main-view").component().getLeftComponent()).isInstanceOf(JSplitPane.class);
    assertThat(frame.splitPane("main-view").component().getLeftComponent().getName()).isEqualTo("register-mem");
    assertThat(frame.splitPane("main-view").component().getRightComponent()).isInstanceOf(JSplitPane.class);
    assertThat(frame.splitPane("main-view").component().getRightComponent().getName()).isEqualTo("code-tas");
    assertThat(frame.splitPane("main-view").component().getDividerLocation()).isEqualTo(IntegerSettings.MAIN_FRAME_SLIDER_REGISTERMEMORY_REST.getValue());

    assertThat(frame.splitPane("register-mem").component().getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(frame.splitPane("register-mem").component().getLeftComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane("register-mem").component().getLeftComponent().getName()).isEqualTo("register");
    assertThat(frame.splitPane("register-mem").component().getRightComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane("register-mem").component().getRightComponent().getName()).isEqualTo("memory");
    assertThat(frame.splitPane("register-mem").component().getDividerLocation()).isEqualTo(IntegerSettings.MAIN_FRAME_SLIDER_REGISTER_MEMORY.getValue());

    assertThat(frame.splitPane("code-tas").component().getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(frame.splitPane("code-tas").component().getLeftComponent()).isInstanceOf(JSplitPane.class);
    assertThat(frame.splitPane("code-tas").component().getLeftComponent().getName()).isEqualTo("code");
    assertThat(frame.splitPane("code-tas").component().getRightComponent()).isInstanceOf(JSplitPane.class);
    assertThat(frame.splitPane("code-tas").component().getRightComponent().getName()).isEqualTo("processorTas-debuggerTa");
    assertThat(frame.splitPane("code-tas").component().getDividerLocation()).isEqualTo(IntegerSettings.MAIN_FRAME_SLIDER_CODE_TEXTAREAS.getValue());

    assertThat(frame.splitPane("code").component().getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(frame.splitPane("code").component().getLeftComponent()).isInstanceOf(MacroCodeView.class);
    assertThat(frame.splitPane("code").component().getLeftComponent().getName()).isEqualTo("macroCode");
    assertThat(frame.splitPane("code").component().getRightComponent()).isInstanceOf(MicroCodeView.class);
    assertThat(frame.splitPane("code").component().getRightComponent().getName()).isEqualTo("microCode");
    assertThat(frame.splitPane("code").component().getDividerLocation()).isEqualTo(IntegerSettings.MAIN_FRAME_SLIDER_MACRO_MICRO.getValue());

    final String pane = "processorTas-debuggerTa";
    assertThat(frame.splitPane(pane).component().getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(frame.splitPane(pane).component().getLeftComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane(pane).component().getLeftComponent().getName()).isEqualTo("processorTAs");
    assertThat(frame.splitPane(pane).component().getRightComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane(pane).component().getRightComponent().getName()).isEqualTo("debuggerTA");
    assertThat(frame.splitPane(pane).component().getDividerLocation()).isEqualTo(IntegerSettings.MAIN_FRAME_SLIDER_PROCESSOR_DEBUGGER.getValue());
  }

  @Test
  public void testBreakpoints() throws IOException {
    printlnMethodName();
    // instruction writes all registers
    final MicroInstruction in = MicroInstructionReader.read(new ByteArrayInputStream(new byte[] { 0,
                                                                                                 0,
                                                                                                 (byte) 0xFF,
                                                                                                 (byte) 0xFF,
                                                                                                 0 }));

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final MainFrame mainFrame = getFrame(proc);
    final FrameFixture frame = new FrameFixture(robot(), mainFrame);
    frame.show();
    frame.checkBox("bpCB-PC").check();
    assertThat(mainFrame.getController().getBpm().isBreakpoint(0, 0, in, in)).isTrue();
    frame.checkBox("bpCB-PC").uncheck();
    assertThat(mainFrame.getController().getBpm().isBreakpoint(0, 0, in, in)).isFalse();
  }

  @Test
  public void testMicroCode() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final MainFrame mainFrame = getFrame(proc);
    final FrameFixture frame = new FrameFixture(robot(), mainFrame);
    frame.show();

    slowDownRobot();

    assertMicroHighlight(frame.panel("microCode"), 0);

    proc.microStep();
    assertMicroHighlight(frame.panel("microCode"), 2);
    proc.microStep();
    assertMicroHighlight(frame.panel("microCode"), 0);
    proc.microStep();
    assertMicroHighlight(frame.panel("microCode"), 2);
    proc.microStep();
    assertMicroHighlight(frame.panel("microCode"), 16);
    proc.microStep();
    assertMicroHighlight(frame.panel("microCode"), 22);
    proc.microStep();
    assertMicroHighlight(frame.panel("microCode"), 23);
    proc.microStep();
    assertMicroHighlight(frame.panel("microCode"), 2);
    proc.microStep();
    assertMicroHighlight(frame.panel("microCode"), 89);
  }

  @Test
  public void testMacroCode() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final MainFrame mainFrame = getFrame(proc);
    final FrameFixture frame = new FrameFixture(robot(), mainFrame);
    frame.show();

    assertMacroNoHighlight(frame.panel("macroCode"));
    proc.microStep();
    assertMacroNoHighlight(frame.panel("macroCode"));
    proc.microStep(2);
    assertMacroHighlight(frame.panel("macroCode"), 0);
    proc.microStep(2);
    assertMacroNoHighlight(frame.panel("macroCode"));
    proc.microStep(2);
    assertMacroHighlight(frame.panel("macroCode"), 2);
    proc.step();
    assertMacroHighlight(frame.panel("macroCode"), 3);
    proc.step();
    assertMacroHighlight(frame.panel("macroCode"), 5);
    proc.step();
    assertMacroHighlight(frame.panel("macroCode"), 7);
    proc.step();
    assertMacroHighlight(frame.panel("macroCode"), 9);
    proc.step();
    assertMacroHighlight(frame.panel("macroCode"), 10);
  }

  private void assertMacroNoHighlight(final JPanelFixture panel) {
    ACodeAreaTest.assertNoLineHighlighted(panel.textBox());
    LineNumberLabelTest.assertLabelHasNoHighlight(panel.label("macroCode-code-ta-line-numbers"),
                                                  panel.textBox().targetCastedTo(ACodeArea.class).getLineCount(),
                                                  panel.targetCastedTo(MacroCodeView.class).getLineNumberMapper());
  }

  private void assertMicroHighlight(final JPanelFixture panel, final int high) {
    ACodeAreaTest.assertLineHighlighted(panel.textBox(), high);
    LineNumberLabelTest.assertLabelHas(panel.label("microCode-code-ta-line-numbers"), 512, high,
                                       CodeControllerTest.MICRO_MAPPER);
  }

  private void assertMacroHighlight(final JPanelFixture panel, final int high) {
    ACodeAreaTest.assertLineHighlighted(panel.textBox(), panel.targetCastedTo(MacroCodeView.class)
                                                              .getLineNumberMapper()
                                                              .getNumberForLine(high));
    LineNumberLabelTest.assertLabelHas(panel.label("macroCode-code-ta-line-numbers"),
                                       panel.textBox().targetCastedTo(ACodeArea.class).getLineCount(), high,
                                       panel.targetCastedTo(MacroCodeView.class).getLineNumberMapper());
  }
}
