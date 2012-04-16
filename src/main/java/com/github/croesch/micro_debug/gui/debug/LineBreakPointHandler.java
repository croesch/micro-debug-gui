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

import java.util.ArrayList;
import java.util.List;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.api.ILineBreakPointManager;

/**
 * A handler that is able to manage some breakpoints for line numbers.
 * 
 * @author croesch
 * @since Date: Mar 21, 2012
 */
public class LineBreakPointHandler implements ILineBreakPointManager {

  /** the list of lines that contain breakpoints */
  @NotNull
  private final List<Integer> breakPoints = new ArrayList<Integer>();

  /** {@inheritDoc} */
  public final void toggleBreakpoint(final int line) {
    if (isBreakpoint(line)) {
      this.breakPoints.remove(Integer.valueOf(line));
    } else {
      this.breakPoints.add(Integer.valueOf(line));
    }
  }

  /** {@inheritDoc} */
  public final boolean isBreakpoint(final int line) {
    return this.breakPoints.contains(Integer.valueOf(line));
  }
}
