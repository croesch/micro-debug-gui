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
package com.github.croesch.micro_debug.gui.i18n;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.properties.XMLPropertiesProvider;

/**
 * This class provides access to the internationalized text resources.
 * 
 * @author croesch
 * @since Date: Feb 28, 2012
 */
public enum GuiText {

  /** the text for the version of the program. */
  VERSION,
  /** the text to visualize a border */
  BORDER,
  /** the text that is printed when the program is started, before any error message */
  GREETING,

  /** the text for the command to browse something */
  GUI_COMMAND_BROWSE,

  /** the title of the about information */
  GUI_ABOUT_TITLE,
  /** the title of license information in about file */
  GUI_ABOUT_LICENSE,
  /** the description of the debugger - as shown in the about section */
  GUI_ABOUT_DESCRIPTION,

  /** the title of the start frame */
  GUI_START_TITLE,
  /** the description of the start frame for the user */
  GUI_START_DESCRIPTION,
  /** the message to explain that the macro file is wrong */
  GUI_START_MACRO_WFF,
  /** the message to explain that the micro file is wrong */
  GUI_START_MICRO_WFF,
  /** the text to represent the action to start the debugger */
  GUI_START_OKAY,
  /** the text to identify the components for macro assembler binary code */
  GUI_START_MACRO,
  /** the text to identify the components for micro assembler binary code */
  GUI_START_MICRO,

  /** the title of the main frame */
  GUI_MAIN_TITLE,
  /** the description for the number format: binary */
  GUI_MAIN_BINARY,
  /** the description for the number format: decimal */
  GUI_MAIN_DECIMAL,
  /** the description for the number format: hexadecimal */
  GUI_MAIN_HEXADECIMAL,

  /** the text for the about action */
  GUI_ACTIONS_ABOUT,
  /** the text for the micro step action */
  GUI_ACTIONS_MICRO_STEP,
  /** the text for the reset action */
  GUI_ACTIONS_RESET,
  /** the text for the run action */
  GUI_ACTIONS_RUN,
  /** the text for the step action */
  GUI_ACTIONS_STEP,
  /** the text for the exit action */
  GUI_ACTIONS_EXIT,
  /** the text for the help action */
  GUI_ACTIONS_HELP,
  /** the text for the interrupt action */
  GUI_ACTIONS_INTERRUPT,

  /** the text for the help menu */
  GUI_MENU_HELP,
  /** the text for the processor menu */
  GUI_MENU_PROCESSOR,
  /** the text for the micro-debug menu */
  GUI_MENU_MICRODEBUG;

  /** the value of this instance */
  @NotNull
  private final String string;

  /**
   * Constructs a new instance of a text that is part of the i18n. Each key will be searched in the file
   * 'lang/text*.xml' (where '*' is a string build from the locales properties language, country and variant, so there
   * will be four file names and the most specific will be searched first). The name of this enumeration is the suffix
   * of the key where underscores will be replaced by minuses.
   * 
   * @since Date: Feb 28, 2012
   */
  private GuiText() {
    this.string = XMLPropertiesProvider.getInstance().get("lang/text-gui", name());
  }

  @Override
  @NotNull
  public String toString() {
    return text();
  }

  /**
   * String representation of this object
   * 
   * @since Date: Feb 28, 2012
   * @return the String that represents the object
   */
  @NotNull
  public String text() {
    return this.string;
  }

  /**
   * String representation of this object, but {x} will be replaced by argument number x starting to count from 0.
   * 
   * @since Date: Feb 28, 2012
   * @param s the replacements
   * @return the String that represents the object with replaced placeholders
   * @see XMLPropertiesProvider#replacePlaceholdersInString(String, Object...)
   */
  @NotNull
  public String text(final Object ... s) {
    return XMLPropertiesProvider.replacePlaceholdersInString(this.string, s);
  }
}
