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

  private MDLabel getLabel(final String name, final Object text) {
    return GuiActionRunner.execute(new GuiQuery<MDLabel>() {
      @Override
      protected MDLabel executeInEDT() {
        return new MDLabel(name, text);
      }
    });
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

    labelFixture = new JLabelFixture(robot(), getLabel("", null));
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
