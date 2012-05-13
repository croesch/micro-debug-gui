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
import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.gui.components.about.AboutFrame;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Provides an enumeration of {@link AbstractAction}s to control the debugger.
 * 
 * @author croesch
 * @since Date: May 12, 2012
 */
public enum Actions {

  /** action to visualize the about frame */
  ABOUT (GuiText.GUI_ACTIONS_ABOUT) {
    /** the about frame */
    private AboutFrame frame;

    {
      try {
        SwingUtilities.invokeAndWait(new Runnable() {
          public void run() {
            frame = new AboutFrame();
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
          }
        });
      } catch (final InvocationTargetException e) {
        Utils.logThrownThrowable(e);
      } catch (final InterruptedException e) {
        Utils.logThrownThrowable(e);
      }
    }

    @Override
    protected void perform() {
      this.frame.setVisible(true);
    }
  },

  /** the action to quit the program */
  EXIT (GuiText.GUI_ACTIONS_ABOUT) {
    @Override
    protected void perform() {
      // TODO Auto-generated method stub
    }
  },

  /** the action to show the help frame */
  HELP (GuiText.GUI_ACTIONS_ABOUT) {
    @Override
    protected void perform() {
      // TODO Auto-generated method stub
    }
  },

  /** the action to perform a micro step */
  MICRO_STEP (GuiText.GUI_ACTIONS_ABOUT) {
    @Override
    protected void perform() {
      // TODO Auto-generated method stub
    }
  },

  /** the action to reset the processor */
  RESET (GuiText.GUI_ACTIONS_ABOUT) {
    @Override
    protected void perform() {
      // TODO Auto-generated method stub
    }
  },

  /** the action to run the debugger */
  RUN (GuiText.GUI_ACTIONS_ABOUT) {
    @Override
    protected void perform() {
      // TODO Auto-generated method stub
    }
  },

  /** the action to perform a macro step */
  STEP (GuiText.GUI_ACTIONS_ABOUT) {
    @Override
    protected void perform() {
      // TODO Auto-generated method stub
    }
  };

  /** the action that wraps the functionality of the specific action for the swing components */
  private final Action action;

  /**
   * Constructs the specific action and the wrapper for swing components.
   * 
   * @since Date: May 13, 2012
   * @param name the {@link GuiText} containing the name of the description
   */
  private Actions(final GuiText name) {
    this.action = new AbstractAction(name.text()) {

      /** generated serial version UID */
      private static final long serialVersionUID = -7910947802169844436L;

      public void actionPerformed(final ActionEvent e) {
        perform();
      }
    };
  }

  /**
   * The action the specific {@link Actions} perform.
   * 
   * @since Date: May 13, 2012
   */
  protected abstract void perform();

  /**
   * Returns the wrapper of this action for swing components.
   * 
   * @since Date: May 13, 2012
   * @return the wrapper of this action for swing components.
   */
  public Action toAction() {
    return this.action;
  }
}
