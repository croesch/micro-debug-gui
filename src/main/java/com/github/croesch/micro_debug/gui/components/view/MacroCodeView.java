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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.console.MemoryInterpreter;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.components.code.MacroCodeArea;
import com.github.croesch.micro_debug.gui.debug.MacroLineBreakpointHandler;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * The panel that visualises the macro code.
 * 
 * @author croesch
 * @since Date: Apr 19, 2012
 */
public final class MacroCodeView extends ACodeView {

  /** generated serial version UID */
  private static final long serialVersionUID = -5565126433795206450L;

  /**
   * Creates this panel that visualises the macro code with the given name.
   * 
   * @since Date: Apr 19, 2012
   * @param name the name of this {@link MacroCodeView}.
   * @param proc the processor being debugged, may not be <code>null</code>.
   * @param bpm the {@link BreakpointManager} containing breakpoint information for the debugger
   */
  public MacroCodeView(final String name, final Mic1 proc, final BreakpointManager bpm) {
    super(name, proc, new MacroCodeArea(name + "-code-ta"), new MacroLineBreakpointHandler(bpm));
  }

  @Override
  protected void addCode() {
    final MemoryInterpreter interpreter = new MemoryInterpreter(getProcessor().getMemory());

    final Map<Integer, String> codeMap = interpreter.getCodeMap();
    final List<Integer> lines = getSortedLineNumbers(codeMap);

    final StringBuilder sb = new StringBuilder();
    for (int line = 0; line < lines.size(); ++line) {
      sb.append(codeMap.get(lines.get(line)));
      if (line < lines.size() - 1) {
        sb.append("\n");
      }
    }
    getCodeArea().setText(sb.toString());

    addLineNumbers(lines);
  }

  /**
   * Reads the line numbers from the given code map and returns a list with sorted line numbers.
   * 
   * @since Date: Apr 19, 2012
   * @param codeMap the map that contains code and line numbers
   * @return a sorted list of line numbers
   */
  @NotNull
  private List<Integer> getSortedLineNumbers(final Map<Integer, String> codeMap) {
    final List<Integer> lines = new ArrayList<Integer>();
    for (final Integer line : codeMap.keySet()) {
      lines.add(line);
    }
    Collections.sort(lines);
    return lines;
  }

  /**
   * Sets the given line numbers to the line number mapper, so that the it has the correct mapping.
   * 
   * @since Date: Apr 19, 2012
   * @param lines the line numbers viewed to the user
   */
  private void addLineNumbers(final List<Integer> lines) {
    final int[] lineNumbers = new int[lines.size()];
    for (int i = 0; i < lines.size(); ++i) {
      lineNumbers[i] = lines.get(i).intValue();
    }
    getLineNumberMapper().setNewLines(lineNumbers);
  }
}
