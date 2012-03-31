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

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.mic1.register.Register;

/**
 * Formats micro code.
 * 
 * @author croesch
 * @since Date: Mar 30, 2012
 */
public final class MicroCodeFormatter extends ACodeFormatter {

  /** the token that are identified as control lines */
  private static final List<String> LINES = Arrays.asList("Z", "N", "rd", "wr", "fetch");

  /** the token that are identified as keywords */
  private static final List<String> KEYWORDS = Arrays.asList("if", "else", "goto", "nop");

  /** the token that are identified as operators */
  private static final List<String> OPERATORS = Arrays.asList("AND", "OR", "NOT");

  /** the token that are identified as operators and don't need a separator around them */
  private static final List<Character> SEP_OPS = Arrays.asList('+', '-', '=', '>', '<');

  /** the token that are identified as separators */
  private static final List<Character> SEPS = Arrays.asList(';', '(', ')', ' ', '\t', '\n');

  /** the attributes to format register tokens */
  private final MutableAttributeSet registerFormat = new SimpleAttributeSet();

  /** the attributes to format control line tokens */
  private final MutableAttributeSet linesFormat = new SimpleAttributeSet();

  /** the attributes to format keywords */
  private final MutableAttributeSet keywordsFormat = new SimpleAttributeSet();

  /** the attributes to format operators */
  private final MutableAttributeSet operatorsFormat = new SimpleAttributeSet();

  /** the attributes to format separators */
  private final MutableAttributeSet separatorsFormat = new SimpleAttributeSet();

  /** the attributes to format numbers */
  private final MutableAttributeSet numberFormat = new SimpleAttributeSet();

  /** the attributes to format all text that isn't identified to be something special */
  private final MutableAttributeSet normalFormat = new SimpleAttributeSet();

  /**
   * Constructs a formatter for micro code.
   * 
   * @since Date: Mar 31, 2012
   */
  public MicroCodeFormatter() {
    // intensity of RGB - values
    final int low = 34;
    final int medium = 139;
    final int high = 160;
    final int higher = 220;

    StyleConstants.setBold(this.registerFormat, true);
    StyleConstants.setForeground(this.registerFormat, new Color(low, low, medium));

    StyleConstants.setBold(this.linesFormat, true);
    StyleConstants.setForeground(this.linesFormat, new Color(higher, medium, 0));

    StyleConstants.setBold(this.keywordsFormat, false);
    StyleConstants.setForeground(this.keywordsFormat, Color.DARK_GRAY);

    StyleConstants.setBold(this.operatorsFormat, false);
    StyleConstants.setForeground(this.operatorsFormat, new Color(low, medium, low));

    StyleConstants.setBold(this.numberFormat, true);
    StyleConstants.setForeground(this.numberFormat, new Color(high, low, low));

    StyleConstants.setBold(this.separatorsFormat, false);
    StyleConstants.setForeground(this.separatorsFormat, Color.GRAY);

    StyleConstants.setBold(this.normalFormat, false);
    StyleConstants.setForeground(this.normalFormat, Color.RED);
  }

  @Override
  protected void format(final StyledDocument doc) {

    final String text = getText(doc);

    if (text != null) {

      int begin = 0;
      int end = 0;

      // iterate over the whole text, shifting the to indexes
      while (end < text.length()) {

        // search for the next separating character (or the EOF)
        while (end < text.length() && !SEPS.contains(text.charAt(end)) && !SEP_OPS.contains(text.charAt(end))) {
          ++end;
        }

        final String token = text.substring(begin, end);

        formatToken(doc, begin, token);

        if (end < text.length()) {
          formatSeparator(doc, end, text.charAt(end));
        }

        // shift the indexes to the position after the separating character already formatted
        begin = ++end;
      }

    }
  }

  /**
   * Returns the text of the given document. Theoretically this could return <code>null</code> if the document throws a
   * {@link BadLocationException}, but this should never happen!
   * 
   * @since Date: Mar 31, 2012
   * @param doc the document to read the text from.
   * @return the whole text of the document.
   */
  private String getText(final StyledDocument doc) {
    try {
      return doc.getText(0, doc.getLength());
    } catch (final BadLocationException e) {
      Utils.logThrownThrowable(e);
    }
    return null;
  }

  /**
   * Performs to add formatting information to the given document about the given separator at the given index.
   * 
   * @since Date: Mar 31, 2012
   * @param doc the document that holds the given separator at the given position
   * @param index the index, where the separator is placed in the document
   * @param sep the separating character itself.
   */
  private void formatSeparator(final StyledDocument doc, final int index, final char sep) {
    if (SEP_OPS.contains(Character.valueOf(sep))) {
      // we have just an operator that works as a separator
      doc.setCharacterAttributes(index, 1, this.operatorsFormat, true);
    } else {
      // we have a real separating character
      doc.setCharacterAttributes(index, 1, this.separatorsFormat, true);
    }
  }

  /**
   * Formats the given token that starts at the given index in the given document.
   * 
   * @since Date: Mar 31, 2012
   * @param doc the document that holds the token and should receive style information about the token.
   * @param index the index, where the token starts in the document.
   * @param token the token itself
   */
  private void formatToken(final StyledDocument doc, final int index, final String token) {
    // the attributes defining how to format this token
    MutableAttributeSet format;

    if (KEYWORDS.contains(token)) {
      format = this.keywordsFormat;
    } else if (LINES.contains(token)) {
      format = this.linesFormat;
    } else if (OPERATORS.contains(token)) {
      format = this.operatorsFormat;
    } else if (isRegister(token)) {
      format = this.registerFormat;
    } else if (token.matches("([0-9]+)|(0x[0-9A-F]+)")) {
      format = this.numberFormat;
    } else {
      format = this.normalFormat;
    }

    // inform the document about how to format the given token
    doc.setCharacterAttributes(index, token.length(), format, true);
  }

  /**
   * Returns whether the given token is representing a {@link Register}.
   * 
   * @since Date: Mar 31, 2012
   * @param token the token to check
   * @return <code>true</code> whether the given token represents a {@link Register},<br>
   *         or <code>false</code> otherwise
   */
  private boolean isRegister(final String token) {
    for (final Register r : Register.values()) {
      if (token.equals(r.name())) {
        return true;
      }
    }
    return false;
  }
}
