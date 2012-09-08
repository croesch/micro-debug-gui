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

import static com.github.croesch.micro_debug.gui.components.start.StartFrameTest.MACRO_BROWSE_BT_NAME;
import static com.github.croesch.micro_debug.gui.components.start.StartFrameTest.MACRO_PATH_TF_NAME;
import static com.github.croesch.micro_debug.gui.components.start.StartFrameTest.MICRO_PATH_TF_NAME;
import static com.github.croesch.micro_debug.gui.components.start.StartFrameTest.OKAY_BT_NAME;
import static com.github.croesch.micro_debug.gui.components.start.StartFrameTest.assertEnabledAndEmpty;
import static com.github.croesch.micro_debug.gui.components.start.StartFrameTest.assertNotEditableAndContainsText;
import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JFileChooserFixture;
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

  private final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();

  private final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();

  @Test
  public void testCreate() throws FileFormatException, FileNotFoundException {
    printlnMethodName();

    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        new Mic1Starter().create(Mic1StarterTest.this.micFile, Mic1StarterTest.this.macFile);
      }
    });

    final FrameFixture frame = WindowFinder.findFrame(MainFrame.class).using(robot());
    frame.requireVisible();
    final Mic1 expected = new Mic1(new FileInputStream(this.micFile), new FileInputStream(this.macFile));
    assertThat(frame.targetCastedTo(MainFrame.class).getProcessor()).isEqualTo(expected);
    frame.close();
  }

  @Test
  public void testStart() throws FileFormatException, FileNotFoundException {
    printlnMethodName();

    new Mic1Starter().start();

    FrameFixture frame = WindowFinder.findFrame(StartFrame.class).using(robot());
    frame.requireVisible();
    frame.button(OKAY_BT_NAME).requireDisabled();

    frame.textBox(MICRO_PATH_TF_NAME).enterText("abcdef");
    frame.button(OKAY_BT_NAME).requireDisabled();
    frame.button(MACRO_BROWSE_BT_NAME).click();
    final JFileChooserFixture fileChooser = new JFileChooserFixture(robot());
    fileChooser.setCurrentDirectory(new File(getUserHome()));
    fileChooser.selectFile(new File("some.ijvm"));
    fileChooser.approve();

    frame.button(OKAY_BT_NAME).click();

    frame = WindowFinder.findFrame(StartFrame.class).using(robot());
    frame.requireVisible();
    frame.button(OKAY_BT_NAME).requireDisabled();

    frame.textBox(MICRO_PATH_TF_NAME).requireEmpty();
    frame.textBox(MACRO_PATH_TF_NAME).requireText(getUserHome() + getFileSeparator() + "some.ijvm");
    frame.textBox(MACRO_PATH_TF_NAME).requireDisabled();
    frame.textBox(MACRO_PATH_TF_NAME).requireNotEditable();

    enterText(frame.textBox(MICRO_PATH_TF_NAME), this.micFile);

    frame.button(OKAY_BT_NAME).click();

    frame = WindowFinder.findFrame(StartFrame.class).using(robot());
    frame.requireVisible();
    frame.button(OKAY_BT_NAME).requireDisabled();

    frame.textBox(MICRO_PATH_TF_NAME).requireText(this.micFile);
    frame.textBox(MICRO_PATH_TF_NAME).requireDisabled();
    frame.textBox(MICRO_PATH_TF_NAME).requireNotEditable();
    frame.textBox(MACRO_PATH_TF_NAME).requireEmpty();

    enterText(frame.textBox(MACRO_PATH_TF_NAME), this.macFile);

    frame.button(OKAY_BT_NAME).click();

    frame = WindowFinder.findFrame(MainFrame.class).using(robot());
    frame.requireVisible();
    final Mic1 expected = new Mic1(new FileInputStream(this.micFile), new FileInputStream(this.macFile));
    assertThat(frame.targetCastedTo(MainFrame.class).getProcessor()).isEqualTo(expected);
    frame.close();
  }

  @Test
  public void testStartWithPaths() throws FileFormatException, FileNotFoundException {
    printlnMethodName();

    final Mic1Starter starter = new Mic1Starter();
    starter.setMicroFilePath(this.micFile);
    starter.setMacroFilePath(this.macFile);
    assertThat(starter.getMicroFilePath()).isEqualTo(this.micFile);
    assertThat(starter.getMacroFilePath()).isEqualTo(this.macFile);

    starter.start();

    final FrameFixture frame = WindowFinder.findFrame(MainFrame.class).using(robot());
    frame.requireVisible();
    final Mic1 expected = new Mic1(new FileInputStream(this.micFile), new FileInputStream(this.macFile));
    assertThat(frame.targetCastedTo(MainFrame.class).getProcessor()).isEqualTo(expected);
    frame.close();
  }

  @Test
  public void testStartWithoutPath() {
    printlnMethodName();

    final Mic1Starter starter = new Mic1Starter();
    starter.setMacroFilePath(null);
    assertThat(starter.getMicroFilePath()).isEmpty();
    assertThat(starter.getMacroFilePath()).isEmpty();
    starter.start();
    final FrameFixture startFrame = WindowFinder.findFrame(StartFrame.class).using(robot());

    assertEnabledAndEmpty(startFrame.textBox(MICRO_PATH_TF_NAME));
    assertEnabledAndEmpty(startFrame.textBox(MACRO_PATH_TF_NAME));
    startFrame.button(OKAY_BT_NAME).requireDisabled();
  }

  @Test
  public void testStartWithMicroPath() {
    printlnMethodName();

    final Mic1Starter starter = new Mic1Starter();
    starter.setMacroFilePath(null);
    starter.setMicroFilePath(this.micFile);
    assertThat(starter.getMicroFilePath()).isEqualTo(this.micFile);
    assertThat(starter.getMacroFilePath()).isEmpty();
    starter.start();
    final FrameFixture startFrame = WindowFinder.findFrame(StartFrame.class).using(robot());

    assertNotEditableAndContainsText(startFrame.textBox(MICRO_PATH_TF_NAME), this.micFile, robot());
    assertEnabledAndEmpty(startFrame.textBox(MACRO_PATH_TF_NAME));
    startFrame.button(OKAY_BT_NAME).requireDisabled();
  }

  @Test
  public void testStartWithMacroPath() {
    printlnMethodName();

    final Mic1Starter starter = new Mic1Starter();
    starter.setMicroFilePath(null);
    starter.setMacroFilePath(this.macFile);
    assertThat(starter.getMicroFilePath()).isEmpty();
    assertThat(starter.getMacroFilePath()).isEqualTo(this.macFile);
    starter.start();
    final FrameFixture startFrame = WindowFinder.findFrame(StartFrame.class).using(robot());

    assertEnabledAndEmpty(startFrame.textBox(MICRO_PATH_TF_NAME));
    assertNotEditableAndContainsText(startFrame.textBox(MACRO_PATH_TF_NAME), this.macFile, robot());
    startFrame.button(OKAY_BT_NAME).requireDisabled();
  }
}
