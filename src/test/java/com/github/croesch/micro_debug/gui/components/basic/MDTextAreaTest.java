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
package com.github.croesch.micro_debug.gui.components.basic;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.JTextComponentFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link MDTextArea}.
 * 
 * @author croesch
 * @since Date: Mar 14, 2012
 */
public class MDTextAreaTest extends DefaultGUITestCase {

  private MDTextArea getTA(final String name, final Object text) {
    return GuiActionRunner.execute(new GuiQuery<MDTextArea>() {
      @Override
      protected MDTextArea executeInEDT() {
        return new MDTextArea(name, text);
      }
    });
  }

  private MDTextArea getTA(final String name) {
    return GuiActionRunner.execute(new GuiQuery<MDTextArea>() {
      @Override
      protected MDTextArea executeInEDT() {
        return new MDTextArea(name);
      }
    });
  }

  @Test
  public void testTextArea() {
    printlnMethodName();
    JTextComponentFixture taFixture = new JTextComponentFixture(robot(), getTA("ta", "text"));
    taFixture.requireText("text");
    assertThat(taFixture.component().getName()).isEqualTo("ta");

    taFixture = new JTextComponentFixture(robot(), getTA("", 12));
    taFixture.requireText("12");
    assertThat(taFixture.component().getName()).isEmpty();

    taFixture = new JTextComponentFixture(robot(), getTA(null, ""));
    taFixture.requireText("");
    assertThat(taFixture.component().getName()).isNull();

    taFixture = new JTextComponentFixture(robot(), getTA("", null));
    taFixture.requireText("");
    assertThat(taFixture.component().getName()).isEmpty();

    taFixture = new JTextComponentFixture(robot(), getTA(""));
    taFixture.requireText("");
    assertThat(taFixture.component().getName()).isEmpty();
  }
}
