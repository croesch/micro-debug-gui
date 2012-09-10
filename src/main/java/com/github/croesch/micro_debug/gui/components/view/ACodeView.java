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

import java.awt.BorderLayout;

import javax.swing.AbstractAction;

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.api.ILineBreakPointManager;
import com.github.croesch.micro_debug.gui.components.basic.MDButton;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.MDScrollPane;
import com.github.croesch.micro_debug.gui.components.basic.MDTextField;
import com.github.croesch.micro_debug.gui.components.code.ACodeArea;
import com.github.croesch.micro_debug.gui.components.code.LineNumberLabel;
import com.github.croesch.micro_debug.gui.components.code.Ruler;
import com.github.croesch.micro_debug.gui.debug.LineNumberMapper;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * An abstract code view containing the text area to visualise the code, a ruler for adding breakpoints and a line
 * number view.
 * 
 * @author croesch
 * @since Date: Apr 19, 2012
 */
public abstract class ACodeView extends MDPanel {

  /** generated serial version UID */
  private static final long serialVersionUID = 2958988292113089274L;

  /** the text area, containing the code */
  @NotNull
  private final ACodeArea codeArea;

  /** the mapper for mapping line numbers to users interpretion of line numbers */
  @NotNull
  private final transient LineNumberMapper mapper = new LineNumberMapper();

  /** the processor being debugged */
  @NotNull
  private final Mic1 processor;

  /** the button to make single steps */
  @NotNull
  private final MDButton stepButton = new MDButton("stepButton", "step");

  /** the textfield to enter the number of steps to execute */
  @NotNull
  private final MDTextField stepTextField = new MDTextField("stepField", 1);

  /**
   * Creates an abstract code view containing the text area to visualise the code, a ruler for adding breakpoints and a
   * line number view.
   * 
   * @since Date: Apr 19, 2012
   * @param name the name of this code view
   * @param proc the processor being debugged
   * @param ta the text area to show the code in
   * @param bph the handler for the breakpoints
   */
  public ACodeView(final String name, final Mic1 proc, final ACodeArea ta, final ILineBreakPointManager bph) {
    super(name, new MigLayout("fill,wrap 1", "[]", "[][grow]"));
    if (proc == null) {
      throw new IllegalArgumentException();
    }
    this.processor = proc;

    this.codeArea = ta;
    addCode();

    final MDPanel header = new MDPanel(name + "-code-header", new MigLayout("fill"));
    this.stepTextField.setColumns(3);
    this.stepTextField.setToolTipText(GuiText.GUI_TIP_STEP_COUNT.text());
    header.add(this.stepTextField);
    header.add(this.stepButton);
    add(header);

    // #58 insert the code area into a extra panel to avoid wrapping of the code
    final MDPanel noWrapView = new MDPanel("no-wrap-panel", new BorderLayout());
    noWrapView.add(this.codeArea);
    final MDScrollPane pane = new MDScrollPane(name + "-code-scrollpane", noWrapView);

    final MDPanel rowHeader = new MDPanel(name + "-code-rowheader");
    rowHeader.setLayout(new MigLayout("fill", "0![]0![]0!", "0![grow,fill]0!"));
    rowHeader.add(new Ruler(name + "-ruler", this.codeArea, bph, this.mapper));
    rowHeader.add(new LineNumberLabel(this.codeArea, this.mapper));

    pane.setRowHeaderView(rowHeader);

    add(pane, "grow");

    update();

    // #32 scroll text areas to position 0 on startup
    this.codeArea.select(0, 0);
  }

  /**
   * Returns the processor being debugged.
   * 
   * @since Date: Apr 19, 2012
   * @return the {@link Mic1} processor debugged.
   */
  @NotNull
  protected final Mic1 getProcessor() {
    return this.processor;
  }

  /**
   * Adds the code to the code area.
   * 
   * @since Date: Apr 19, 2012
   */
  protected abstract void addCode();

  /**
   * Highlights the line with the given number. If called multiple times, the highlight will be moved. Call with an
   * invalid line number <em>to remove</em> the highlight.
   * 
   * @since Date: Apr 18, 2012
   * @param line the number of the line to highlight, zero-based,<br>
   *        if less than zero or not enough lines are shown in the text pane, then nothing will be highlighted and if a
   *        highlight exists this will be removed.
   */
  public final void highlight(final int line) {
    this.codeArea.highlight(this.mapper.getNumberForLine(line));
    // #35 highlight of line number view is too slow
    //    this.lineNumberView.highlight(this.mapper.getNumberForLine(line));
  }

  /**
   * Returns the line number mapper that this view is using.
   * 
   * @since Date: Apr 19, 2012
   * @return the {@link LineNumberMapper} this view uses.
   */
  @NotNull
  public final LineNumberMapper getLineNumberMapper() {
    return this.mapper;
  }

  /**
   * Returns the code area of this view.
   * 
   * @since Date: Apr 19, 2012
   * @return the text area containing the code.
   */
  @NotNull
  protected final ACodeArea getCodeArea() {
    return this.codeArea;
  }

  /**
   * Performs an update of the highlight of the code pane.
   * 
   * @since Date: May 11, 2012
   */
  public abstract void update();

  /**
   * Sets the step action for this code part.
   * 
   * @since Date: Sep 9, 2012
   * @param action the step action
   */
  public final void setStepAction(final AbstractAction action) {
    this.stepButton.setAction(action);
  }

  /**
   * Returns the textfield to enter the number of steps to execute
   * 
   * @since Date: Sep 9, 2012
   * @return the textfield to enter the number of steps to execute
   */
  public final MDTextField getStepField() {
    return this.stepTextField;
  }
}
