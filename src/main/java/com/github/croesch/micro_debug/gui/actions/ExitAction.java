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
import javax.swing.JFrame;

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

  /** the main frame to be disposed on exit */
  @Nullable
  private final JFrame frame;

  /** the thread running actions outside the EDT */
  @Nullable
  private final Thread thread;

  /** the processor being debugged */
  @Nullable
  private final Mic1 processor;

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
    this.frame = f;
    this.thread = t;
    this.processor = proc;
  }

  /**
   * {@inheritDoc}
   */
  public void actionPerformed(final ActionEvent e) {
    if (this.frame != null) {
      this.frame.dispose();
    }
    if (this.thread != null) {
      this.thread.interrupt();
    }
    if (this.processor != null) {
      this.processor.interrupt();
    }
  }
}
