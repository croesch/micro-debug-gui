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
import javax.swing.text.Highlighter.Highlight;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link ACodeArea}.
 * 
 * @author croesch
 * @since Date: Mar 30, 2012
 */
public class ACodeAreaTest extends DefaultGUITestCase {

  private FrameFixture showFrameWithArea() {
    final FrameFixture frameFixture = new FrameFixture(robot(), GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() {
        final JFrame frame = new JFrame();
        frame.add(new ACodeArea(null, null) {
          /** default */
          private static final long serialVersionUID = 1L;
        });
        return frame;
      }
    }));
    frameFixture.show(new Dimension(500, 500));
    return frameFixture;
  }

  @Test
  public void testConstructor_Null() {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().requireNotEditable();
    frame.textBox().setText("...");
    frame.textBox().requireText("...");
  }

  @Test
  public void testHighlight() throws InterruptedException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().requireNotEditable();
    frame.textBox().setText("...\nzweite Zeile\n\n4");
    highlightLine(frame.textBox(), 0);
    assertLineHighlighted(frame.textBox(), 0);

    highlightLine(frame.textBox(), 1);
    assertLineHighlighted(frame.textBox(), 1);

    highlightLine(frame.textBox(), 2);
    assertLineHighlighted(frame.textBox(), 2);

    highlightLine(frame.textBox(), 3);
    assertLineHighlighted(frame.textBox(), 3);

    highlightLine(frame.textBox(), 4);
    assertNoLineHighlighted(frame.textBox());

    highlightLine(frame.textBox(), 3);
    assertLineHighlighted(frame.textBox(), 3);

    highlightLine(frame.textBox(), -1);
    assertNoLineHighlighted(frame.textBox());
  }

  public static void assertLineHighlighted(final JTextComponentFixture textBox, final int line) {
    final Highlight[] highlights = textBox.targetCastedTo(ACodeArea.class).getHighlighter().getHighlights();
    assertThat(highlights).hasSize(1);
    assertThat(highlights[0].getStartOffset()).isEqualTo(textBox.targetCastedTo(ACodeArea.class)
                                                           .getLineStartOffset(line));
    assertThat(highlights[0].getPainter()).isInstanceOf(LineHighlighter.class);
  }

  public static void assertNoLineHighlighted(final JTextComponentFixture textBox) {
    final Highlight[] highlights = textBox.targetCastedTo(ACodeArea.class).getHighlighter().getHighlights();
    assertThat(highlights).isEmpty();
  }

  public static void highlightLine(final JTextComponentFixture textBox, final int line) {
    textBox.targetCastedTo(ACodeArea.class).highlight(line);
  }
}
