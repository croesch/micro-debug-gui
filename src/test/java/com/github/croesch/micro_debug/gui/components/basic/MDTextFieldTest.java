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
 * Provides test cases for {@link MDTextField}.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
public class MDTextFieldTest extends DefaultGUITestCase {

  private MDTextField getTF(final String name, final Object text) {
    return GuiActionRunner.execute(new GuiQuery<MDTextField>() {
      @Override
      protected MDTextField executeInEDT() {
        return new MDTextField(name, text);
      }
    });
  }

  private MDTextField getTF(final String name) {
    return GuiActionRunner.execute(new GuiQuery<MDTextField>() {
      @Override
      protected MDTextField executeInEDT() {
        return new MDTextField(name);
      }
    });
  }

  @Test
  public void testTextField() {
    printlnMethodName();
    JTextComponentFixture tfFixture = new JTextComponentFixture(robot(), getTF("tf", "text"));
    tfFixture.requireText("text");
    assertThat(tfFixture.component().getName()).isEqualTo("tf");

    tfFixture = new JTextComponentFixture(robot(), getTF("", 12));
    tfFixture.requireText("12");
    assertThat(tfFixture.component().getName()).isEmpty();

    tfFixture = new JTextComponentFixture(robot(), getTF(null, ""));
    tfFixture.requireText("");
    assertThat(tfFixture.component().getName()).isNull();

    tfFixture = new JTextComponentFixture(robot(), getTF("", null));
    tfFixture.requireText("");
    assertThat(tfFixture.component().getName()).isEmpty();

    tfFixture = new JTextComponentFixture(robot(), getTF(""));
    tfFixture.requireText("");
    assertThat(tfFixture.component().getName()).isEmpty();
  }
}
