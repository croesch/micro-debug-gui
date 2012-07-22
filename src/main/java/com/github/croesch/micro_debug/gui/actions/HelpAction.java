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

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.help.HelpFrame;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Action to view the help.
 * 
 * @author croesch
 * @since Date: May 14, 2012
 */
public final class HelpAction extends AbstractAction {

  /** generated serial version UID */
  private static final long serialVersionUID = 6485977055982330277L;

  /** the help frame */
  @NotNull
  private final HelpFrame frame;

  /**
   * Constructs the action to view the help.
   * 
   * @since Date: May 14, 2012
   */
  public HelpAction() {
    super(GuiText.GUI_ACTIONS_HELP.text());
    this.frame = new HelpFrame();
    this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  }

  /**
   * {@inheritDoc}
   */
  public void actionPerformed(final ActionEvent e) {
    this.frame.setVisible(true);
  }

  /**
   * Returns the frame this action visualises when being executed.
   * 
   * @since Date: Jul 22, 2012
   * @return the {@link HelpFrame} to show when being executed.
   */
  @NotNull
  public HelpFrame getHelpFrame() {
    return this.frame;
  }
}
