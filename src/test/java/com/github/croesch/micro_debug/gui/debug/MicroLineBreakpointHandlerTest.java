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

import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.DefaultTestCase;

/**
 * Provides test cases for {@link MicroLineBreakpointHandler}.
 * 
 * @author croesch
 * @since Date: Apr 18, 2012
 */
public class MicroLineBreakpointHandlerTest extends DefaultTestCase {

  @Test
  public void testBreakpoints() {
    printlnMethodName();

    final BreakpointManager bpm = new BreakpointManager();
    final MicroLineBreakpointHandler handler = new MicroLineBreakpointHandler(bpm);

    handler.addBreakpoint(12);
    assertThat(handler.isBreakpoint(12)).isTrue();
    assertThat(bpm.isMicroBreakpoint(12)).isTrue();

    handler.addBreakpoint(42);
    assertThat(handler.isBreakpoint(12)).isTrue();
    assertThat(bpm.isMicroBreakpoint(12)).isTrue();
    assertThat(handler.isBreakpoint(42)).isTrue();
    assertThat(bpm.isMicroBreakpoint(42)).isTrue();

    handler.removeBreakpoint(42);
    assertThat(handler.isBreakpoint(12)).isTrue();
    assertThat(bpm.isMicroBreakpoint(12)).isTrue();
    assertThat(handler.isBreakpoint(42)).isFalse();
    assertThat(bpm.isMicroBreakpoint(42)).isFalse();

    bpm.addMicroBreakpoint(Integer.valueOf(4711));
    assertThat(handler.isBreakpoint(12)).isTrue();
    assertThat(bpm.isMicroBreakpoint(12)).isTrue();
    assertThat(handler.isBreakpoint(42)).isFalse();
    assertThat(bpm.isMicroBreakpoint(42)).isFalse();
    assertThat(handler.isBreakpoint(4711)).isTrue();
    assertThat(bpm.isMicroBreakpoint(4711)).isTrue();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullBpm() {
    new MicroLineBreakpointHandler(null);
  }
}
