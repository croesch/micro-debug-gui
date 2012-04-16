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

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.gui.components.basic.MDLabel;
import com.github.croesch.micro_debug.gui.debug.LineNumberMapper;

/**
 * Vertical row view that shows line numbers for a given text component.
 * 
 * @author croesch
 * @since Date: Mar 19, 2012
 */
public class LineNumberLabel extends MDLabel implements DocumentListener {

  /** mask to select the bits for representing a html-code color */
  private static final int HTML_COLOR_CODE_MASK = 0xFFFFFF;

  /** generated serial version UID */
  private static final long serialVersionUID = -5234458420083482975L;

  /** the string that represents the HTML-Code for the color to highlight a specific line */
  @NotNull
  private final String highColor;

  /** the number of the line that is highlighted */
  private int highlightedLine = -1;

  /** the text component this component shows the line numbers for */
  @NotNull
  private final ACodeArea textArea;

  /** the abstraction layer that maps real line numbers to line numbers for the user */
  @NotNull
  private final transient LineNumberMapper lineNumberMapper;

  /**
   * Constructs this ruler that shows line numbers for the given component. Will update itself, when the underlying
   * document of the given text component changes.
   * 
   * @since Date: Mar 20, 2012
   * @param ta the text component to show line numbers for, must not be <code>null</code>.
   * @param mapper instance of a mapper for line numbers that maps real internal line numbers to the representation for
   *        the user
   */
  public LineNumberLabel(final ACodeArea ta, final LineNumberMapper mapper) {
    super(ta.getName() + "-line-numbers", null);
    setVerticalAlignment(SwingConstants.TOP);

    this.lineNumberMapper = mapper;
    this.highColor = "#"
                     + Integer.toHexString(UIManager.getColor("Label.background").darker().getRGB()
                                           & HTML_COLOR_CODE_MASK);

    this.textArea = ta;
    this.textArea.getDocument().addDocumentListener(this);
    update();
  }

  /**
   * Updates the line numbers.<br>
   * <i>Caution:</i> Do use carefully! Will rebuild the line numbers completely, which is not performant - especially
   * for high line count.
   * 
   * @since Date: Mar 20, 2012
   */
  private void update() {
    final StringBuilder sb = new StringBuilder("<html>");

    for (int line = 0; line < this.textArea.getLineCount(); ++line) {
      if (line == this.highlightedLine) {
        // insert color information for highlighted line
        sb.append("<font bgcolor='").append(this.highColor).append("'>");
      }

      sb.append(Utils.toHexString(this.lineNumberMapper.getLineForNumber(line)));

      if (line == this.highlightedLine) {
        // end color information for highlighted line
        sb.append("</font>");
      }
      sb.append("<br>");
    }

    setText(sb.toString());
  }

  /**
   * Changes the highlight to the given line. If the given line doesn't exist, nothing will be highlighted.
   * <i>Caution:</i> Do use carefully! Will rebuild the line numbers completely, which is not performant - especially
   * for high line count.
   * 
   * @since Date: Mar 20, 2012
   * @param line the number of the line to highlight
   */
  public final void highlight(final int line) {
    this.highlightedLine = line;
    update();
  }

  /** {@inheritDoc} */
  public final void insertUpdate(final DocumentEvent e) {
    update();
  }

  /** {@inheritDoc} */
  public final void removeUpdate(final DocumentEvent e) {
    update();
  }

  /** {@inheritDoc} */
  public final void changedUpdate(final DocumentEvent e) {
    // nothing to do
  }
}
