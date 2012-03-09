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
package com.github.croesch.micro_debug.gui.components.start;

import javax.swing.Action;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 * {@link DocumentListener} for the {@link StartFrame} to deactivate the start action, if at least one field is empty.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
class TextFieldsDocumentListener implements DocumentListener {

  /** the field that contains the path to the binary micro assembler file */
  private final JTextComponent microPathField;

  /** the field that contains the path to the binary macro assembler file */
  private final JTextComponent macroPathField;

  /** the {@link Action} to deactivate if at least one of the fields is empty */
  private final Action action;

  /**
   * Constructs this {@link DocumentListener} for the {@link StartFrame} to deactivate the start action, if at least one
   * field is empty.
   * 
   * @since Date: Mar 10, 2012
   * @param microField the field that contains the path to the binary micro assembler file and to add this listener to
   * @param macroField the field that contains the path to the binary macro assembler file and to add this listener to
   * @param act the {@link Action} to deactivate if at least one of the fields is empty
   */
  public TextFieldsDocumentListener(final JTextComponent microField, final JTextComponent macroField, final Action act) {
    if (microField == null || macroField == null || act == null) {
      throw new IllegalArgumentException();
    }
    this.macroPathField = macroField;
    this.microPathField = microField;
    this.action = act;
    this.macroPathField.getDocument().addDocumentListener(this);
    this.microPathField.getDocument().addDocumentListener(this);
    update();
  }

  /**
   * Insert event happened.
   * 
   * @since Date: Mar 10, 2012
   * @param e event that happened.
   */
  public void insertUpdate(final DocumentEvent e) {
    update();
  }

  /**
   * Remove event happened.
   * 
   * @since Date: Mar 10, 2012
   * @param e event that happened.
   */
  public void removeUpdate(final DocumentEvent e) {
    update();
  }

  /**
   * Change event happened.
   * 
   * @since Date: Mar 10, 2012
   * @param e event that happened.
   */
  public void changedUpdate(final DocumentEvent e) {
    update();
  }

  /**
   * Updates the state of the {@link Action}.
   * 
   * @since Date: Mar 10, 2012
   */
  private void update() {
    final String macroText = this.macroPathField.getText();
    final String microText = this.microPathField.getText();
    final boolean enable = macroText != null && microText != null && macroText.trim().length() > 0
                           && microText.trim().length() > 0;
    this.action.setEnabled(enable);
  }
}
