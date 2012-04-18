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
package com.github.croesch.micro_debug.gui.debug;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.components.api.ILineBreakPointManager;

/**
 * Provides a wrapper for the {@link BreakpointManager}, subclasses can implement connection to it (which breakpoints
 * they would like to set).
 * 
 * @author croesch
 * @since Date: Apr 18, 2012
 */
abstract class ALineBreakpointHandler implements ILineBreakPointManager {

  /** the breakpoint manager that contains all information about breakpoints in the debugger */
  @NotNull
  private final BreakpointManager breakpointManager;

  /**
   * Constructs a wrapper for the {@link BreakpointManager}, subclasses can implement connection to it (which
   * breakpoints they would like to set).
   * 
   * @since Date: Apr 18, 2012
   * @param bpm the {@link BreakpointManager} containing all information about breakpoints in the debugger
   */
  public ALineBreakpointHandler(final BreakpointManager bpm) {
    if (bpm == null) {
      throw new IllegalArgumentException();
    }
    this.breakpointManager = bpm;
  }

  /**
   * Returns the {@link BreakpointManager} containing all information about breakpoints in the debugger.
   * 
   * @since Date: Apr 18, 2012
   * @return the {@link BreakpointManager} that has all information about breakpoints
   */
  @NotNull
  protected BreakpointManager getBreakpointManager() {
    return this.breakpointManager;
  }

  /** {@inheritDoc} */
  public final void toggleBreakpoint(final int line) {
    if (isBreakpoint(line)) {
      removeBreakpoint(line);
    } else {
      addBreakpoint(line);
    }
  }

  /**
   * Adds a breakpoint at the given line.
   * 
   * @since Date: Apr 18, 2012
   * @param line the line to set the breakpoint
   */
  protected abstract void addBreakpoint(final int line);

  /**
   * Removes a breakpoint at the given line.
   * 
   * @since Date: Apr 18, 2012
   * @param line the line to remove any breakpoint
   */
  protected abstract void removeBreakpoint(final int line);
}
