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

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.annotation.Nullable;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Action to quit the debugger.
 * 
 * @author croesch
 * @since Date: May 14, 2012
 */
public final class ExitAction extends AbstractAction {

  /** generated serial version UID */
  private static final long serialVersionUID = -6692659010876966645L;

  /** the windows to dispose when executing this action */
  @NotNull
  private final List<Window> windows = new ArrayList<Window>();

  /** the thread running actions outside the EDT */
  @Nullable
  private final transient Thread thread;

  /** the processor being debugged */
  @Nullable
  private final transient Mic1 processor;

  /**
   * Constructs the action to quit the debugger.
   * 
   * @since Date: May 14, 2012
   * @param f the main frame to be disposed on exit
   * @param t the thread running actions outside the EDT
   * @param proc the processor being debugged
   */
  public ExitAction(final JFrame f, final Thread t, final Mic1 proc) {
    super(GuiText.GUI_ACTIONS_EXIT.text());
    addWindow(f);
    this.thread = t;
    this.processor = proc;
  }

  /**
   * Adds the given {@link Window} to be disposed in case this action is executed.
   * 
   * @since Date: Jul 10, 2012
   * @param w the window to dispose when executing this action.
   */
  public void addWindow(final Window w) {
    if (w != null) {
      this.windows.add(w);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void actionPerformed(final ActionEvent e) {
    for (final Window w : this.windows) {
      w.dispose();
    }
    if (this.thread != null) {
      this.thread.interrupt();
    }
    if (this.processor != null) {
      this.processor.interrupt();
    }
  }
}
