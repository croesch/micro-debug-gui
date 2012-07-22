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
package com.github.croesch.micro_debug.gui.actions;

import java.util.EnumMap;

import javax.swing.Action;
import javax.swing.JFrame;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.actions.api.IActionProvider;
import com.github.croesch.micro_debug.gui.commons.WorkerThread;
import com.github.croesch.micro_debug.gui.components.controller.MainController;

/**
 * Provides the actions used by the debugger.
 * 
 * @author croesch
 * @since Date: May 13, 2012
 */
public final class ActionProvider implements IActionProvider {

  /** the map that holds the action for each key */
  @NotNull
  private final EnumMap<Actions, Action> actions = new EnumMap<Actions, Action>(Actions.class);

  /**
   * Constructs this provider that holds references for the actions used by the debugger.
   * 
   * @since Date: May 13, 2012
   * @param cont the controller of the debugger, having access to the processor and the view
   * @param frame the main frame
   */
  public ActionProvider(final MainController cont, final JFrame frame) {
    final WorkerThread thread = new WorkerThread("action-worker");

    final AboutAction aboutAction = new AboutAction();
    final HelpAction helpAction = new HelpAction();
    final ExitAction exitAction = new ExitAction(frame, thread, cont.getProcessor());
    exitAction.addWindow(aboutAction.getAboutFrame());
    exitAction.addWindow(helpAction.getHelpFrame());

    this.actions.put(Actions.ABOUT, aboutAction);
    this.actions.put(Actions.EXIT, exitAction);
    this.actions.put(Actions.HELP, helpAction);
    this.actions.put(Actions.MICRO_STEP, new MicroStepAction(cont, thread, this));
    this.actions.put(Actions.RESET, new ResetAction(cont, thread, this));
    this.actions.put(Actions.RUN, new RunAction(cont, thread, this));
    this.actions.put(Actions.STEP, new StepAction(cont, thread, this));
    this.actions.put(Actions.INTERRUPT, new InterruptAction(cont));
  }

  /**
   * {@inheritDoc}
   */
  public Action getAction(final Actions key) {
    return this.actions.get(key);
  }
}
