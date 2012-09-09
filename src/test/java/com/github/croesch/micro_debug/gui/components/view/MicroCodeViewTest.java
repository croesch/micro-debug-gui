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

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.error.MacroFileFormatException;
import com.github.croesch.micro_debug.error.MicroFileFormatException;
import com.github.croesch.micro_debug.gui.components.code.ACodeAreaTest;
import com.github.croesch.micro_debug.gui.components.code.LineNumberLabelTest;
import com.github.croesch.micro_debug.gui.debug.LineNumberMapper;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link MicroCodeView}.
 * 
 * @author croesch
 * @since Date: Apr 18, 2012
 */
public class MicroCodeViewTest extends ACodeViewTest {

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
  public void testSetStepAction() throws IOException {
    final String micFile = MicroCodeView.class.getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = MicroCodeView.class.getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final MicroCodeView p = getPanel("micro", proc, new BreakpointManager());
    showInFrame(p);
    final JPanelFixture panel = new JPanelFixture(robot(), p);

    final AbstractAction action = new AbstractAction("ACTION_TXT") {
      private static final long serialVersionUID = 1L;

      public void actionPerformed(final ActionEvent e) {}
    };

    setAction(p, action);
    panel.button("stepButton").requireEnabled();
    panel.button("stepButton").requireText("ACTION_TXT");

    setActionEnabled(action, false);
    panel.button("stepButton").requireDisabled();
    panel.button("stepButton").requireText("ACTION_TXT");
  }

  @Test
  public void testPanel() throws IOException {
    final String micFile = MicroCodeView.class.getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = MicroCodeView.class.getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final MicroCodeView p = getPanel("micro", proc, new BreakpointManager());
    showInFrame(p);
    final JPanelFixture panel = new JPanelFixture(robot(), p);
    panel.button("stepButton").requireEnabled();
    assertThat(panel.textBox("micro-code-ta").component().getSelectionStart()).isZero();
    assertThat(panel.textBox("micro-code-ta").component().getSelectionEnd()).isZero();
    assertThat(panel.textBox("micro-code-ta").component().getText()).isEqualTo(readFile("mic1/mic1ijvm.mic1.disp",
                                                                                        false).toString());

    panel.textBox("stepField").requireText("1");
    panel.textBox("stepField").requireToolTip(GuiText.GUI_TIP_STEP_COUNT.text());
    assertThat(p.getStepField()).isSameAs(panel.textBox("stepField").component());

    highlight(p, 17);
    ACodeAreaTest.assertLineHighlighted(panel.textBox("micro-code-ta"), 17);
    LineNumberLabelTest.assertLabelHas(panel.label("micro-code-ta-line-numbers"), 512, 17, new LineNumberMapper());
  }
}
