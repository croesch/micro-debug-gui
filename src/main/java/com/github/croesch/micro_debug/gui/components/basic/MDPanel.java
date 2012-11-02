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

import java.awt.LayoutManager;

import com.github.croesch.components.CPanel;

/**
 * An extension of {@link CPanel} that contains some default behavior that not each client should have to implement.
 * 
 * @author croesch
 * @since Date: Apr 8, 2012
 */
public class MDPanel extends CPanel {

  /** generated serial version UID */
  private static final long serialVersionUID = 4720214109287024970L;

  /**
   * Constructs an empty {@link MDPanel} with the given name.
   * 
   * @since Date: Apr 8, 2012
   * @param name the name of this {@link MDPanel}
   */
  public MDPanel(final String name) {
    super(name);
  }

  /**
   * Constructs an empty {@link MDPanel} with the given name.
   * 
   * @since Date: Apr 8, 2012
   * @param name the name of this {@link MDPanel}
   * @param isDoubleBuffered <code>true</code> for using double-buffering,<br>
   *        or <code>false</code> otherwise
   */
  public MDPanel(final String name, final boolean isDoubleBuffered) {
    super(name, isDoubleBuffered);
  }

  /**
   * Constructs an empty {@link MDPanel} with the given name and the given layout manager.
   * 
   * @since Date: Apr 8, 2012
   * @param name the name of this {@link MDPanel}
   * @param layout the {@link LayoutManager} for the panel
   * @param isDoubleBuffered <code>true</code> for using double-buffering,<br>
   *        or <code>false</code> otherwise
   */
  public MDPanel(final String name, final LayoutManager layout, final boolean isDoubleBuffered) {
    super(name, layout, isDoubleBuffered);
  }

  /**
   * Constructs an empty {@link MDPanel} with the given name and the given layout manager.
   * 
   * @since Date: Apr 8, 2012
   * @param name the name of this {@link MDPanel}
   * @param layout the {@link LayoutManager} for the panel
   */
  public MDPanel(final String name, final LayoutManager layout) {
    super(name, layout);
  }
}
