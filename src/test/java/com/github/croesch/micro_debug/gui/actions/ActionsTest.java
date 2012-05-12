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

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.about.AboutFrame;

/**
 * Provides test cases for {@link Actions}.
 * 
 * @author croesch
 * @since Date: May 12, 2012
 */
public class ActionsTest extends DefaultGUITestCase {

  @Test
  public void testAbout() {
    printlnMethodName();

    perform(Actions.ABOUT);
    final FrameFixture frame = WindowFinder.findFrame(AboutFrame.class).using(robot());
    frame.close();
    perform(Actions.ABOUT);
    frame.requireVisible();
    perform(Actions.ABOUT);
    frame.requireVisible();
    frame.close();
    frame.requireNotVisible();
  }

  private void perform(final Actions act) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        act.perform();
      }
    });
  }

}
