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
package com.github.croesch.micro_debug.gui.components.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.text.JTextComponent;

/**
 * Mouse listener that activates a component after double click.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
public final class DoubleClickActivatingListener extends MouseAdapter {

  @Override
  public void mouseClicked(final MouseEvent e) {
    if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
      e.getComponent().setEnabled(true);
      if (e.getComponent() instanceof JTextComponent) {
        ((JTextComponent) e.getComponent()).setEditable(true);
      }
      e.getComponent().removeMouseListener(this);
    } else {
      super.mouseClicked(e);
    }
  }
}
