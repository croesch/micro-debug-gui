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

import java.awt.Dimension;
import java.awt.Graphics;

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

  /** generated serial version UID */
  private static final long serialVersionUID = -5234458420083482975L;

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

    this.textArea = ta;
    this.textArea.getDocument().addDocumentListener(this);

    manageSize();

    repaint();
  }

  /**
   * Sets the size of this component, since we choose custom painting, we have to control the minimum and preferred size
   * ourselves.
   * 
   * @since Date: Jul 14, 2012
   */
  private void manageSize() {
    final String highestLN = Utils.toHexString(this.lineNumberMapper.getLineForNumber(this.textArea.getLineCount() - 1));

    final StringBuilder sb = new StringBuilder(highestLN.length());
    sb.append("0x");
    for (int i = 0; i < highestLN.length() - 2; ++i) {
      sb.append('A');
    }

    setMinimumSize(new Dimension(getFontMetrics(getFont()).stringWidth(sb.toString()),
                                 this.textArea.getMinimumSize().height));

    // preferred - add some extra space
    sb.append('.');

    setPreferredSize(new Dimension(getFontMetrics(getFont()).stringWidth(sb.toString()),
                                   this.textArea.getPreferredSize().height));
  }

  @Override
  protected final void paintComponent(final Graphics g) {
    final float fontHeight = g.getFontMetrics().getLineMetrics("0", null).getHeight();
    final float lineOffset = (this.textArea.getLineHeight() - fontHeight);

    for (int line = 0; line < this.textArea.getLineCount(); ++line) {
      final int x = 0;
      final int y = this.textArea.getLineHeight() * (line + 1);
      final int width = getWidth();

      if (line == this.highlightedLine) {
        // insert color information for highlighted line
        g.setColor(UIManager.getColor("Label.background").darker());
        g.fillRect(x, y, width, this.textArea.getLineHeight());
      }

      g.setColor(UIManager.getColor("Label.foreground"));
      final String lineNumber = Utils.toHexString(this.lineNumberMapper.getLineForNumber(line));
      g.drawString(lineNumber, x, (int) (y - lineOffset) - 1);
    }
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
    repaint();
  }

  /** {@inheritDoc} */
  public final void insertUpdate(final DocumentEvent e) {
    repaint();
  }

  /** {@inheritDoc} */
  public final void removeUpdate(final DocumentEvent e) {
    repaint();
  }

  /** {@inheritDoc} */
  public final void changedUpdate(final DocumentEvent e) {
    // nothing to do
  }
}
