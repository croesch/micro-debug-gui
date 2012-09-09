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
package com.github.croesch.micro_debug.gui.components.basic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.mic1.io.Input;

/**
 * Text field that can be used to give input for the processors {@link Input}.
 * 
 * @author croesch
 * @since Date: Mar 17, 2012
 */
public class InputTextField extends MDTextField {

  /** generated serial version UID */
  private static final long serialVersionUID = 3090320096442873111L;

  /** the lock to acquire for manipulating the content entered via the text field */
  @NotNull
  private final Lock lock = new ReentrantLock();

  /** condition that signals when the user has entered text */
  @NotNull
  private final transient Condition notEmpty = this.lock.newCondition();

  /** the input stream that reads the data entered via the text field */
  @NotNull
  private final transient InputStream stream = new TextFieldInputStream();

  /** contains the text entered via the text field, not read by the processor yet */
  @NotNull
  private final StringBuffer sb = new StringBuffer();

  /** {@link Runnable} clearing the state that the text field requests input */
  @NotNull
  private final transient Runnable resetRequestInput;

  /** {@link Runnable} visualizing the state that the text field requests input */
  @NotNull
  private final transient Runnable markRequestInput;

  /**
   * Constructs a new text field that is able to give input for the processor. To let the processor read the data
   * entered into this text field invoke {@link #activate()}.
   * 
   * @since Date: Mar 17, 2012
   * @param name the name for that component
   */
  public InputTextField(final String name) {
    super(name);
    setAction(new TextFieldAction());

    this.resetRequestInput = new Runnable() {
      public void run() {
        setBackground(Color.white);
      }
    };
    this.markRequestInput = new Runnable() {
      public void run() {
        setBackground(Color.orange);
      }
    };
  }

  /**
   * Informs the {@link Input} to the data entered via this text field.
   * 
   * @since Date: Mar 17, 2012
   */
  public final void activate() {
    Input.setQuiet(true);
    Input.setIn(this.stream);
  }

  /**
   * Action that stores the text of the text field, when the user hits enter.
   * 
   * @author croesch
   * @since Date: Mar 17, 2012
   */
  private class TextFieldAction extends AbstractAction {

    /** generated serial version UID */
    private static final long serialVersionUID = 7421339690645719571L;

    /**
     * Creates the action with the text of {@link GuiText#GUI_ACTIONS_INPUT_OKAY}.
     * 
     * @since Date: Sep 9, 2012
     */
    public TextFieldAction() {
      super(GuiText.GUI_ACTIONS_INPUT_OKAY.text());
    }

    /**
     * Invoked when an action occurs. Transfers the text of the text field into the storage where the
     * {@link InputStream} reads from.
     * 
     * @author croesch
     * @since Date: Mar 17, 2012
     * @param e the event that caused the invocation of this method
     */
    public void actionPerformed(final ActionEvent e) {
      InputTextField.this.lock.lock();
      try {
        // fetch, clear and store text
        final String txt = getText();
        setText(null);
        InputTextField.this.sb.append(txt).append("\n");
        SwingUtilities.invokeLater(InputTextField.this.resetRequestInput);

        InputTextField.this.notEmpty.signal();
      } finally {
        InputTextField.this.lock.unlock();
      }
    }
  }

  /**
   * Stream that reads the data entered by the user and confirmed via ENTER. Will block, if the user hasn't entered
   * enough data.
   * 
   * @author croesch
   * @since Date: Mar 17, 2012
   */
  private class TextFieldInputStream extends InputStream {

    @Override
    public int read() throws IOException {
      InputTextField.this.lock.lock();
      try {
        while (InputTextField.this.sb.length() == 0) {
          SwingUtilities.invokeLater(InputTextField.this.markRequestInput);
          // wait for the user to enter the next data
          InputTextField.this.notEmpty.await();
        }

        // read the next character and remove it from storage
        final int retValue = InputTextField.this.sb.charAt(0);
        InputTextField.this.sb.replace(0, 1, "");

        return retValue;
      } catch (final InterruptedException e) {
        Utils.logThrownThrowable(e);
      } finally {
        InputTextField.this.lock.unlock();
      }
      return -1;
    }
  }
}
