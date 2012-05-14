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
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link MDMenuItem}.
 * 
 * @author croesch
 * @since Date: Mar 14, 2012
 */
public class MDMenuItemTest extends DefaultGUITestCase {

  public static MDMenuItem getMenuItem(final String name) {
    return GuiActionRunner.execute(new GuiQuery<MDMenuItem>() {
      @Override
      protected MDMenuItem executeInEDT() {
        return new MDMenuItem(name);
      }
    });
  }

  public static MDMenuItem getMenuItem(final String name, final String text) {
    return GuiActionRunner.execute(new GuiQuery<MDMenuItem>() {
      @Override
      protected MDMenuItem executeInEDT() {
        return new MDMenuItem(name, text);
      }
    });
  }

  public static MDMenuItem getMenuItem(final String name, final Action a) {
    return GuiActionRunner.execute(new GuiQuery<MDMenuItem>() {
      @Override
      protected MDMenuItem executeInEDT() {
        return new MDMenuItem(name, a);
      }
    });
  }

  public static MDMenuItem getMenuItem(final String name, final Icon icon) {
    return GuiActionRunner.execute(new GuiQuery<MDMenuItem>() {
      @Override
      protected MDMenuItem executeInEDT() {
        return new MDMenuItem(name, icon);
      }
    });
  }

  public static MDMenuItem getMenuItem(final String name, final String text, final Icon icon) {
    return GuiActionRunner.execute(new GuiQuery<MDMenuItem>() {
      @Override
      protected MDMenuItem executeInEDT() {
        return new MDMenuItem(name, text, icon);
      }
    });
  }

  @Test
  public void testMenuItem_String() {
    printlnMethodName();

    MDMenuItem item = getMenuItem("item");
    assertThat(item.getName()).isEqualTo("item");

    item = getMenuItem("");
    assertThat(item.getName()).isEmpty();

    item = getMenuItem(null);
    assertThat(item.getName()).isNull();

    item = getMenuItem(" .. ");
    assertThat(item.getName()).isEqualTo(" .. ");
  }

  @Test
  public void testMenuItem_StringString() {
    printlnMethodName();

    MDMenuItem item = getMenuItem("item", (String) null);
    assertThat(item.getName()).isEqualTo("item");
    assertThat(item.getText()).isEmpty();

    item = getMenuItem("", "txt");
    assertThat(item.getName()).isEmpty();
    assertThat(item.getText()).isEqualTo("txt");

    item = getMenuItem(null, "");
    assertThat(item.getName()).isNull();
    assertThat(item.getText()).isEmpty();

    item = getMenuItem(" .. ", "12 <-> 34");
    assertThat(item.getName()).isEqualTo(" .. ");
    assertThat(item.getText()).isEqualTo("12 <-> 34");
  }

  @Test
  public void testMenuItem_StringStringIcon() {
    printlnMethodName();

    Icon icon = new ImageIcon(new byte[214]);
    MDMenuItem item = getMenuItem("item", (String) null, icon);
    assertThat(item.getName()).isEqualTo("item");
    assertThat(item.getText()).isEmpty();
    assertThat(item.getIcon()).isEqualTo(icon);

    item = getMenuItem("", "txt", icon);
    assertThat(item.getName()).isEmpty();
    assertThat(item.getText()).isEqualTo("txt");
    assertThat(item.getIcon()).isEqualTo(icon);

    icon = new ImageIcon(new byte[42]);
    item = getMenuItem(null, "", icon);
    assertThat(item.getName()).isNull();
    assertThat(item.getText()).isEmpty();
    assertThat(item.getIcon()).isEqualTo(icon);

    icon = new ImageIcon();
    item = getMenuItem(" .. ", "12 <-> 34", icon);
    assertThat(item.getName()).isEqualTo(" .. ");
    assertThat(item.getText()).isEqualTo("12 <-> 34");
    assertThat(item.getIcon()).isEqualTo(icon);
  }

  @Test
  public void testMenuItem_StringIcon() {
    printlnMethodName();

    Icon icon = new ImageIcon(new byte[214]);
    MDMenuItem item = getMenuItem("item", icon);
    assertThat(item.getName()).isEqualTo("item");
    assertThat(item.getIcon()).isEqualTo(icon);

    item = getMenuItem("", icon);
    assertThat(item.getName()).isEmpty();
    assertThat(item.getIcon()).isEqualTo(icon);

    icon = new ImageIcon(new byte[42]);
    item = getMenuItem(null, icon);
    assertThat(item.getName()).isNull();
    assertThat(item.getIcon()).isEqualTo(icon);

    icon = new ImageIcon();
    item = getMenuItem(" .. ", icon);
    assertThat(item.getName()).isEqualTo(" .. ");
    assertThat(item.getIcon()).isEqualTo(icon);
  }

  @Test
  public void testMenuItem_StringAction() {
    printlnMethodName();

    MDMenuItem item = getMenuItem("item", (Action) null);
    assertThat(item.getName()).isEqualTo("item");
    assertThat(item.getAction()).isNull();

    item = getMenuItem("", new AbstractAction("ACTION") {
      /** ... */
      private static final long serialVersionUID = 1L;

      public void actionPerformed(final ActionEvent e) {}
    });
    assertThat(item.getName()).isEmpty();
    assertThat(item.getText()).isEqualTo("ACTION");
    assertThat(item.getAction()).isInstanceOf(AbstractAction.class);
  }
}
