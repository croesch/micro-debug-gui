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
package com.github.croesch.micro_debug.gui.components.view;

import javax.swing.ButtonGroup;

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.MDRadioButton;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * The panel to switch different styles for number format.
 * 
 * @author croesch
 * @since Date: May 27, 2012
 */
public class NumberStyleSwitcher extends MDPanel {

  /** generated serial version UID */
  private static final long serialVersionUID = 2778321848705452342L;

  /** the button to switch the number format to binary */
  @NotNull
  private final MDRadioButton binaryBtn = new MDRadioButton("binary", GuiText.GUI_MAIN_BINARY.text());

  /** the button to switch the number format to decimal */
  @NotNull
  private final MDRadioButton decimalBtn = new MDRadioButton("decimal", GuiText.GUI_MAIN_DECIMAL.text());

  /** the button to switch the number format to hexadecimal */
  @NotNull
  private final MDRadioButton hexBtn = new MDRadioButton("hexadecimal", GuiText.GUI_MAIN_HEXADECIMAL.text());

  /**
   * Constructs the panel to switch different styles for number format.
   * 
   * @since Date: May 27, 2012
   * @param name the name for the style switcher.
   */
  public NumberStyleSwitcher(final String name) {
    super(name);
    setLayout(new MigLayout("fill"));

    add(this.binaryBtn);
    add(this.decimalBtn);
    add(this.hexBtn);

    final ButtonGroup group = new ButtonGroup();
    group.add(this.binaryBtn);
    group.add(this.decimalBtn);
    group.add(this.hexBtn);
  }

  /**
   * Returns the button that switches the style format to binary.
   * 
   * @since Date: May 27, 2012
   * @return the button that switches the style format to binary.
   */
  public final MDRadioButton getBinaryBtn() {
    return this.binaryBtn;
  }

  /**
   * Returns the button that switches the style format to decimal.
   * 
   * @since Date: May 27, 2012
   * @return the button that switches the style format to decimal.
   */
  public final MDRadioButton getDecimalBtn() {
    return this.decimalBtn;
  }

  /**
   * Returns the button that switches the style format to hexadecimal.
   * 
   * @since Date: May 27, 2012
   * @return the button that switches the style format to hexadecimmal.
   */
  public final MDRadioButton getHexadecimalBtn() {
    return this.hexBtn;
  }
}
