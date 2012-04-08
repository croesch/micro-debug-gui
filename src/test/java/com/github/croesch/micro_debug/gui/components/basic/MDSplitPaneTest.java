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

import java.awt.Component;

import javax.swing.JSplitPane;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link MDSplitPane}.
 * 
 * @author croesch
 * @since Date: Apr 8, 2012
 */
public class MDSplitPaneTest extends DefaultGUITestCase {

  public static MDSplitPane getSplitPane(final String name) {
    return GuiActionRunner.execute(new GuiQuery<MDSplitPane>() {
      @Override
      protected MDSplitPane executeInEDT() {
        return new MDSplitPane(name);
      }
    });
  }

  public static MDSplitPane getSplitPane(final String name, final int newOrientation) {
    return GuiActionRunner.execute(new GuiQuery<MDSplitPane>() {
      @Override
      protected MDSplitPane executeInEDT() {
        return new MDSplitPane(name, newOrientation);
      }
    });
  }

  public static MDSplitPane getSplitPane(final String name, final int newOrientation, final boolean newContinuousLayout) {
    return GuiActionRunner.execute(new GuiQuery<MDSplitPane>() {
      @Override
      protected MDSplitPane executeInEDT() {
        return new MDSplitPane(name, newOrientation, newContinuousLayout);
      }
    });
  }

  public static MDSplitPane getSplitPane(final String name,
                                         final int newOrientation,
                                         final Component left,
                                         final Component right) {
    return GuiActionRunner.execute(new GuiQuery<MDSplitPane>() {
      @Override
      protected MDSplitPane executeInEDT() {
        return new MDSplitPane(name, newOrientation, left, right);
      }
    });
  }

  public static MDSplitPane getSplitPane(final String name,
                                         final int newOrientation,
                                         final boolean newContinuousLayout,
                                         final Component left,
                                         final Component right) {
    return GuiActionRunner.execute(new GuiQuery<MDSplitPane>() {
      @Override
      protected MDSplitPane executeInEDT() {
        return new MDSplitPane(name, newOrientation, newContinuousLayout, left, right);
      }
    });
  }

  @Test
  public void testSplitPane_String() {
    printlnMethodName();
    JSplitPane pane = getSplitPane("pane");
    assertThat(pane.getName()).isEqualTo("pane");

    pane = getSplitPane("");
    assertThat(pane.getName()).isEmpty();

    pane = getSplitPane(null);
    assertThat(pane.getName()).isNull();

    pane = getSplitPane(".*+*.");
    assertThat(pane.getName()).isEqualTo(".*+*.");
  }

  @Test
  public void testSplitPane_StringInt() {
    printlnMethodName();
    JSplitPane pane = getSplitPane("pane", JSplitPane.HORIZONTAL_SPLIT);
    assertThat(pane.getName()).isEqualTo("pane");
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);

