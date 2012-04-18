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

import com.github.croesch.micro_debug.debug.BreakpointManager;

/**
 * A wrapper for the micro code breakpoint management in the debugger for the specific view.
 * 
 * @author croesch
 * @since Date: Apr 18, 2012
 */
public final class MicroLineBreakpointHandler extends ALineBreakpointHandler {

  /**
   * Construct this handler to manage micro breakpoints.
   * 
   * @since Date: Apr 18, 2012
   * @param bpm the {@link BreakpointManager} that has all information about breakpoints in the debugger.
   */
  public MicroLineBreakpointHandler(final BreakpointManager bpm) {
    super(bpm);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isBreakpoint(final int line) {
    return getBreakpointManager().isMicroBreakpoint(Integer.valueOf(line));
  }

  @Override
  protected void addBreakpoint(final int line) {
    getBreakpointManager().addMicroBreakpoint(Integer.valueOf(line));
  }

  @Override
  protected void removeBreakpoint(final int line) {
    getBreakpointManager().removeMicroBreakpoint(Integer.valueOf(line));
  }
}
