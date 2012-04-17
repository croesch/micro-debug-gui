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

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;

import javax.swing.JFrame;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.mic1.io.Input;

/**
 * Provides test cases for {@link InputTextField}.
 * 
 * @author croesch
 * @since Date: Mar 17, 2012
 */
public class InputTextFieldTest extends DefaultGUITestCase {

  private FrameFixture showFrameWithField(final String name) {
    final FrameFixture frameFixture = new FrameFixture(robot(), GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() {
        final JFrame frame = new JFrame();
        frame.add(new InputTextField(name));
        return frame;
      }
    }));
    frameFixture.show(new Dimension(200, 50));
    return frameFixture;
  }

  @After
  public void afterTest() {
    robot().releaseKey(KeyEvent.VK_ENTER);
  }

  @Test
  public void testTextField() {
    printlnMethodName();
    final FrameFixture frameFixture = showFrameWithField("tf");
    final JTextComponentFixture tfFixture = frameFixture.textBox("tf");
    tfFixture.requireEmpty();
    assertThat(tfFixture.component().getName()).isEqualTo("tf");
  }

  @Test
  public void testTextFieldInput() throws InterruptedException {
    printlnMethodName();

    Input.setIn(new ByteArrayInputStream("won't be read ..".getBytes()));
    Input.setQuiet(false);

    final FrameFixture frameFixture = showFrameWithField("tf");
    final JTextComponentFixture tfFixture = frameFixture.textBox("tf");
    tfFixture.requireEmpty();
    activate(tfFixture);

    getThreadTyping(robot(), "Test", 500, tfFixture).start();

    assertThat(Input.read()).isEqualTo("T".getBytes()[0]);

    tfFixture.requireEmpty();
    tfFixture.enterText("this");

    assertThat(Input.read()).isEqualTo("e".getBytes()[0]);

    robot().pressAndReleaseKeys(KeyEvent.VK_ENTER);
    tfFixture.requireEmpty();

    assertThat(Input.read()).isEqualTo("s".getBytes()[0]);
    assertThat(Input.read()).isEqualTo("t".getBytes()[0]);
    assertThat(Input.read()).isEqualTo("\n".getBytes()[0]);
    assertThat(Input.read()).isEqualTo("t".getBytes()[0]);
    assertThat(Input.read()).isEqualTo("h".getBytes()[0]);
    assertThat(Input.read()).isEqualTo("i".getBytes()[0]);
    assertThat(Input.read()).isEqualTo("s".getBytes()[0]);
    assertThat(Input.read()).isEqualTo("\n".getBytes()[0]);

    getThreadTyping(robot(), "...", 500, tfFixture).start();

    assertThat(Input.read()).isEqualTo(".".getBytes()[0]);
    assertThat(Input.read()).isEqualTo(".".getBytes()[0]);
    assertThat(Input.read()).isEqualTo(".".getBytes()[0]);
    assertThat(Input.read()).isEqualTo("\n".getBytes()[0]);

    assertThat(out.toString()).isEmpty();
  }

  public static Thread getThreadTyping(final Robot robot,
                                       final String text,
                                       final long timeOut,
                                       final JTextComponentFixture tfFixture) {
    return new Thread() {
      @Override
      public void run() {
        try {
          Thread.sleep(timeOut);
        } catch (final InterruptedException e) {
          Assert.fail();
        }
        tfFixture.enterText(text);
        robot.pressAndReleaseKeys(KeyEvent.VK_ENTER);
      };
    };
  }

  private void activate(final JTextComponentFixture tfFixture) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        tfFixture.targetCastedTo(InputTextField.class).activate();
      }
    });
  }
}
