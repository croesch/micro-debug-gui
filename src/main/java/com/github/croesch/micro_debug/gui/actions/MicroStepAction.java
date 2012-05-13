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
import javax.swing.text.JTextComponent;

import com.github.croesch.micro_debug.annotation.Nullable;
import com.github.croesch.micro_debug.commons.Printer;
import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.i18n.Text;
import com.github.croesch.micro_debug.mic1.Mic1;
import com.github.croesch.micro_debug.parser.IntegerParser;

/**
 * Action to perform a micro step of the processor.
 * 
 * @author croesch
 * @since Date: May 13, 2012
 */
public final class MicroStepAction extends AbstractAction {

  /** generated serial version UID */
  private static final long serialVersionUID = 3119114931438478253L;

  /** the processor being debugged */
  private final Mic1 processor;

  /** the text component containing the number of ticks to do */
  @Nullable
  private JTextComponent textComponent = null;

  /** the {@link IntegerParser} to parse the number from the text component */
  private final IntegerParser parser = new IntegerParser();

  /**
   * Constructs the action to perform a micro step of the processor.
   * 
   * @since Date: May 13, 2012
   * @param proc the {@link Mic1} processor being debugged
   */
  public MicroStepAction(final Mic1 proc) {
    super(GuiText.GUI_ACTIONS_MICRO_STEP.text());
    this.processor = proc;
  }

  /**
   * {@inheritDoc}
   */
  public void actionPerformed(final ActionEvent e) {

    if (this.textComponent != null && !Utils.isNullOrEmpty(this.textComponent.getText())) {
      final Integer num = this.parser.parse(this.textComponent.getText());

      if (num == null) {
        // delete the text, if it's no valid number
        Printer.printErrorln(Text.INVALID_NUMBER.text(this.textComponent.getText()));
        this.textComponent.setText(null);
      } else {
        // valid number in text field -> do the number of steps
        this.processor.microStep(num.intValue());
      }
    } else {
      // no text component or empty -> do one step
      this.processor.microStep();
    }
  }

  /**
   * Setting the text component that contains information (the number) about how many ticks should be done, when
   * performing this action. If the given text component is <code>null</code>, one tick will be done when performing
   * this action.
   * 
   * @since Date: May 13, 2012
   * @param comp the text component containing information about how many ticks to do when this action is performed
   */
  public void setTextComponent(final JTextComponent comp) {
    this.textComponent = comp;
  }
}
