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

import java.awt.event.ActionEvent;

import com.github.croesch.micro_debug.gui.commons.WorkerThread;
import com.github.croesch.micro_debug.gui.components.controller.MainController;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Action to run the program of the processor.
 * 
 * @author croesch
 * @since Date: May 13, 2012
 */
public final class RunAction extends AbstractExecuteOnWorkerThreadAction {

  /** generated serial version UID */
  private static final long serialVersionUID = 9082637554492739783L;

  /**
   * Constructs the action to run the program of the processor.
   * 
   * @since Date: May 13, 2012
   * @param cont the controller of the debugger, having access to the processor and the view
   * @param thread the thread to use for executing the action instead of the EDT.
   * @param provider the {@link ActionProvider} holding references to all actions, especially to the
   *        {@link AbstractExecuteOnWorkerThreadAction}s.
   */
  public RunAction(final MainController cont, final WorkerThread thread, final ActionProvider provider) {
    super(GuiText.GUI_ACTIONS_RUN, cont, thread, provider);
  }

  @Override
  public void perform(final ActionEvent e) {
    getProcessor().run();
  }
}
