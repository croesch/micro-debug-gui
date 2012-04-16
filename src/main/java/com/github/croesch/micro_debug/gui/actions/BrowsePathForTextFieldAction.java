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

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.text.JTextComponent;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.annotation.Nullable;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * {@link AbstractAction} that opens a file chooser when performed and passes the result to a text component.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
public final class BrowsePathForTextFieldAction extends AbstractAction {

  /** generated serial version UID */
  private static final long serialVersionUID = -6707366915038196520L;

  /** the text component to set the browsed file path to */
  @NotNull
  private final JTextComponent txtComponent;

  /** the parent component for adjusting the file chooser */
  @Nullable
  private final Component parent;

  /**
   * Constructs this {@link AbstractAction} that opens a file chooser when performed and passes the result to a text
   * component.
   * 
   * @since Date: Mar 9, 2012
   * @param comp the text component that'll received the fetched information from the file chooser
   * @param p the parent component for adjusting the file chooser, may be <code>null</code>
   */
  public BrowsePathForTextFieldAction(final JTextComponent comp, final Component p) {
    super(GuiText.GUI_COMMAND_BROWSE.text());
    if (comp == null) {
      throw new IllegalArgumentException();
    }
    this.txtComponent = comp;
    this.parent = p;
  }

  /**
   * Opens a file chooser to browse the file and sets the result to the text component.
   * 
   * @since Date: Mar 9, 2012
   * @param e the event that caused this action to be performed
   */
  public void actionPerformed(final ActionEvent e) {
    final JFileChooser fc = new JFileChooser();
    final int returnVal = fc.showOpenDialog(this.parent);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      this.txtComponent.setText(fc.getSelectedFile().getAbsolutePath());
      this.txtComponent.setEnabled(true);
      this.txtComponent.setEditable(true);
    }
  }
}
