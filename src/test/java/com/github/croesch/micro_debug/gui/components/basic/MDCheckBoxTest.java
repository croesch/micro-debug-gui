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

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.JCheckBoxFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link MDCheckBox}.
 * 
 * @author croesch
 * @since Date: Apr 9, 2012
 */
public class MDCheckBoxTest extends DefaultGUITestCase {

  public static MDCheckBox getCheckBox(final String name) {
    return GuiActionRunner.execute(new GuiQuery<MDCheckBox>() {
      @Override
      protected MDCheckBox executeInEDT() throws Throwable {
        return new MDCheckBox(name);
      }
    });
  }

  public static MDCheckBox getCheckBox(final String name, final Action a) {
    return GuiActionRunner.execute(new GuiQuery<MDCheckBox>() {
      @Override
      protected MDCheckBox executeInEDT() throws Throwable {
        return new MDCheckBox(name, a);
      }
    });
  }

  public static MDCheckBox getCheckBox(final String name, final Icon icon) {
    return GuiActionRunner.execute(new GuiQuery<MDCheckBox>() {
      @Override
      protected MDCheckBox executeInEDT() throws Throwable {
        return new MDCheckBox(name, icon);
      }
    });
  }

  public static MDCheckBox getCheckBox(final String name, final String text) {
    return GuiActionRunner.execute(new GuiQuery<MDCheckBox>() {
      @Override
      protected MDCheckBox executeInEDT() throws Throwable {
        return new MDCheckBox(name, text);
      }
    });
  }

  public static MDCheckBox getCheckBox(final String name, final Icon icon, final boolean selected) {
    return GuiActionRunner.execute(new GuiQuery<MDCheckBox>() {
      @Override
      protected MDCheckBox executeInEDT() throws Throwable {
        return new MDCheckBox(name, icon, selected);
      }
    });
  }

  public static MDCheckBox getCheckBox(final String name, final String text, final boolean selected) {
    return GuiActionRunner.execute(new GuiQuery<MDCheckBox>() {
      @Override
      protected MDCheckBox executeInEDT() throws Throwable {
        return new MDCheckBox(name, text, selected);
      }
    });
  }

  public static MDCheckBox getCheckBox(final String name, final String text, final Icon icon) {
    return GuiActionRunner.execute(new GuiQuery<MDCheckBox>() {
      @Override
      protected MDCheckBox executeInEDT() throws Throwable {
        return new MDCheckBox(name, text, icon);
      }
    });
  }

  public static MDCheckBox getCheckBox(final String name, final String text, final Icon icon, final boolean selected) {
    return GuiActionRunner.execute(new GuiQuery<MDCheckBox>() {
      @Override
      protected MDCheckBox executeInEDT() throws Throwable {
        return new MDCheckBox(name, text, icon, selected);
      }
    });
  }

  @Test
  public void testCstr_String() {
    printlnMethodName();

    JCheckBoxFixture cb = new JCheckBoxFixture(robot(), getCheckBox("cb"));
    assertThat(cb.component().getName()).isEqualTo("cb");
    cb.requireNotSelected();

    cb = new JCheckBoxFixture(robot(), getCheckBox(""));
    cb.requireNotSelected();
    assertThat(cb.component().getName()).isEqualTo("");

    cb = new JCheckBoxFixture(robot(), getCheckBox(null));
    cb.requireNotSelected();
    assertThat(cb.component().getName()).isNull();
  }

  @Test
  public void testCstr_StringAction() {
    printlnMethodName();

    final Action a = new AbstractAction() {
      /** default */
      private static final long serialVersionUID = 1L;

      public void actionPerformed(final ActionEvent e) {}
    };
    JCheckBoxFixture cb = new JCheckBoxFixture(robot(), getCheckBox("cb", a));
    cb.requireNotSelected();
    assertThat(cb.component().getName()).isEqualTo("cb");
    assertThat(cb.component().getAction()).isEqualTo(a);

    cb = new JCheckBoxFixture(robot(), getCheckBox("", a));
    cb.requireNotSelected();
    assertThat(cb.component().getName()).isEqualTo("");
    assertThat(cb.component().getAction()).isEqualTo(a);

    cb = new JCheckBoxFixture(robot(), getCheckBox(null, a));
    cb.requireNotSelected();
    assertThat(cb.component().getName()).isNull();
    assertThat(cb.component().getAction()).isEqualTo(a);
  }

  @Test
  public void testCstr_StringIcon() {
    printlnMethodName();

    Icon i = new ImageIcon();
    JCheckBoxFixture cb = new JCheckBoxFixture(robot(), getCheckBox("cb", i));
    cb.requireNotSelected();
    assertThat(cb.component().getName()).isEqualTo("cb");
    assertThat(cb.component().getIcon()).isEqualTo(i);

    cb = new JCheckBoxFixture(robot(), getCheckBox("", (Icon) null));
    cb.requireNotSelected();
    assertThat(cb.component().getName()).isEqualTo("");
    assertThat(cb.component().getIcon()).isNull();

    i = new ImageIcon(new byte[27]);
    cb = new JCheckBoxFixture(robot(), getCheckBox(null, i));
    cb.requireNotSelected();
    assertThat(cb.component().getName()).isNull();
    assertThat(cb.component().getIcon()).isEqualTo(i);
  }

