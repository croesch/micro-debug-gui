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
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.commons.Printer;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.error.MacroFileFormatException;
import com.github.croesch.micro_debug.error.MicroFileFormatException;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.basic.InputTextFieldTest;
import com.github.croesch.micro_debug.mic1.Mic1;
import com.github.croesch.micro_debug.mic1.io.Input;
import com.github.croesch.micro_debug.mic1.io.Output;

/**
 * Provides test cases for {@link MainView}.
 * 
 * @author croesch
 * @since Date: Apr 11, 2012
 */
public class MainViewTest extends DefaultGUITestCase {

  public static JFrame showViewInFrame(final String name, final Mic1 proc, final BreakpointManager bpm) {
    return GuiActionRunner.execute(new GuiQuery<JFrame>() {

      @Override
      protected JFrame executeInEDT() throws Throwable {
        final JFrame f = new JFrame();
        f.setLayout(new MigLayout("fill"));
        f.add(new MainView(name, proc, bpm).getViewComponent(), "grow");
        return f;
      }
    });
  }

  @Test(timeout = 10000)
  public void testView() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final FrameFixture frame = new FrameFixture(robot(), showViewInFrame("main-view", proc, new BreakpointManager()));
    frame.show(new Dimension(800, 500));
    assertThat(frame.splitPane("main-view").component().getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(frame.splitPane("main-view").component().getLeftComponent()).isInstanceOf(JSplitPane.class);
    assertThat(frame.splitPane("main-view").component().getLeftComponent().getName()).isEqualTo("register-mem");
    assertThat(frame.splitPane("main-view").component().getRightComponent()).isInstanceOf(JSplitPane.class);
    assertThat(frame.splitPane("main-view").component().getRightComponent().getName()).isEqualTo("code-tas");

    assertThat(frame.splitPane("register-mem").component().getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(frame.splitPane("register-mem").component().getLeftComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane("register-mem").component().getLeftComponent().getName()).isEqualTo("register");
    assertThat(frame.splitPane("register-mem").component().getRightComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane("register-mem").component().getRightComponent().getName()).isEqualTo("memory");

    assertThat(frame.splitPane("code-tas").component().getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(frame.splitPane("code-tas").component().getLeftComponent()).isInstanceOf(JTabbedPane.class);
    assertThat(frame.splitPane("code-tas").component().getLeftComponent().getName()).isEqualTo("code");
    assertThat(frame.splitPane("code-tas").component().getRightComponent()).isInstanceOf(JSplitPane.class);
    assertThat(frame.splitPane("code-tas").component().getRightComponent().getName()).isEqualTo("processorTas-debuggerTa");

    final String pane = "processorTas-debuggerTa";
    assertThat(frame.splitPane(pane).component().getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(frame.splitPane(pane).component().getLeftComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane(pane).component().getLeftComponent().getName()).isEqualTo("processorTAs");
    assertThat(frame.splitPane(pane).component().getRightComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane(pane).component().getRightComponent().getName()).isEqualTo("debuggerTA");

    frame.textBox("printer-ta").requireEmpty();
    Printer.println("Hello world!");
    frame.textBox("printer-ta").requireText("Hello world!" + getLineSeparator());
    Printer.println("Ciao world!");
    frame.textBox("printer-ta").requireText("Hello world!" + getLineSeparator() + "Ciao world!" + getLineSeparator());

    frame.textBox("output-ta").requireEmpty();
    frame.textBox("input-tf").requireEmpty();
    frame.textBox("input-tf").background().requireEqualTo(Color.white);
    InputTextFieldTest.getThreadTyping(robot(), "hi", 50, frame.textBox("input-tf"), true, getThrownInOtherThreads())
                      .start();

    Output.print(Input.read());
    frame.textBox("input-tf").background().requireEqualTo(Color.white);
    Output.print(Input.read());
    Output.print(Input.read());
    frame.textBox("output-ta").requireText("hi\n");
    frame.textBox("input-tf").requireEmpty();
  }
}
