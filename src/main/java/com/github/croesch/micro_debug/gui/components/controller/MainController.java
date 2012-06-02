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
package com.github.croesch.micro_debug.gui.components.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.components.view.MainView;
import com.github.croesch.micro_debug.mic1.Mic1;
import com.github.croesch.micro_debug.mic1.api.IProcessorInterpreter;
import com.github.croesch.micro_debug.mic1.controlstore.MicroInstruction;

/**
 * The main controller of the GUI of the debugger. Contains the logical actions to perform based on events received from
 * the main GUIs components.
 * 
 * @author croesch
 * @since Date: Apr 11, 2012
 */
public final class MainController implements IProcessorInterpreter {

  /** the {@link BreakpointManager} that contains the breakpoints currently set in the debugger */
  @NotNull
  private final BreakpointManager breakpointManager;

  /** the different controllers of the program - each responsible for a small part */
  @NotNull
  private final List<IController> controllers = new ArrayList<IController>();

  /**
   * Constructs the main controller for the given main view.
   * 
   * @since Date: Apr 11, 2012
   * @param proc the processor to interprete
   * @param view the view this controller controlls and interacts with
   * @param bpm the model for breakpoints of this debugger
   */
  public MainController(final Mic1 proc, final MainView view, final BreakpointManager bpm) {
    if (proc == null || bpm == null || view == null) {
      throw new IllegalArgumentException();
    }
    this.breakpointManager = bpm;
    proc.setProcessorInterpreter(this);

    this.controllers.add(new RegisterController(view.getRegisterView(), this.breakpointManager));
    this.controllers.add(new MemoryController(view.getMemoryView()));
    this.controllers.add(new CodeController(view.getMicroCodeView()));
    this.controllers.add(new CodeController(view.getMacroCodeView()));
  }

  /**
   * Returns the breakpoint model used by this controller.
   * 
   * @since Date: Apr 11, 2012
   * @return the {@link BreakpointManager} that is the model, managing the breakpoints of the debugger.
   */
  @NotNull
  public BreakpointManager getBpm() {
    return this.breakpointManager;
  }

  /**
   * {@inheritDoc}
   */
  public boolean canContinue(final int microLine,
                             final int macroLine,
                             final MicroInstruction currentInstruction,
                             final MicroInstruction nextInstruction) {
    return !this.breakpointManager.isBreakpoint(microLine, macroLine, currentInstruction, nextInstruction);
  }

  /**
   * {@inheritDoc}
   */
  public void tickDone(final MicroInstruction instruction, final boolean macroCodeFetching) {
    try {
      final Runnable viewUpdate = new Runnable() {
        public void run() {
          for (final IController controller : MainController.this.controllers) {
            controller.performViewUpdate();
          }
        }
      };
      if (SwingUtilities.isEventDispatchThread()) {
        viewUpdate.run();
      } else {
        SwingUtilities.invokeAndWait(viewUpdate);
      }
    } catch (final InterruptedException e) {
      Utils.logThrownThrowable(e);
    } catch (final InvocationTargetException e) {
      Utils.logThrownThrowable(e);
    }
  }
}
