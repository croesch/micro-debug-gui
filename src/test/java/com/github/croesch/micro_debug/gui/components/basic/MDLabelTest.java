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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.JLabelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link MDLabel}.
 * 
 * @author croesch
 * @since Date: Mar 3, 2012
 */
public class MDLabelTest extends DefaultGUITestCase {

  public static MDLabel getLabel(final String name) {
    return GuiActionRunner.execute(new GuiQuery<MDLabel>() {
      @Override
      protected MDLabel executeInEDT() {
        return new MDLabel(name);
      }
    });
  }

  public static MDLabel getLabel(final String name, final Icon image) {
    return GuiActionRunner.execute(new GuiQuery<MDLabel>() {
      @Override
      protected MDLabel executeInEDT() {
        return new MDLabel(name, image);
      }
    });
  }

  public static MDLabel getLabel(final String name, final Object text) {
    return GuiActionRunner.execute(new GuiQuery<MDLabel>() {
      @Override
      protected MDLabel executeInEDT() {
        return new MDLabel(name, text);
      }
    });
  }

  public static MDLabel getLabel(final String name, final Icon image, final int horizontalAlignment) {
    return GuiActionRunner.execute(new GuiQuery<MDLabel>() {
      @Override
      protected MDLabel executeInEDT() {
        return new MDLabel(name, image, horizontalAlignment);
      }
    });
  }

  public static MDLabel getLabel(final String name, final Object text, final int horizontalAlignment) {
    return GuiActionRunner.execute(new GuiQuery<MDLabel>() {
      @Override
      protected MDLabel executeInEDT() {
        return new MDLabel(name, text, horizontalAlignment);
      }
    });
  }

  public static MDLabel getLabel(final String name, final Object text, final Icon icon, final int horizontalAlignment) {
    return GuiActionRunner.execute(new GuiQuery<MDLabel>() {
      @Override
      protected MDLabel executeInEDT() {
        return new MDLabel(name, text, icon, horizontalAlignment);
      }
    });
  }

  @Test
  public void testCstr_String() {
    printlnMethodName();
    JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel("label"));
    assertThat(labelFixture.component().getName()).isEqualTo("label");

    labelFixture = new JLabelFixture(robot(), getLabel(""));
    assertThat(labelFixture.component().getName()).isEmpty();

