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
package com.github.croesch.micro_debug.gui.actions;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.Action;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.about.AboutFrame;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Provides test cases for {@link AboutAction}.
 * 
 * @author croesch
 * @since Date: May 12, 2012
 */
public class AboutActionTest extends DefaultGUITestCase {

  private AboutAction action;

  @Override
  protected void setUpTestCase() throws Exception {
    this.action = GuiActionRunner.execute(new GuiQuery<AboutAction>() {
      @Override
      protected AboutAction executeInEDT() throws Throwable {
        return new AboutAction();
      }
    });
  }

  @Test
  public void testAction() {
    printlnMethodName();

    assertThat(this.action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_ABOUT.text());

    perform(this.action);
    final FrameFixture frame = WindowFinder.findFrame(AboutFrame.class).using(robot());
    frame.close();
    perform(this.action);
    frame.requireVisible();
    perform(this.action);
    frame.requireVisible();
    frame.close();
    frame.requireNotVisible();
  }
}
