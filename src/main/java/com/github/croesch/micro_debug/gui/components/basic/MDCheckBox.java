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

import com.github.croesch.components.CCheckBox;

/**
 * An extension of {@link CCheckBox} that contains some default behavior that not each client should have to implement.
 * 
 * @author croesch
 * @since Date: Apr 9, 2012
 */
public class MDCheckBox extends CCheckBox {

  /** generated serial version UID */
  private static final long serialVersionUID = -4966283337378730078L;

  /**
   * Creates an unselected {@link MDCheckBox} button with the given name, no text and no icon.
   * 
   * @since Date: Apr 9, 2012
   * @param name the name of this {@link MDCheckBox}
   */
  public MDCheckBox(final String name) {
    super(name);
  }

  /**
   * Creates an unselected {@link MDCheckBox} button with the given name, the given icon but no text.
   * 
   * @since Date: Apr 9, 2012
   * @param name the name of this {@link MDCheckBox}
   * @param icon the image to display
   */
  public MDCheckBox(final String name, final Icon icon) {
    super(name, icon);
  }

  /**
   * Creates an unselected {@link MDCheckBox} button with the given name, the given text but no icon.
   * 
   * @since Date: Apr 9, 2012
   * @param name the name of this {@link MDCheckBox}
   * @param text the text of this {@link MDCheckBox}
   */
  public MDCheckBox(final String name, final String text) {
    super(name, text);
  }

  /**
   * Creates an unselected {@link MDCheckBox} button with the given name, and other properties are fetched from the
   * given {@link Action}.
   * 
   * @since Date: Apr 9, 2012
   * @param name the name of this {@link MDCheckBox}
   * @param a the {@link Action} to fetch some properties from
   */
  public MDCheckBox(final String name, final Action a) {
    super(name, a);
  }

  /**
   * Creates a {@link MDCheckBox} button with the given name, the given icon but no text.
   * 
   * @since Date: Apr 9, 2012
   * @param name the name of this {@link MDCheckBox}
   * @param icon the image to display
   * @param selected the initial selection state, <code>true</code> if selected,<br>
   *        or <code>false</code> otherwise
   */
  public MDCheckBox(final String name, final Icon icon, final boolean selected) {
    super(name, icon, selected);
  }

  /**
   * Creates a {@link MDCheckBox} button with the given name, the given text but no icon.
   * 
   * @since Date: Apr 9, 2012
   * @param name the name of this {@link MDCheckBox}
   * @param text the text of this {@link MDCheckBox}
   * @param selected the initial selection state, <code>true</code> if selected,<br>
   *        or <code>false</code> otherwise
   */
  public MDCheckBox(final String name, final String text, final boolean selected) {
    super(name, text, selected);
  }

  /**
   * Creates an unselected {@link MDCheckBox} button with the given name, the given text and the given icon.
   * 
   * @since Date: Apr 9, 2012
   * @param name the name of this {@link MDCheckBox}
   * @param text the text of this {@link MDCheckBox}
   * @param icon the image to display
   */
  public MDCheckBox(final String name, final String text, final Icon icon) {
    super(name, text, icon);
  }

  /**
   * Creates a {@link MDCheckBox} button with the given name, the given text and the given icon.
   * 
   * @since Date: Apr 9, 2012
   * @param name the name of this {@link MDCheckBox}
   * @param text the text of this {@link MDCheckBox}
   * @param icon the image to display
   * @param selected the initial selection state, <code>true</code> if selected,<br>
   *        or <code>false</code> otherwise
   */
  public MDCheckBox(final String name, final String text, final Icon icon, final boolean selected) {
    super(name, text, icon, selected);
  }
}
