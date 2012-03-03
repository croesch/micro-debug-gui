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

  GUI_ABOUT_TITLE, GUI_ABOUT_LICENSE, GUI_ABOUT_DESCRIPTION;

  /** the value of this instance */
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
  public String toString() {
    return text();
  }

  /**
   * String representation of this object
   * 
   * @since Date: Feb 28, 2012
   * @return the String that represents the object
   */
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
  public String text(final Object ... s) {
    return XMLPropertiesProvider.replacePlaceholdersInString(this.string, s);
  }
}
