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
package com.github.croesch.micro_debug.gui.settings;

import javax.swing.KeyStroke;

import com.github.croesch.micro_debug.annotation.Nullable;
import com.github.croesch.micro_debug.properties.PropertiesProvider;

/**
 * Provides some keystrokes, that may be modified by the user.
 * 
 * @author croesch
 * @since Date: May 25, 2012
 */
public enum KeyStrokes {

  /** keystroke to visualize the about frame */
  ABOUT (null),

  /** keystroke to quit the program */
  EXIT ("ctrl Q"),

  /** keystroke to show the help frame */
  HELP ("F1"),

  /** keystroke to perform a micro step */
  MICRO_STEP ("F5"),

  /** keystroke to reset the processor */
  RESET (null),

  /** keystroke to run the debugger */
  RUN ("F8"),

  /** keystroke to perform a macro step */
  STEP ("F6"),

  /** the keystroke to interrupt the running processor */
  INTERRUPT (null);

  /** the keystroke */
  @Nullable
  private final KeyStroke keystroke;

  /**
   * Constructs this setting. Loads the properties from file, if not yet done and fetches the keystroke for this
   * setting. The key is the name of the setting.
   * 
   * @since Date: May 25, 2012
   * @param defaultStroke keystroke to use if the properties file doesn't contain a valid keystroke
   */
  private KeyStrokes(final String defaultStroke) {
    final String val = PropertiesProvider.getInstance().get("micro-debug", "gui.keystroke." + name());
    final KeyStroke stroke = KeyStroke.getKeyStroke(val);

    if (stroke == null) {
      this.keystroke = KeyStroke.getKeyStroke(defaultStroke);
    } else {
      this.keystroke = stroke;
    }
  }

  /**
   * Returns the keystroke of this setting.
   * 
   * @since Date: May 25, 2012
   * @return the keystroke of this setting, created from the value in the properties file.
   */
  @Nullable
  public KeyStroke stroke() {
    return this.keystroke;
  }
}
