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

import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.components.code.MicroCodeArea;
import com.github.croesch.micro_debug.gui.debug.MicroLineBreakpointHandler;
import com.github.croesch.micro_debug.mic1.Mic1;
import com.github.croesch.micro_debug.mic1.controlstore.MicroInstructionDecoder;

/**
 * The panel that visualises the micro code.
 * 
 * @author croesch
 * @since Date: Apr 18, 2012
 */
public final class MicroCodeView extends ACodeView {

  /** generated serial version UID */
  private static final long serialVersionUID = -6199816912760089269L;

  /**
   * Creates this panel that visualises the micro code with the given name.
   * 
   * @since Date: Apr 18, 2012
   * @param name the name of this {@link MicroCodeView}.
   * @param proc the processor being debugged, may not be <code>null</code>.
   * @param bpm the {@link BreakpointManager} containing breakpoint information for the debugger
   */
  public MicroCodeView(final String name, final Mic1 proc, final BreakpointManager bpm) {
    super(name, proc, new MicroCodeArea(name + "-code-ta"), new MicroLineBreakpointHandler(bpm));
  }

  @Override
  protected void addCode() {
    final StringBuilder sb = new StringBuilder(getProcessor().getControlStore().getSize() * 10);
    for (int i = 0; i < getProcessor().getControlStore().getSize(); ++i) {
      sb.append(MicroInstructionDecoder.decode(getProcessor().getControlStore().getInstruction(i)));
      if (i < getProcessor().getControlStore().getSize() - 1) {
        sb.append("\n");
      }
    }
    getCodeArea().setText(sb.toString());
  }

  @Override
  public void update() {
    highlight(getProcessor().getNextMpc());
  }
}
