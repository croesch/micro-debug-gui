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

import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.parser.IntegerParser;
import com.github.croesch.micro_debug.properties.APropertiesProvider;

/**
 * This label is a special label to visualise numerical values.
 * 
 * @author croesch
 * @since Date: Mar 13, 2012
 */
public class NumberLabel extends MDLabel {

  /** generated serial version UID */
  private static final long serialVersionUID = -1068419079694826800L;

  /** the number that is the value this label holds */
  private int number = 0;

  /** the mask to display the number */
  private final String mask;

  /**
   * Different styles to format numbers.
   * 
   * @author croesch
   * @since Date: Mar 14, 2012
   */
  private enum STYLE {
    /** decimal number format */
    DECIMAL {
      @Override
      public String getRepresentation(final int num) {
        return String.valueOf(num);
      }
    },
    /** hexadecimal (base 16) format */
    HEXADECIMAL {
      @Override
      public String getRepresentation(final int num) {
        return Utils.toHexString(num);
      }
    },
    /** binary (base 2) format */
    BINARY {
      @Override
      public String getRepresentation(final int num) {
        return Utils.toBinaryString(num);
      }
    };

    /**
     * Returns the {@link String} representation of the given number.
     * 
     * @since Date: Mar 14, 2012
     * @param num the number to format to {@link String} with this number format
     * @return the {@link String} representing the given number in this style
     */
    public abstract String getRepresentation(int num);
  }

  /** the current style to format the numerical value */
  private STYLE numberStyle = STYLE.DECIMAL;

  /**
   * Constructs a new label with value zero, the given name and the given mask.
   * 
   * @since Date: Mar 13, 2012
   * @param name the name of the label
   * @param textMask the mask to visualise the value of the label
   */
  public NumberLabel(final String name, final Object textMask) {
    super(name);
    this.mask = Utils.toString(textMask);
    updateNumber();
  }

  /**
   * Constructs a new label with the given value and the given name. The mask will be, just displaying the given number.
   * 
   * @since Date: Apr 12, 2012
   * @param name the name of the label
   * @param num the value of the label
   */
  public NumberLabel(final String name, final int num) {
    super(name);
    this.mask = "{0}";
    this.number = num;
    updateNumber();
  }

  @Override
  public final void setText(final String text) {
    final Integer num = new IntegerParser().parse(text);
    if (num == null) {
      this.number = 0;
    } else {
      this.number = num.intValue();
    }
    updateNumber();
  }

  /**
   * Sets the new number value for this label.
   * 
   * @since Date: Mar 13, 2012
   * @param num the new number for this labels value.
   */
  public final void setNumber(final int num) {
    this.number = num;
    updateNumber();
  }

  /**
   * Gets the current number value for this label.
   * 
   * @since Date: Apr 9, 2012
   * @return the current number stored in this label
   */
  public final int getNumber() {
    return this.number;
  }

  /**
   * Updates the text displayed on the label.
   * 
   * @since Date: Mar 13, 2012
   */
  private void updateNumber() {
    if (this.mask != null) {
      final String numberString = this.numberStyle.getRepresentation(this.number);
      super.setText(APropertiesProvider.replacePlaceholdersInString(this.mask, numberString));
    }
  }

  /**
   * Switch numerical representation to decimal format.
   * 
   * @since Date: Mar 14, 2012
   */
  public final void viewDecimal() {
    this.numberStyle = STYLE.DECIMAL;
    updateNumber();
  }

  /**
   * Switch numerical representation to hexadecimal format.
   * 
   * @since Date: Mar 14, 2012
   */
  public final void viewHexadecimal() {
    this.numberStyle = STYLE.HEXADECIMAL;
    updateNumber();
  }

  /**
   * Switch numerical representation to binary format.
   * 
   * @since Date: Mar 14, 2012
   */
  public final void viewBinary() {
    this.numberStyle = STYLE.BINARY;
    updateNumber();
  }
}
