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

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.controller.MainController;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Action to interrupt the running processor.
 * 
 * @author croesch
 * @since Date: Jun 2, 2012
 */
public final class InterruptAction extends AbstractAction {

  /** generated serial version UID */
  private static final long serialVersionUID = -5089282502958007311L;

  /** the controller of the debugger, having access to the processor and the view */
  @NotNull
  private final MainController controller;

  /**
   * Constructs the action to interrupt the runnint processor.
   * 
   * @since Date: Jun 2, 2012
   * @param cont the controller of the debugger, having access to the processor and the view
   */
  public InterruptAction(final MainController cont) {
    super(GuiText.GUI_ACTIONS_INTERRUPT.text());
    if (cont == null) {
      throw new IllegalArgumentException();
    }

    this.controller = cont;
    setEnabled(false);
  }

  /**
   * {@inheritDoc}
   */
  public void actionPerformed(final ActionEvent e) {
    this.controller.setInterrupted(true);
    setEnabled(false);
  }
}
