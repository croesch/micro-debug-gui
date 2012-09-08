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
import com.github.croesch.micro_debug.gui.components.start.Mic1Starter;

/**
 * Argument to give the binary file path for the debugger.
 * 
 * @author croesch
 * @since Date: Sep 8, 2012
 */
public final class Files extends AArgument {

  /**
   * Hide constructor from being invoked.
   * 
   * @since Date: Sep 8, 2012
   * @param numOfParams the number of parameters
   */
  private Files(final int numOfParams) {
    super(numOfParams);
  }

  /**
   * Class that holds the singleton of this argument.
   * 
   * @author croesch
   * @since Date: Sep 8, 2012
   */
  private static class LazyHolder {
    /** the single instance of the argument */
    private static final Files INSTANCE = new Files(2);
  }

  /**
   * The singleton instance of this argument.
   * 
   * @since Date: Sep 8, 2012
   * @return the single instance of this argument.
   */
  @NotNull
  public static Files getInstance() {
    return LazyHolder.INSTANCE;
  }

  @Override
  @NotNull
  protected String name() {
    return "files";
  }

  @Override
  public boolean execute(final String ... params) {
    return execute(null, params);
  }

  /**
   * Executes command that result in the specific argument.
   * 
   * @since Date: Sep 8, 2012
   * @param starter the {@link Mic1Starter} to set the initial file paths to.
   * @param params the parameters of that argument.
   * @return <code>false</code>, if the argument enforces the application to stop
   */
  public boolean execute(final Mic1Starter starter, final String ... params) {
    if (starter != null) {
      starter.setMicroFilePath(params[0]);
      starter.setMacroFilePath(params[1]);
    }
    return true;
  }
}
