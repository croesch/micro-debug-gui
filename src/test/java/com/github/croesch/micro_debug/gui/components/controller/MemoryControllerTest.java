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
package com.github.croesch.micro_debug.gui.components.controller;

import static org.fest.assertions.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.error.MacroFileFormatException;
import com.github.croesch.micro_debug.error.MicroFileFormatException;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.basic.NumberLabel;
import com.github.croesch.micro_debug.gui.components.basic.NumberLabel.STYLE;
import com.github.croesch.micro_debug.gui.components.view.MemoryView;
import com.github.croesch.micro_debug.gui.components.view.MemoryPanelTest;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link MemoryController}.
 * 
 * @author croesch
 * @since Date: Apr 16, 2012
 */
public class MemoryControllerTest extends DefaultGUITestCase {

  @Test(expected = IllegalArgumentException.class)
  public void testCstr_NullView() {
    new MemoryController(null);
  }

  @Test
  public void testUpdate() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final MemoryView p = MemoryPanelTest.getPanel("mem", proc);
    final MemoryController controller = getController(p);
    showInFrame(p);
    final JPanelFixture panel = new JPanelFixture(robot(), p);

    for (int offs = 0; offs < 250; offs += 50) {
      panel.scrollBar().scrollTo(offs);
      for (int i = 0; i < 20; ++i) {
        final NumberLabel label = panel.label("memValue-" + i).targetCastedTo(NumberLabel.class);
        assertThat(label.getNumber()).isEqualTo(proc.getMemoryValue(offs + i));
      }
    }

    proc.setMemoryValue(17, 42);
    proc.setMemoryValue(211, 42);

    panel.scrollBar().scrollToMinimum();

    for (int offs = 0; offs < 250; offs += 50) {
      panel.scrollBar().scrollTo(offs);
      for (int i = 0; i < 20; ++i) {
        final int adr = offs + i;
        final NumberLabel label = panel.label("memValue-" + i).targetCastedTo(NumberLabel.class);
        assertThat(label.getNumber()).isEqualTo(proc.getMemoryValue(adr));
      }
    }

    panel.scrollBar().scrollTo(17);
    proc.setMemoryValue(17, 17);
    proc.setMemoryValue(18, 18);
    NumberLabel label = panel.label("memValue-0").targetCastedTo(NumberLabel.class);
    assertThat(label.getNumber()).isEqualTo(42);
    assertThat(label.getNumber()).isNotEqualTo(17);

    update(controller);

    label = panel.label("memValue-0").targetCastedTo(NumberLabel.class);
    assertThat(label.getNumber()).isNotEqualTo(42);
    assertThat(label.getNumber()).isEqualTo(17);

    proc.setMemoryValue(18, -42);
    label = panel.label("memValue-1").targetCastedTo(NumberLabel.class);
    assertThat(label.getNumber()).isEqualTo(18);
    assertThat(label.getNumber()).isNotEqualTo(-42);

    panel.scrollBar().scrollUnitDown();

    label = panel.label("memValue-0").targetCastedTo(NumberLabel.class);
    assertThat(label.getNumber()).isNotEqualTo(18);
    assertThat(label.getNumber()).isEqualTo(-42);
  }

  private void update(final MemoryController controller) {
    GuiActionRunner.execute(new GuiTask() {

      @Override
      protected void executeInEDT() throws Throwable {
        controller.performViewUpdate();
      }
    });
  }

  @Test
  public void testNumberStyleSwitcher() throws MacroFileFormatException, MicroFileFormatException,
                                       FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final MemoryView p = MemoryPanelTest.getPanel("mem", proc);
    getController(p);
    showInFrame(p);
    final JPanelFixture panel = new JPanelFixture(robot(), p);

    for (int i = 0; i < 20; ++i) {
      final NumberLabel label = panel.label("memValue-" + i).targetCastedTo(NumberLabel.class);
      assertThat(label.getNumberStyle()).isEqualTo(STYLE.HEXADECIMAL);
    }

    panel.radioButton("binary").check();
    for (int i = 0; i < 20; ++i) {
      final NumberLabel label = panel.label("memValue-" + i).targetCastedTo(NumberLabel.class);
      assertThat(label.getNumberStyle()).isEqualTo(STYLE.BINARY);
    }

    panel.radioButton("decimal").check();
    for (int i = 0; i < 20; ++i) {
      final NumberLabel label = panel.label("memValue-" + i).targetCastedTo(NumberLabel.class);
      assertThat(label.getNumberStyle()).isEqualTo(STYLE.DECIMAL);
    }

    panel.radioButton("hexadecimal").check();
    for (int i = 0; i < 20; ++i) {
      final NumberLabel label = panel.label("memValue-" + i).targetCastedTo(NumberLabel.class);
      assertThat(label.getNumberStyle()).isEqualTo(STYLE.HEXADECIMAL);
    }
  }

  private MemoryController getController(final MemoryView p) {
    return GuiActionRunner.execute(new GuiQuery<MemoryController>() {
      @Override
      protected MemoryController executeInEDT() throws Throwable {
        return new MemoryController(p);
      }
    });
  }
}
