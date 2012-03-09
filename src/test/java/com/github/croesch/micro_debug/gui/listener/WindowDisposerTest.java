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
package com.github.croesch.micro_debug.gui.listener;

import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultTestCase;

/**
 * Provides test cases for {@link WindowDisposer}.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
public class WindowDisposerTest extends DefaultTestCase {

  @Test(expected = IllegalArgumentException.class)
  public void testIAE_WindowNull() {
    new WindowDisposer(null);
  }

}
