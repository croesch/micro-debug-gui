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
package com.github.croesch.micro_debug.gui.components.code;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

/**
 * Formats the text of the document that is being listened on each change of the underlying text.
 * 
 * @author croesch
 * @since Date: Mar 30, 2012
 */
public abstract class ACodeFormatter implements DocumentListener {

  /**
   * Performs the update of the text style on changes of the text to format.
   * 
   * @since Date: Mar 30, 2012
   * @param document the document containing the text to format and where the change happened.
   */
  private void performUpdate(final Document document) {
    if (document instanceof StyledDocument) {
      final StyledDocument doc = (StyledDocument) document;

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          format(doc);
        }
      });
    }
  }

  /**
   * Formats the text of the given document.
   * 
   * @since Date: Mar 30, 2012
   * @param doc the document containing the text to format.
   */
  protected abstract void format(final StyledDocument doc);

  /** {@inheritDoc} */
  public final void insertUpdate(final DocumentEvent e) {
    performUpdate(e.getDocument());
  }

  /** {@inheritDoc} */
  public final void removeUpdate(final DocumentEvent e) {
    performUpdate(e.getDocument());
  }

  /** {@inheritDoc} */
  public void changedUpdate(final DocumentEvent e) {
    /*
     * This method is called on attribute changes, so it wouldn't be a good idea to change the attributes within an
     * attribute change event. (Because of recursive calls)
     */
  }
}
