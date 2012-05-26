/*
 * Copyright (C) 2011-2012  Christian Roesch
 * 
 * This file is part of micro-debug.
 * 
 * micro-debug is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * micro-debug is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with micro-debug.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.croesch.micro_debug.gui.commons;

import static org.fest.assertions.Assertions.assertThat;

import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultTestCase;

/**
 * Provides test cases for {@link WorkerThread}.
 * 
 * @author croesch
 * @since Date: May 26, 2012
 */
public class WorkerThreadTest extends DefaultTestCase {

  private WorkerThread thread;

  @Override
  protected void setUpDetails() throws Exception {
    this.thread = new WorkerThread("working for test case");
  }

  @After
  public void clean() {
    this.thread.interrupt();
  }

  private static Runnable createRunnableWritingToStream(final String txt, final OutputStream out) {
    return new Runnable() {
      public void run() {
        new PrintStream(out).append(txt);
      }
    };
  }

  private Runnable createInterruptingRunnable(final WorkerThread thread) {
    return new Runnable() {
      public void run() {
        thread.interrupt();
      }
    };
  }

  @Test
  public void testThread() {
    printlnMethodName();

    assertThat(this.thread.getName()).isEqualTo("working for test case");
    assertThat(this.thread.isDaemon()).isTrue();
    assertThat(this.thread.isAlive()).isTrue();
  }

  @Test
  public void testExecutionOrder() throws InterruptedException {
    printlnMethodName();

    final Runnable r1 = createRunnableWritingToStream("1", out);
    final Runnable r2 = createRunnableWritingToStream("2", out);
    final Runnable r3 = createRunnableWritingToStream("3", out);
    final Runnable r4 = createRunnableWritingToStream("4", out);
    this.thread.invokeLater(r1);
    this.thread.invokeLater(r3);
    this.thread.invokeLater(r3);
    this.thread.invokeLater(r4);
    this.thread.invokeLater(r2);

    Thread.sleep(100);

    assertThat(out.toString()).isEqualTo("13342");
  }

  @Test
  public void testInterrupt() throws InterruptedException {
    printlnMethodName();

    final Runnable r1 = createRunnableWritingToStream("1", out);
    final Runnable r2 = createRunnableWritingToStream("2", out);
    final Runnable r3 = createRunnableWritingToStream("3", out);
    this.thread.invokeLater(r1);
    this.thread.invokeLater(r3);
    this.thread.invokeLater(r3);
    this.thread.invokeLater(createInterruptingRunnable(this.thread));
    this.thread.invokeLater(r2);

    Thread.sleep(100);

    assertThat(out.toString()).isEqualTo("133");
  }
}
