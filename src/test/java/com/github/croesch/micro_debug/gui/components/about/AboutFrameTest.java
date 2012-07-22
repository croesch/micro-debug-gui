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
package com.github.croesch.micro_debug.gui.components.about;

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
 * Provides test cases for {@link AboutFrame}.
 * 
 * @author croesch
 * @since Date: Mar 3, 2012
 */
public class AboutFrameTest extends DefaultGUITestCase {

  private FrameFixture aboutFrame;

  @Override
  protected void setUpTestCase() {
    final AboutFrame frame = GuiActionRunner.execute(new GuiQuery<AboutFrame>() {
      @Override
      protected AboutFrame executeInEDT() {
        return new AboutFrame();
      }
    });
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.aboutFrame = new FrameFixture(robot(), frame);
    this.aboutFrame.show();
  }

  @Override
  protected void onTearDown() {
    this.aboutFrame.close();
  }

  @Test
  public void testNameAndVersion() {
    printlnMethodName();
    final Pattern aboutPattern = Pattern.compile("<html><h(.)>" + InternalSettings.NAME + "</h\\1>\n<(.+)>"
                                                 + InternalSettings.VERSION + "</\\2>");
    this.aboutFrame.label("name-and-version").requireText(aboutPattern);
  }

  @Test
  public void testFrame() {
    printlnMethodName();
    this.aboutFrame.requireSize(new Dimension(400, 647));
    assertThat(this.aboutFrame.component().isResizable()).isFalse();
    assertThat(this.aboutFrame.component().getTitle()).isEqualTo(GuiText.GUI_ABOUT_TITLE.text());
  }

  @Test
  public void testLicense() throws IOException {
    printlnMethodName();
    this.aboutFrame.label("license-title").requireText(GuiText.GUI_ABOUT_LICENSE.text());
    this.aboutFrame.label("license-text").requireText(readFile("gui/about/license.txt").toString());
  }

  @Test
  public void testCopyright() throws IOException {
    printlnMethodName();
    this.aboutFrame.label("copyright-text").requireText(readFile("gui/about/copyright.txt").toString());
  }

  @Test
  public void testDescription() throws IOException {
    printlnMethodName();
    this.aboutFrame.label("description").requireText(GuiText.GUI_ABOUT_DESCRIPTION.text(InternalSettings.NAME));
  }
}
