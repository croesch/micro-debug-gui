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

import com.github.croesch.components.CTabbedPane;

/**
 * An extension of {@link CTabbedPane} that contains some default behavior that not each client should have to
 * implement.
 * 
 * @author croesch
 * @since Date: May 10, 2012
 */
public class MDTabbedPane extends CTabbedPane {

  /** generated serial version UID */
  private static final long serialVersionUID = -4308900315432849168L;

  /**
   * Creates an empty <code>MDTabbedPane</code> with tab placement <code>JTabbedPane.TOP</code>.
   * 
   * @since Date: May 10, 2012
   * @param name the name of this {@link MDTabbedPane}
   */
  public MDTabbedPane(final String name) {
    super(name);
  }

  /**
   * Creates an empty <code>TabbedPane</code> with the specified tab placement.
   * 
   * @since Date: May 10, 2012
   * @param name the name of this {@link MDTabbedPane}
   * @param tabPlacement the placement for the tabs relative to the content
   */
  public MDTabbedPane(final String name, final int tabPlacement) {
    super(name, tabPlacement);
  }

  /**
   * Creates an empty <code>TabbedPane</code> with the specified tab placement and tab layout policy.
   * 
   * @since Date: May 10, 2012
   * @param name the name of this {@link MDTabbedPane}
   * @param tabPlacement the placement for the tabs relative to the content
   * @param tabLayoutPolicy the policy for laying out tabs when all tabs will not fit on one run
   */
  public MDTabbedPane(final String name, final int tabPlacement, final int tabLayoutPolicy) {
    super(name, tabPlacement, tabLayoutPolicy);
  }
}
