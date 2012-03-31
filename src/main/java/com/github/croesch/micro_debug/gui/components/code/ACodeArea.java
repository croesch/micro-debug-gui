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

import javax.swing.text.DefaultStyledDocument;

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

  /**
   * Constructs the code area with a styled document and adds the given formatter to format the text of the area.
   * 
   * @since Date: Mar 30, 2012
   * @param name the name for the text component to set
   * @param formatter the component responsible for formatting the text of the code area.
   */
  public ACodeArea(final String name, final ACodeFormatter formatter) {
    super(name, new DefaultStyledDocument());
    getDocument().addDocumentListener(formatter);
    setEditable(false);
    setFont(new Font("Monospaced", getFont().getStyle(), getFont().getSize()));
  }
}
