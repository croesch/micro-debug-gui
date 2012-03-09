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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link TextFieldsDocumentListener}.
 * 
 * @author croesch
 * @since Date: Mar 10, 2012
 */
public class TextFieldsDocumentListenerTest extends DefaultGUITestCase {

  @Test(expected = IllegalArgumentException.class)
  public void testIAE_microFieldNull() {
    final JTextComponent field = GuiActionRunner.execute(new GuiQuery<JTextComponent>() {
      @Override
      protected JTextComponent executeInEDT() {
        return new JTextField();
      }
    });
    new TextFieldsDocumentListener(null, field, new AbstractAction() {
      /** generated */
      private static final long serialVersionUID = 1L;

      public void actionPerformed(final ActionEvent e) {}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIAE_macroFieldNull() {
    final JTextComponent field = GuiActionRunner.execute(new GuiQuery<JTextComponent>() {
      @Override
      protected JTextComponent executeInEDT() {
        return new JTextField();
      }
    });
    new TextFieldsDocumentListener(field, null, new AbstractAction() {
      /** generated */
      private static final long serialVersionUID = 1L;

      public void actionPerformed(final ActionEvent e) {}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIAE_ActionNull() {
    final JTextComponent field = GuiActionRunner.execute(new GuiQuery<JTextComponent>() {
      @Override
      protected JTextComponent executeInEDT() {
        return new JTextField();
      }
    });
    new TextFieldsDocumentListener(field, field, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIAE_AllNull() {
    new TextFieldsDocumentListener(null, null, null);
  }

}
