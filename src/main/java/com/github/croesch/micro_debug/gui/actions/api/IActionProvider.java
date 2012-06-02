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
package com.github.croesch.micro_debug.gui.actions.api;

import javax.swing.Action;

import com.github.croesch.micro_debug.annotation.Nullable;

/**
 * Provides the actions used by the debugger.
 * 
 * @author croesch
 * @since Date: Jun 2, 2012
 */
public interface IActionProvider {

  /**
   * Returns the {@link Action} that is stored for this key.
   * 
   * @since Date: May 13, 2012
   * @param key the key that identifies the action to fetch
   * @return the stored {@link Action} for the given key
   */
  @Nullable
  Action getAction(final Actions key);

  /**
   * Provides an enumeration of actions to control the debugger.
   * 
   * @author croesch
   * @since Date: May 12, 2012
   */
  public enum Actions {

    /** action to visualize the about frame */
    ABOUT,

    /** the action to quit the program */
    EXIT,

    /** the action to show the help frame */
    HELP,

    /** the action to perform a micro step */
    MICRO_STEP,

    /** the action to reset the processor */
    RESET,

    /** the action to run the debugger */
    RUN,

    /** the action to perform a macro step */
    STEP;
  }
}
