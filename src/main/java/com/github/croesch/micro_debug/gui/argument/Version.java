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
package com.github.croesch.micro_debug.gui.argument;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.argument.AArgument;
import com.github.croesch.micro_debug.commons.Printer;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;
import com.github.croesch.micro_debug.i18n.Text;

/**
 * argument to view the version of the debugger
 * 
 * @author croesch
 * @since Date: Feb 28, 2012
 */
public final class Version extends AArgument {

  /**
   * Hide constructor from being invoked.
   * 
   * @since Date: Feb 28, 2012
   */
  private Version() {
    // hidden constructor
  }

  /**
   * Class that holds the singleton of this argument.
   * 
   * @author croesch
   * @since Date: Feb 28, 2012
   */
  private static class LazyHolder {
    /** the single instance of the argument */
    private static final Version INSTANCE = new Version();
  }

  /**
   * The singleton instance of this argument.
   * 
   * @since Date: Feb 28, 2012
   * @return the single instance of this argument.
   */
  @NotNull
  public static Version getInstance() {
    return LazyHolder.INSTANCE;
  }

  @Override
  @NotNull
  protected String name() {
    return "version";
  }

  @Override
  public boolean execute(final String ... params) {
    Printer.println(Text.VERSION.text(com.github.croesch.micro_debug.settings.InternalSettings.VERSION));
    Printer.println(GuiText.VERSION.text(InternalSettings.NAME, InternalSettings.VERSION));
    return false;
  }
}
