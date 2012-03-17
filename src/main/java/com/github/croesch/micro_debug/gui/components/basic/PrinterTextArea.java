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

import com.github.croesch.micro_debug.commons.Printer;

/**
 * Text area that can be used to view the output of the {@link Printer}.
 * 
 * @author croesch
 * @since Date: Mar 17, 2012
 */
public class PrinterTextArea extends AStreamTextArea {

  /** generated serial version UID */
  private static final long serialVersionUID = -3719155556096664592L;

  /**
   * Constructs a new text area, without activating it. To see the output of the {@link Printer} see {@link #activate()}
   * .
   * 
   * @since Date: Mar 17, 2012
   * @param name the name of this text area
   * @see #activate()
   */
  public PrinterTextArea(final String name) {
    super(name);
  }

  @Override
  public final void activate() {
    Printer.setPrintStream(getStream());
  }
}
