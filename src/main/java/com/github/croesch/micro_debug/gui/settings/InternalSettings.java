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

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.properties.PropertiesProvider;

/**
 * Provides some internal settings, that shouldn't be modified by the user.
 * 
 * @author croesch
 * @since Date: Feb 25, 2012
 */
public enum InternalSettings {

  /** the version of the gui of the debugger */
  VERSION,
  /** the name of the gui of the debugger */
  NAME;

  /** the value set up in the properties file */
  @NotNull
  private final String value;

  /**
   * Constructs this setting. Loads the properties from file, if not yet done and fetches the value for this setting.
   * The key is the name of the setting.
   * 
   * @since Date: Feb 25, 2012
   */
  private InternalSettings() {
    String val = PropertiesProvider.getInstance().get("micro-debug-gui-internal", name());
    if (val == null) {
      val = "..missing..";
    }
    this.value = val;
  }

  /**
   * Returns the value of this setting.
   * 
   * @since Date: Feb 25, 2012
   * @return the value of this setting, read from the properties file.
   */
  @NotNull
  public String getValue() {
    return this.value;
  }

  @Override
  @NotNull
  public String toString() {
    return getValue();
  }
}
