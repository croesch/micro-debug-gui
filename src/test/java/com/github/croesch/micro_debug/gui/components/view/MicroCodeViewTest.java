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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.error.MacroFileFormatException;
import com.github.croesch.micro_debug.error.MicroFileFormatException;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.code.ACodeAreaTest;
import com.github.croesch.micro_debug.gui.components.code.LineNumberLabelTest;
import com.github.croesch.micro_debug.gui.debug.LineNumberMapper;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link MicroCodeView}.
 * 
 * @author croesch
 * @since Date: Apr 18, 2012
 */
public class MicroCodeViewTest extends DefaultGUITestCase {

  public static MicroCodeView getPanel(final String name, final Mic1 proc, final BreakpointManager bpm) {
    return GuiActionRunner.execute(new GuiQuery<MicroCodeView>() {
      @Override
      protected MicroCodeView executeInEDT() throws Throwable {
        return new MicroCodeView(name, proc, bpm);
      }
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullProcessor() {
    getPanel("..", null, new BreakpointManager());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullBpm() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    final String micFile = MicroCodeView.class.getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = MicroCodeView.class.getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));
    getPanel("..", proc, null);
  }

  @Test
  public void testPanel() throws IOException {
    final String micFile = MicroCodeView.class.getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = MicroCodeView.class.getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final MicroCodeView p = getPanel("micro", proc, new BreakpointManager());
    showInFrame(p);
    final JPanelFixture panel = new JPanelFixture(robot(), p);
    assertThat(panel.textBox().component().getText()).isEqualTo(readFile("mic1/add.ijvm.disp", false).toString());

    highlight(p, 17);
    ACodeAreaTest.assertLineHighlighted(panel.textBox(), 17);
    LineNumberLabelTest.assertLabelHas(panel.label("micro-code-ta-line-numbers"), 512, 17, new LineNumberMapper());
  }

  private void highlight(final MicroCodeView p, final int line) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        p.highlight(line);
      }
    });
  }
}
