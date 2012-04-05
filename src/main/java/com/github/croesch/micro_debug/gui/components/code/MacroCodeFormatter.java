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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.github.croesch.micro_debug.mic1.mem.IJVMCommand;
import com.github.croesch.micro_debug.mic1.mem.IJVMConfigReader;

/**
 * Formats macro code.
 * 
 * @author croesch
 * @since Date: Apr 4, 2012
 */
final class MacroCodeFormatter extends ACodeFormatter {

  /** the token that are identified as separators */
  private static final List<Character> SEPS = Arrays.asList(' ', '\t', '\n', '\r');

  /** the token that are identified as commands */
  private final List<String> commands;

  /** the attributes to format command tokens */
  private final MutableAttributeSet commandFormat = new SimpleAttributeSet();

  /** the attributes to format addresses */
  private final MutableAttributeSet addressFormat = new SimpleAttributeSet();

  /** the attributes to format brackets */
  private final MutableAttributeSet bracketsFormat = new SimpleAttributeSet();

  /** the attributes to format separators */
  private final MutableAttributeSet separatorsFormat = new SimpleAttributeSet();

  /** the attributes to format numbers */
  private final MutableAttributeSet numberFormat = new SimpleAttributeSet();

  /**
   * Constructs a formatter for macro code.
   * 
   * @since Date: Apr 4, 2012
   */
  public MacroCodeFormatter() {
    // intensity of RGB - values
    final int low = 34;
    final int medium = 139;
    final int high = 160;

    StyleConstants.setBold(this.commandFormat, true);
    StyleConstants.setForeground(this.commandFormat, new Color(low, low, medium));

    StyleConstants.setBold(this.addressFormat, false);
    StyleConstants.setForeground(this.addressFormat, Color.DARK_GRAY);

    StyleConstants.setBold(this.bracketsFormat, false);
    StyleConstants.setForeground(this.bracketsFormat, new Color(low, medium, low));

    StyleConstants.setBold(this.numberFormat, true);
    StyleConstants.setForeground(this.numberFormat, new Color(high, low, low));

    StyleConstants.setBold(this.separatorsFormat, false);
    StyleConstants.setForeground(this.separatorsFormat, Color.GRAY);

    final InputStream in = getClass().getClassLoader().getResourceAsStream("ijvm.conf");
    final Map<Integer, IJVMCommand> cmds = new IJVMConfigReader().readConfig(in);
    this.commands = new ArrayList<String>();
    for (final IJVMCommand cmd : cmds.values()) {
      this.commands.add(cmd.getName());
    }
  }

  @Override
  protected void formatSeparator(final StyledDocument doc, final int index, final char sep) {
    doc.setCharacterAttributes(index, 1, this.separatorsFormat, true);
  }

  @Override
  protected void formatToken(final StyledDocument doc, final int index, final String token) {
    if (token.matches(".*\\[=.+\\]")) {
      final int bracketIndex = token.indexOf("[=");
      if (bracketIndex > 0) {
        format(doc, index, token.substring(0, bracketIndex));
      }
      doc.setCharacterAttributes(index + bracketIndex, 2, this.bracketsFormat, true);
      format(doc, index + bracketIndex + 2, token.substring(2 + bracketIndex, token.length() - 1));
      doc.setCharacterAttributes(index + token.length() - 1, 1, this.bracketsFormat, true);
    } else if (token.startsWith("[") && token.endsWith("]")) {
      doc.setCharacterAttributes(index, 1, this.bracketsFormat, true);
      doc.setCharacterAttributes(index + 1, token.length() - 2, this.addressFormat, true);
      doc.setCharacterAttributes(index + token.length() - 1, 1, this.bracketsFormat, true);
    } else if (token.endsWith("]")) {
      doc.setCharacterAttributes(index, token.length() - 1, this.addressFormat, true);
      doc.setCharacterAttributes(index + token.length() - 1, 1, this.bracketsFormat, true);
    } else if (token.startsWith("[")) {
      doc.setCharacterAttributes(index, 1, this.bracketsFormat, true);
      doc.setCharacterAttributes(index + 1, token.length() - 1, this.addressFormat, true);
    } else {
      format(doc, index, token);
    }
  }

  /**
   * Formats the given token that does neither contain brackets, nor separators.
   * 
   * @since Date: Apr 6, 2012
   * @param doc the document that holds the token and should receive style information about the token.
   * @param index the index, where the token starts in the document.
   * @param token the token itself
   */
  private void format(final StyledDocument doc, final int index, final String token) {
    // the attributes defining how to format this token
    MutableAttributeSet format;

    if (this.commands.contains(token)) {
      format = this.commandFormat;
    } else if (token.matches("([0-9]+)|(0x[0-9A-F]+)")) {
      format = this.numberFormat;
    } else {
      format = getInvalidFormat();
    }

    // inform the document about how to format the given token
    doc.setCharacterAttributes(index, token.length(), format, true);
  }

  @Override
  protected boolean isSeparator(final char c) {
    return SEPS.contains(c);
  }
}
