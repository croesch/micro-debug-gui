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

import com.github.croesch.micro_debug.commons.Printer;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides some test cases for {@link PrinterTextArea}.
 * 
 * @author croesch
 * @since Date: Mar 17, 2012
 */
public class PrinterTextAreaTest extends DefaultGUITestCase {

  private PrinterTextArea getTA(final String name) {
    return GuiActionRunner.execute(new GuiQuery<PrinterTextArea>() {
      @Override
      protected PrinterTextArea executeInEDT() {
        return new PrinterTextArea(name);
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

    Printer.println("not printed line ...");
    taFixture.requireEmpty();
    assertThat(out.toString()).isEqualTo("not printed line ..." + getLineSeparator());
    out.reset();

    activate(taFixture);

    Printer.println("printed line ...");
    taFixture.requireText("printed line ..." + getLineSeparator());
    assertThat(out.toString()).isEmpty();

    reset(taFixture);
    taFixture.requireEmpty();

    SwingUtilities.invokeAndWait(new Runnable() {
      public void run() {
        Printer.println("printed another line ...");
      }
    });
    taFixture.requireText("printed another line ..." + getLineSeparator());
    assertThat(out.toString()).isEmpty();

    Printer.println("printed line ...");
    taFixture.requireText("printed another line ..." + getLineSeparator() + "printed line ..." + getLineSeparator());
    assertThat(out.toString()).isEmpty();
  }

  private void activate(final JTextComponentFixture taFixture) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        taFixture.targetCastedTo(PrinterTextArea.class).activate();
      }
    });
  }

  private void reset(final JTextComponentFixture taFixture) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        taFixture.targetCastedTo(PrinterTextArea.class).reset();
      }
    });
  }
}
