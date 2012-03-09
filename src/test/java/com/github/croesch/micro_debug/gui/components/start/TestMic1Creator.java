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
package com.github.croesch.micro_debug.gui.components.start;

import org.junit.Ignore;

import com.github.croesch.micro_debug.gui.actions.api.IMic1Creator;

/**
 * Test implementation of {@link IMic1Creator}.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
@Ignore("no test class")
public class TestMic1Creator implements IMic1Creator {

  private String microPath = "";

  private String macroPath = "";

  private boolean written = false;

  public void create(final String microFilePath, final String macroFilePath) {
    this.microPath = microFilePath;
    this.macroPath = macroFilePath;
    this.written = true;
  }

  public String getMicroPath() {
    return this.microPath;
  }

  public String getMacroPath() {
    return this.macroPath;
  }

  public boolean isWritten() {
    return this.written;
  }

  public void reset() {
    this.microPath = "";
    this.macroPath = "";
    this.written = false;
  }
}
