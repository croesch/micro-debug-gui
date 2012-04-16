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

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.annotation.Nullable;
import com.github.croesch.micro_debug.commons.Utils;

/**
 * Formats the text of the document that is being listened on each change of the underlying text.
 * 
 * @author croesch
 * @since Date: Mar 30, 2012
 */
abstract class ACodeFormatter implements DocumentListener {

  /** the attributes to format all text that isn't identified to be something special */
  @NotNull
  private final MutableAttributeSet invalidFormat = new SimpleAttributeSet();

  /**
   * Constructs a code formatter.
   * 
   * @since Date: Apr 6, 2012
   */
  ACodeFormatter() {
    StyleConstants.setBold(this.invalidFormat, false);
    StyleConstants.setForeground(this.invalidFormat, Color.RED);
  }

  /**
   * Returns the {@link MutableAttributeSet} to format invalid tokens with.
   * 
   * @since Date: Apr 6, 2012
   * @return the {@link MutableAttributeSet} to format invalid tokens with
   */
  @NotNull
  public final MutableAttributeSet getInvalidFormat() {
    return this.invalidFormat;
  }

  /**
   * Performs the update of the text style on changes of the text to format.
   * 
   * @since Date: Mar 30, 2012
   * @param document the document containing the text to format and where the change happened.
   */
  private void performUpdate(final Document document) {
    if (document instanceof StyledDocument) {
      final StyledDocument doc = (StyledDocument) document;

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          format(doc);
        }
      });
    }
  }

  /**
   * Formats the text of the given document.
   * 
   * @since Date: Mar 30, 2012
   * @param doc the document containing the text to format.
   */
  private void format(final StyledDocument doc) {

    final String text = getText(doc);

    if (text != null) {

      int begin = 0;
      int end = 0;

      // iterate over the whole text, shifting the to indexes
      while (end < text.length()) {

        // search for the next separating character (or the EOF)
        while (end < text.length() && !isSeparator(text.charAt(end))) {
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
   * Returns whether the given character is a separator.
   * 
   * @since Date: Apr 6, 2012
   * @param c the character to check
   * @return <code>true</code> if the given character is a separator,<br>
   *         <code>false</code> otherwise
   */
  protected abstract boolean isSeparator(char c);

  /**
   * Performs to add formatting information to the given document about the given separator at the given index.
   * 
   * @since Date: Mar 31, 2012
   * @param doc the document that holds the given separator at the given position
   * @param index the index, where the separator is placed in the document
   * @param sep the separating character itself.
   */
  protected abstract void formatSeparator(final StyledDocument doc, final int index, final char sep);

  /**
   * Formats the given token that starts at the given index in the given document.
   * 
   * @since Date: Mar 31, 2012
   * @param doc the document that holds the token and should receive style information about the token.
   * @param index the index, where the token starts in the document.
   * @param token the token itself
   */
  protected abstract void formatToken(final StyledDocument doc, final int index, final String token);

  /**
   * Returns the text of the given document. Theoretically this could return <code>null</code> if the document throws a
   * {@link BadLocationException}, but this should never happen!
   * 
   * @since Date: Mar 31, 2012
   * @param doc the document to read the text from.
   * @return the whole text of the document.
   */
  @Nullable
  private String getText(final StyledDocument doc) {
    try {
      return doc.getText(0, doc.getLength());
    } catch (final BadLocationException e) {
      Utils.logThrownThrowable(e);
    }
    return null;
  }

  /** {@inheritDoc} */
  public final void insertUpdate(final DocumentEvent e) {
    performUpdate(e.getDocument());
  }

  /** {@inheritDoc} */
  public final void removeUpdate(final DocumentEvent e) {
    performUpdate(e.getDocument());
  }

  /** {@inheritDoc} */
  public void changedUpdate(final DocumentEvent e) {
    /*
     * This method is called on attribute changes, so it wouldn't be a good idea to change the attributes within an
     * attribute change event. (Because of recursive calls)
     */
  }
}
