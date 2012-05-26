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

import java.io.FileInputStream;

import javax.swing.Action;
import javax.swing.JFrame;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link ExitAction}.
 * 
 * @author croesch
 * @since Date: May 14, 2012
 */
public class ExitActionTest extends DefaultGUITestCase {

  private FrameFixture frameFixture;

  private Thread thread;

  private Action action;

  private Mic1 processor;

  private boolean thrown = false;

  private JFrame frame;

  @Override
  protected void setUpTestCase() throws Exception {
    this.frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() throws Throwable {
        final JFrame f = new JFrame();
        f.setSize(300, 300);
        f.setVisible(true);
        return f;
      }
    });
    this.thread = new Thread(new Runnable() {
      public void run() {
        try {
          Thread.sleep(10000);
        } catch (final InterruptedException e) {
          ExitActionTest.this.thrown = true;
        }
      }
    });
    this.thread.start();

    final String micFile = getClass().getClassLoader().getResource("mic1/hi.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/hi.ijvm").getPath();
    this.processor = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));

    this.action = createAction(this.frame, this.thread, this.processor);
    this.frameFixture = new FrameFixture(robot(), this.frame);

    this.thrown = false;
  }

  public static ExitAction createAction(final JFrame frame, final Thread thread, final Mic1 proc) {
    return GuiActionRunner.execute(new GuiQuery<ExitAction>() {
      @Override
      protected ExitAction executeInEDT() throws Throwable {
        return new ExitAction(frame, thread, proc);
      }
    });
  }

  @Test
  public void testAction() throws InterruptedException {
    printlnMethodName();

    assertThat(this.action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_EXIT.text());
    this.frameFixture.requireVisible();
    assertThat(this.thread.isAlive()).isTrue();
    assertThat(this.processor.isInterrupted()).isFalse();
    assertThat(this.thrown).isFalse();

    perform(this.action);

    // wait for thread to die
    Thread.sleep(100);

    this.frameFixture.requireNotVisible();
    assertThat(this.thread.isAlive()).isFalse();
    assertThat(this.processor.isInterrupted()).isTrue();
    assertThat(this.thrown).isTrue();
  }

  @Test
  public void testAction_FrameNull() throws InterruptedException {
    printlnMethodName();

    this.action = createAction(null, this.thread, this.processor);

    assertThat(this.action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_EXIT.text());
    assertThat(this.thread.isAlive()).isTrue();
    assertThat(this.processor.isInterrupted()).isFalse();
    assertThat(this.thrown).isFalse();

    perform(this.action);

    // wait for thread to die
    Thread.sleep(100);

    assertThat(this.thread.isAlive()).isFalse();
    assertThat(this.processor.isInterrupted()).isTrue();
    assertThat(this.thrown).isTrue();
  }

  @Test
  public void testAction_ThreadNull() throws InterruptedException {
    printlnMethodName();

    this.action = createAction(this.frame, null, this.processor);

    assertThat(this.action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_EXIT.text());
    this.frameFixture.requireVisible();
    assertThat(this.processor.isInterrupted()).isFalse();

    perform(this.action);

    // wait for thread to die
    Thread.sleep(100);

    this.frameFixture.requireNotVisible();
    assertThat(this.processor.isInterrupted()).isTrue();
  }

  @Test
  public void testAction_ProcessorNull() throws InterruptedException {
    printlnMethodName();

    this.action = createAction(this.frame, this.thread, null);

    assertThat(this.action.getValue(Action.NAME)).isEqualTo(GuiText.GUI_ACTIONS_EXIT.text());
    this.frameFixture.requireVisible();
    assertThat(this.thread.isAlive()).isTrue();
    assertThat(this.thrown).isFalse();

    perform(this.action);

    // wait for thread to die
    Thread.sleep(100);

    this.frameFixture.requireNotVisible();
    assertThat(this.thread.isAlive()).isFalse();
    assertThat(this.thrown).isTrue();
  }
}
