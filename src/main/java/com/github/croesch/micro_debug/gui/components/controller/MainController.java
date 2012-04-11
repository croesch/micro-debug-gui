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

import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.components.view.MainView;

/**
 * The main controller of the GUI of the debugger. Contains the logical actions to perform based on events received from
 * the main GUIs components.
 * 
 * @author croesch
 * @since Date: Apr 11, 2012
 */
public final class MainController {

  /** the {@link BreakpointManager} that contains the breakpoints currently set in the debugger */
  private final BreakpointManager bpm = new BreakpointManager();

  /** the view of the debugger */
  private final MainView mainView;

  /**
   * Constructs the main controller for the given main view.
   * 
   * @since Date: Apr 11, 2012
   * @param view the view this controller controlls and interacts with
   */
  public MainController(final MainView view) {
    this.mainView = view;
    new RegisterController(this.mainView.getRegisterView(), this.bpm);
  }

  /**
   * Returns the breakpoint model used by this controller.
   * 
   * @since Date: Apr 11, 2012
   * @return the {@link BreakpointManager} that is the model, managing the breakpoints of the debugger.
   */
  public BreakpointManager getBpm() {
    return this.bpm;
  }
}
