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
package com.github.croesch.micro_debug.gui.components.code;

/**
 * A code area especially to visualize macro code.
 * 
 * @author croesch
 * @since Date: Apr 4, 2012
 */
public class MacroCodeArea extends ACodeArea {

  /** generated serial version UID */
  private static final long serialVersionUID = -8915699947085602295L;

  /**
   * Constructs a code area that can visualize macro code with highlighted syntax.
   * 
   * @since Date: Apr 4, 2012
   * @param name the name for the text component to set
   */
  public MacroCodeArea(final String name) {
    super(name, new MacroCodeFormatter());
  }
}
