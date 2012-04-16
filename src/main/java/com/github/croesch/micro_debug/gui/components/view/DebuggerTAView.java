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
package com.github.croesch.micro_debug.gui.components.view;

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.MDScrollPane;
import com.github.croesch.micro_debug.gui.components.basic.PrinterTextArea;

/**
 * A view visualising output of the debugger. Simply contains a text component showing all output of
 * {@link com.github.croesch.micro_debug.commons.Printer}.
 * 
 * @author croesch
 * @since Date: Apr 16, 2012
 */
final class DebuggerTAView extends MDPanel {

  /** generated serial version UID */
  private static final long serialVersionUID = 7655477750780707386L;

  /**
   * Creates the view that visualises output of the debugger. Simply contains a text component showing all output of
   * {@link com.github.croesch.micro_debug.commons.Printer}.
   * 
   * @since Date: Apr 16, 2012
   * @param name the name of this {@link DebuggerTAView}.
   */
  public DebuggerTAView(final String name) {
    super(name);

    final PrinterTextArea ta = new PrinterTextArea("printer-ta");
    ta.activate();

    setLayout(new MigLayout("fill"));
    add(new MDScrollPane("printer-scrollpane", ta), "grow");
  }
}
