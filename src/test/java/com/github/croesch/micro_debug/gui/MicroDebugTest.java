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
package com.github.croesch.micro_debug.gui;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.github.croesch.micro_debug.gui.settings.InternalSettings;

/**
 * Provides test cases for {@link MicroDebug}.
 * 
 * @author croesch
 * @since Date: Feb 25, 2012
 */
public class MicroDebugTest extends DefaultTestCase {

  @Test
  public void testVersion() {
    printlnMethodName();
    MicroDebug.main(new String[] { "-v" });
    assertThat(out.toString()).isEqualTo(InternalSettings.VERSION.getValue() + getLineSeparator());
    out.reset();

    MicroDebug.main(new String[] { "--version" });
    assertThat(out.toString()).isEqualTo(InternalSettings.VERSION.getValue() + getLineSeparator());
  }

  @Test
  public void testHelp() throws IOException {
    printlnMethodName();
    MicroDebug.main(new String[] { "-h" });
    assertThat(out.toString()).isEqualTo(getHelpFileText());
    out.reset();

    MicroDebug.main(new String[] { "--help" });
    assertThat(out.toString()).isEqualTo(getHelpFileText());
  }
}
