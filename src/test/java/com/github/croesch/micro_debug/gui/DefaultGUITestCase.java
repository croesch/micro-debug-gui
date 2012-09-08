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

import static org.fest.assertions.Assertions.assertThat;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JFileChooserFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;

import com.github.croesch.micro_debug.gui.actions.AbstractExecuteOnWorkerThreadAction;
import com.github.croesch.micro_debug.gui.actions.ActionProvider;
import com.github.croesch.micro_debug.gui.actions.api.IActionProvider.Actions;
import com.github.croesch.micro_debug.gui.commons.WorkerThread;
import com.github.croesch.micro_debug.gui.components.MainFrame;
import com.github.croesch.micro_debug.gui.components.start.Mic1Starter;

/**
 * Default test case to be extended by all gui test classes.
 * 
 * @author croesch
 * @since Date: Jan 22, 2012
 */
@Ignore("Just default case")
public class DefaultGUITestCase extends DefaultTestCase {

  private static final int NORMAL_DELAY = 50;

  private Robot robot;

  private static final WorkerThread worker = new WorkerThread("worker for test cases");

  private List<Throwable> thrownInOtherThreads = new ArrayList<Throwable>();

  @BeforeClass
  public static void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @Override
  protected final void setUpDetails() throws Exception {
    setUpRobot();
    this.thrownInOtherThreads = new ArrayList<Throwable>();
    setUpTestCase();
  }

  protected void setUpTestCase() throws Exception {
    // let that be defined by subclasses
  }

  /**
   * Creates this test's <code>{@link Robot}</code> using a new AWT hierarchy.
   */
  protected final void setUpRobot() {
    this.robot = BasicRobot.robotWithCurrentAwtHierarchy();
    this.robot.settings().delayBetweenEvents(NORMAL_DELAY);
    this.robot.settings().eventPostingDelay(NORMAL_DELAY);
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
      if (!this.thrownInOtherThreads.isEmpty()) {
        for (final Throwable t : this.thrownInOtherThreads) {
          t.printStackTrace();
        }
        Assert.fail("Exception occured");
      }
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

  protected void showInFrame(final JPanel panel) {
    GuiActionRunner.execute(new GuiTask() {

      @Override
      protected void executeInEDT() throws Throwable {
        final JFrame f = new JFrame();
        f.setLayout(new BorderLayout());
        f.add(panel, BorderLayout.CENTER);
        f.setSize(500, 500);
        f.setVisible(true);
      }
    });
  }

  protected void enterText(final JTextComponentFixture textBox, final String string) {
    for (final char c : string.toCharArray()) {
      switch (c) {
        case '/':
          textBox.pressKey(KeyEvent.VK_SHIFT).pressAndReleaseKeys(KeyEvent.VK_7).releaseKey(KeyEvent.VK_SHIFT);
          break;
        default:
          textBox.enterText(String.valueOf(c));
          break;
      }
    }
  }

  protected void slowDownRobot() {
    robot().settings().eventPostingDelay(10 * NORMAL_DELAY);
    robot().settings().delayBetweenEvents(2 * NORMAL_DELAY);
  }

  protected void perform(final Action act) {
    perform(null, act);
  }

  protected void perform(final ActionProvider provider, final Action act) {
    // let the worker wait a bit before executing the action, that we can test the state of actions
    invokeSleepActionOnWorker(10);

    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        act.actionPerformed(null);
      }
    });

    if (act instanceof AbstractExecuteOnWorkerThreadAction) {
      assertWorkerActionsEnabled(provider, false);
      waitForWorkerThreadBeingIdle();
    }
    assertWorkerActionsEnabled(provider, true);
  }

  private void invokeSleepActionOnWorker(final long millis) {
    worker.invokeLater(new Runnable() {
      public void run() {
        try {
          Thread.sleep(millis);
        } catch (final InterruptedException e) {
          DefaultGUITestCase.this.thrownInOtherThreads.add(e);
        }
      }
    });
  }

  private void assertWorkerActionsEnabled(final ActionProvider provider, final boolean enabled) {
    if (provider != null) {
      for (final Actions act : Actions.values()) {
        final Action action = provider.getAction(act);
        if (action != null && action instanceof AbstractExecuteOnWorkerThreadAction) {
          assertThat(action.isEnabled()).isEqualTo(enabled);
        }
      }
      assertThat(provider.getAction(Actions.INTERRUPT).isEnabled()).isEqualTo(!enabled);
    }
  }

  protected static WorkerThread getWorker() {
    return worker;
  }

  protected static void waitForWorkerThreadBeingIdle() {
    final ReentrantLock lock = new ReentrantLock();
    final Condition wait = lock.newCondition();

    worker.invokeLater(new Runnable() {
      public void run() {
        lock.lock();
        try {
          wait.signal();
        } finally {
          lock.unlock();
        }
      }
    });

    lock.lock();
    try {
      wait.await();
    } catch (final InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public List<Throwable> getThrownInOtherThreads() {
    return this.thrownInOtherThreads;
  }

  protected FrameFixture getMainFrame(final String micFile, final String macFile) {
    new Mic1Starter().start();

    FrameFixture frame = WindowFinder.findFrame("start-frame").using(robot());
    frame.requireVisible();
    frame.button("okay").requireDisabled();

    frame.button("micro-assembler-file-browse").click();
    final JFileChooserFixture fileChooser = new JFileChooserFixture(robot());
    fileChooser.selectFile(new File(micFile));
    fileChooser.approve();
    frame.textBox("micro-assembler-file-path").requireText(micFile);
    enterText(frame.textBox("macro-assembler-file-path"), macFile);
    frame.textBox("macro-assembler-file-path").requireText(macFile);

    frame.button("okay").click();

    frame = WindowFinder.findFrame(MainFrame.class).using(robot());
    frame.requireVisible();
    return frame;
  }

  protected void closeStartFrame(final FrameFixture frame) {
    if (frame != null && frame.component().isShowing() && frame.component().isVisible()) {
      frame.close();
    }
  }
}
