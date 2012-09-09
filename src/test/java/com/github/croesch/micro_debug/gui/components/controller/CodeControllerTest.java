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

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.console.Mic1Interpreter;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.error.MacroFileFormatException;
import com.github.croesch.micro_debug.error.MicroFileFormatException;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.code.ACodeArea;
import com.github.croesch.micro_debug.gui.components.code.ACodeAreaTest;
import com.github.croesch.micro_debug.gui.components.code.LineNumberLabelTest;
import com.github.croesch.micro_debug.gui.components.view.ACodeView;
import com.github.croesch.micro_debug.gui.components.view.MacroCodeViewTest;
import com.github.croesch.micro_debug.gui.components.view.MicroCodeViewTest;
import com.github.croesch.micro_debug.gui.debug.LineNumberMapper;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link CodeController}.
 * 
 * @author croesch
 * @since Date: May 11, 2012
 */
public class CodeControllerTest extends DefaultGUITestCase {

  public static final LineNumberMapper MICRO_MAPPER = new LineNumberMapper();

  @Test(expected = IllegalArgumentException.class)
  public void testCstr_NullView() {
    new CodeController(null);
  }

  @Test
  public void testUpdateMicro() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));
    new Mic1Interpreter(proc);

    final ACodeView v = MicroCodeViewTest.getPanel("micro", proc, new BreakpointManager());
    final CodeController controller = new CodeController(v);
    showInFrame(v);
    final JPanelFixture panel = new JPanelFixture(robot(), v);

    assertMicroHighlight(controller, panel, 0, 0);

    proc.microStep();
    assertMicroHighlight(controller, panel, 0, 2);
    proc.microStep();
    assertMicroHighlight(controller, panel, 2, 0);
    proc.microStep();
    assertMicroHighlight(controller, panel, 0, 2);
    proc.microStep();
    assertMicroHighlight(controller, panel, 2, 16);
    proc.microStep();
    assertMicroHighlight(controller, panel, 16, 22);
    proc.microStep();
    assertMicroHighlight(controller, panel, 22, 23);
    proc.microStep();
    assertMicroHighlight(controller, panel, 23, 2);
    proc.microStep();
    assertMicroHighlight(controller, panel, 2, 89);
  }

  @Test
  public void testUpdateMacro() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));
    new Mic1Interpreter(proc);

    final ACodeView v = MacroCodeViewTest.getPanel("macro", proc, new BreakpointManager());
    final CodeController controller = new CodeController(v);
    showInFrame(v);
    final JPanelFixture panel = new JPanelFixture(robot(), v);

    assertMacroNoHighlight(controller, panel);
    proc.microStep();

    assertMacroNoHighlight(controller, panel);

    proc.microStep(2);

    assertMacroNoHighlight(controller, panel);
    update(controller);
    assertMacroHighlight(controller, panel, 0);

    proc.microStep(2);

    assertMacroHighlight(controller, panel, 0);
    update(controller);
    assertMacroNoHighlight(controller, panel);

    proc.microStep(2);

    assertMacroNoHighlight(controller, panel);
    update(controller);
    assertMacroHighlight(controller, panel, 2);

    proc.step();

    assertMacroHighlight(controller, panel, 2, 3);
    proc.step();
    assertMacroHighlight(controller, panel, 3, 5);
    proc.step();
    assertMacroHighlight(controller, panel, 5, 7);
    proc.step();
    assertMacroHighlight(controller, panel, 7, 9);
    proc.step();
    assertMacroHighlight(controller, panel, 9, 10);
  }

  private void assertMacroNoHighlight(final CodeController controller, final JPanelFixture panel) {
    ACodeAreaTest.assertNoLineHighlighted(panel.textBox("macro-code-ta"));
    LineNumberLabelTest.assertLabelHasNoHighlight(panel.label("macro-code-ta-line-numbers"),
                                                  panel.textBox("macro-code-ta")
                                                       .targetCastedTo(ACodeArea.class)
                                                       .getLineCount(), controller.getView().getLineNumberMapper());
  }

  private void assertMicroHighlight(final CodeController controller,
                                    final JPanelFixture panel,
                                    final int oldH,
                                    final int newH) {
    ACodeAreaTest.assertLineHighlighted(panel.textBox("micro-code-ta"), oldH);
    LineNumberLabelTest.assertLabelHas(panel.label("micro-code-ta-line-numbers"), 512, oldH, MICRO_MAPPER);
    update(controller);
    ACodeAreaTest.assertLineHighlighted(panel.textBox("micro-code-ta"), newH);
    LineNumberLabelTest.assertLabelHas(panel.label("micro-code-ta-line-numbers"), 512, newH, MICRO_MAPPER);
  }

  private void assertMacroHighlight(final CodeController controller,
                                    final JPanelFixture panel,
                                    final int oldH,
                                    final int newH) {
    assertMacroHighlight(controller, panel, oldH);
    update(controller);
    assertMacroHighlight(controller, panel, newH);
  }

  private void assertMacroHighlight(final CodeController controller, final JPanelFixture panel, final int oldH) {
    ACodeAreaTest.assertLineHighlighted(panel.textBox("macro-code-ta"), controller.getView()
                                                                                  .getLineNumberMapper()
                                                                                  .getNumberForLine(oldH));
    LineNumberLabelTest.assertLabelHas(panel.label("macro-code-ta-line-numbers"), panel.textBox("macro-code-ta")
                                                                                       .targetCastedTo(ACodeArea.class)
                                                                                       .getLineCount(), oldH,
                                       controller.getView().getLineNumberMapper());
  }

  private void update(final CodeController controller) {
    GuiActionRunner.execute(new GuiTask() {

      @Override
      protected void executeInEDT() throws Throwable {
        controller.performViewUpdate();
      }
    });
  }
}
