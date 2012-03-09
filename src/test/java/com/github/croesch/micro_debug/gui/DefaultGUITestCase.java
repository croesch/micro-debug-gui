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
package com.github.croesch.micro_debug.gui;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 * Default test case to be extended by all gui test classes.
 * 
 * @author croesch
 * @since Date: Jan 22, 2012
 */
@Ignore("Just default case")
public class DefaultGUITestCase extends DefaultTestCase {

  private Robot robot;

  @BeforeClass
  public static void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @Override
  protected final void setUpDetails() throws Exception {
    setUpRobot();
    setUpTestCase();
  }

  protected void setUpTestCase() throws Exception {
    // let that be defined by subclasses
  }

  /**
   * Creates this test's <code>{@link Robot}</code> using a new AWT hierarchy.
   */
  protected final void setUpRobot() {
    this.robot = BasicRobot.robotWithNewAwtHierarchy();
    this.robot.settings().delayBetweenEvents(50);
    this.robot.settings().eventPostingDelay(50);
  }

  /**
   * Cleans up resources used by this test's <code>{@link Robot}</code>.
   */
  protected final void cleanUp() {
    this.robot.cleanUp();
  }

  /**
   * Returns this test's <code>{@link Robot}</code>.
   * 
   * @return this test's <code>{@link Robot}</code>
   */
  protected final Robot robot() {
    return this.robot;
  }

  /**
   * Cleans up any resources used in this test. After calling <code>{@link #onTearDown()}</code>, this method cleans up
   * resources used by this test's <code>{@link Robot}</code>.
   * 
   * @see #cleanUp()
   * @see #onTearDown()
   */
  @After
  public final void tearDown() {
    try {
      onTearDown();
    } finally {
      cleanUp();
    }
  }

  /**
   * Subclasses need to clean up resources in this method. This method is called <strong>before</strong> executing
   * <code>{@link #tearDown()}</code>.
   */
  protected void onTearDown() {}

}