  @Test
  public void testCstr_StringIconBoolean() {
    printlnMethodName();

    Icon i = new ImageIcon();
    JCheckBoxFixture cb = new JCheckBoxFixture(robot(), getCheckBox("cb", i, false));
    cb.requireNotSelected();
    assertThat(cb.component().getName()).isEqualTo("cb");
    assertThat(cb.component().getIcon()).isEqualTo(i);

    cb = new JCheckBoxFixture(robot(), getCheckBox("", (Icon) null, true));
    cb.requireSelected();
    assertThat(cb.component().getName()).isEqualTo("");
    assertThat(cb.component().getIcon()).isNull();

    i = new ImageIcon(new byte[27]);
    cb = new JCheckBoxFixture(robot(), getCheckBox(null, i, true));
    cb.requireSelected();
    assertThat(cb.component().getName()).isNull();
    assertThat(cb.component().getIcon()).isEqualTo(i);
  }

  @Test
  public void testCstr_StringString() {
    printlnMethodName();

    JCheckBoxFixture cb = new JCheckBoxFixture(robot(), getCheckBox("cb", ""));
    assertThat(cb.component().getName()).isEqualTo("cb");
    cb.requireNotSelected();
    cb.requireText("");

    cb = new JCheckBoxFixture(robot(), getCheckBox("", (String) null));
    cb.requireNotSelected();
    cb.requireText("");
    assertThat(cb.component().getName()).isEqualTo("");

    cb = new JCheckBoxFixture(robot(), getCheckBox(null, "txt"));
    cb.requireNotSelected();
    cb.requireText("txt");
    assertThat(cb.component().getName()).isNull();
  }

  @Test
  public void testCstr_StringStringBoolean() {
    printlnMethodName();

    JCheckBoxFixture cb = new JCheckBoxFixture(robot(), getCheckBox("cb", "", true));
    assertThat(cb.component().getName()).isEqualTo("cb");
    cb.requireSelected();
    cb.requireText("");

    cb = new JCheckBoxFixture(robot(), getCheckBox("", (String) null, true));
    cb.requireSelected();
    cb.requireText("");
    assertThat(cb.component().getName()).isEqualTo("");

    cb = new JCheckBoxFixture(robot(), getCheckBox(null, "txt", false));
    cb.requireNotSelected();
    cb.requireText("txt");
    assertThat(cb.component().getName()).isNull();
  }

  @Test
  public void testCstr_StringStringIcon() {
    printlnMethodName();

    Icon i = new ImageIcon();
    JCheckBoxFixture cb = new JCheckBoxFixture(robot(), getCheckBox("cb", null, i));
    cb.requireNotSelected();
    cb.requireText("");
    assertThat(cb.component().getName()).isEqualTo("cb");
    assertThat(cb.component().getIcon()).isEqualTo(i);

    cb = new JCheckBoxFixture(robot(), getCheckBox("", "txt", null));
    cb.requireNotSelected();
    cb.requireText("txt");
    assertThat(cb.component().getName()).isEqualTo("");
    assertThat(cb.component().getIcon()).isNull();

    i = new ImageIcon(new byte[27]);
    cb = new JCheckBoxFixture(robot(), getCheckBox(null, "", i));
    cb.requireNotSelected();
    cb.requireText("");
    assertThat(cb.component().getName()).isNull();
    assertThat(cb.component().getIcon()).isEqualTo(i);
  }

  @Test
  public void testCstr_StringStringIconBoolean() {
    printlnMethodName();

    Icon i = new ImageIcon();
    JCheckBoxFixture cb = new JCheckBoxFixture(robot(), getCheckBox("cb", null, i, true));
    cb.requireSelected();
    cb.requireText("");
    assertThat(cb.component().getName()).isEqualTo("cb");
    assertThat(cb.component().getIcon()).isEqualTo(i);

    cb = new JCheckBoxFixture(robot(), getCheckBox("", "txt", null, false));
    cb.requireNotSelected();
    cb.requireText("txt");
    assertThat(cb.component().getName()).isEqualTo("");
    assertThat(cb.component().getIcon()).isNull();

    i = new ImageIcon(new byte[27]);
    cb = new JCheckBoxFixture(robot(), getCheckBox(null, "", i, true));
    cb.requireSelected();
    cb.requireText("");
    assertThat(cb.component().getName()).isNull();
    assertThat(cb.component().getIcon()).isEqualTo(i);
  }

  @Test
  public void testInvert() {
    printlnMethodName();
    final JCheckBoxFixture cb = new JCheckBoxFixture(robot(), getCheckBox("cb", "text"));
    final Color normalColor = cb.background().target();
    final Color invertedColor = UIManager.getColor("CheckBox.background").darker();
    cb.background().requireEqualTo(normalColor);

    invertColor(cb);
    cb.background().requireEqualTo(invertedColor);

    invertColor(cb);
    cb.background().requireEqualTo(normalColor);

    invertColor(cb);
    cb.background().requireEqualTo(invertedColor);

    invertColor(cb);
    cb.background().requireEqualTo(normalColor);
  }

  private void invertColor(final JCheckBoxFixture cbFixture) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        cbFixture.targetCastedTo(MDCheckBox.class).invert();
      }
    });
  }
}
