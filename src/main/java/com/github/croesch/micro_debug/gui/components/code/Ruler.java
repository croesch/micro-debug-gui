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
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.api.ILineBreakPointManager;
import com.github.croesch.micro_debug.gui.components.basic.ComponentRepaintListener;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.debug.LineNumberMapper;

/**
 * This is a ruler that is able to view some information (such as breakpoints) for a given text component.
 * 
 * @author croesch
 * @since Date: Mar 20, 2012
 */
public class Ruler extends MDPanel {

  /** generated serial version UID */
  private static final long serialVersionUID = 237974555720522800L;

  /** the width of the ruler */
  private static final int WIDTH = 10;

  /** the size of a single marker in the ruler */
  private static final int MARKER_SIZE = 6;

  /** the space the marker is away from the border of the ruler */
  private static final int SPACE = (WIDTH - MARKER_SIZE) / 2;

  /** the size of the shadow of the marker */
  private static final int SMALL_MARKER_SIZE = 2;

  /** the text component this ruler shows information for */
  @NotNull
  private final ACodeArea textComponent;

  /** the manager for breakpoints */
  @NotNull
  private final transient ILineBreakPointManager lineBreakPointManager;

  /** the abstraction layer that maps real line numbers to line numbers for the user */
  @NotNull
  private final transient LineNumberMapper lineNumberMapper;

  /**
   * Constructs the ruler for the given text component that uses the given breakpoint manager to handle breakpoints.
   * 
   * @since Date: Mar 21, 2012
   * @param name the name of this {@link Ruler}
   * @param tc the text component to fetch information from, such as line numbers, line height, etc.
   * @param bpm the manager for breakpoints that stores them and provides information, if a line contains a breakpoint
   * @param mapper instance of a mapper for line numbers that maps real internal line numbers to the representation for
   *        the user
   */
  public Ruler(final String name, final ACodeArea tc, final ILineBreakPointManager bpm, final LineNumberMapper mapper) {
    super(name);

    if (tc == null || bpm == null || mapper == null) {
      throw new IllegalArgumentException();
    }
    this.textComponent = tc;
    this.lineBreakPointManager = bpm;
    this.lineNumberMapper = mapper;

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(final MouseEvent evt) {
        if (isEventValid(evt)) {
          final int offset = Ruler.this.textComponent.viewToModel(new Point(0, evt.getY()));
          final int line = Ruler.this.textComponent.getLineOfOffset(offset);
          toggleBreakpoint(Ruler.this.lineNumberMapper.getLineForNumber(line));
        }
      }

      private boolean isEventValid(final MouseEvent evt) {
        return evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() % 2 == 0;
      }
    });

    this.textComponent.getDocument().addDocumentListener(new ComponentRepaintListener(this));
  }

  /**
   * Sets the preferred height for the ruler.
   * 
   * @since Date: Mar 21, 2012
   * @param ph the new preferred height for the ruler.
   */
  public final void setPreferredHeight(final int ph) {
    setPreferredSize(new Dimension(WIDTH, ph));
  }

  @Override
  public final void paint(final Graphics g) {
    super.paint(g);
    setPreferredHeight(this.textComponent.getHeight());

    final int lineHeight = this.textComponent.getLineHeight();

    for (int line = 0; line < this.textComponent.getLineCount(); line += 1) {
      if (this.lineBreakPointManager.isBreakpoint(this.lineNumberMapper.getLineForNumber(line))) {
        g.setColor(getForeground());
        g.fillOval(SPACE, line * lineHeight + (lineHeight - MARKER_SIZE) / 2, MARKER_SIZE, MARKER_SIZE);
        g.setColor(getForeground().brighter());
        g.fillOval(SPACE, line * lineHeight + (lineHeight - MARKER_SIZE) / 2, MARKER_SIZE, SMALL_MARKER_SIZE);
        g.setColor(getBackground().brighter());
        g.drawOval(SPACE - 1, line * lineHeight + (lineHeight - MARKER_SIZE) / 2 - 1, MARKER_SIZE + 2, MARKER_SIZE + 2);
        g.setColor(getForeground().darker());
        g.drawOval(SPACE, line * lineHeight + (lineHeight - MARKER_SIZE) / 2, MARKER_SIZE, MARKER_SIZE);
      }
    }
  }

  /**
   * Toggles the breakpoint for the given line: Switches it <code>on</code>, if it was <code>off</code>; switches it
   * <code>off</code>, if it was <code>on</code>.
   * 
   * @since Date: Mar 21, 2012
   * @param line the number of the line to toggle the breakpoint
   */
  final void toggleBreakpoint(final int line) {
    this.lineBreakPointManager.toggleBreakpoint(line);
    repaint();
  }

  /**
   * Returns whether there is currently a breakpoint set for the given line.
   * 
   * @since Date: Mar 21, 2012
   * @param line the number of the line that should be checked for a breakpoint.
   * @return <code>true</code> whether there is currently a breakpoint in the given line,<br>
   *         <code>false</code> otherwise.
   */
  final boolean isBreakpoint(final int line) {
    return this.lineBreakPointManager.isBreakpoint(line);
  }
}
