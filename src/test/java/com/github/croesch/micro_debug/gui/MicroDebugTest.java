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
package com.github.croesch.micro_debug.gui;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.components.MainFrame;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;
import com.github.croesch.micro_debug.i18n.Text;

/**
 * Provides test cases for {@link MicroDebug}.
 * 
 * @author croesch
 * @since Date: Feb 25, 2012
 */
public class MicroDebugTest extends DefaultGUITestCase {

  private final String GREETING = GuiText.GREETING.text(InternalSettings.NAME) + getLineSeparator() + GuiText.BORDER
                                  + getLineSeparator();

  @Test
  public void testVersion() {
    printlnMethodName();
    final String versionInformation = Text.VERSION.text(com.github.croesch.micro_debug.settings.InternalSettings.VERSION)
                                      + getLineSeparator()
                                      + GuiText.VERSION.text(InternalSettings.NAME, InternalSettings.VERSION)
                                      + getLineSeparator();

    MicroDebug.main(new String[] { "-v" });
    assertThat(out.toString()).isEqualTo(this.GREETING + versionInformation);
    out.reset();

    MicroDebug.main(new String[] { "--version" });
    assertThat(out.toString()).isEqualTo(this.GREETING + versionInformation);
  }

  @Test
  public void testHelp() throws IOException {
    printlnMethodName();
    MicroDebug.main(new String[] { "-h" });
    assertThat(out.toString()).isEqualTo(this.GREETING + getHelpFileText());
    out.reset();

    MicroDebug.main(new String[] { "--help" });
    assertThat(out.toString()).isEqualTo(this.GREETING + getHelpFileText());
  }

  @Test
  public void testStart() {
    printlnMethodName();

    MicroDebug.main(new String[] {});
    FrameFixture frame = WindowFinder.findFrame("start-frame").using(robot());
    frame.requireVisible();
    frame.close();

    MicroDebug.main(new String[] {});
    frame = WindowFinder.findFrame("start-frame").using(robot());
    frame.requireVisible();
    frame.close();
  }

  @Test
  public void testStartWithFiles() {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();

    MicroDebug.main(new String[] { "-f", micFile, macFile });
    final FrameFixture frame = WindowFinder.findFrame(MainFrame.class).using(robot());
    frame.requireVisible();
    frame.close();
  }

  @Test
  public void testStartWithWrongFiles() {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();

    MicroDebug.main(new String[] { "--files", macFile, micFile });
    final FrameFixture frame = WindowFinder.findFrame("start-frame").using(robot());
    frame.requireVisible();
    frame.close();
  }

  @Test(expected = WaitTimedOutError.class)
  public void testVersionDoesntStart() {
    MicroDebug.main(new String[] { "-v" });
    WindowFinder.findFrame("start-frame").withTimeout(1000).using(robot());
  }

  @Test(expected = WaitTimedOutError.class)
  public void testHelpDoesntStart() {
    MicroDebug.main(new String[] { "-h" });
    WindowFinder.findFrame("start-frame").withTimeout(1000).using(robot());
  }

  @Test(expected = WaitTimedOutError.class)
  public void testUnknownDoesntStart() {
    MicroDebug.main(new String[] { "-u" });
    WindowFinder.findFrame("start-frame").withTimeout(1000).using(robot());
  }
}
