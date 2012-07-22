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
package com.github.croesch.micro_debug.gui.items;

import static org.fest.assertions.Assertions.assertThat;

import java.io.FileNotFoundException;

import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.error.FileFormatException;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.about.AboutFrame;
import com.github.croesch.micro_debug.gui.components.help.HelpFrame;

/**
 * Provides test cases for item #38.
 * 
 * @author croesch
 * @since Date: Jul 10, 2012
 */
public class Item38Test extends DefaultGUITestCase {

  @Test
  public void testAboutFrameDisposed() throws FileFormatException, FileNotFoundException, InterruptedException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final FrameFixture frame = getMainFrame(micFile, macFile);

    frame.menuItem("about").click();
    final FrameFixture aboutFrame = WindowFinder.findFrame(AboutFrame.class).using(robot());

    // behavior when exiting via menu item
    frame.menuItem("exit").click();
    aboutFrame.requireNotVisible();
    assertThat(aboutFrame.component().isDisplayable()).isFalse();

    showAgain(frame, aboutFrame);

    // behavior when exiting via close
    frame.close();
    frame.requireNotVisible();
    aboutFrame.requireNotVisible();
    assertThat(aboutFrame.component().isDisplayable()).isFalse();

    showAgain(frame, aboutFrame);

    // behavior when closing about frame and then the normal frame
    aboutFrame.close();
    frame.requireVisible();
    aboutFrame.requireNotVisible();
    assertThat(aboutFrame.component().isDisplayable()).isTrue();

    frame.close();
    frame.requireNotVisible();
    aboutFrame.requireNotVisible();
    assertThat(aboutFrame.component().isDisplayable()).isFalse();
  }

  @Test
  public void testHelpFrameDisposed() throws FileFormatException, FileNotFoundException, InterruptedException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final FrameFixture frame = getMainFrame(micFile, macFile);

    frame.menuItem("help-item").click();
    final FrameFixture helpFrame = WindowFinder.findFrame(HelpFrame.class).using(robot());

    // behavior when exiting via menu item
    frame.menuItem("exit").click();
    helpFrame.requireNotVisible();
    assertThat(helpFrame.component().isDisplayable()).isFalse();

    showAgain(frame, helpFrame);

    // behavior when exiting via close
    frame.close();
    frame.requireNotVisible();
    helpFrame.requireNotVisible();
    assertThat(helpFrame.component().isDisplayable()).isFalse();

    showAgain(frame, helpFrame);

    // behavior when closing about frame and then the normal frame
    helpFrame.close();
    frame.requireVisible();
    helpFrame.requireNotVisible();
    assertThat(helpFrame.component().isDisplayable()).isTrue();

    frame.close();
    frame.requireNotVisible();
    helpFrame.requireNotVisible();
    assertThat(helpFrame.component().isDisplayable()).isFalse();
  }

  private void showAgain(final FrameFixture frame, final FrameFixture dependendFrame) {
    frame.show();
    dependendFrame.show();
    frame.requireVisible();
    dependendFrame.requireVisible();
    assertThat(dependendFrame.component().isDisplayable()).isTrue();
  }
}
