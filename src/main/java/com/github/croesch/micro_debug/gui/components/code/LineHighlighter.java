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
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.JTextComponent;

import com.github.croesch.micro_debug.commons.Utils;

/**
 * Highlights a whole line in a text component.
 * 
 * @author croesch
 * @since Date: Mar 31, 2012
 */
public final class LineHighlighter implements HighlightPainter {

  /** {@inheritDoc} */
  public void paint(final Graphics g, final int p0, final int p1, final Shape bounds, final JTextComponent c) {
    try {
      // check if the offset to highlight is in the range of the document
      if (p0 >= 0 && p0 <= c.getDocument().getLength()) {

        final Rectangle r = c.modelToView(p0);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, r.y, c.getWidth(), r.height);

      }
    } catch (final BadLocationException e) {
      Utils.logThrownThrowable(e);
    }
  }
}
