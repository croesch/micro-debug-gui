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
package com.github.croesch.micro_debug.gui.components.basic;

import javax.swing.text.StyledDocument;

import com.github.croesch.components.CTextPane;

/**
 * An extension of {@link CTextPane} that contains some default behavior that not each client should have to implement.
 * 
 * @author croesch
 * @since Date: Mar 31, 2012
 */
public class MDTextPane extends CTextPane {

  /** generated serial version UID */
  private static final long serialVersionUID = -1439369119660645723L;

  /**
   * Constructs the text pane and sets the given name.
   * 
   * @since Date: Mar 31, 2012
   * @param name the name of the component to set.
   */
  public MDTextPane(final String name) {
    super(name);
  }

  /**
   * Constructs the text pane and sets the given name.
   * 
   * @since Date: Mar 31, 2012
   * @param name the name of the component to set.
   * @param doc the document model
   */
  public MDTextPane(final String name, final StyledDocument doc) {
    super(name, doc);
  }
}
