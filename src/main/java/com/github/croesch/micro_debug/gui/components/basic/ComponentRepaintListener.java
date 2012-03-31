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

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Listener that repaints the component if any event happens.
 * 
 * @author croesch
 * @since Date: Mar 21, 2012
 */
public final class ComponentRepaintListener implements DocumentListener {

  /** the component to repaint on events */
  private final Component component;

  /**
   * Constructs a listener that repaints the given component
   * 
   * @since Date: Mar 21, 2012
   * @param comp the component to repaint on any event
   */
  public ComponentRepaintListener(final Component comp) {
    this.component = comp;
  }

  /** {@inheritDoc} */
  public void removeUpdate(final DocumentEvent e) {
    this.component.repaint();
  }

  /** {@inheritDoc} */
  public void insertUpdate(final DocumentEvent e) {
    this.component.repaint();
  }

  /** {@inheritDoc} */
  public void changedUpdate(final DocumentEvent e) {
    this.component.repaint();
  }
}
