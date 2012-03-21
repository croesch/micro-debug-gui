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
package com.github.croesch.micro_debug.gui.components.api;

/**
 * A component that is able to manage some breakpoints.
 * 
 * @author croesch
 * @since Date: Mar 21, 2012
 */
public interface ILineBreakPointManager {

  /**
   * Toggles the breakpoint for the given line: Switches it <code>on</code>, if it was <code>off</code>; switches it
   * <code>off</code>, if it was <code>on</code>.
   * 
   * @since Date: Mar 21, 2012
   * @param line the number of the line to toggle the breakpoint
   */
  void toggleBreakpoint(int line);

  /**
   * Returns whether there is currently a breakpoint set for the given line.
   * 
   * @since Date: Mar 21, 2012
   * @param line the number of the line that should be checked for a breakpoint.
   * @return <code>true</code> whether there is currently a breakpoint in the given line,<br>
   *         <code>false</code> otherwise.
   */
  boolean isBreakpoint(int line);
}
