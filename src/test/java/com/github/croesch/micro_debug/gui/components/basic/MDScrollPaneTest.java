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

import javax.swing.JScrollPane;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.JScrollPaneFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link MDScrollPane}.
 * 
 * @author croesch
 * @since Date: Apr 7, 2012
 */
public class MDScrollPaneTest extends DefaultGUITestCase {

  public static MDScrollPane getScrollPane(final String name) {
    return GuiActionRunner.execute(new GuiQuery<MDScrollPane>() {
      @Override
      protected MDScrollPane executeInEDT() {
        return new MDScrollPane(name);
      }
    });
  }

  public static MDScrollPane getScrollPane(final String name, final Component view, final int vPolicy, final int hPolicy) {
    return GuiActionRunner.execute(new GuiQuery<MDScrollPane>() {
      @Override
      protected MDScrollPane executeInEDT() {
        return new MDScrollPane(name, view, vPolicy, hPolicy);
      }
    });
  }

  public static MDScrollPane getScrollPane(final String name, final int vPolicy, final int hPolicy) {
    return GuiActionRunner.execute(new GuiQuery<MDScrollPane>() {
      @Override
      protected MDScrollPane executeInEDT() {
        return new MDScrollPane(name, vPolicy, hPolicy);
      }
    });
  }

  public static MDScrollPane getScrollPane(final String name, final Component view) {
    return GuiActionRunner.execute(new GuiQuery<MDScrollPane>() {
      @Override
      protected MDScrollPane executeInEDT() {
        return new MDScrollPane(name, view);
      }
    });
  }

  private void assertHorizontalScrollbarPolicy(final JScrollPaneFixture scrollPaneFixture, final int horizontalPolicy) {
    assertThat(scrollPaneFixture.component().getHorizontalScrollBarPolicy()).isEqualTo(horizontalPolicy);
  }

  private void assertVerticalScrollbarPolicy(final JScrollPaneFixture scrollPaneFixture, final int verticalPolicy) {
    assertThat(scrollPaneFixture.component().getVerticalScrollBarPolicy()).isEqualTo(verticalPolicy);
  }

  @Test
  public void testScrollPane_String() {
    printlnMethodName();

    JScrollPaneFixture scrollPaneFixture = new JScrollPaneFixture(robot(), getScrollPane("pane"));
    assertThat(scrollPaneFixture.component().getName()).isEqualTo("pane");
    assertThat(scrollPaneFixture.component().getViewport().getView()).isNull();
    assertVerticalScrollbarPolicy(scrollPaneFixture, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    assertHorizontalScrollbarPolicy(scrollPaneFixture, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    scrollPaneFixture = new JScrollPaneFixture(robot(), getScrollPane(""));
    assertThat(scrollPaneFixture.component().getName()).isEmpty();
    assertThat(scrollPaneFixture.component().getViewport().getView()).isNull();
    assertVerticalScrollbarPolicy(scrollPaneFixture, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    assertHorizontalScrollbarPolicy(scrollPaneFixture, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    scrollPaneFixture = new JScrollPaneFixture(robot(), getScrollPane(null));
    assertThat(scrollPaneFixture.component().getName()).isNull();
    assertThat(scrollPaneFixture.component().getViewport().getView()).isNull();
    assertVerticalScrollbarPolicy(scrollPaneFixture, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    assertHorizontalScrollbarPolicy(scrollPaneFixture, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }

  @Test
  public void testScrollPane_StringComponentIntInt() {
    printlnMethodName();

    Component view = getScrollPane("innerPane");
    JScrollPaneFixture scrollPaneFixture = new JScrollPaneFixture(robot(),
                                                                  getScrollPane("pane",
                                                                                view,
                                                                                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
    assertThat(scrollPaneFixture.component().getName()).isEqualTo("pane");
    assertThat(scrollPaneFixture.component().getViewport().getView()).isEqualTo(view);
    assertVerticalScrollbarPolicy(scrollPaneFixture, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    assertHorizontalScrollbarPolicy(scrollPaneFixture, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    view = MDLabelTest.getLabel("innerThing", "...");
    scrollPaneFixture = new JScrollPaneFixture(robot(), getScrollPane("$the-pane$", view,
                                                                      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                                      JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
    assertThat(scrollPaneFixture.component().getName()).isEqualTo("$the-pane$");
    assertThat(scrollPaneFixture.component().getViewport().getView()).isEqualTo(view);
    assertVerticalScrollbarPolicy(scrollPaneFixture, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    assertHorizontalScrollbarPolicy(scrollPaneFixture, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
  }

  @Test
  public void testScrollPane_StringIntInt() {
    printlnMethodName();

    JScrollPaneFixture scrollPaneFixture = new JScrollPaneFixture(robot(),
                                                                  getScrollPane("pane",
                                                                                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                                                                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
    assertThat(scrollPaneFixture.component().getName()).isEqualTo("pane");
    assertThat(scrollPaneFixture.component().getViewport().getView()).isNull();
    assertVerticalScrollbarPolicy(scrollPaneFixture, JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    assertHorizontalScrollbarPolicy(scrollPaneFixture, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    scrollPaneFixture = new JScrollPaneFixture(robot(), getScrollPane("$the-pane$",
                                                                      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                                      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    assertThat(scrollPaneFixture.component().getName()).isEqualTo("$the-pane$");
    assertThat(scrollPaneFixture.component().getViewport().getView()).isNull();
    assertVerticalScrollbarPolicy(scrollPaneFixture, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    assertHorizontalScrollbarPolicy(scrollPaneFixture, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }

  @Test
  public void testScrollPane_StringComponent() {
    printlnMethodName();

    Component view = MDTextAreaTest.getTA("%view%");
    JScrollPaneFixture scrollPaneFixture = new JScrollPaneFixture(robot(), getScrollPane("%pane~", view));
    assertThat(scrollPaneFixture.component().getName()).isEqualTo("%pane~");
    assertThat(scrollPaneFixture.component().getViewport().getView()).isEqualTo(view);
    assertVerticalScrollbarPolicy(scrollPaneFixture, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    assertHorizontalScrollbarPolicy(scrollPaneFixture, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    view = MDButtonTest.getButton("THE BUTTON", "hello world");
    scrollPaneFixture = new JScrollPaneFixture(robot(), getScrollPane("_the-pane*", view));
    assertThat(scrollPaneFixture.component().getName()).isEqualTo("_the-pane*");
    assertThat(scrollPaneFixture.component().getViewport().getView()).isEqualTo(view);
    assertVerticalScrollbarPolicy(scrollPaneFixture, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    assertHorizontalScrollbarPolicy(scrollPaneFixture, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }
}
