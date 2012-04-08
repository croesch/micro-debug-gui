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

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.mic1.Mic1;

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
  public void testFrame() {
    printlnMethodName();

    final FrameFixture frame = new FrameFixture(robot(), getFrame(null));
    frame.show();
    assertThat(frame.splitPane("register-rest").component().getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(frame.splitPane("register-rest").component().getLeftComponent()).isInstanceOf(JSplitPane.class);
    assertThat(frame.splitPane("register-rest").component().getLeftComponent().getName()).isEqualTo("register-mem");
    assertThat(frame.splitPane("register-rest").component().getRightComponent()).isInstanceOf(JSplitPane.class);
    assertThat(frame.splitPane("register-rest").component().getRightComponent().getName()).isEqualTo("code-tas");

    assertThat(frame.splitPane("register-mem").component().getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(frame.splitPane("register-mem").component().getLeftComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane("register-mem").component().getLeftComponent().getName()).isEqualTo("register");
    assertThat(frame.splitPane("register-mem").component().getRightComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane("register-mem").component().getRightComponent().getName()).isEqualTo("memory");

    assertThat(frame.splitPane("code-tas").component().getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    // TODO change to tabbed pane
    //    assertThat(frame.splitPane("code-tas").component().getLeftComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane("code-tas").component().getLeftComponent().getName()).isEqualTo("code");
    assertThat(frame.splitPane("code-tas").component().getRightComponent()).isInstanceOf(JSplitPane.class);
    assertThat(frame.splitPane("code-tas").component().getRightComponent().getName())
      .isEqualTo("processorTas-debuggerTa");

    final String pane = "processorTas-debuggerTa";
    assertThat(frame.splitPane(pane).component().getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(frame.splitPane(pane).component().getLeftComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane(pane).component().getLeftComponent().getName()).isEqualTo("processorTAs");
    assertThat(frame.splitPane(pane).component().getRightComponent()).isInstanceOf(JScrollPane.class);
    assertThat(frame.splitPane(pane).component().getRightComponent().getName()).isEqualTo("debuggerTA");
  }
}
