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
package com.github.croesch.micro_debug.gui.listener;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link ActionListener} that disposes a {@link Window} when the action is performed.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
public final class WindowDisposer implements ActionListener {

  /** the window to dispose when the action is performed */
  private final Window window;

  /**
   * Constructs this {@link ActionListener} that disposes a {@link Window} when the action is performed.
   * 
   * @since Date: Mar 9, 2012
   * @param w the {@link Window} to dispose when the action is performed
   */
  public WindowDisposer(final Window w) {
    this.window = w;
  }

  /**
   * Disposes the given window.
   * 
   * @since Date: Mar 9, 2012
   * @param e the event that caused this action
   */
  public void actionPerformed(final ActionEvent e) {
    this.window.dispose();
  }
}
