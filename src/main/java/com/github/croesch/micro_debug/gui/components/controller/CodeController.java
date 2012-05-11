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

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.view.ACodeView;

/**
 * The controller that coordinates user input for a code view.
 * 
 * @author croesch
 * @since Date: May 11, 2012
 */
final class CodeController implements IController {

  /** the code view */
  @NotNull
  private final ACodeView view;

  /**
   * Constructs the controller for the given code view.
   * 
   * @since Date: May 11, 2012
   * @param v the view to build the controller upon
   */
  public CodeController(final ACodeView v) {
    if (v == null) {
      throw new IllegalArgumentException();
    }
    this.view = v;
  }

  /**
   * {@inheritDoc}
   */
  public void performViewUpdate() {
    this.view.update();
  }

  /**
   * Returns the (code) view of this controller.
   * 
   * @since Date: May 11, 2012
   * @return the view this controller belongs to
   */
  @NotNull
  public ACodeView getView() {
    return this.view;
  }
}
