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

import javax.swing.AbstractAction;

import com.github.croesch.micro_debug.gui.commons.WorkerThread;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Action that shouldn't be executed on the EDT because of long running methods.
 * 
 * @author croesch
 * @since Date: May 26, 2012
 */
public abstract class AbstractExecuteOnWorkerThreadAction extends AbstractAction {

  /** generated serial version UID */
  private static final long serialVersionUID = -8749069618913022096L;

  /** the thread executing actions */
  private final WorkerThread workerThread;

  /**
   * Constructs the Action that shouldn't be executed on the EDT because of long running methods.
   * 
   * @since Date: May 26, 2012
   * @param text {@link GuiText} that contains the name of the action
   * @param thread the thread to use for executing the action instead of the EDT.
   */
  public AbstractExecuteOnWorkerThreadAction(final GuiText text, final WorkerThread thread) {
    super(text.text());
    this.workerThread = thread;
  }

  /**
   * {@inheritDoc}
   */
  public final void actionPerformed(final ActionEvent e) {
    final Runnable run = new Runnable() {
      public void run() {
        perform(e);
      }
    };
    this.workerThread.invokeLater(run);
  }

  /**
   * The actual action that'll be executed on the thread.
   * 
   * @since Date: May 26, 2012
   * @param e the event that caused the action to be performed
   */
  protected abstract void perform(ActionEvent e);
}