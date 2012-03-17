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
package com.github.croesch.micro_debug.gui.components.basic;

import com.github.croesch.micro_debug.mic1.io.Output;

/**
 * Text area that can be used to view the output of the {@link Output}.
 * 
 * @author croesch
 * @since Date: Mar 14, 2012
 */
public class OutputTextArea extends AStreamTextArea {

  /** generated serial version UID */
  private static final long serialVersionUID = -7528655002913929267L;

  /**
   * Constructs a new text area, without activating it. To see the output of the {@link Output} see {@link #activate()}
   * .
   * 
   * @since Date: Mar 14, 2012
   * @param name the name of this text area
   * @see #activate()
   */
  public OutputTextArea(final String name) {
    super(name);
  }

  @Override
  public final void activate() {
    Output.setBuffered(false);
    Output.setOut(getStream());
  }
}
