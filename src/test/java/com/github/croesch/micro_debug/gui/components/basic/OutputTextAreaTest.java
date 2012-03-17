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

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.JTextComponentFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.mic1.io.Output;

/**
 * Provides some test cases for {@link OutputTextArea}.
 * 
 * @author croesch
 * @since Date: Mar 14, 2012
 */
public class OutputTextAreaTest extends DefaultGUITestCase {

  private OutputTextArea getTA(final String name) {
    return GuiActionRunner.execute(new GuiQuery<OutputTextArea>() {
      @Override
      protected OutputTextArea executeInEDT() {
        return new OutputTextArea(name);
      }
    });
  }

  @Test
  public void testTextArea() throws InterruptedException, InvocationTargetException {
    printlnMethodName();
    final JTextComponentFixture taFixture = new JTextComponentFixture(robot(), getTA("ta"));
    taFixture.requireNotEditable();
    taFixture.requireEmpty();
    assertThat(taFixture.component().getName()).isEqualTo("ta");

    for (final byte b : "not printed line ...\n".getBytes()) {
      Output.print(b);
    }
    taFixture.requireEmpty();
    assertThat(micOut.toString()).isEqualTo("not printed line ...\n");
    micOut.reset();

    activate(taFixture);

    for (final byte b : "printed line ...".getBytes()) {
      Output.print(b);
    }
    taFixture.requireText("printed line ...");
    assertThat(micOut.toString()).isEmpty();

    reset(taFixture);
    taFixture.requireEmpty();

    SwingUtilities.invokeAndWait(new Runnable() {
      public void run() {
        for (final byte b : "printed another line ...\n".getBytes()) {
          Output.print(b);
        }
      }
    });
    taFixture.requireText("printed another line ...\n");
    assertThat(micOut.toString()).isEmpty();

    for (final byte b : "printed line ...".getBytes()) {
      Output.print(b);
    }
    taFixture.requireText("printed another line ...\nprinted line ...");
    Output.flush();
    taFixture.requireText("printed another line ...\nprinted line ...");
    assertThat(micOut.toString()).isEmpty();
  }

  private void activate(final JTextComponentFixture taFixture) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        taFixture.targetCastedTo(OutputTextArea.class).activate();
      }
    });
  }

  private void reset(final JTextComponentFixture taFixture) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        taFixture.targetCastedTo(OutputTextArea.class).reset();
      }
    });
  }
}
