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
package com.github.croesch.micro_debug.gui.debug;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultTestCase;

/**
 * Provides test cases for {@link LineBreakPointHandler}.
 * 
 * @author croesch
 * @since Date: Mar 21, 2012
 */
public class LineBreakPointHandlerTest extends DefaultTestCase {

  @Test
  public void testIsBreakpoint() {
    printMethodName();

    final LineBreakPointHandler handler = new LineBreakPointHandler();
    for (int i = 0; i < 1000; ++i) {
      assertThat(handler.isBreakpoint(i)).isFalse();
      printStep();
    }
    printLoopEnd();

    final int[] lines = new int[] { 0, 10, 20, 40, 30, Integer.MAX_VALUE, Integer.MIN_VALUE };

    for (final int line : lines) {
      handler.toggleBreakpoint(line);
      printStep();
      printLoopEnd();
    }

    for (int line = 0; line < 1000; ++line) {
      boolean contains = false;
      for (final int i : lines) {
        if (i == line) {
          contains = true;
        }
        printStep();
      }
      assertThat(handler.isBreakpoint(line)).isEqualTo(contains);

      printLoopEnd();
    }

    printEndOfMethod();
  }

  @Test
  public void testToggling() {
    printMethodName();
    final LineBreakPointHandler handler = new LineBreakPointHandler();

    for (int line = 1; line < 1000; ++line) {
      handler.toggleBreakpoint(line);
      assertThat(handler.isBreakpoint(line)).isTrue();
      handler.toggleBreakpoint(-line);
      assertThat(handler.isBreakpoint(line)).isTrue();
      handler.toggleBreakpoint(line);
      handler.toggleBreakpoint(line);
      assertThat(handler.isBreakpoint(line)).isTrue();
      handler.toggleBreakpoint(line);
      assertThat(handler.isBreakpoint(line)).isFalse();
      printStep();
    }

    printEndOfMethod();
  }

}
