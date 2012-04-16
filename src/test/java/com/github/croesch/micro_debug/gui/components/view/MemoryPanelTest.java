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

package com.github.croesch.micro_debug.gui.components.view;

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
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link MemoryPanel}.
 * 
 * @author croesch
 * @since Date: Apr 12, 2012
 */
public class MemoryPanelTest extends DefaultGUITestCase {

  public static MemoryPanel getPanel(final String name, final Mic1 proc) {
    return GuiActionRunner.execute(new GuiQuery<MemoryPanel>() {
      @Override
      protected MemoryPanel executeInEDT() throws Throwable {
        return new MemoryPanel(name, proc);
      }
    });
  }

  @Test
  public void testPanel() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    printlnMethodName();

    /**
     * TODO problem with FEST showing the frame containing 65K JLabels
     */
    //    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    //    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    //    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));
    //
    //    final MemoryPanel p = getPanel("mem", proc);

    final MemoryPanel p = getPanel("mem", null);
    showInFrame(p);
    final JPanelFixture panel = new JPanelFixture(robot(), p);
    assertThat(panel.component().getName()).isEqualTo("mem");

    //    for (int addr = 0; addr < proc.getMemory().getSize(); ++addr) {
    //      panel.label("memDesc-" + addr).requireText(addr + ":");
    //      assertThat(panel.label("memValue-" + addr).targetCastedTo(NumberLabel.class).getNumber())
    //        .isEqualTo(proc.getMemoryValue(addr));
    //    }
    for (int addr = 0; addr < 100; ++addr) {
      panel.label("memDesc-" + addr).requireText(addr + ":");
      assertThat(panel.label("memValue-" + addr).targetCastedTo(NumberLabel.class).getNumber()).isEqualTo(100);
    }
  }

  @Test
  public void testUpdate() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 proc = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    final MemoryPanel p = getPanel("mem", proc);

    for (int i = 0; i < proc.getMemory().getSize(); ++i) {
      assertThat(p.getLabel(i).getNumber()).isEqualTo(proc.getMemoryValue(i));
    }

    final int value1 = proc.getMemoryValue(17);
    final int value2 = proc.getMemoryValue(4711);
    proc.setMemoryValue(17, 42);
    proc.setMemoryValue(4711, 42);

    for (int i = 0; i < proc.getMemory().getSize(); ++i) {
      if (i != 17 && i != 4711) {
        assertThat(p.getLabel(i).getNumber()).isEqualTo(proc.getMemoryValue(i));
      }
    }
    assertThat(p.getLabel(17).getNumber()).isEqualTo(value1);
    assertThat(p.getLabel(17).getNumber()).isNotEqualTo(proc.getMemoryValue(17));
    assertThat(p.getLabel(4711).getNumber()).isEqualTo(value2);
    assertThat(p.getLabel(4711).getNumber()).isNotEqualTo(proc.getMemoryValue(4711));

    update(p);

    for (int i = 0; i < proc.getMemory().getSize(); ++i) {
      assertThat(p.getLabel(i).getNumber()).isEqualTo(proc.getMemoryValue(i));
    }
  }

  private void update(final MemoryPanel p) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        p.update();
      }
    });
  }
}