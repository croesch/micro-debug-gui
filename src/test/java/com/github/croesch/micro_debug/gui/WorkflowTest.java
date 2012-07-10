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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JFileChooserFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.error.FileFormatException;
import com.github.croesch.micro_debug.gui.components.MainFrame;
import com.github.croesch.micro_debug.gui.components.start.Mic1Starter;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides some integration tests, real possible workflows.
 * 
 * @author croesch
 * @since Date: Mar 11, 2012
 */
public class WorkflowTest extends DefaultGUITestCase {

  @Test
  public void testWorkflow() throws FileFormatException, FileNotFoundException, InterruptedException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();

    new Mic1Starter().start();

    FrameFixture frame = WindowFinder.findFrame("start-frame").using(robot());
    frame.requireVisible();
    frame.button("okay").requireDisabled();

    enterText(frame.textBox("micro-assembler-file-path"), "this will hopefully replaced :-)");
    frame.button("micro-assembler-file-browse").click();
    final JFileChooserFixture fileChooser = new JFileChooserFixture(robot());
    fileChooser.selectFile(new File(micFile));
    fileChooser.approve();
    frame.textBox("micro-assembler-file-path").requireText(micFile);
    enterText(frame.textBox("macro-assembler-file-path"), macFile);
    frame.textBox("macro-assembler-file-path").requireText(macFile);

    frame.button("okay").click();

    frame = WindowFinder.findFrame(MainFrame.class).using(robot());
    frame.requireVisible();
    final Mic1 expected = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));
    assertThat(frame.targetCastedTo(MainFrame.class).getProcessor()).isEqualTo(expected);
    frame.close();
  }
}
