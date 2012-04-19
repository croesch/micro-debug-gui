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

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.console.MemoryInterpreter;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.MDScrollPane;
import com.github.croesch.micro_debug.gui.components.code.ACodeArea;
import com.github.croesch.micro_debug.gui.components.code.LineNumberLabel;
import com.github.croesch.micro_debug.gui.components.code.MacroCodeArea;
import com.github.croesch.micro_debug.gui.components.code.Ruler;
import com.github.croesch.micro_debug.gui.debug.LineNumberMapper;
import com.github.croesch.micro_debug.gui.debug.MacroLineBreakpointHandler;
import com.github.croesch.micro_debug.mic1.Mic1;
import com.github.croesch.micro_debug.mic1.mem.Memory;

/**
 * The panel that visualises the macro code.
 * 
 * @author croesch
 * @since Date: Apr 19, 2012
 */
public final class MacroCodeView extends MDPanel {

  /** generated serial version UID */
  private static final long serialVersionUID = -5565126433795206450L;

  /** the text area, containing the code */
  @NotNull
  private final ACodeArea codeArea;

  /** the label containing the line numbers */
  @NotNull
  private final LineNumberLabel lineNumberView;

  /** the mapper for mapping line numbers to users interpretion of line numbers */
  @NotNull
  private final transient LineNumberMapper mapper = new LineNumberMapper();

  /**
   * Creates this panel that visualises the macro code with the given name.
   * 
   * @since Date: Apr 19, 2012
   * @param name the name of this {@link MacroCodeView}.
   * @param proc the processor being debugged, may not be <code>null</code>.
   * @param bpm the {@link BreakpointManager} containing breakpoint information for the debugger
   */
  public MacroCodeView(final String name, final Mic1 proc, final BreakpointManager bpm) {
    super(name, new MigLayout("fill"));
    if (proc == null) {
      throw new IllegalArgumentException();
    }

    this.codeArea = new MacroCodeArea(name + "-code-ta");
    addMacroCode(this.codeArea, proc.getMemory());
    this.lineNumberView = new LineNumberLabel(this.codeArea, this.mapper);

    final MDScrollPane pane = new MDScrollPane("macro-code-scrollpane", this.codeArea);

    final MDPanel rowHeader = new MDPanel("macro-code-rowheader");
    rowHeader.setLayout(new MigLayout("fill", "0![]0![]0!", "0![]0!"));
    rowHeader.add(new Ruler(this.codeArea, new MacroLineBreakpointHandler(bpm), this.mapper), "grow");
    rowHeader.add(this.lineNumberView);

    pane.setRowHeaderView(rowHeader);

    add(pane, "grow");
  }

  /**
   * Fetches the macro code from the model and adds it to this view.
   * 
   * @since Date: Apr 19, 2012
   * @param ta the text area that should receive the macro code
   * @param memory the main memory
   */
  private void addMacroCode(final ACodeArea ta, final Memory memory) {
    final MemoryInterpreter interpreter = new MemoryInterpreter(memory);

    final Map<Integer, String> codeMap = interpreter.getCodeMap();
    final List<Integer> lines = getSortedLineNumbers(codeMap);

    final StringBuilder sb = new StringBuilder();
    for (int line = 0; line < lines.size(); ++line) {
      sb.append(codeMap.get(lines.get(line)));
      if (line < lines.size() - 1) {
        sb.append("\n");
      }
    }
    ta.setText(sb.toString());

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
    this.mapper.setNewLines(lineNumbers);
  }

  /**
   * Highlights the line with the given number. If called multiple times, the highlight will be moved. Call with an
   * invalid line number <em>to remove</em> the highlight.
   * 
   * @since Date: Apr 18, 2012
   * @param line the number of the line to highlight, zero-based,<br>
   *        if less than zero or not enough lines are shown in the text pane, then nothing will be highlighted and if a
   *        highlight exists this will be removed.
   */
  public void highlight(final int line) {
    this.codeArea.highlight(this.mapper.getNumberForLine(line));
    this.lineNumberView.highlight(this.mapper.getNumberForLine(line));
  }

  /**
   * Returns the line number mapper that this view is using.
   * 
   * @since Date: Apr 19, 2012
   * @return the {@link LineNumberMapper} this view uses.
   */
  @NotNull
  public LineNumberMapper getLineNumberMapper() {
    return this.mapper;
  }
}
