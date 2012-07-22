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
package com.github.croesch.micro_debug.gui.components.help;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Dimension;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;

/**
 * Provides test cases for {@link HelpFrame}.
 * 
 * @author croesch
 * @since Date: Jul 22, 2012
 */
public class HelpFrameTest extends DefaultGUITestCase {

  private FrameFixture helpFrame;

  @Override
  protected void setUpTestCase() {
    final HelpFrame frame = GuiActionRunner.execute(new GuiQuery<HelpFrame>() {
      @Override
      protected HelpFrame executeInEDT() {
        return new HelpFrame();
      }
    });
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.helpFrame = new FrameFixture(robot(), frame);
    this.helpFrame.show();
  }

  @Override
  protected void onTearDown() {
    this.helpFrame.close();
  }

  @Test
  public void testNameAndVersion() {
    printlnMethodName();
    final Pattern aboutPattern = Pattern.compile("<html><h(.)>" + InternalSettings.NAME + "</h\\1>\n<(.+)>"
                                                 + GuiText.GUI_HELP_TITLE + "</\\2>");
    this.helpFrame.label("name-and-version").requireText(aboutPattern);
  }

  @Test
  public void testFrame() {
    printlnMethodName();
    this.helpFrame.requireSize(new Dimension(400, 647));
    assertThat(this.helpFrame.component().isResizable()).isTrue();
    assertThat(this.helpFrame.component().getTitle()).isEqualTo(GuiText.GUI_HELP_TITLE.text());
  }

  @Test
  public void testComponentDescription() throws IOException {
    printlnMethodName();
    this.helpFrame.label("component-description").requireText(GuiText.GUI_HELP_COMP_DESCR.text());
  }

  @Test
  public void testIssueTrackingDescription() throws IOException {
    printlnMethodName();
    this.helpFrame.label("issue-tracking-description").requireText(GuiText.GUI_HELP_ISSUE_DESCR.text());
  }

  @Test
  public void testDevelopmentDescription() throws IOException {
    printlnMethodName();
    this.helpFrame.label("development-description").requireText(GuiText.GUI_HELP_DEV_DESCR.text(InternalSettings.NAME));
  }
}
