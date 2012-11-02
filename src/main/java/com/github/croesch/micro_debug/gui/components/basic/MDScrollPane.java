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

import java.awt.Component;

import com.github.croesch.components.CScrollPane;

/**
 * An extension of {@link CScrollPane} that contains some default behavior that not each client should have to
 * implement.
 * 
 * @author croesch
 * @since Date: Apr 7, 2012
 */
public class MDScrollPane extends CScrollPane {

  /** generated serial version UID */
  private static final long serialVersionUID = 484402533151913534L;

  /**
   * Creates an empty <code>MDScrollPane</code> with the given name where both horizontal and vertical scrollbars appear
   * when needed.
   * 
   * @since Date: Apr 7, 2012
   * @param name the name of this {@link MDScrollPane}
   */
  public MDScrollPane(final String name) {
    super(name);
  }

  /**
   * Creates a <code>MDScrollPane</code> with the given name that displays the given view and shows the scrollbars as
   * specified by the given scrollbar policies.
   * 
   * @since Date: Apr 7, 2012
   * @param name the name of this {@link MDScrollPane}
   * @param view the {@link Component} to display in this scroll pane
   * @param vsbPolicy the vertical scrollbar policy - when to show vertical scrollbar
   * @param hsbPolicy the horizontal scrollbar policy - when to show horizontal scrollbar
   */
  public MDScrollPane(final String name, final Component view, final int vsbPolicy, final int hsbPolicy) {
    super(name, view, vsbPolicy, hsbPolicy);
  }

  /**
   * Creates a <code>MDScrollPane</code> with the given view.
   * 
   * @since Date: Apr 7, 2012
   * @param name the name of this {@link MDScrollPane}
   * @param view the {@link Component} to display in this scroll pane
   */
  public MDScrollPane(final String name, final Component view) {
    super(name, view);
  }

  /**
   * Creates an empty <code>MDScrollPane</code> with the given name and the given scrollbar policies.
   * 
   * @since Date: Apr 7, 2012
   * @param name the name of this {@link MDScrollPane}
   * @param vsbPolicy the vertical scrollbar policy - when to show vertical scrollbar
   * @param hsbPolicy the horizontal scrollbar policy - when to show horizontal scrollbar
   */
  public MDScrollPane(final String name, final int vsbPolicy, final int hsbPolicy) {
    super(name, vsbPolicy, hsbPolicy);
  }
}
