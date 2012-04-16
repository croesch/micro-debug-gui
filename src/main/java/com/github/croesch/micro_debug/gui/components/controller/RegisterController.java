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

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.components.view.RegisterPanel;
import com.github.croesch.micro_debug.mic1.register.Register;

/**
 * The controller that coordinates user input for the register view.
 * 
 * @author croesch
 * @since Date: Apr 11, 2012
 */
final class RegisterController implements ChangeListener, IController {

  /** the {@link BreakpointManager} that contains the breakpoint definitions of the debugger */
  @NotNull
  private final BreakpointManager breakpointManager;

  /** the register view */
  @NotNull
  private final RegisterPanel view;

  /**
   * Constructs the controller for the given register view. And passes information based on user input to the given
   * {@link BreakpointManager}.
   * 
   * @since Date: Apr 11, 2012
   * @param v the view to build the controller upon
   * @param bpm th {@link BreakpointManager} that should receive breakpoints set/unset by the user
   */
  public RegisterController(final RegisterPanel v, final BreakpointManager bpm) {
    if (v == null || bpm == null) {
      throw new IllegalArgumentException();
    }
    this.view = v;
    this.breakpointManager = bpm;
    // register the controller at the view
    for (final Register r : Register.values()) {
      this.view.getCheckBox(r).addChangeListener(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void stateChanged(final ChangeEvent e) {
    // sanity check 
    if (e != null && e.getSource() instanceof JCheckBox) {
      for (final Register r : Register.values()) {
        // check which register is affected by the event
        if (this.view.getCheckBox(r).equals(e.getSource())) {
          // check if we have to set or unset the breakpoint
          if (this.view.getCheckBox(r).isSelected()) {
            this.breakpointManager.addRegisterBreakpoint(r);
          } else {
            this.breakpointManager.removeRegisterBreakpoint(r);
          }
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void performViewUpdate() {
    this.view.update();
  }
}
