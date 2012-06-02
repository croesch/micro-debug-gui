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
import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.gui.actions.api.IActionProvider.Actions;
import com.github.croesch.micro_debug.gui.commons.WorkerThread;
import com.github.croesch.micro_debug.gui.components.controller.MainController;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.mic1.Mic1;

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
  @NotNull
  private final WorkerThread workerThread;

  /** the {@link ActionProvider} holding references to all actions */
  @NotNull
  private final ActionProvider actionProvider;

  /** the controller of the debugger, having access to the processor and the view */
  @NotNull
  private final transient MainController controller;

  /**
   * Constructs the Action that shouldn't be executed on the EDT because of long running methods.
   * 
   * @since Date: May 26, 2012
   * @param text {@link GuiText} that contains the name of the action
   * @param thread the thread to use for executing the action instead of the EDT.
   * @param cont the controller of the debugger, having access to the processor and the view
   * @param provider the {@link ActionProvider} holding references to all actions, especially to the
   *        {@link AbstractExecuteOnWorkerThreadAction}s.
   */
  public AbstractExecuteOnWorkerThreadAction(final GuiText text,
                                             final MainController cont,
                                             final WorkerThread thread,
                                             final ActionProvider provider) {
    super(text.text());
    if (cont == null || thread == null || provider == null) {
      throw new IllegalArgumentException();
    }
    this.controller = cont;
    this.workerThread = thread;
    this.actionProvider = provider;
  }

  /**
   * {@inheritDoc}
   */
  public final void actionPerformed(final ActionEvent e) {
    this.controller.setInterrupted(false);

    final Runnable run = new Runnable() {
      public void run() {
        // perform the action
        perform(e);

        AbstractExecuteOnWorkerThreadAction.this.controller.updateView();

        // enable the actions again
        enableWorkerActionsOnEDTAndWait();
      }
    };
    enableWorkerActions(false);
    this.workerThread.invokeLater(run);
  }

  /**
   * Enables all {@link AbstractExecuteOnWorkerThreadAction}s the {@link ActionProvider} provides on the EDT and waits
   * until this has been done.
   * 
   * @since Date: Jun 2, 2012
   */
  private void enableWorkerActionsOnEDTAndWait() {
    try {
      SwingUtilities.invokeAndWait(new Runnable() {
        public void run() {
          enableWorkerActions(true);
        }
      });
    } catch (final InterruptedException t) {
      Utils.logThrownThrowable(t);
    } catch (final InvocationTargetException t) {
      Utils.logThrownThrowable(t);
    }
  }

  /**
   * Enables or disables all {@link AbstractExecuteOnWorkerThreadAction}s the {@link ActionProvider} provides.
   * 
   * @since Date: May 27, 2012
   * @param enable <code>true</code>, if the {@link Action}s should be enabled,<br>
   *        or <code>false</code> if they should be disabled.
   * @see Action#setEnabled(boolean)
   */
  private void enableWorkerActions(final boolean enable) {
    for (final Actions act : Actions.values()) {
      final Action action = this.actionProvider.getAction(act);
      if (action instanceof AbstractExecuteOnWorkerThreadAction) {
        action.setEnabled(enable);
      }
    }
    this.actionProvider.getAction(Actions.INTERRUPT).setEnabled(!enable);
  }

  /**
   * The actual action that'll be executed on the thread.
   * 
   * @since Date: May 26, 2012
   * @param e the event that caused the action to be performed
   */
  protected abstract void perform(ActionEvent e);

  /**
   * Returns the processor being debugged.
   * 
   * @since Date: May 27, 2012
   * @return the processor that is being debugged by this debugger.
   */
  @NotNull
  protected final Mic1 getProcessor() {
    return this.controller.getProcessor();
  }
}
