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
package com.github.croesch.micro_debug.gui.components.code;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link ACodeFormatter}.
 * 
 * @author croesch
 * @since Date: Mar 30, 2012
 */
public class ACodeFormatterTest extends DefaultGUITestCase {

  private int invoked = 0;

  @Override
  protected void setUpTestCase() throws Exception {
    this.invoked = 0;
  }

  private FrameFixture showFrameWithArea(final JTextComponent ta) {
    final FrameFixture frameFixture = new FrameFixture(robot(), GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() {
        final JFrame frame = new JFrame();
        frame.add(ta);
        return frame;
      }
    }));
    frameFixture.show(new Dimension(500, 500));
    return frameFixture;
  }

  @Test
  public void testFormatWithoutStyledDocument() {
    final JTextArea ta = GuiActionRunner.execute(new GuiQuery<JTextArea>() {
      @Override
      protected JTextArea executeInEDT() throws Throwable {
        return new JTextArea();
      }
    });
    ta.getDocument().addDocumentListener(new ACodeFormatter() {

      @Override
      protected void format(final StyledDocument doc) {
        ++ACodeFormatterTest.this.invoked;
      }
    });
    showFrameWithArea(ta);

    new JTextComponentFixture(robot(), ta).enterText("12345");
    assertThat(this.invoked).isZero();
  }

  @Test
  public void testFormatWithStyledDocument() {
    final JTextPane ta = GuiActionRunner.execute(new GuiQuery<JTextPane>() {
      @Override
      protected JTextPane executeInEDT() throws Throwable {
        return new JTextPane();
      }
    });
    ta.getDocument().addDocumentListener(new ACodeFormatter() {

      @Override
      protected void format(final StyledDocument doc) {
        ++ACodeFormatterTest.this.invoked;
      }
    });
    showFrameWithArea(ta);

    assertThat(this.invoked).isZero();
    final JTextComponentFixture tf = new JTextComponentFixture(robot(), ta).enterText("12345");
    assertThat(this.invoked).isEqualTo(5);

    tf.deleteText();
    assertThat(this.invoked).isEqualTo(6);
  }
}
