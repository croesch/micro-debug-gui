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

import com.github.croesch.micro_debug.annotation.Nullable;
import com.github.croesch.properties.PropertiesProvider;

/**
 * Provides some boolean settings, that may be modified by the user.
 * 
 * @author croesch
 * @since Date: Jun 2, 2012
 */
public enum BooleanSettings {

  /** setting if the view should be updated after each tick */
  UPDATE_AFTER_EACH_TICK (false),

  /** option to override the keystroke entries of the laf */
  OVERRIDE_LAF_KEYSTROKES (true),

  /** if the main frame should be created maximized */
  MAIN_FRAME_MAXIMIZED (true);

  /** the value */
  private final boolean value;

  /**
   * Constructs this setting. Loads the properties from file, if not yet done and fetches the value for this setting.
   * The key is the name of the setting.
   * 
   * @since Date: Jun 2, 2012
   * @param defaultValue value to use if the properties file doesn't contain a valid value
   */
  private BooleanSettings(final boolean defaultValue) {
    final String val = PropertiesProvider.getInstance().get("micro-debug", "gui." + name());
    final Boolean b = Boolean.valueOf(val);

    if (b == null) {
      this.value = defaultValue;
    } else {
      this.value = b.booleanValue();
    }
  }

  /**
   * Returns the value of this setting.
   * 
   * @since Date: Jun 2, 2012
   * @return the value of this setting, created from the value in the properties file.
   */
  @Nullable
  public boolean value() {
    return this.value;
  }
}
