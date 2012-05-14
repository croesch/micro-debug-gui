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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link MDMenu}.
 * 
 * @author croesch
 * @since Date: Mar 14, 2012
 */
public class MDMenuTest extends DefaultGUITestCase {

  public static MDMenu getMenu(final String name) {
    return GuiActionRunner.execute(new GuiQuery<MDMenu>() {
      @Override
      protected MDMenu executeInEDT() {
        return new MDMenu(name);
      }
    });
  }

  public static MDMenu getMenu(final String name, final String text) {
    return GuiActionRunner.execute(new GuiQuery<MDMenu>() {
      @Override
      protected MDMenu executeInEDT() {
        return new MDMenu(name, text);
      }
    });
  }

  public static MDMenu getMenu(final String name, final Action a) {
    return GuiActionRunner.execute(new GuiQuery<MDMenu>() {
      @Override
      protected MDMenu executeInEDT() {
        return new MDMenu(name, a);
      }
    });
  }

  public static MDMenu getMenu(final String name, final String text, final boolean tornOff) {
    return GuiActionRunner.execute(new GuiQuery<MDMenu>() {
      @Override
      protected MDMenu executeInEDT() {
        return new MDMenu(name, text, tornOff);
      }
    });
  }

  @Test
  public void testMenu_String() {
    printlnMethodName();

    MDMenu menu = getMenu("menu");
    assertThat(menu.getName()).isEqualTo("menu");

    menu = getMenu("");
    assertThat(menu.getName()).isEmpty();

    menu = getMenu(null);
    assertThat(menu.getName()).isNull();

    menu = getMenu(" .. ");
    assertThat(menu.getName()).isEqualTo(" .. ");
  }

  @Test
  public void testMenu_StringString() {
    printlnMethodName();

    MDMenu menu = getMenu("menu", (String) null);
    assertThat(menu.getName()).isEqualTo("menu");
    assertThat(menu.getText()).isEmpty();

    menu = getMenu("", "txt");
    assertThat(menu.getName()).isEmpty();
    assertThat(menu.getText()).isEqualTo("txt");

    menu = getMenu(null, "");
    assertThat(menu.getName()).isNull();
    assertThat(menu.getText()).isEmpty();

    menu = getMenu(" .. ", "12 <-> 34");
    assertThat(menu.getName()).isEqualTo(" .. ");
    assertThat(menu.getText()).isEqualTo("12 <-> 34");
  }

  @Test
  public void testMenu_StringStringBoolean() {
    // TODO uncomment if 'tear off' is implemented
    printlnMethodName();

    MDMenu menu = getMenu("menu", (String) null, true);
    assertThat(menu.getName()).isEqualTo("menu");
    assertThat(menu.getText()).isEmpty();
    //    assertThat(menu.isTearOff()).isTrue();

    menu = getMenu("", "txt", false);
    assertThat(menu.getName()).isEmpty();
    assertThat(menu.getText()).isEqualTo("txt");
    //    assertThat(menu.isTearOff()).isFalse();

    menu = getMenu(null, "", true);
    assertThat(menu.getName()).isNull();
    assertThat(menu.getText()).isEmpty();
    //    assertThat(menu.isTearOff()).isTrue();

    menu = getMenu(" .. ", "12 <-> 34", false);
    assertThat(menu.getName()).isEqualTo(" .. ");
    assertThat(menu.getText()).isEqualTo("12 <-> 34");
    //    assertThat(menu.isTearOff()).isFalse();
  }

  @Test
  public void testMenu_StringAction() {
    printlnMethodName();

    MDMenu menu = getMenu("menu", (Action) null);
    assertThat(menu.getName()).isEqualTo("menu");
    assertThat(menu.getAction()).isNull();

    menu = getMenu("", new AbstractAction("ACTION") {
      /** ... */
      private static final long serialVersionUID = 1L;

      public void actionPerformed(final ActionEvent e) {}
    });
    assertThat(menu.getName()).isEmpty();
    assertThat(menu.getText()).isEqualTo("ACTION");
    assertThat(menu.getAction()).isInstanceOf(AbstractAction.class);
  }
}
