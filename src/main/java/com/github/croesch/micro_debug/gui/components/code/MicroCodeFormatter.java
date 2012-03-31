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
 * TODO Comment here ...
 * 
 * @author croesch
 * @since Date: Mar 30, 2012
 */
public final class MicroCodeFormatter extends ACodeFormatter {

  private static final List<String> LINES = Arrays.asList("Z", "N", "rd", "wr", "fetch");

  private static final List<String> KEYWORDS = Arrays.asList("if", "else", "goto", "nop");

  private static final List<String> OPERATORS = Arrays.asList("AND", "OR", "NOT");

  private static final List<Character> SEP_OPS = Arrays.asList('+', '-', '=', '>', '<');

  private static final List<Character> SEPS = Arrays.asList(';', '(', ')', ' ', '\t', '\n');

  private final MutableAttributeSet registerFormat = new SimpleAttributeSet();

  private final MutableAttributeSet linesFormat = new SimpleAttributeSet();

  private final MutableAttributeSet keywordsFormat = new SimpleAttributeSet();

  private final MutableAttributeSet operatorsFormat = new SimpleAttributeSet();

  private final MutableAttributeSet separatorsFormat = new SimpleAttributeSet();

  private final MutableAttributeSet numberFormat = new SimpleAttributeSet();

  private final MutableAttributeSet normalFormat = new SimpleAttributeSet();

  public MicroCodeFormatter() {
    StyleConstants.setBold(this.registerFormat, true);
    StyleConstants.setForeground(this.registerFormat, new Color(34, 34, 139));

    StyleConstants.setBold(this.linesFormat, true);
    StyleConstants.setForeground(this.linesFormat, new Color(220, 139, 0));

    StyleConstants.setBold(this.keywordsFormat, false);
    StyleConstants.setForeground(this.keywordsFormat, Color.DARK_GRAY);

    StyleConstants.setBold(this.operatorsFormat, false);
    StyleConstants.setForeground(this.operatorsFormat, new Color(34, 139, 34));

    StyleConstants.setBold(this.numberFormat, true);
    StyleConstants.setForeground(this.numberFormat, new Color(160, 34, 34));

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

      while (end < text.length()) {

        begin = end;

        while (end < text.length() && !SEPS.contains(text.charAt(end)) && !SEP_OPS.contains(text.charAt(end))) {
          ++end;
        }

        final String token = text.substring(begin, end);

        formatToken(doc, begin, token);
        if (end < text.length()) {
          formatSeparator(doc, end, text.charAt(end));
        }

        ++end;
      }

    }
  }

  private String getText(final StyledDocument doc) {
    try {
      return doc.getText(0, doc.getLength());
    } catch (final BadLocationException e) {
      Utils.logThrownThrowable(e);
    }
    return null;
  }

  private void formatSeparator(final StyledDocument doc, final int index, final char sep) {
    if (SEP_OPS.contains(Character.valueOf(sep))) {
      doc.setCharacterAttributes(index, 1, this.operatorsFormat, true);
    } else {
      doc.setCharacterAttributes(index, 1, this.separatorsFormat, true);
    }
  }

  private void formatToken(final StyledDocument doc, final int index, final String token) {
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
    doc.setCharacterAttributes(index, token.length(), format, true);
  }

  private boolean isRegister(final String token) {
    for (final Register r : Register.values()) {
      if (token.equals(r.name())) {
        return true;
      }
    }
    return false;
  }

}
