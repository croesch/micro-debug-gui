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

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import net.miginfocom.swing.MigLayout;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link MacroCodeArea}.
 * 
 * @author croesch
 * @since Date: Apr 5, 2012
 */
public class MacroCodeAreaTest extends DefaultGUITestCase {

  private FrameFixture showFrameWithArea() {
    final FrameFixture frameFixture = new FrameFixture(robot(), GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() {
        final JFrame frame = new JFrame();
        frame.setLayout(new MigLayout("fill"));
        frame.add(new JScrollPane(new MacroCodeArea(null)), "grow");
        return frame;
      }
    }));
    frameFixture.show(new Dimension(500, 500));
    return frameFixture;
  }

  @Test
  public void testSyntaxHighlightning_MultipleLines_LF() throws InterruptedException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().setText("[ 0x15] ILOAD 2\n" + "[ 0xB6] INVOKEVIRTUAL 2[=0x94]\n" + "[ 0xFF] HALT\n"
                                    + "[0x000] NOP\n" + "[  0x1] -\n" + "[  0x0] NOP\n" + "[  0x1] -\n"
                                    + "[ 0x10] BIPUSH 0x0\n" + "[ 0x36] ISTORE 1\n");

    final Document document = frame.textBox().component().getDocument();
    assertThat(document).isInstanceOf(StyledDocument.class);

    final StyledDocument doc = (StyledDocument) document;

    assertThat(doc.getLength()).isEqualTo(140);

    // [ 0x15] ILOAD 2\n
    assertBracket(doc, 0);
    assertSeparator(doc, 1);
    assertAddress(doc, 2, 5);
    assertBracket(doc, 6);
    assertSeparator(doc, 7);
    assertCommand(doc, 8, 12);
    assertSeparator(doc, 13);
    assertNumber(doc, 14);
    assertSeparator(doc, 15);

    // [ 0xB6] INVOKEVIRTUAL 2[=0x94]\n
    assertBracket(doc, 16);
    assertSeparator(doc, 17);
    assertAddress(doc, 18, 21);
    assertBracket(doc, 22);
    assertSeparator(doc, 23);
    assertCommand(doc, 24, 36);
    assertSeparator(doc, 37);
    assertNumber(doc, 38);
    assertBracket(doc, 39, 40);
    assertNumber(doc, 41, 44);
    assertBracket(doc, 45);
    assertSeparator(doc, 46);

    // [ 0xFF] HALT\n
    assertBracket(doc, 47);
    assertSeparator(doc, 48);
    assertAddress(doc, 49, 52);
    assertBracket(doc, 53);
    assertSeparator(doc, 54);
    assertCommand(doc, 55, 58);
    assertSeparator(doc, 59);

    // [0x000] NOP\n
    assertBracket(doc, 60);
    assertAddress(doc, 61, 65);
    assertBracket(doc, 66);
    assertSeparator(doc, 67);
    assertCommand(doc, 68, 70);
    assertSeparator(doc, 71);

    // [  0x1] -\n
    assertBracket(doc, 72);
    assertSeparator(doc, 73, 74);
    assertAddress(doc, 75, 77);
    assertBracket(doc, 78);
    assertSeparator(doc, 79);
    assertInvalid(doc, 80);
    assertSeparator(doc, 81);

    // [  0x0] NOP\n
    assertBracket(doc, 82);
    assertSeparator(doc, 83, 84);
    assertAddress(doc, 85, 87);
    assertBracket(doc, 88);
    assertSeparator(doc, 89);
    assertCommand(doc, 90, 92);
    assertSeparator(doc, 93);

    // [  0x1] -\n
    assertBracket(doc, 94);
    assertSeparator(doc, 95, 96);
    assertAddress(doc, 97, 99);
    assertBracket(doc, 100);
    assertSeparator(doc, 101);
    assertInvalid(doc, 102);
    assertSeparator(doc, 103);

    // [ 0x10] BIPUSH 0x0\n
    assertBracket(doc, 104);
    assertSeparator(doc, 105);
    assertAddress(doc, 106, 109);
    assertBracket(doc, 110);
    assertSeparator(doc, 111);
    assertCommand(doc, 112, 117);
    assertSeparator(doc, 118);
    assertNumber(doc, 119, 121);
    assertSeparator(doc, 122);

    // [ 0x36] ISTORE 1\n
    assertBracket(doc, 123);
    assertSeparator(doc, 124);
    assertAddress(doc, 125, 128);
    assertBracket(doc, 129);
    assertSeparator(doc, 130);
    assertCommand(doc, 131, 136);
    assertSeparator(doc, 137);
    assertNumber(doc, 138);
    assertSeparator(doc, 139);
  }

  @Test
  public void testSyntaxHighlightning_MultipleLines_NoLF() throws InterruptedException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().setText("[ 0xB6] INVOKEVIRTUAL 11[=0x43]\n" + "[ 0x36] ISTORE 0\n" + "[ 0x10] BIPUSH 0x2B");

    final Document document = frame.textBox().component().getDocument();
    assertThat(document).isInstanceOf(StyledDocument.class);

    final StyledDocument doc = (StyledDocument) document;

    assertThat(doc.getLength()).isEqualTo(68);

    // [ 0xB6] INVOKEVIRTUAL 11[=0x43]\n
    assertBracket(doc, 0);
    assertSeparator(doc, 1);
    assertAddress(doc, 2, 5);
    assertBracket(doc, 6);
    assertSeparator(doc, 7);
    assertCommand(doc, 8, 20);
    assertSeparator(doc, 21);
    assertNumber(doc, 22, 23);
    assertBracket(doc, 24, 25);
    assertNumber(doc, 26, 29);
    assertBracket(doc, 30);
    assertSeparator(doc, 31);

    // [ 0x36] ISTORE 0\n
    assertBracket(doc, 32);
    assertSeparator(doc, 33);
    assertAddress(doc, 34, 37);
    assertBracket(doc, 38);
    assertSeparator(doc, 39);
    assertCommand(doc, 40, 45);
    assertSeparator(doc, 46);
    assertNumber(doc, 47);
    assertSeparator(doc, 48);

    // [ 0x10] BIPUSH 0x2B
    assertBracket(doc, 49);
    assertSeparator(doc, 50);
    assertAddress(doc, 51, 54);
    assertBracket(doc, 55);
    assertSeparator(doc, 56);
    assertCommand(doc, 57, 62);
    assertSeparator(doc, 63);
    assertNumber(doc, 64, 67);
  }

  @Test
  public void testSyntaxHighlightning_SingleLine_NoLF() throws InterruptedException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().setText("[ 0x59] DUP");

    final Document document = frame.textBox().component().getDocument();
    assertThat(document).isInstanceOf(StyledDocument.class);

    final StyledDocument doc = (StyledDocument) document;

    assertThat(doc.getLength()).isEqualTo(11);

    // [ 0x59] DUP
    assertBracket(doc, 0);
    assertSeparator(doc, 1);
    assertAddress(doc, 2, 5);
    assertBracket(doc, 6);
    assertSeparator(doc, 7);
    assertCommand(doc, 8, 10);
  }

  @Test
  public void testSyntaxHighlightning_SingleLine_LF() throws InterruptedException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().setText("[ 0x13] LDC_W 0[=0x40]\n");

    final Document document = frame.textBox().component().getDocument();
    assertThat(document).isInstanceOf(StyledDocument.class);

    final StyledDocument doc = (StyledDocument) document;

    assertThat(doc.getLength()).isEqualTo(23);

    // [ 0x13] LDC_W 0[=0x40]\n
    assertBracket(doc, 0);
    assertSeparator(doc, 1);
    assertAddress(doc, 2, 5);
    assertBracket(doc, 6);
    assertSeparator(doc, 7);
    assertCommand(doc, 8, 12);
    assertSeparator(doc, 13);
    assertNumber(doc, 14);
    assertBracket(doc, 15, 16);
    assertNumber(doc, 17, 20);
    assertBracket(doc, 21);
    assertSeparator(doc, 22);
  }

  @Test
  public void testSyntaxHighlightning_SingleLine_CRLF() throws InterruptedException, BadLocationException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().setText("[ 0x60] IADD\r\n");
    frame.textBox().requireText("[ 0x60] IADD\r\n");

    final Document document = frame.textBox().component().getDocument();
    assertThat(document).isInstanceOf(StyledDocument.class);

    final StyledDocument doc = (StyledDocument) document;

    assertThat(doc.getLength()).isEqualTo(13);

    // [ 0x60] IADD\r\n
    assertBracket(doc, 0);
    assertSeparator(doc, 1);
    assertAddress(doc, 2, 5);
    assertBracket(doc, 6);
    assertSeparator(doc, 7);
    assertCommand(doc, 8, 11);
    assertSeparator(doc, 12);
  }

  private void assertAddress(final StyledDocument doc, final int from, final int to) {
    for (int i = from; i <= to; ++i) {
      assertAttributes(doc, i, false, false, Color.DARK_GRAY);
    }
  }

  private void assertNumber(final StyledDocument doc, final int pos) {
    assertNumber(doc, pos, pos);
  }

  private void assertNumber(final StyledDocument doc, final int from, final int to) {
    for (int i = from; i <= to; ++i) {
      assertAttributes(doc, i, false, true, new Color(160, 34, 34));
    }
  }

  private void assertCommand(final StyledDocument doc, final int from, final int to) {
    for (int i = from; i <= to; ++i) {
      assertAttributes(doc, i, false, true, new Color(34, 34, 139));
    }
  }

  private void assertBracket(final StyledDocument doc, final int pos) {
    assertOperator(doc, pos, pos);
  }

  private void assertBracket(final StyledDocument doc, final int from, final int to) {
    for (int i = from; i <= to; ++i) {
      assertBracket(doc, i);
    }
  }

  private void assertOperator(final StyledDocument doc, final int from, final int to) {
    for (int i = from; i <= to; ++i) {
      assertAttributes(doc, i, false, false, new Color(34, 139, 34));
    }
  }

  private void assertInvalid(final StyledDocument doc, final int pos) {
    assertAttributes(doc, pos, false, false, Color.RED);
  }

  private void assertSeparator(final StyledDocument doc, final int pos) {
    assertAttributes(doc, pos, false, false, Color.GRAY);
  }

  private void assertSeparator(final StyledDocument doc, final int from, final int to) {
    for (int i = from; i <= to; ++i) {
      assertSeparator(doc, i);
    }
  }

  private void assertAttributes(final StyledDocument doc,
                                final int i,
                                final boolean italic,
                                final boolean bold,
                                final Color color) {
    final AttributeSet attributes = doc.getCharacterElement(i).getAttributes();

    String error;
    if (italic) {
      error = "Character [" + i + "] should be italic, but isn't.";
    } else {
      error = "Character [" + i + "] shouldn't be italic, but is.";
    }
    assertThat(StyleConstants.isItalic(attributes)).overridingErrorMessage(error).isEqualTo(italic);
    if (bold) {
      error = "Character [" + i + "] should be bold, but isn't.";
    } else {
      error = "Character [" + i + "] shouldn't be bold, but is.";
    }
    assertThat(StyleConstants.isBold(attributes)).overridingErrorMessage(error).isEqualTo(bold);
    assertThat(StyleConstants.getForeground(attributes)).isEqualTo(color);
  }
}
