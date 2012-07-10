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
import com.github.croesch.micro_debug.gui.components.about.AboutFrame;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Action to visualize the about frame
 * 
 * @author croesch
 * @since Date: May 13, 2012
 */
public final class AboutAction extends AbstractAction {

  /** generated serial version UID */
  private static final long serialVersionUID = 7934852167202508201L;

  /** the about frame */
  @NotNull
  private final AboutFrame frame;

  /**
   * Constructs the action to visualize the about frame.
   * 
   * @since Date: May 13, 2012
   */
  public AboutAction() {
    super(GuiText.GUI_ACTIONS_ABOUT.text());
    this.frame = new AboutFrame();
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
   * @since Date: Jul 10, 2012
   * @return the {@link AboutFrame} to show when being executed.
   */
  @NotNull
  public AboutFrame getAboutFrame() {
    return this.frame;
  }
}
