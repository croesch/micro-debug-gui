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
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

import org.fest.swing.core.MouseButton;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JFileChooserFixture;
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

  private final TestMic1Creator mic1Creator = new TestMic1Creator();

  @Override
  protected void setUpTestCase() throws IOException, ClassNotFoundException, InstantiationException,
                                IllegalAccessException, UnsupportedLookAndFeelException {
    cleanUpObjects();
    final StartFrame frame = GuiActionRunner.execute(new GuiQuery<StartFrame>() {
      @Override
      protected StartFrame executeInEDT() {
        return new StartFrame(StartFrameTest.this.mic1Creator);
      }
    });
    setStartFrameAndShow(frame);
  }

  private void setStartFrameAndShow(final StartFrame frame) {
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.startFrame = new FrameFixture(robot(), frame);
    this.startFrame.show();
    this.startFrame.requireVisible();
    assertThat(this.mic1Creator.isWritten()).isFalse();
  }

  private void createStartFrame(final String micAsmPath, final String asmPath) {
    cleanUpObjects();
    final StartFrame frame = GuiActionRunner.execute(new GuiQuery<StartFrame>() {
      @Override
      protected StartFrame executeInEDT() {
        return new StartFrame(micAsmPath, asmPath, StartFrameTest.this.mic1Creator);
      }
    });
    setStartFrameAndShow(frame);
  }

  private void cleanUpObjects() {
    this.mic1Creator.reset();
    closeStartFrame();
  }

  @Override
  protected void onTearDown() {
    closeStartFrame();
  }

  private void closeStartFrame() {
    if (this.startFrame != null && this.startFrame.component().isShowing() && this.startFrame.component().isVisible()) {
      this.startFrame.close();
    }
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

    robot().doubleClick(textBox.component());
    textBox.requireEnabled();
    textBox.requireEditable();

    robot().doubleClick(textBox.component());
    textBox.requireEnabled();
    textBox.requireEditable();
  }

  @Test
  public void testOkayButton() throws Exception {
    printlnMethodName();
    assertThat(this.mic1Creator.isWritten()).isFalse();
    this.startFrame.button("okay").requireEnabled();
    this.startFrame.button("okay").click();
    assertThat(this.mic1Creator.getMicroPath()).isEmpty();
    assertThat(this.mic1Creator.getMacroPath()).isEmpty();
    assertThat(this.mic1Creator.isWritten()).isTrue();
    this.startFrame.requireNotVisible();

    setUpTestCase();

    this.startFrame.textBox("micro-assembler-file-path").enterText("/macro/path.txt");
    this.startFrame.textBox("micro-assembler-file-path").deleteText();
    this.startFrame.textBox("micro-assembler-file-path").enterText("/micro/path.txt");
    this.startFrame.textBox("macro-assembler-file-path").enterText("/macro/path.txt");
    assertThat(this.mic1Creator.isWritten()).isFalse();
    this.startFrame.button("okay").requireEnabled();
    this.startFrame.button("okay").click();
    assertThat(this.mic1Creator.getMicroPath()).isEqualTo("/micro/path.txt");
    assertThat(this.mic1Creator.getMacroPath()).isEqualTo("/macro/path.txt");
    assertThat(this.mic1Creator.isWritten()).isTrue();
    this.startFrame.requireNotVisible();

    createStartFrame(null, null);

    assertThat(this.mic1Creator.isWritten()).isFalse();
    this.startFrame.button("okay").requireEnabled();
    this.startFrame.button("okay").click();
    assertThat(this.mic1Creator.getMicroPath()).isEmpty();
    assertThat(this.mic1Creator.getMacroPath()).isEmpty();
    assertThat(this.mic1Creator.isWritten()).isTrue();
    this.startFrame.requireNotVisible();

    createStartFrame("/some/path/to/there.txt", "/some/path/to/here.txt");

    assertThat(this.mic1Creator.isWritten()).isFalse();
    this.startFrame.button("okay").requireEnabled();
    this.startFrame.button("okay").click();
    assertThat(this.mic1Creator.getMicroPath()).isEqualTo("/some/path/to/there.txt");
    assertThat(this.mic1Creator.getMacroPath()).isEqualTo("/some/path/to/here.txt");
    assertThat(this.mic1Creator.isWritten()).isTrue();
    this.startFrame.requireNotVisible();

    createStartFrame("/some/path/to/there.txt", "/some/path/to/here.txt");

    robot().doubleClick(this.startFrame.textBox("macro-assembler-file-path").component());
    this.startFrame.textBox("macro-assembler-file-path").deleteText();
    assertEnabledAndEmpty(this.startFrame.textBox("macro-assembler-file-path"));
    this.startFrame.textBox("macro-assembler-file-path").enterText("/some/path/to/there.txt");
    assertThat(this.mic1Creator.isWritten()).isFalse();
    this.startFrame.button("okay").requireEnabled();
    this.startFrame.button("okay").click();
    assertThat(this.mic1Creator.getMicroPath()).isEqualTo("/some/path/to/there.txt");
    assertThat(this.mic1Creator.getMacroPath()).isEqualTo("/some/path/to/there.txt");
    assertThat(this.mic1Creator.isWritten()).isTrue();
    this.startFrame.requireNotVisible();
  }

  @Test
  public void testBrowse_Micro() {
    printlnMethodName();
    this.startFrame.button("micro-assembler-file-browse").click();
    JFileChooserFixture fileChooser = new JFileChooserFixture(robot());
    fileChooser.cancel();
    assertEnabledAndEmpty(this.startFrame.textBox("micro-assembler-file-path"));
    assertEnabledAndEmpty(this.startFrame.textBox("macro-assembler-file-path"));

    this.startFrame.button("micro-assembler-file-browse").click();
    fileChooser = new JFileChooserFixture(robot());
    fileChooser.selectFile(new File("anything.mic1"));
    fileChooser.approve();
    this.startFrame.textBox("micro-assembler-file-path")
      .requireText(System.getProperty("user.home") + "/anything.mic1");
    this.startFrame.textBox("micro-assembler-file-path").requireEditable();
    this.startFrame.textBox("micro-assembler-file-path").requireEnabled();

    assertEnabledAndEmpty(this.startFrame.textBox("macro-assembler-file-path"));

    this.startFrame.button("macro-assembler-file-browse").click();
    fileChooser = new JFileChooserFixture(robot());
    fileChooser.selectFile(new File("some.ijvm"));
    fileChooser.approve();
    this.startFrame.textBox("micro-assembler-file-path")
      .requireText(System.getProperty("user.home") + "/anything.mic1");
    this.startFrame.textBox("micro-assembler-file-path").requireEditable();
    this.startFrame.textBox("micro-assembler-file-path").requireEnabled();

    this.startFrame.textBox("macro-assembler-file-path").requireText(System.getProperty("user.home") + "/some.ijvm");
    this.startFrame.textBox("macro-assembler-file-path").requireEditable();
    this.startFrame.textBox("macro-assembler-file-path").requireEnabled();

    this.startFrame.button("micro-assembler-file-browse").click();
    fileChooser = new JFileChooserFixture(robot());
    fileChooser.selectFile(new File("some.mic1"));
    fileChooser.approve();
    this.startFrame.textBox("micro-assembler-file-path").requireText(System.getProperty("user.home") + "/some.mic1");
    this.startFrame.textBox("micro-assembler-file-path").requireEditable();
    this.startFrame.textBox("micro-assembler-file-path").requireEnabled();

    this.startFrame.textBox("macro-assembler-file-path").requireText(System.getProperty("user.home") + "/some.ijvm");
    this.startFrame.textBox("macro-assembler-file-path").requireEditable();
    this.startFrame.textBox("macro-assembler-file-path").requireEnabled();

    createStartFrame("/path/one", "/path/two");
    this.startFrame.textBox("micro-assembler-file-path").requireText("/path/one");
    this.startFrame.textBox("micro-assembler-file-path").requireNotEditable();
    this.startFrame.textBox("micro-assembler-file-path").requireDisabled();
    this.startFrame.textBox("macro-assembler-file-path").requireText("/path/two");
    this.startFrame.textBox("macro-assembler-file-path").requireNotEditable();
    this.startFrame.textBox("macro-assembler-file-path").requireDisabled();

    this.startFrame.button("micro-assembler-file-browse").click();
    fileChooser = new JFileChooserFixture(robot());
    fileChooser.selectFile(new File("some.mic1"));
    fileChooser.approve();
    this.startFrame.textBox("micro-assembler-file-path").requireText(System.getProperty("user.home") + "/some.mic1");
    this.startFrame.textBox("micro-assembler-file-path").requireEditable();
    this.startFrame.textBox("micro-assembler-file-path").requireEnabled();

    this.startFrame.textBox("macro-assembler-file-path").requireText("/path/two");
    this.startFrame.textBox("macro-assembler-file-path").requireNotEditable();
    this.startFrame.textBox("macro-assembler-file-path").requireDisabled();
  }
}
