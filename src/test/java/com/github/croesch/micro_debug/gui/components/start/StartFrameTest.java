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

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

import org.fest.swing.core.MouseButton;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;

/**
 * Provides test cases for {@link StartFrame}.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
public class StartFrameTest extends DefaultGUITestCase {

  private FrameFixture startFrame;

  @Override
  protected void setUpTestCase() throws IOException, ClassNotFoundException, InstantiationException,
                                IllegalAccessException, UnsupportedLookAndFeelException {
    final StartFrame frame = GuiActionRunner.execute(new GuiQuery<StartFrame>() {
      @Override
      protected StartFrame executeInEDT() {
        return new StartFrame();
      }
    });
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.startFrame = new FrameFixture(robot(), frame);
    this.startFrame.show();
  }

  private void createStartFrame(final String micAsmPath, final String asmPath) {
    this.startFrame.close();
    final StartFrame frame = GuiActionRunner.execute(new GuiQuery<StartFrame>() {
      @Override
      protected StartFrame executeInEDT() {
        return new StartFrame(micAsmPath, asmPath);
      }
    });
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.startFrame = new FrameFixture(robot(), frame);
    this.startFrame.show();
  }

  @Override
  protected void onTearDown() {
    this.startFrame.close();
  }

  @Test
  public void testFrame() {
    printlnMethodName();
    this.startFrame.requireSize(new Dimension(485, 300));
    assertThat(this.startFrame.component().isResizable()).isFalse();
    assertThat(this.startFrame.component().getTitle()).isEqualTo(GuiText.GUI_START_TITLE.text(InternalSettings.NAME));
  }

  @Test
  public void testLabels() {
    printlnMethodName();
    this.startFrame.label("micro-assembler-file").requireText(GuiText.GUI_START_MICRO.text());
    this.startFrame.label("macro-assembler-file").requireText(GuiText.GUI_START_MACRO.text());
  }

  @Test
  public void testTextFields() {
    printlnMethodName();
    assertEnabledAndEmpty(this.startFrame.textBox("micro-assembler-file-path"));
    assertEnabledAndEmpty(this.startFrame.textBox("macro-assembler-file-path"));
  }

  private void assertEnabledAndEmpty(final JTextComponentFixture textBox) {
    textBox.requireEmpty();
    textBox.requireEditable();
    textBox.requireEnabled();
  }

  @Test
  public void testButtons() {
    printlnMethodName();
    this.startFrame.button("micro-assembler-file-browse").requireText(GuiText.GUI_COMMAND_BROWSE.text());
    this.startFrame.button("macro-assembler-file-browse").requireText(GuiText.GUI_COMMAND_BROWSE.text());
    this.startFrame.button("okay").requireText(GuiText.GUI_START_OKAY.text());
  }

  @Test
  public void testStartFrameStringString() {
    printlnMethodName();
    createStartFrame(null, null);
    assertEnabledAndEmpty(this.startFrame.textBox("micro-assembler-file-path"));
    assertEnabledAndEmpty(this.startFrame.textBox("macro-assembler-file-path"));

    createStartFrame(" \t\n   \t  ", "");
    assertEnabledAndEmpty(this.startFrame.textBox("micro-assembler-file-path"));
    assertEnabledAndEmpty(this.startFrame.textBox("macro-assembler-file-path"));

    createStartFrame(" path to nirvana ", "");
    assertNotEditableAndContainsText(this.startFrame.textBox("micro-assembler-file-path"), "path to nirvana");
    assertEnabledAndEmpty(this.startFrame.textBox("macro-assembler-file-path"));

    createStartFrame(null, " path to nirvana ");
    assertEnabledAndEmpty(this.startFrame.textBox("micro-assembler-file-path"));
    assertNotEditableAndContainsText(this.startFrame.textBox("macro-assembler-file-path"), "path to nirvana");

    createStartFrame(" path to nirvana ", "something");
    assertNotEditableAndContainsText(this.startFrame.textBox("micro-assembler-file-path"), "path to nirvana");
    assertNotEditableAndContainsText(this.startFrame.textBox("macro-assembler-file-path"), "something");
  }

  private void assertNotEditableAndContainsText(final JTextComponentFixture textBox, final String text) {
    textBox.requireText(text);
    textBox.requireDisabled();
    textBox.requireNotEditable();

    robot().click(textBox.component());
    textBox.requireDisabled();
    textBox.requireNotEditable();

    robot().click(textBox.component(), MouseButton.RIGHT_BUTTON, 3);
    textBox.requireDisabled();
    textBox.requireNotEditable();

    robot().click(textBox.component(), MouseButton.RIGHT_BUTTON, 2);
    textBox.requireDisabled();
    textBox.requireNotEditable();

    robot().click(textBox.component(), MouseButton.LEFT_BUTTON, 2);
    textBox.requireEnabled();
    textBox.requireEditable();

    robot().click(textBox.component(), MouseButton.LEFT_BUTTON, 2);
    textBox.requireEnabled();
    textBox.requireEditable();
  }
}
