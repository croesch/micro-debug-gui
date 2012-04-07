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
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.JLabelFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link NumberLabel}.
 * 
 * @author croesch
 * @since Date: Mar 13, 2012
 */
public class NumberLabelTest extends DefaultGUITestCase {

  public static NumberLabel getLabel(final String name, final Object mask) {
    return GuiActionRunner.execute(new GuiQuery<NumberLabel>() {
      @Override
      protected NumberLabel executeInEDT() {
        return new NumberLabel(name, mask);
      }
    });
  }

  @Test
  public void testLabel_NumericalVisualisations() {
    printlnMethodName();
    JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel("label", "{0}"));
    labelFixture.requireText("0");

    setDecimal(labelFixture);
    labelFixture.requireText("0");

    setHexadecimal(labelFixture);
    labelFixture.requireText("0x0");

    setBinary(labelFixture);
    labelFixture.requireText("0000 0000 0000 0000 0000 0000 0000 0000");

    setNumber(labelFixture, 24);
    labelFixture.requireText("0000 0000 0000 0000 0000 0000 0001 1000");

    setDecimal(labelFixture);
    labelFixture.requireText("24");

    setHexadecimal(labelFixture);
    labelFixture.requireText("0x18");

    setNumber(labelFixture, Integer.MIN_VALUE);
    labelFixture.requireText("0x80000000");

    setBinary(labelFixture);
    labelFixture.requireText("1000 0000 0000 0000 0000 0000 0000 0000");

    setDecimal(labelFixture);
    labelFixture.requireText(String.valueOf(Integer.MIN_VALUE));

    setNumber(labelFixture, Integer.MAX_VALUE);
    labelFixture.requireText(String.valueOf(Integer.MAX_VALUE));

    setHexadecimal(labelFixture);
    labelFixture.requireText("0x7FFFFFFF");

    setBinary(labelFixture);
    labelFixture.requireText("0111 1111 1111 1111 1111 1111 1111 1111");

    setNumber(labelFixture, -1);
    labelFixture.requireText("1111 1111 1111 1111 1111 1111 1111 1111");

    setDecimal(labelFixture);
    labelFixture.requireText("-1");

    setHexadecimal(labelFixture);
    labelFixture.requireText("0xFFFFFFFF");

    labelFixture = new JLabelFixture(robot(), getLabel("label", "-->{0}<--"));
    setNumber(labelFixture, 34);
    labelFixture.requireText("-->34<--");

    setHexadecimal(labelFixture);
    labelFixture.requireText("-->0x22<--");

    setBinary(labelFixture);
    labelFixture.requireText("-->0000 0000 0000 0000 0000 0000 0010 0010<--");
  }

  @Test
  public void testLabel() {
    printlnMethodName();
    JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel("label", "{0}"));
    labelFixture.requireText("0");
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
  public void testMask() {
    printlnMethodName();
    JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel("label", "{0}:"));
    labelFixture.requireText("0:");

    setNumber(labelFixture, 12);
    labelFixture.requireText("12:");

    labelFixture = new JLabelFixture(robot(), getLabel("label", "ab {0} ba"));
    labelFixture.requireText("ab 0 ba");

    setNumber(labelFixture, 12);
    labelFixture.requireText("ab 12 ba");

    labelFixture = new JLabelFixture(robot(), getLabel("label", "ab {1} ba"));
    labelFixture.requireText("ab {1} ba");

    setNumber(labelFixture, 12);
    labelFixture.requireText("ab {1} ba");

    labelFixture = new JLabelFixture(robot(), getLabel("label", "{0}{1}{2}3"));
    labelFixture.requireText("0{1}{2}3");

    setNumber(labelFixture, 12);
    labelFixture.requireText("12{1}{2}3");
  }

  @Test
  public void testSetText() {
    printlnMethodName();
    final JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel("label", "{0}"));
    labelFixture.requireText("0");
    assertThat(labelFixture.component().getName()).isEqualTo("label");

    setText(labelFixture, "12");
    labelFixture.requireText("12");

    setText(labelFixture, null);
    labelFixture.requireText("0");

    setText(labelFixture, "1");
    labelFixture.requireText("1");

    setText(labelFixture, "-1");
    labelFixture.requireText("-1");

    setText(labelFixture, "110_2");
    labelFixture.requireText("6");

    setText(labelFixture, "110_");
    labelFixture.requireText("0");
  }

  @Test
  public void testSetNumber() {
    printlnMethodName();
    final JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel("label", "{0}"));
    labelFixture.requireText("0");
    assertThat(labelFixture.component().getName()).isEqualTo("label");

    setNumber(labelFixture, 12);
    labelFixture.requireText("12");

    setNumber(labelFixture, 0);
    labelFixture.requireText("0");

    setNumber(labelFixture, 1);
    labelFixture.requireText("1");

    setNumber(labelFixture, -1);
    labelFixture.requireText("-1");

    setNumber(labelFixture, 0x6);
    labelFixture.requireText("6");

    setNumber(labelFixture, 0xA);
    labelFixture.requireText("10");
  }

  private void setText(final JLabelFixture labelFixture, final String text) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        labelFixture.component().setText(text);
      }
    });
  }

  private void setNumber(final JLabelFixture labelFixture, final int num) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        labelFixture.targetCastedTo(NumberLabel.class).setNumber(num);
      }
    });
  }

  private void setDecimal(final JLabelFixture labelFixture) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        labelFixture.targetCastedTo(NumberLabel.class).viewDecimal();
      }
    });
  }

  private void setHexadecimal(final JLabelFixture labelFixture) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        labelFixture.targetCastedTo(NumberLabel.class).viewHexadecimal();
      }
    });
  }

  private void setBinary(final JLabelFixture labelFixture) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        labelFixture.targetCastedTo(NumberLabel.class).viewBinary();
      }
    });
  }
}
