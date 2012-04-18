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

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.MDScrollPane;
import com.github.croesch.micro_debug.gui.components.code.ACodeArea;
import com.github.croesch.micro_debug.gui.components.code.LineNumberLabel;
import com.github.croesch.micro_debug.gui.components.code.MicroCodeArea;
import com.github.croesch.micro_debug.gui.components.code.Ruler;
import com.github.croesch.micro_debug.gui.debug.LineNumberMapper;
import com.github.croesch.micro_debug.gui.debug.MicroLineBreakpointHandler;
import com.github.croesch.micro_debug.mic1.Mic1;
import com.github.croesch.micro_debug.mic1.controlstore.MicroControlStore;
import com.github.croesch.micro_debug.mic1.controlstore.MicroInstructionDecoder;

/**
 * The panel that visualises the micro code.
 * 
 * @author croesch
 * @since Date: Apr 18, 2012
 */
public final class MicroCodeView extends MDPanel {

  /** generated serial version UID */
  private static final long serialVersionUID = -6199816912760089269L;

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
   * Creates this panel that visualises the micro code with the given name.
   * 
   * @since Date: Apr 18, 2012
   * @param name the name of this {@link MicroCodeView}.
   * @param proc the processor being debugged, may not be <code>null</code>.
   * @param bpm the {@link BreakpointManager} containing breakpoint information for the debugger
   */
  public MicroCodeView(final String name, final Mic1 proc, final BreakpointManager bpm) {
    super(name, new MigLayout("fill"));
    if (proc == null) {
      throw new IllegalArgumentException();
    }

    this.codeArea = new MicroCodeArea(name + "-code-ta");
    addMicroCode(this.codeArea, proc.getControlStore());
    this.lineNumberView = new LineNumberLabel(this.codeArea, this.mapper);

    final MDScrollPane pane = new MDScrollPane("micro-code-scrollpane", this.codeArea);

    final MDPanel rowHeader = new MDPanel("micro-code-rowheader");
    rowHeader.setLayout(new MigLayout("fill", "0![]0![]0!", "0![]0!"));
    rowHeader.add(new Ruler(this.codeArea, new MicroLineBreakpointHandler(bpm), this.mapper), "grow");
    rowHeader.add(this.lineNumberView);

    pane.setRowHeaderView(rowHeader);

    add(pane, "grow");
  }

  /**
   * Fetches the micro code from the model and adds it to this view.
   * 
   * @since Date: Apr 18, 2012
   * @param ta the text area that should receive the micro code
   * @param store the model to fetch the code from
   */
  private void addMicroCode(final ACodeArea ta, final MicroControlStore store) {
    final StringBuilder sb = new StringBuilder(store.getSize() * 10);
    for (int i = 0; i < store.getSize(); ++i) {
      sb.append(MicroInstructionDecoder.decode(store.getInstruction(i)));
      if (i < store.getSize() - 1) {
        sb.append("\n");
      }
    }
    ta.setText(sb.toString());
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
}