    pane = getSplitPane("", JSplitPane.VERTICAL_SPLIT);
    assertThat(pane.getName()).isEmpty();
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);

    pane = getSplitPane(null, JSplitPane.HORIZONTAL_SPLIT);
    assertThat(pane.getName()).isNull();
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);

    pane = getSplitPane(".*+*.", JSplitPane.VERTICAL_SPLIT);
    assertThat(pane.getName()).isEqualTo(".*+*.");
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
  }

  @Test
  public void testSplitPane_StringIntBoolean() {
    printlnMethodName();
    JSplitPane pane = getSplitPane("pane", JSplitPane.HORIZONTAL_SPLIT, true);
    assertThat(pane.getName()).isEqualTo("pane");
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(pane.isContinuousLayout()).isTrue();

    pane = getSplitPane("", JSplitPane.VERTICAL_SPLIT, true);
    assertThat(pane.getName()).isEmpty();
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(pane.isContinuousLayout()).isTrue();

    pane = getSplitPane(null, JSplitPane.HORIZONTAL_SPLIT, false);
    assertThat(pane.getName()).isNull();
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(pane.isContinuousLayout()).isFalse();

    pane = getSplitPane(".*+*.", JSplitPane.VERTICAL_SPLIT, false);
    assertThat(pane.getName()).isEqualTo(".*+*.");
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(pane.isContinuousLayout()).isFalse();
  }

  @Test
  public void testSplitPane_StringIntComponentComponent() {
    printlnMethodName();

    Component comp1 = MDLabelTest.getLabel("lbl", "someText");
    Component comp2 = MDLabelTest.getLabel("lbl2", "otherText");
    JSplitPane pane = getSplitPane("pane", JSplitPane.HORIZONTAL_SPLIT, comp1, comp2);
    assertThat(pane.getName()).isEqualTo("pane");
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(pane.getLeftComponent()).isEqualTo(comp1);
    assertThat(pane.getRightComponent()).isEqualTo(comp2);

    comp1 = MDButtonTest.getButton("btn", "text");
    comp2 = MDTextAreaTest.getTA("ta");
    pane = getSplitPane("", JSplitPane.VERTICAL_SPLIT, comp1, comp2);
    assertThat(pane.getName()).isEmpty();
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(pane.getLeftComponent()).isEqualTo(comp1);
    assertThat(pane.getRightComponent()).isEqualTo(comp2);

    comp1 = getSplitPane("pane1");
    comp2 = getSplitPane("pane2");
    pane = getSplitPane(null, JSplitPane.HORIZONTAL_SPLIT, comp1, comp2);
    assertThat(pane.getName()).isNull();
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(pane.getLeftComponent()).isEqualTo(comp1);
    assertThat(pane.getRightComponent()).isEqualTo(comp2);

    comp1 = MDPanelTest.getPanel("name");
    comp2 = MDButtonTest.getButton("button", "CLICK");
    pane = getSplitPane(".*+*.", JSplitPane.VERTICAL_SPLIT, comp1, comp2);
    assertThat(pane.getName()).isEqualTo(".*+*.");
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(pane.getLeftComponent()).isEqualTo(comp1);
    assertThat(pane.getRightComponent()).isEqualTo(comp2);
  }

  @Test
  public void testSplitPane_StringIntBooleanComponentComponent() {
    printlnMethodName();

    Component comp1 = MDLabelTest.getLabel("lbl", "someText");
    Component comp2 = MDLabelTest.getLabel("lbl2", "otherText");
    JSplitPane pane = getSplitPane("pane", JSplitPane.HORIZONTAL_SPLIT, true, comp1, comp2);
    assertThat(pane.getName()).isEqualTo("pane");
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(pane.isContinuousLayout()).isTrue();
    assertThat(pane.getLeftComponent()).isEqualTo(comp1);
    assertThat(pane.getRightComponent()).isEqualTo(comp2);

    comp1 = MDButtonTest.getButton("btn", "text");
    comp2 = MDTextAreaTest.getTA("ta");
    pane = getSplitPane("", JSplitPane.VERTICAL_SPLIT, true, comp1, comp2);
    assertThat(pane.getName()).isEmpty();
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(pane.isContinuousLayout()).isTrue();
    assertThat(pane.getLeftComponent()).isEqualTo(comp1);
    assertThat(pane.getRightComponent()).isEqualTo(comp2);

    comp1 = getSplitPane("pane1");
    comp2 = getSplitPane("pane2");
    pane = getSplitPane(null, JSplitPane.HORIZONTAL_SPLIT, false, comp1, comp2);
    assertThat(pane.getName()).isNull();
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.HORIZONTAL_SPLIT);
    assertThat(pane.isContinuousLayout()).isFalse();
    assertThat(pane.getLeftComponent()).isEqualTo(comp1);
    assertThat(pane.getRightComponent()).isEqualTo(comp2);

    comp1 = MDPanelTest.getPanel("name");
    comp2 = MDButtonTest.getButton("button", "CLICK");
    pane = getSplitPane(".*+*.", JSplitPane.VERTICAL_SPLIT, false, comp1, comp2);
    assertThat(pane.getName()).isEqualTo(".*+*.");
    assertThat(pane.getOrientation()).isEqualTo(JSplitPane.VERTICAL_SPLIT);
    assertThat(pane.isContinuousLayout()).isFalse();
    assertThat(pane.getLeftComponent()).isEqualTo(comp1);
    assertThat(pane.getRightComponent()).isEqualTo(comp2);
  }
}
