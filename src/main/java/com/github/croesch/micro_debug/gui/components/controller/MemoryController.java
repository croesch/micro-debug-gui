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
package com.github.croesch.micro_debug.gui.components.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.view.MemoryPanel;

/**
 * The controller that coordinates user input for the memory view.
 * 
 * @author croesch
 * @since Date: Apr 16, 2012
 */
final class MemoryController implements IController, ActionListener {

  /** the memory view */
  @NotNull
  private final MemoryPanel view;

  /**
   * Constructs the controller for the given memory view.
   * 
   * @since Date: Apr 16, 2012
   * @param v the view to build the controller upon
   */
  public MemoryController(final MemoryPanel v) {
    if (v == null) {
      throw new IllegalArgumentException();
    }
    this.view = v;

    this.view.getNumberStyleSwitcher().getBinaryBtn().addActionListener(this);
    this.view.getNumberStyleSwitcher().getDecimalBtn().addActionListener(this);
    this.view.getNumberStyleSwitcher().getHexadecimalBtn().addActionListener(this);

    this.view.getNumberStyleSwitcher().getHexadecimalBtn().setSelected(true);
    this.view.viewHexadecimal();
  }

  /**
   * {@inheritDoc}
   */
  public void performViewUpdate() {
    this.view.update();
  }

  /**
   * {@inheritDoc}
   */
  public void actionPerformed(final ActionEvent e) {
    if (this.view.getNumberStyleSwitcher().getBinaryBtn().equals(e.getSource())) {
      this.view.viewBinary();
    } else if (this.view.getNumberStyleSwitcher().getDecimalBtn().equals(e.getSource())) {
      this.view.viewDecimal();
    } else if (this.view.getNumberStyleSwitcher().getHexadecimalBtn().equals(e.getSource())) {
      this.view.viewHexadecimal();
    }
  }
}