    labelFixture = new JLabelFixture(robot(), getLabel(null));
    assertThat(labelFixture.component().getName()).isNull();
  }

  @Test
  public void testCstr_StringIcon() {
    printlnMethodName();
    Icon i = new ImageIcon();
    JLabelFixture label = new JLabelFixture(robot(), getLabel("label", i));
    assertThat(label.component().getName()).isEqualTo("label");
    assertThat(label.component().getIcon()).isEqualTo(i);

    label = new JLabelFixture(robot(), getLabel("", i));
    assertThat(label.component().getName()).isEmpty();
    assertThat(label.component().getIcon()).isEqualTo(i);

    i = new ImageIcon(new byte[214]);
    label = new JLabelFixture(robot(), getLabel(null, i));
    assertThat(label.component().getName()).isNull();
    assertThat(label.component().getIcon()).isEqualTo(i);
  }

  @Test
  public void testCstr_StringIconInt() {
    printlnMethodName();
    Icon i = new ImageIcon();
    JLabelFixture label = new JLabelFixture(robot(), getLabel("label", i, SwingConstants.LEFT));
    assertThat(label.component().getName()).isEqualTo("label");
    assertThat(label.component().getIcon()).isEqualTo(i);
    assertThat(label.component().getHorizontalAlignment()).isEqualTo(SwingConstants.LEFT);

    label = new JLabelFixture(robot(), getLabel("", i, SwingConstants.RIGHT));
    assertThat(label.component().getName()).isEmpty();
    assertThat(label.component().getIcon()).isEqualTo(i);
    assertThat(label.component().getHorizontalAlignment()).isEqualTo(SwingConstants.RIGHT);

    i = new ImageIcon(new byte[214]);
    label = new JLabelFixture(robot(), getLabel(null, i, SwingConstants.CENTER));
    assertThat(label.component().getName()).isNull();
    assertThat(label.component().getIcon()).isEqualTo(i);
    assertThat(label.component().getHorizontalAlignment()).isEqualTo(SwingConstants.CENTER);
  }

  @Test
  public void testCstr_StringObject() {
    printlnMethodName();
    JLabelFixture label = new JLabelFixture(robot(), getLabel("label", "text"));
    assertThat(label.component().getName()).isEqualTo("label");
    label.requireText("text");

    label = new JLabelFixture(robot(), getLabel("", 12));
    assertThat(label.component().getName()).isEmpty();
    label.requireText("12");

    label = new JLabelFixture(robot(), getLabel(null, ""));
    assertThat(label.component().getName()).isNull();
    label.requireText("");
  }

  @Test
  public void testCstr_StringObjectIconInt() {
    printlnMethodName();

    Icon i = new ImageIcon("asdasldj".getBytes());
    JLabelFixture label = new JLabelFixture(robot(), getLabel("label", "text", i, SwingConstants.TRAILING));
    assertThat(label.component().getName()).isEqualTo("label");
    label.requireText("text");
    assertThat(label.component().getHorizontalAlignment()).isEqualTo(SwingConstants.TRAILING);
    assertThat(label.component().getIcon()).isEqualTo(i);

    label = new JLabelFixture(robot(), getLabel("", 12, i, SwingConstants.RIGHT));
    assertThat(label.component().getName()).isEmpty();
    label.requireText("12");
    assertThat(label.component().getHorizontalAlignment()).isEqualTo(SwingConstants.RIGHT);
    assertThat(label.component().getIcon()).isEqualTo(i);

    i = new ImageIcon("foo bar".getBytes());
    label = new JLabelFixture(robot(), getLabel(null, "", i, SwingConstants.LEADING));
    assertThat(label.component().getName()).isNull();
    label.requireText("");
    assertThat(label.component().getHorizontalAlignment()).isEqualTo(SwingConstants.LEADING);
    assertThat(label.component().getIcon()).isEqualTo(i);
  }

  @Test
  public void testCstr_StringObjectInt() {
    printlnMethodName();
    JLabelFixture label = new JLabelFixture(robot(), getLabel("label", "text", SwingConstants.TRAILING));
    assertThat(label.component().getName()).isEqualTo("label");
    label.requireText("text");
    assertThat(label.component().getHorizontalAlignment()).isEqualTo(SwingConstants.TRAILING);

    label = new JLabelFixture(robot(), getLabel("", 12, SwingConstants.RIGHT));
    assertThat(label.component().getName()).isEmpty();
    label.requireText("12");
    assertThat(label.component().getHorizontalAlignment()).isEqualTo(SwingConstants.RIGHT);

    label = new JLabelFixture(robot(), getLabel(null, "", SwingConstants.LEADING));
    assertThat(label.component().getName()).isNull();
    label.requireText("");
    assertThat(label.component().getHorizontalAlignment()).isEqualTo(SwingConstants.LEADING);
  }

  @Test
  public void testLabel() {
    printlnMethodName();
    JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel("label", "text"));
    labelFixture.requireText("text");
    assertThat(labelFixture.component().getName()).isEqualTo("label");

    labelFixture = new JLabelFixture(robot(), getLabel("", 12));
    labelFixture.requireText("12");
    assertThat(labelFixture.component().getName()).isEmpty();

    labelFixture = new JLabelFixture(robot(), getLabel(null, ""));
    labelFixture.requireText("");
    assertThat(labelFixture.component().getName()).isNull();

    labelFixture = new JLabelFixture(robot(), getLabel(""));
    labelFixture.requireText("");
    assertThat(labelFixture.component().getName()).isEmpty();
  }

  @Test
  public void testInvert() {
    printlnMethodName();
    final JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel("label", "text"));
    final Color normalColor = labelFixture.background().target();
    final Color invertedColor = UIManager.getColor("Label.background").darker();
    labelFixture.background().requireEqualTo(normalColor);

    invertColor(labelFixture);
    labelFixture.background().requireEqualTo(invertedColor);

    invertColor(labelFixture);
    labelFixture.background().requireEqualTo(normalColor);

    invertColor(labelFixture);
    labelFixture.background().requireEqualTo(invertedColor);

    invertColor(labelFixture);
    labelFixture.background().requireEqualTo(normalColor);
  }

  private void invertColor(final JLabelFixture labelFixture) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        labelFixture.targetCastedTo(MDLabel.class).invert();
      }
    });
  }
}
