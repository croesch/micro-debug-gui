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
import javax.swing.Icon;

import com.github.croesch.components.CMenuItem;

/**
 * An extension of {@link CMenuItem} that contains some default behavior that not each client should have to implement.
 * 
 * @author croesch
 * @since Date: May 14, 2012
 */
public class MDMenuItem extends CMenuItem {

  /** generated serial version UID */
  private static final long serialVersionUID = -6056965227011873471L;

  /**
   * Creates a new {@link MDMenuItem} with the given name, but without text and icon.
   * 
   * @since Date: May 14, 2012
   * @param name the name of this {@link MDMenu}.
   */
  public MDMenuItem(final String name) {
    super(name, (String) null);
  }

  /**
   * Creates a new {@link MDMenuItem} with the given name and the given icon.
   * 
   * @since Date: May 14, 2012
   * @param name the name of this {@link MDMenu}.
   * @param icon the icon for this menu item
   */
  public MDMenuItem(final String name, final Icon icon) {
    super(name, null, icon);
  }

  /**
   * Creates a new {@link MDMenuItem} with the given name and the given text.
   * 
   * @since Date: May 14, 2012
   * @param name the name of this {@link MDMenu}.
   * @param text the text of the menu item
   */
  public MDMenuItem(final String name, final String text) {
    super(name, text);
  }

  /**
   * Creates a new {@link MDMenuItem} with the given name and other properties're fetched from the given {@link Action}.
   * 
   * @since Date: May 14, 2012
   * @param name the name of this {@link MDMenu}.
   * @param a the {@link Action} to fetch properties from
   */
  public MDMenuItem(final String name, final Action a) {
    super(name, a);
  }

  /**
   * Creates a new {@link MDMenuItem} with the given name, text and icon.
   * 
   * @since Date: May 14, 2012
   * @param name the name of this {@link MDMenu}.
   * @param text the text for this menu item
   * @param icon the icon for this menu item
   */
  public MDMenuItem(final String name, final String text, final Icon icon) {
    super(name, text, icon);
  }

  /**
   * Creates a new {@link MDMenuItem} with the given name, text and mnemonic.
   * 
   * @since Date: May 14, 2012
   * @param name the name of this {@link MDMenu}.
   * @param text the text for this menu item
   * @param mnemonic the mnemonic for this menu item
   */
  public MDMenuItem(final String name, final String text, final int mnemonic) {
    super(name, text, mnemonic);
  }
}
