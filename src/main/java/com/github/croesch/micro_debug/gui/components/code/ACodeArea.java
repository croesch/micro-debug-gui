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

import java.awt.Font;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.gui.components.basic.MDTextPane;

/**
 * A code area to visualize a piece of code.
 * 
 * @author croesch
 * @since Date: Mar 30, 2012
 */
public abstract class ACodeArea extends MDTextPane {

  /** generated serial version UID */
  private static final long serialVersionUID = 2012704080901679378L;

  /** the object that identifies the highlight marker, <code>null</code> if no highlight currently exists */
  private Object highlightMarker = null;

  /**
   * Constructs the code area with a styled document and adds the given formatter to format the text of the area.
   * 
   * @since Date: Mar 30, 2012
   * @param name the name for the text component to set
   * @param formatter the component responsible for formatting the text of the code area.
   */
  public ACodeArea(final String name, final ACodeFormatter formatter) {
    super(name, new DefaultStyledDocument());

    /* remove top and bottom margins, for better experience with Ruler and LineNumberLabel */
    getMargin().top = 0;
    getMargin().bottom = 0;

    getDocument().addDocumentListener(formatter);
    setEditable(false);
    setFont(new Font("Monospaced", getFont().getStyle(), getFont().getSize()));
  }

  /**
   * Highlights the line with the given number. If called multiple times, the highlight will be moved. Call with an
   * invalid line number <em>to remove</em> the highlight.
   * 
   * @since Date: Apr 3, 2012
   * @param line the number of the line to highlight, zero-based,<br>
   *        if less than zero or not enough lines are shown in the text pane, then nothing will be highlighted and if a
   *        highlight exists this will be removed.
   */
  public final void highlight(final int line) {
    try {

      if (line < 0 || line >= getLineCount()) {
        // don't add highlight and remove old highlight
        if (this.highlightMarker != null) {
          getHighlighter().removeHighlight(this.highlightMarker);
          this.highlightMarker = null;
        }

      } else if (this.highlightMarker == null) {
        // adding new highlight
        this.highlightMarker = getHighlighter().addHighlight(getLineStartOffset(line), 0, new LineHighlighter());
      } else {
        // moving existing highlight
        getHighlighter().changeHighlight(this.highlightMarker, getLineStartOffset(line), 0);
      }
      // do a repaint, ensure that old highlight is removed
      repaint();

    } catch (final BadLocationException e) {
      // should not happen, but anyway log it - just in case
      Utils.logThrownThrowable(e);
    }
  }
}
