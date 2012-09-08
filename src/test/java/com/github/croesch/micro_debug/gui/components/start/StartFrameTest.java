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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
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

  public static final String DESCRIPTION_LB_NAME = "description";

  public static final String OKAY_BT_NAME = "okay";

  public static final String MACRO_BROWSE_BT_NAME = "macro-assembler-file-browse";

  public static final String MICRO_BROWSE_BT_NAME = "micro-assembler-file-browse";

  public static final String MACRO_PATH_TF_NAME = "macro-assembler-file-path";

  public static final String MICRO_PATH_TF_NAME = "micro-assembler-file-path";

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
    robot().waitForIdle();
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
    closeStartFrame(this.startFrame);
  }

  @Override
  protected void onTearDown() {
    closeStartFrame(this.startFrame);
  }

  @Test
  public void testFrame() {
    printlnMethodName();
    this.startFrame.requireSize(new Dimension(485, 300));
    assertThat(this.startFrame.component().isResizable()).isTrue();
    assertThat(this.startFrame.component().getName()).isEqualTo("start-frame");
    assertThat(this.startFrame.component().getTitle()).isEqualTo(GuiText.GUI_START_TITLE.text(InternalSettings.NAME));
  }

  @Test
  public void testLabels() {
    printlnMethodName();
    this.startFrame.label("micro-assembler-file").requireText(GuiText.GUI_START_MICRO.text());
    this.startFrame.label("macro-assembler-file").requireText(GuiText.GUI_START_MACRO.text());

    this.startFrame.label(DESCRIPTION_LB_NAME).requireText(GuiText.GUI_START_DESCRIPTION.text());
    this.startFrame.label(DESCRIPTION_LB_NAME).foreground().requireEqualTo(UIManager.getColor("Label.foreground"));
  }

  @Test
  public void testTextFields() {
    printlnMethodName();
    assertEnabledAndEmpty(this.startFrame.textBox(MICRO_PATH_TF_NAME));
    assertEnabledAndEmpty(this.startFrame.textBox(MACRO_PATH_TF_NAME));
  }

  public static void assertEnabledAndEmpty(final JTextComponentFixture textBox) {
    textBox.requireEmpty();
    textBox.requireEditable();
    textBox.requireEnabled();
  }

  @Test
  public void testButtons() {
    printlnMethodName();
    this.startFrame.button(MICRO_BROWSE_BT_NAME).requireText(GuiText.GUI_COMMAND_BROWSE.text());
    this.startFrame.button(MACRO_BROWSE_BT_NAME).requireText(GuiText.GUI_COMMAND_BROWSE.text());
    this.startFrame.button(OKAY_BT_NAME).requireText(GuiText.GUI_START_OKAY.text());
    this.startFrame.button(OKAY_BT_NAME).requireDisabled();

    assertThat(this.startFrame.button(MICRO_BROWSE_BT_NAME).component().isDefaultButton()).isFalse();
    assertThat(this.startFrame.button(MACRO_BROWSE_BT_NAME).component().isDefaultButton()).isFalse();
    assertThat(this.startFrame.button(OKAY_BT_NAME).component().isDefaultButton()).isTrue();
  }

  @Test
  public void testStartFrameStringString() {
    printlnMethodName();
    createStartFrame(null, null);
    assertEnabledAndEmpty(this.startFrame.textBox(MICRO_PATH_TF_NAME));
    assertEnabledAndEmpty(this.startFrame.textBox(MACRO_PATH_TF_NAME));
    this.startFrame.button(OKAY_BT_NAME).requireDisabled();
    this.startFrame.label(DESCRIPTION_LB_NAME).requireText(GuiText.GUI_START_DESCRIPTION.text());
    this.startFrame.label(DESCRIPTION_LB_NAME).foreground().requireEqualTo(UIManager.getColor("Label.foreground"));

    createStartFrame(" \t\n   \t  ", "");
    assertEnabledAndEmpty(this.startFrame.textBox(MICRO_PATH_TF_NAME));
    assertEnabledAndEmpty(this.startFrame.textBox(MACRO_PATH_TF_NAME));
    this.startFrame.button(OKAY_BT_NAME).requireDisabled();
    this.startFrame.label(DESCRIPTION_LB_NAME).requireText(GuiText.GUI_START_DESCRIPTION.text());
    this.startFrame.label(DESCRIPTION_LB_NAME).foreground().requireEqualTo(UIManager.getColor("Label.foreground"));

    createStartFrame(" path to nirvana ", "");
    assertNotEditableAndContainsText(this.startFrame.textBox(MICRO_PATH_TF_NAME), "path to nirvana", robot());
    assertEnabledAndEmpty(this.startFrame.textBox(MACRO_PATH_TF_NAME));
    this.startFrame.button(OKAY_BT_NAME).requireDisabled();
    this.startFrame.label(DESCRIPTION_LB_NAME).requireText(GuiText.GUI_START_MACRO_WFF.text());
    this.startFrame.label(DESCRIPTION_LB_NAME).foreground().requireEqualTo(Color.RED);

    createStartFrame(null, " path to nirvana ");
    assertEnabledAndEmpty(this.startFrame.textBox(MICRO_PATH_TF_NAME));
    assertNotEditableAndContainsText(this.startFrame.textBox(MACRO_PATH_TF_NAME), "path to nirvana", robot());
    this.startFrame.button(OKAY_BT_NAME).requireDisabled();
    this.startFrame.label(DESCRIPTION_LB_NAME).requireText(GuiText.GUI_START_MICRO_WFF.text());
    this.startFrame.label(DESCRIPTION_LB_NAME).foreground().requireEqualTo(Color.RED);

    createStartFrame(" path to nirvana ", "something");
    assertNotEditableAndContainsText(this.startFrame.textBox(MICRO_PATH_TF_NAME), "path to nirvana", robot());
    assertNotEditableAndContainsText(this.startFrame.textBox(MACRO_PATH_TF_NAME), "something", robot());
    this.startFrame.button(OKAY_BT_NAME).requireEnabled();
    this.startFrame.label(DESCRIPTION_LB_NAME).requireText(GuiText.GUI_START_DESCRIPTION.text());
    this.startFrame.label(DESCRIPTION_LB_NAME).foreground().requireEqualTo(UIManager.getColor("Label.foreground"));
  }

  public static void assertNotEditableAndContainsText(final JTextComponentFixture textBox,
                                                      final String text,
                                                      final Robot robot) {
    textBox.requireText(text);
    textBox.requireDisabled();
    textBox.requireNotEditable();

    robot.click(textBox.component());
    textBox.requireDisabled();
    textBox.requireNotEditable();

    robot.click(textBox.component(), MouseButton.RIGHT_BUTTON, 3);
    textBox.requireDisabled();
    textBox.requireNotEditable();

    robot.click(textBox.component(), MouseButton.RIGHT_BUTTON, 2);
    textBox.requireDisabled();
    textBox.requireNotEditable();

    robot.doubleClick(textBox.component());
    textBox.requireEnabled();
    textBox.requireEditable();

    robot.doubleClick(textBox.component());
    textBox.requireEnabled();
    textBox.requireEditable();
  }

  @Test
  public void testOkayButton() throws Exception {
    printlnMethodName();
    enterText(this.startFrame.textBox(MICRO_PATH_TF_NAME), "/macro/path.txt");
    this.startFrame.textBox(MICRO_PATH_TF_NAME).deleteText();
    this.startFrame.button(OKAY_BT_NAME).requireDisabled();
    enterText(this.startFrame.textBox(MICRO_PATH_TF_NAME), "/micro/path.txt");
    enterText(this.startFrame.textBox(MACRO_PATH_TF_NAME), "/macro/path.txt");
    assertThat(this.mic1Creator.isWritten()).isFalse();
    this.startFrame.button(OKAY_BT_NAME).requireEnabled();
    this.startFrame.button(OKAY_BT_NAME).click();
    assertThat(this.mic1Creator.getMicroPath()).isEqualTo("/micro/path.txt");
    assertThat(this.mic1Creator.getMacroPath()).isEqualTo("/macro/path.txt");
    assertThat(this.mic1Creator.isWritten()).isTrue();
    this.startFrame.requireNotVisible();

    createStartFrame("/some/path/to/there.txt", "/some/path/to/here.txt");

    assertThat(this.mic1Creator.isWritten()).isFalse();
    this.startFrame.button(OKAY_BT_NAME).requireEnabled();
    this.startFrame.pressAndReleaseKeys(KeyEvent.VK_ENTER);
    assertThat(this.mic1Creator.getMicroPath()).isEqualTo("/some/path/to/there.txt");
    assertThat(this.mic1Creator.getMacroPath()).isEqualTo("/some/path/to/here.txt");
    assertThat(this.mic1Creator.isWritten()).isTrue();
    this.startFrame.requireNotVisible();

    createStartFrame("/some/path/to/there.txt", "/some/path/to/here.txt");

    robot().doubleClick(this.startFrame.textBox(MACRO_PATH_TF_NAME).component());
    this.startFrame.textBox(MACRO_PATH_TF_NAME).deleteText();
    this.startFrame.button(OKAY_BT_NAME).requireDisabled();
    assertEnabledAndEmpty(this.startFrame.textBox(MACRO_PATH_TF_NAME));
    enterText(this.startFrame.textBox(MACRO_PATH_TF_NAME), "/some/path/to/there.txt");
    assertThat(this.mic1Creator.isWritten()).isFalse();
    this.startFrame.button(OKAY_BT_NAME).requireEnabled();
    this.startFrame.button(OKAY_BT_NAME).click();
    assertThat(this.mic1Creator.getMicroPath()).isEqualTo("/some/path/to/there.txt");
    assertThat(this.mic1Creator.getMacroPath()).isEqualTo("/some/path/to/there.txt");
    assertThat(this.mic1Creator.isWritten()).isTrue();
    this.startFrame.requireNotVisible();
  }

  @Test
  public void testBrowse_Micro() {
    printlnMethodName();
    this.startFrame.button(MICRO_BROWSE_BT_NAME).click();
    JFileChooserFixture fileChooser = new JFileChooserFixture(robot());
    fileChooser.cancel();
    assertEnabledAndEmpty(this.startFrame.textBox(MICRO_PATH_TF_NAME));
    assertEnabledAndEmpty(this.startFrame.textBox(MACRO_PATH_TF_NAME));
    this.startFrame.button(OKAY_BT_NAME).requireDisabled();

    this.startFrame.button(MICRO_BROWSE_BT_NAME).click();
    fileChooser = new JFileChooserFixture(robot());
    fileChooser.setCurrentDirectory(new File(getUserHome()));
    fileChooser.selectFile(new File("anything.mic1"));
    fileChooser.approve();
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireText(getUserHome() + getFileSeparator() + "anything.mic1");
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireEditable();
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireEnabled();
    assertEnabledAndEmpty(this.startFrame.textBox(MACRO_PATH_TF_NAME));
    this.startFrame.button(OKAY_BT_NAME).requireDisabled();

    this.startFrame.button(MACRO_BROWSE_BT_NAME).click();
    fileChooser = new JFileChooserFixture(robot());
    fileChooser.selectFile(new File("some.ijvm"));
    fileChooser.approve();
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireText(getUserHome() + getFileSeparator() + "anything.mic1");
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireEditable();
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireEnabled();

    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireText(getUserHome() + getFileSeparator() + "some.ijvm");
    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireEditable();
    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireEnabled();
    this.startFrame.button(OKAY_BT_NAME).requireEnabled();

    this.startFrame.button(MICRO_BROWSE_BT_NAME).click();
    fileChooser = new JFileChooserFixture(robot());
    fileChooser.selectFile(new File("some.mic1"));
    fileChooser.approve();
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireText(getUserHome() + getFileSeparator() + "some.mic1");
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireEditable();
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireEnabled();

    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireText(getUserHome() + getFileSeparator() + "some.ijvm");
    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireEditable();
    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireEnabled();
    this.startFrame.button(OKAY_BT_NAME).requireEnabled();

    createStartFrame("/path/one", "/path/two");
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireText("/path/one");
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireNotEditable();
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireDisabled();
    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireText("/path/two");
    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireNotEditable();
    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireDisabled();
    this.startFrame.button(OKAY_BT_NAME).requireEnabled();

    this.startFrame.button(MICRO_BROWSE_BT_NAME).click();
    fileChooser = new JFileChooserFixture(robot());
    fileChooser.selectFile(new File("some.mic1"));
    fileChooser.approve();
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireText(getUserHome() + getFileSeparator() + "some.mic1");
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireEditable();
    this.startFrame.textBox(MICRO_PATH_TF_NAME).requireEnabled();

    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireText("/path/two");
    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireNotEditable();
    this.startFrame.textBox(MACRO_PATH_TF_NAME).requireDisabled();
    this.startFrame.button(OKAY_BT_NAME).requireEnabled();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_Creator() {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        new StartFrame(null);
      }
    });
  }
}
