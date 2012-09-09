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

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.After;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.basic.InputTextFieldTest;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.mic1.io.Input;
import com.github.croesch.micro_debug.mic1.io.Output;

/**
 * Provides test cases for {@link ProcessorTAView}.
 * 
 * @author croesch
 * @since Date: Apr 17, 2012
 */
public class ProcessorTAViewTest extends DefaultGUITestCase {

  public static ProcessorTAView getPanel(final String name) {
    return GuiActionRunner.execute(new GuiQuery<ProcessorTAView>() {
      @Override
      protected ProcessorTAView executeInEDT() throws Throwable {
        return new ProcessorTAView(name);
      }
    });
  }

  @After
  public void afterTest() {
    robot().releaseKey(KeyEvent.VK_ENTER);
  }

  @Test
  public void testPanel() {
    printlnMethodName();

    final ProcessorTAView p = getPanel("processor");
    showInFrame(p);
    final JPanelFixture panel = new JPanelFixture(robot(), p);
    assertThat(panel.component().getName()).isEqualTo("processor");
    panel.scrollPane("output-scrollpane");
    panel.textBox("output-ta").requireEmpty();
    panel.textBox("input-tf").requireEmpty();
    panel.button("input-tf-button").requireEnabled();
    panel.button("input-tf-button").requireText(GuiText.GUI_ACTIONS_INPUT_OKAY.text());

    panel.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "a", 200, panel.textBox("input-tf"), true, getThrownInOtherThreads())
                      .start();
    panel.textBox("output-ta").requireEmpty();

    Output.print(Input.read());
    Output.print(Input.read());
    panel.textBox("output-ta").requireText("a\n");
    panel.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "b", 50, panel.textBox("input-tf"), true, getThrownInOtherThreads())
                      .start();

    Output.print(Input.read());
    Output.print(Input.read());
    panel.textBox("output-ta").requireText("a\nb\n");
    panel.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "c", 50, panel.textBox("input-tf"), true, getThrownInOtherThreads())
                      .start();

    Output.print(Input.read());
    Output.print(Input.read());
    panel.textBox("output-ta").requireText("a\nb\nc\n");
    panel.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "d", 50, panel.textBox("input-tf"), true, getThrownInOtherThreads(),
                                       panel.button("input-tf-button")).start();

    Output.print(Input.read());
    Output.print(Input.read());
    panel.textBox("output-ta").requireText("a\nb\nc\nd\n");
    panel.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "e", 50, panel.textBox("input-tf"), true, getThrownInOtherThreads())
                      .start();

    Output.print(Input.read());
    Output.print(Input.read());
    panel.textBox("output-ta").requireText("a\nb\nc\nd\ne\n");
    panel.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "f", 50, panel.textBox("input-tf"), true, getThrownInOtherThreads())
                      .start();

    Output.print(Input.read());
    Output.print(Input.read());
    panel.textBox("output-ta").requireText("a\nb\nc\nd\ne\nf\n");
    panel.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "g", 50, panel.textBox("input-tf"), true, getThrownInOtherThreads(),
                                       panel.button("input-tf-button")).start();

    Output.print(Input.read());
    Output.print(Input.read());
    panel.textBox("output-ta").requireText("a\nb\nc\nd\ne\nf\ng\n");
    panel.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "h", 50, panel.textBox("input-tf"), true, getThrownInOtherThreads())
                      .start();

    Output.print(Input.read());
    Output.print(Input.read());
    panel.textBox("output-ta").requireText("a\nb\nc\nd\ne\nf\ng\nh\n");
    panel.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "i", 50, panel.textBox("input-tf"), true, getThrownInOtherThreads())
                      .start();

    Output.print(Input.read());
    Output.print(Input.read());
    panel.textBox("output-ta").requireText("a\nb\nc\nd\ne\nf\ng\nh\ni\n");
    panel.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "j", 50, panel.textBox("input-tf"), true, getThrownInOtherThreads(),
                                       panel.button("input-tf-button")).start();

    Output.print(Input.read());
    Output.print(Input.read());
    panel.textBox("output-ta").requireText("a\nb\nc\nd\ne\nf\ng\nh\ni\nj\n");
    panel.textBox("input-tf").requireEmpty();
  }
}
