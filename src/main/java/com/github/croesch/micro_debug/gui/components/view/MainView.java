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

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.MDScrollPane;
import com.github.croesch.micro_debug.gui.components.basic.MDSplitPane;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * The main view of the GUI of the debugger. Contains the assembly of the main GUI components that build the view.
 * 
 * @author croesch
 * @since Date: Apr 11, 2012
 */
public final class MainView {

  /** the {@link JComponent} that contains the whole visualised view */
  @NotNull
  private final JComponent view;

  /** the view that visualises the {@link com.github.croesch.micro_debug.mic1.register.Register}s */
  @NotNull
  private final RegisterPanel registerView = new RegisterPanel("register");

  /** the view that visualises the {@link com.github.croesch.micro_debug.mic1.mem.Memory} */
  @NotNull
  private final MemoryPanel memoryView;

  /**
   * Constructs the main view with the given name.
   * 
   * @since Date: Apr 11, 2012
   * @param name the name of the view to set
   * @param proc the processor being debugged
   */
  public MainView(final String name, final Mic1 proc) {
    this.memoryView = new MemoryPanel("memory", proc);

    final JScrollPane regPane = new MDScrollPane("register", this.registerView);
    final JScrollPane memPane = new MDScrollPane("memory", this.memoryView);
    final JScrollPane codePane = new MDScrollPane("code", new MDPanel("code"));
    final JScrollPane procPane = new MDScrollPane("processorTAs", new MDPanel("processorTAs"));
    final JScrollPane debugPane = new MDScrollPane("debuggerTA", new DebuggerTAView("debuggerTA"));

    final JSplitPane bottomPane = new MDSplitPane("processorTas-debuggerTa",
                                                  JSplitPane.HORIZONTAL_SPLIT,
                                                  procPane,
                                                  debugPane);

    final JSplitPane leftPane = new MDSplitPane("register-mem", JSplitPane.VERTICAL_SPLIT, regPane, memPane);
    final JSplitPane rightPane = new MDSplitPane("code-tas", JSplitPane.VERTICAL_SPLIT, codePane, bottomPane);

    this.view = new MDSplitPane(name, JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
  }

  /**
   * Returns the component that contains the whole view.
   * 
   * @since Date: Apr 11, 2012
   * @return a {@link JComponent} containing the whole view.
   */
  @NotNull
  public JComponent getViewComponent() {
    return this.view;
  }

  /**
   * Returns the component responsible for visualising the {@link com.github.croesch.micro_debug.mic1.register.Register}
   * s.
   * 
   * @since Date: Apr 11, 2012
   * @return the {@link JComponent} containing the view visualising the
   *         {@link com.github.croesch.micro_debug.mic1.register.Register}s.
   */
  @NotNull
  public RegisterPanel getRegisterView() {
    return this.registerView;
  }

  /**
   * Returns the component responsible for visualising the {@link com.github.croesch.micro_debug.mic1.mem.Memory} s
   * values.
   * 
   * @since Date: Apr 16, 2012
   * @return the {@link JComponent} containing the view visualising the
   *         {@link com.github.croesch.micro_debug.mic1.mem.Memory}s values.
   */
  @NotNull
  public MemoryPanel getMemoryView() {
    return this.memoryView;
  }
}
