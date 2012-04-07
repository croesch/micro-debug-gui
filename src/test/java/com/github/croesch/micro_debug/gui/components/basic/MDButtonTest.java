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
import org.fest.swing.fixture.JButtonFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link MDButton}.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
public class MDButtonTest extends DefaultGUITestCase {

  public static MDButton getButton(final String name, final Object text) {
    return GuiActionRunner.execute(new GuiQuery<MDButton>() {
      @Override
      protected MDButton executeInEDT() {
        return new MDButton(name, text);
      }
    });
  }

  @Test
  public void testButton() {
    printlnMethodName();
    JButtonFixture buttonFixture = new JButtonFixture(robot(), getButton("tf", "text"));
    buttonFixture.requireText("text");
    assertThat(buttonFixture.component().getName()).isEqualTo("tf");

    buttonFixture = new JButtonFixture(robot(), getButton("", 12));
    buttonFixture.requireText("12");
    assertThat(buttonFixture.component().getName()).isEmpty();

    buttonFixture = new JButtonFixture(robot(), getButton(null, ""));
    buttonFixture.requireText("");
    assertThat(buttonFixture.component().getName()).isNull();

    buttonFixture = new JButtonFixture(robot(), getButton("", null));
    buttonFixture.requireText("");
    assertThat(buttonFixture.component().getName()).isEmpty();
  }
}
