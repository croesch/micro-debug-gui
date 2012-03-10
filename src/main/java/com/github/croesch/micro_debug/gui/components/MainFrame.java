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
package com.github.croesch.micro_debug.gui.components;

import java.awt.Dimension;

import com.github.croesch.micro_debug.gui.components.basic.SizedFrame;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * The main frame of the debugger.
 * 
 * @author croesch
 * @since Date: Mar 10, 2012
 */
public final class MainFrame extends SizedFrame {

  /** height and width of the frame - TODO change to useful values */
  private static final int SPECIAL_VALUE = 400;

  /** generated serial version UID */
  private static final long serialVersionUID = 888748757383386602L;

  /** the processor to debug */
  private final Mic1 processor;

  /**
   * Constructs the main frame of the application to debug the given processor.
   * 
   * @since Date: Mar 10, 2012
   * @param proc the processor to debug with this frame.
   */
  public MainFrame(final Mic1 proc) {
    super("...", new Dimension(SPECIAL_VALUE, SPECIAL_VALUE));
    this.processor = proc;
  }

  /**
   * Returns the processor that is debugged in this frame.
   * 
   * @since Date: Mar 10, 2012
   * @return the {@link Mic1} that is debugged.
   */
  public Mic1 getProcessor() {
    return this.processor;
  }
}
