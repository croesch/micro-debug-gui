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

import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Action to quit the debugger.
 * 
 * @author croesch
 * @since Date: May 14, 2012
 */
public final class ExitAction extends AbstractAction {

  /** generated serial version UID */
  private static final long serialVersionUID = -6692659010876966645L;

  /**
   * Constructs the action to quit the debugger.
   * 
   * @since Date: May 14, 2012
   */
  public ExitAction() {
    super(GuiText.GUI_ACTIONS_EXIT.text());
  }

  /**
   * {@inheritDoc}
   */
  public void actionPerformed(final ActionEvent e) {
    // TODO implement
  }
}
