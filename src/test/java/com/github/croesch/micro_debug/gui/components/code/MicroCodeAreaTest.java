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
 * Provides test cases for {@link MicroCodeArea}.
 * 
 * @author croesch
 * @since Date: Mar 31, 2012
 */
public class MicroCodeAreaTest extends DefaultGUITestCase {

  private FrameFixture showFrameWithArea() {
    final FrameFixture frameFixture = new FrameFixture(robot(), GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() {
        final JFrame frame = new JFrame();
        frame.setLayout(new MigLayout("fill"));
        frame.add(new JScrollPane(new MicroCodeArea()), "grow");
        return frame;
      }
    }));
    frameFixture.show(new Dimension(500, 500));
    return frameFixture;
  }

  @Test
  public void testSyntaxHighlightning_MultipleLines_LF() throws InterruptedException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().setText("goto 0x2\n" + "PC=PC+1;goto 0x40\n" + "PC=PC+1;fetch;goto (MBR)\n" + "H=TOS;goto 0x4\n");

    final Document document = frame.textBox().component().getDocument();
    assertThat(document).isInstanceOf(StyledDocument.class);

    final StyledDocument doc = (StyledDocument) document;

    assertThat(doc.getLength()).isEqualTo(67);

    // goto 0x2\n
    assertKeyWord(doc, 0, 3);
    assertSeparator(doc, 4);
    assertNumber(doc, 5, 7);
    assertSeparator(doc, 8);

    // PC=PC+1;goto 0x40\n
    assertRegister(doc, 9, 10);
    assertOperator(doc, 11);
    assertRegister(doc, 12, 13);
    assertOperator(doc, 14);
    assertNumber(doc, 15);
    assertSeparator(doc, 16);
    assertKeyWord(doc, 17, 20);
    assertSeparator(doc, 21);
    assertNumber(doc, 22, 25);
    assertSeparator(doc, 26);

    // PC=PC+1;fetch;goto (MBR)\n
    assertRegister(doc, 27, 28);
    assertOperator(doc, 29);
    assertRegister(doc, 30, 31);
    assertOperator(doc, 32);
    assertNumber(doc, 33);
    assertSeparator(doc, 34);
    assertLine(doc, 35, 39);
    assertSeparator(doc, 40);
    assertKeyWord(doc, 41, 44);
    assertSeparator(doc, 45, 46);
    assertRegister(doc, 47, 49);
    assertSeparator(doc, 50, 51);

    // H=TOS;goto 0x4\n
    assertRegister(doc, 52, 52);
    assertOperator(doc, 53);
    assertRegister(doc, 54, 56);
    assertSeparator(doc, 57);
    assertKeyWord(doc, 58, 61);
    assertSeparator(doc, 62);
    assertNumber(doc, 63, 65);
    assertSeparator(doc, 66);
  }

  @Test
  public void testSyntaxHighlightning_MultipleLines_NoLF() throws InterruptedException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().setText("H=H OR MBRU;goto 0x23\n" + "MAR=H+LV;rd;goto 0x19\n" + "H=MBRU<<8;goto 0x25");

    final Document document = frame.textBox().component().getDocument();
    assertThat(document).isInstanceOf(StyledDocument.class);

    final StyledDocument doc = (StyledDocument) document;

    assertThat(doc.getLength()).isEqualTo(63);

    // H=H OR MBRU;goto 0x23\n
    assertRegister(doc, 0, 0);
    assertOperator(doc, 1);
    assertRegister(doc, 2, 2);
    assertSeparator(doc, 3);
    assertOperator(doc, 4, 5);
    assertSeparator(doc, 6);
    assertRegister(doc, 7, 10);
    assertSeparator(doc, 11);
    assertKeyWord(doc, 12, 15);
    assertSeparator(doc, 16);
    assertNumber(doc, 17, 20);
    assertSeparator(doc, 21);

    // MAR=H+LV;rd;goto 0x19\n
    assertRegister(doc, 22, 24);
    assertOperator(doc, 25);
    assertRegister(doc, 26, 26);
    assertOperator(doc, 27);
    assertRegister(doc, 28, 29);
    assertSeparator(doc, 30);
    assertLine(doc, 31, 32);
    assertSeparator(doc, 33);
    assertKeyWord(doc, 34, 37);
    assertSeparator(doc, 38);
    assertNumber(doc, 39, 42);
    assertSeparator(doc, 43);

    // H=MBRU<<8;goto 0x25
    assertRegister(doc, 44, 44);
    assertOperator(doc, 45);
    assertRegister(doc, 46, 49);
    assertOperator(doc, 50, 51);
    assertNumber(doc, 52);
    assertSeparator(doc, 53);
    assertKeyWord(doc, 54, 57);
    assertSeparator(doc, 58);
    assertNumber(doc, 59, 62);
  }

  @Test
  public void testSyntaxHighlightning_SingleLine_NoLF() throws InterruptedException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().setText("Z=OPC;if (Z) goto 0x101; else goto 0x1");

    final Document document = frame.textBox().component().getDocument();
    assertThat(document).isInstanceOf(StyledDocument.class);

    final StyledDocument doc = (StyledDocument) document;

    assertThat(doc.getLength()).isEqualTo(38);

    // Z=OPC;if (Z) goto 0x101; else goto 0x1
    assertLine(doc, 0, 0);
    assertOperator(doc, 1);
    assertRegister(doc, 2, 4);
    assertSeparator(doc, 5);
    assertKeyWord(doc, 6, 7);
    assertSeparator(doc, 8, 9);
    assertLine(doc, 10, 10);
    assertSeparator(doc, 11, 12);
    assertKeyWord(doc, 13, 16);
    assertSeparator(doc, 17);
    assertNumber(doc, 18, 22);
    assertSeparator(doc, 23, 24);
    assertKeyWord(doc, 25, 28);
    assertSeparator(doc, 29);
    assertKeyWord(doc, 30, 33);
    assertSeparator(doc, 34);
    assertNumber(doc, 35, 37);
  }

  @Test
  public void testSyntaxHighlightning_SingleLine_LF() throws InterruptedException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().setText("SP=MAR=SP-1;rd;goto 0x8B\n");

    final Document document = frame.textBox().component().getDocument();
    assertThat(document).isInstanceOf(StyledDocument.class);

    final StyledDocument doc = (StyledDocument) document;

    assertThat(doc.getLength()).isEqualTo(25);

    // SP=MAR=SP-1;rd;goto 0x8B\n
    assertRegister(doc, 0, 1);
    assertOperator(doc, 2);
    assertRegister(doc, 3, 5);
    assertOperator(doc, 6);
    assertRegister(doc, 7, 8);
    assertOperator(doc, 9);
    assertNumber(doc, 10);
    assertSeparator(doc, 11);
    assertLine(doc, 12, 13);
    assertSeparator(doc, 14);
    assertKeyWord(doc, 15, 18);
    assertSeparator(doc, 19);
    assertNumber(doc, 20, 23);
    assertSeparator(doc, 24);
  }

  @Test
  public void testSyntaxHighlightning_SingleLine_CRLF() throws InterruptedException, BadLocationException {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().setText("PC=PC+1;fetch;goto (MBR OR 0x100)\r\n");
    frame.textBox().requireText("PC=PC+1;fetch;goto (MBR OR 0x100)\r\n");

    final Document document = frame.textBox().component().getDocument();
    assertThat(document).isInstanceOf(StyledDocument.class);

    final StyledDocument doc = (StyledDocument) document;

    assertThat(doc.getLength()).isEqualTo(34);

    // PC=PC+1;fetch;goto (MBR OR 0x100)\r\n
    assertRegister(doc, 0, 1);
    assertOperator(doc, 2);
    assertRegister(doc, 3, 4);
    assertOperator(doc, 5);
    assertNumber(doc, 6);
    assertSeparator(doc, 7);
    assertLine(doc, 8, 12);
    assertSeparator(doc, 13);
    assertKeyWord(doc, 14, 17);
    assertSeparator(doc, 18, 19);
    assertRegister(doc, 20, 22);
    assertSeparator(doc, 23);
    assertOperator(doc, 24, 25);
    assertSeparator(doc, 26);
    assertNumber(doc, 27, 31);
    assertSeparator(doc, 32, 33);
  }

  private void assertKeyWord(final StyledDocument doc, final int from, final int to) {
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

  private void assertRegister(final StyledDocument doc, final int from, final int to) {
    for (int i = from; i <= to; ++i) {
      assertAttributes(doc, i, false, true, new Color(34, 34, 139));
    }
  }

  private void assertOperator(final StyledDocument doc, final int pos) {
    assertOperator(doc, pos, pos);
  }

  private void assertOperator(final StyledDocument doc, final int from, final int to) {
    for (int i = from; i <= to; ++i) {
      assertAttributes(doc, i, false, false, new Color(34, 139, 34));
    }
  }

  private void assertLine(final StyledDocument doc, final int from, final int to) {
    for (int i = from; i <= to; ++i) {
      assertAttributes(doc, i, false, true, new Color(220, 139, 0));
    }
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
