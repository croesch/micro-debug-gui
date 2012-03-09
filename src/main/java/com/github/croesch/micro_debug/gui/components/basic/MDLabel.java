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

import javax.swing.JLabel;

import com.github.croesch.micro_debug.commons.Utils;

/**
 * An extension of {@link JLabel} that contains some default behavior that not each client should have to implement.
 * 
 * @author croesch
 * @since Date: Mar 3, 2012
 */
public class MDLabel extends JLabel {

  /** generated serial version UID */
  private static final long serialVersionUID = -3518553239851224436L;

  /**
   * Constructs the label with the given name and the given text.
   * 
   * @since Date: Mar 3, 2012
   * @param name the name of the component to set via {@link #setName(String)}
   * @param text the {@link Object} thats {@link String} representation will be set as the text of the label
   * @see #setName(String)
   */
  public MDLabel(final String name, final Object text) {
    super(Utils.toString(text));
    setName(name);
  }
}
