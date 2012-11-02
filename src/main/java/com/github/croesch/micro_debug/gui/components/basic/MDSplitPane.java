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

import com.github.croesch.components.CSplitPane;

/**
 * An extension of {@link CSplitPane} that contains some default behavior that not each client should have to implement.
 * 
 * @author croesch
 * @since Date: Apr 8, 2012
 */
public class MDSplitPane extends CSplitPane {

  /** generated serial version UID */
  private static final long serialVersionUID = -3599522291129844359L;

  /**
   * Creates a new {@link MDSplitPane} with the given name. It will be horizontally splitted and contain two buttons.
   * 
   * @since Date: Apr 8, 2012
   * @param name the name of this {@link MDSplitPane}
   */
  public MDSplitPane(final String name) {
    super(name);
  }

  /**
   * Creates a new {@link MDSplitPane} with the given name, the given orientation and the given components.
   * 
   * @since Date: Apr 8, 2012
   * @param name the name of this {@link MDSplitPane}
   * @param newOrientation if horizontally or vertically orientated
   * @param newContinuousLayout <code>true</code> if the components should continuously be drawn when divider position
   *        changes,<br>
   *        or <code>false</code> to redraw only when devider position has stopped changing.
   * @param newLeftComponent the component for the left/top side
   * @param newRightComponent the component for the right/bottom side
   */
  public MDSplitPane(final String name,
                     final int newOrientation,
                     final boolean newContinuousLayout,
                     final Component newLeftComponent,
                     final Component newRightComponent) {
    super(name, newOrientation, newContinuousLayout, newLeftComponent, newRightComponent);
  }

  /**
   * Creates a new {@link MDSplitPane} with the given name and the given orientation. Without components.
   * 
   * @since Date: Apr 8, 2012
   * @param name the name of this {@link MDSplitPane}
   * @param newOrientation if horizontally or vertically orientated
   * @param newContinuousLayout <code>true</code> if the components should continuously be drawn when divider position
   *        changes,<br>
   *        or <code>false</code> to redraw only when devider position has stopped changing.
   */
  public MDSplitPane(final String name, final int newOrientation, final boolean newContinuousLayout) {
    super(name, newOrientation, newContinuousLayout);
  }

  /**
   * Creates a new {@link MDSplitPane} with the given name, the given orientation and the given components.
   * 
   * @since Date: Apr 8, 2012
   * @param name the name of this {@link MDSplitPane}
   * @param newOrientation if horizontally or vertically orientated
   * @param newLeftComponent the component for the left/top side
   * @param newRightComponent the component for the right/bottom side
   */
  public MDSplitPane(final String name,
                     final int newOrientation,
                     final Component newLeftComponent,
                     final Component newRightComponent) {
    super(name, newOrientation, newLeftComponent, newRightComponent);
  }

  /**
   * Creates a new {@link MDSplitPane} with the given name, the given orientation. Without components.
   * 
   * @since Date: Apr 8, 2012
   * @param name the name of this {@link MDSplitPane}
   * @param newOrientation if horizontally or vertically orientated
   */
  public MDSplitPane(final String name, final int newOrientation) {
    super(name, newOrientation);
  }
}
