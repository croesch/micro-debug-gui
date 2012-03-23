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

import org.junit.Test;

import com.github.croesch.micro_debug.gui.debug.LineNumberMapper;

/**
 * Provides test cases for {@link LineNumberMapper}.
 * 
 * @author croesch
 * @since Date: Mar 24, 2012
 */
public class LineNumberMapperTest extends DefaultTestCase {

  @Test
  public void testDefaultMapping() {
    printlnMethodName();

    final LineNumberMapper mapper = new LineNumberMapper();

    for (int i = 0; i < 1000; ++i) {
      assertThat(mapper.getLineForNumber(i)).isEqualTo(i);
      assertThat(mapper.getNumberForLine(i)).isEqualTo(i);
    }

    assertThat(mapper.getNumberForLine(-1)).isEqualTo(-1);
    assertThat(mapper.getNumberForLine(0)).isEqualTo(0);
    assertThat(mapper.getNumberForLine(1)).isEqualTo(1);
    assertThat(mapper.getNumberForLine(Integer.MIN_VALUE)).isEqualTo(Integer.MIN_VALUE);
    assertThat(mapper.getNumberForLine(Integer.MAX_VALUE)).isEqualTo(Integer.MAX_VALUE);
  }

  @Test
  public void setNewMapping_Null() {
    printlnMethodName();

    final LineNumberMapper mapper = new LineNumberMapper();

    mapper.setNewLines(null);

    for (int i = 0; i < 1000; ++i) {
      assertThat(mapper.getLineForNumber(i)).isEqualTo(i);
      assertThat(mapper.getNumberForLine(i)).isEqualTo(i);
    }

    mapper.setNewLines();

    for (int i = 0; i < 1000; ++i) {
      assertThat(mapper.getLineForNumber(i)).isEqualTo(i);
      assertThat(mapper.getNumberForLine(i)).isEqualTo(i);
    }

    mapper.setNewLines();

    for (int i = 0; i < 1000; ++i) {
      assertThat(mapper.getLineForNumber(i)).isEqualTo(i);
      assertThat(mapper.getNumberForLine(i)).isEqualTo(i);
    }
  }

  @Test
  public void setNewMapping() {
    printlnMethodName();

    final LineNumberMapper mapper = new LineNumberMapper();

    mapper.setNewLines(2, 4, 6, 8, 10);

    assertThat(mapper.getLineForNumber(0)).isEqualTo(2);
    assertThat(mapper.getLineForNumber(1)).isEqualTo(4);
    assertThat(mapper.getLineForNumber(2)).isEqualTo(6);
    assertThat(mapper.getLineForNumber(3)).isEqualTo(8);
    assertThat(mapper.getLineForNumber(4)).isEqualTo(10);

    assertThat(mapper.getLineForNumber(5)).isEqualTo(-1);
    assertThat(mapper.getLineForNumber(6)).isEqualTo(-1);
    assertThat(mapper.getLineForNumber(7)).isEqualTo(-1);
    assertThat(mapper.getLineForNumber(8)).isEqualTo(-1);
    assertThat(mapper.getLineForNumber(9)).isEqualTo(-1);
    assertThat(mapper.getLineForNumber(10)).isEqualTo(-1);

    assertThat(mapper.getNumberForLine(2)).isEqualTo(0);
    assertThat(mapper.getNumberForLine(4)).isEqualTo(1);
    assertThat(mapper.getNumberForLine(6)).isEqualTo(2);
    assertThat(mapper.getNumberForLine(8)).isEqualTo(3);
    assertThat(mapper.getNumberForLine(10)).isEqualTo(4);

    assertThat(mapper.getNumberForLine(0)).isEqualTo(-1);
    assertThat(mapper.getNumberForLine(1)).isEqualTo(-1);
    assertThat(mapper.getNumberForLine(3)).isEqualTo(-1);
    assertThat(mapper.getNumberForLine(5)).isEqualTo(-1);
    assertThat(mapper.getNumberForLine(7)).isEqualTo(-1);
    assertThat(mapper.getNumberForLine(9)).isEqualTo(-1);

    for (int i = 11; i < 1000; ++i) {
      assertThat(mapper.getLineForNumber(i)).isEqualTo(-1);
      assertThat(mapper.getNumberForLine(i)).isEqualTo(-1);
    }

    mapper.setNewLines(2);
    assertThat(mapper.getNumberForLine(2)).isEqualTo(0);
    assertThat(mapper.getLineForNumber(0)).isEqualTo(2);
    assertThat(mapper.getLineForNumber(1)).isEqualTo(-1);
    assertThat(mapper.getNumberForLine(4)).isEqualTo(-1);

    mapper.setNewLines(2, -2);
    assertThat(mapper.getNumberForLine(2)).isEqualTo(0);
    assertThat(mapper.getLineForNumber(0)).isEqualTo(2);
    assertThat(mapper.getLineForNumber(1)).isEqualTo(-2);
    assertThat(mapper.getLineForNumber(2)).isEqualTo(-1);
    assertThat(mapper.getNumberForLine(4)).isEqualTo(-1);
    assertThat(mapper.getNumberForLine(-2)).isEqualTo(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testSetNewMapping_Duplicate_1() {
    new LineNumberMapper().setNewLines(0, 1, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testSetNewMapping_Duplicate_2() {
    new LineNumberMapper().setNewLines(0, 1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testSetNewMapping_Duplicate_3() {
    new LineNumberMapper().setNewLines(-1, -1);
  }
}
