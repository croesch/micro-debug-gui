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
package com.github.croesch.micro_debug.gui.components.start;

import static org.fest.assertions.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.error.FileFormatException;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.MainFrame;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link Mic1Starter}.
 * 
 * @author croesch
 * @since Date: Mar 10, 2012
 */
public class Mic1StarterTest extends DefaultGUITestCase {

  @Test
  public void testCreate() throws FileFormatException, FileNotFoundException {
    printlnMethodName();
    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();

    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        new Mic1Starter().create(micFile, macFile);
      }
    });

    final FrameFixture frame = WindowFinder.findFrame(MainFrame.class).using(robot());
    frame.requireVisible();
    final Mic1 expected = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));
    assertThat(frame.targetCastedTo(MainFrame.class).getProcessor()).isEqualTo(expected);
    frame.close();
  }
}
