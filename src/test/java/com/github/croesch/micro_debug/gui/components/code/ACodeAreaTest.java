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
package com.github.croesch.micro_debug.gui.components.code;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link ACodeArea}.
 * 
 * @author croesch
 * @since Date: Mar 30, 2012
 */
public class ACodeAreaTest extends DefaultGUITestCase {

  private FrameFixture showFrameWithArea() {
    final FrameFixture frameFixture = new FrameFixture(robot(), GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() {
        final JFrame frame = new JFrame();
        frame.add(new ACodeArea(null, null) {
          /** default */
          private static final long serialVersionUID = 1L;
        });
        return frame;
      }
    }));
    frameFixture.show(new Dimension(500, 500));
    return frameFixture;
  }

  @Test
  public void testConstructor_Null() {
    final FrameFixture frame = showFrameWithArea();
    frame.textBox().requireNotEditable();
    frame.textBox().setText("...");
    frame.textBox().requireText("...");
  }
}
