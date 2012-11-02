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

import javax.swing.Action;

import com.github.croesch.components.CMenu;

/**
 * An extension of {@link CMenu} that contains some default behavior that not each client should have to implement.
 * 
 * @author croesch
 * @since Date: May 14, 2012
 */
public class MDMenu extends CMenu {

  /** generated serial version UID */
  private static final long serialVersionUID = 2725555176853511507L;

  /**
   * Constructs a new {@link MDMenu} with the given name and without text.
   * 
   * @since Date: May 14, 2012
   * @param name the name of this {@link MDMenu}.
   */
  public MDMenu(final String name) {
    super(name, (String) null);
  }

  /**
   * Constructs a new {@link MDMenu} with the given name and the given text.
   * 
   * @since Date: May 14, 2012
   * @param name the name of this {@link MDMenu}.
   * @param text the text for the menu label
   */
  public MDMenu(final String name, final String text) {
    super(name, text);
  }

  /**
   * Constructs a new {@link MDMenu} with the given name and other properties fetched from the given {@link Action}.
   * 
   * @since Date: May 14, 2012
   * @param name the name of this {@link MDMenu}.
   * @param a the {@link Action} that provides some properties for this menu.
   */
  public MDMenu(final String name, final Action a) {
    super(name, a);
  }

  /**
   * Constructs a new {@link MDMenu} with the given name and the given text.
   * 
   * @since Date: May 14, 2012
   * @param name the name of this {@link MDMenu}.
   * @param text the text for the menu label
   * @param b <code>true</code>, if the menu can be torn off
   */
  public MDMenu(final String name, final String text, final boolean b) {
    super(name, text, b);
  }
}
