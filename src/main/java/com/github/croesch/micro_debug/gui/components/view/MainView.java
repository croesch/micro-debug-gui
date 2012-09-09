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

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.components.basic.MDScrollPane;
import com.github.croesch.micro_debug.gui.components.basic.MDSplitPane;
import com.github.croesch.micro_debug.gui.settings.BooleanSettings;
import com.github.croesch.micro_debug.gui.settings.IntegerSettings;
import com.github.croesch.micro_debug.gui.settings.KeyStrokes;
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
  private final RegisterView registerView = new RegisterView("register");

  /** the view that visualises the {@link com.github.croesch.micro_debug.mic1.mem.Memory} */
  @NotNull
  private final MemoryView memoryView;

  /** the view that visualises the micro code */
  @NotNull
  private final MicroCodeView microCodeView;

  /** the view that visualises the macro code */
  @NotNull
  private final MacroCodeView macroCodeView;

  /**
   * Constructs the main view with the given name.
   * 
   * @since Date: Apr 11, 2012
   * @param name the name of the view to set
   * @param proc the processor being debugged
   * @param bpm the model for breakpoints of this debugger
   */
  public MainView(final String name, final Mic1 proc, final BreakpointManager bpm) {
    if (proc == null) {
      throw new IllegalArgumentException();
    }

    this.memoryView = new MemoryView("memory", proc);
    this.macroCodeView = new MacroCodeView("macroCode", proc, bpm);
    this.microCodeView = new MicroCodeView("microCode", proc, bpm);

    final JScrollPane regPane = new MDScrollPane("register", this.registerView);
    final JScrollPane memPane = new MDScrollPane("memory", this.memoryView);
    final JSplitPane leftPane = new MDSplitPane("register-mem", JSplitPane.VERTICAL_SPLIT, regPane, memPane);
    leftPane.setDividerLocation(IntegerSettings.MAIN_FRAME_SLIDER_REGISTER_MEMORY.getValue());

    final JSplitPane codePane = new MDSplitPane("code",
                                                JSplitPane.HORIZONTAL_SPLIT,
                                                this.macroCodeView,
                                                this.microCodeView);
    codePane.setDividerLocation(IntegerSettings.MAIN_FRAME_SLIDER_MACRO_MICRO.getValue());

    final JScrollPane procPane = new MDScrollPane("processorTAs", new ProcessorTAView("processorTAs"));
    final JScrollPane debugPane = new MDScrollPane("debuggerTA", new DebuggerTAView("debuggerTA"));

    final JSplitPane bottomPane = new MDSplitPane("processorTas-debuggerTa",
                                                  JSplitPane.HORIZONTAL_SPLIT,
                                                  procPane,
                                                  debugPane);
    bottomPane.setDividerLocation(IntegerSettings.MAIN_FRAME_SLIDER_PROCESSOR_DEBUGGER.getValue());

    final JSplitPane rightPane = new MDSplitPane("code-tas", JSplitPane.VERTICAL_SPLIT, codePane, bottomPane);
    rightPane.setDividerLocation(IntegerSettings.MAIN_FRAME_SLIDER_CODE_TEXTAREAS.getValue());

    this.view = new MDSplitPane(name, JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
    ((MDSplitPane) this.view).setDividerLocation(IntegerSettings.MAIN_FRAME_SLIDER_REGISTERMEMORY_REST.getValue());

    overrideLAFKeystrokes();
  }

  /**
   * Overrides the laf keystroke entries with the keystrokes defined by the user. The user can deactivate this behavior
   * with the {@link BooleanSettings#OVERRIDE_LAF_KEYSTROKES} option.
   * 
   * @since Date: Jul 30, 2012
   * @see BooleanSettings#OVERRIDE_LAF_KEYSTROKES
   */
  private void overrideLAFKeystrokes() {
    if (BooleanSettings.OVERRIDE_LAF_KEYSTROKES.value()) {
      final InputMap im = this.view.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).getParent();
      for (final KeyStrokes ks : KeyStrokes.values()) {
        im.put(ks.stroke(), null);
      }
    }
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
  public RegisterView getRegisterView() {
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
  public MemoryView getMemoryView() {
    return this.memoryView;
  }

  /**
   * Returns the component responsible for visualising the micro code.
   * 
   * @since Date: May 11, 2012
   * @return the {@link JComponent} containing the view visualising the micro code.
   */
  @NotNull
  public MicroCodeView getMicroCodeView() {
    return this.microCodeView;
  }

  /**
   * Returns the component responsible for visualising the macro code.
   * 
   * @since Date: May 11, 2012
   * @return the {@link JComponent} containing the view visualising the macro code.
   */
  @NotNull
  public MacroCodeView getMacroCodeView() {
    return this.macroCodeView;
  }

  /**
   * Sets the step action for the macro code part.
   * 
   * @since Date: Sep 9, 2012
   * @param action the macro step action
   */
  public void setMacroStepAction(final AbstractAction action) {
    this.macroCodeView.setStepAction(action);
  }

  /**
   * Sets the step action for the micro code part.
   * 
   * @since Date: Sep 9, 2012
   * @param action the micro step action
   */
  public void setMicroStepAction(final AbstractAction action) {
    this.microCodeView.setStepAction(action);
  }
}
