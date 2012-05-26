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

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.commons.Utils;

/**
 * A thread that is able to execute {@link Runnable}s until being interrupted.
 * 
 * @author croesch
 * @since Date: May 26, 2012
 */
public final class WorkerThread extends Thread {

  /** the {@link Logger} for this class */
  private static final Logger LOGGER = Logger.getLogger(WorkerThread.class.getName());

  /** the lock to acquire for manipulating the data in the queues */
  @NotNull
  private final Lock lock = new ReentrantLock();

  /** condition that signals when the queues contain content */
  @NotNull
  private final Condition notEmpty = this.lock.newCondition();

  /** the {@link Runnable}s to be executed by this {@link WorkerThread} */
  @NotNull
  private final Queue<Runnable> runnables = new ConcurrentLinkedQueue<Runnable>();

  /**
   * Constructs this thread with the given name.
   * 
   * @since Date: May 26, 2012
   * @param name the name of this thread
   */
  public WorkerThread(final String name) {
    super(name);
    setDaemon(true);
    start();
  }

  @Override
  public void run() {
    while (!isInterrupted()) {
      Runnable r = null;

      this.lock.lock();
      try {
        while (this.runnables.isEmpty()) {
          // wait for someone to add a runnable
          this.notEmpty.await();
        }
        r = this.runnables.poll();
      } catch (final InterruptedException e) {
        Utils.logThrownThrowable(e);
      } finally {
        this.lock.unlock();
      }

      if (r != null) {
        r.run();
      }
    }
  }

  /**
   * Adds the given {@link Runnable} to the execution queue. There is no guarantee that the {@link Runnable} will be
   * executed. For example no further {@link Runnable}s will be executed, if the thread has been marked as interrupted.
   * 
   * @since Date: May 26, 2012
   * @param r the {@link Runnable} to execute on this thread.
   */
  public void invokeLater(final Runnable r) {
    this.lock.lock();
    try {
      if (!this.runnables.add(r)) {
        LOGGER.warning("Couldn't add for invocation: " + r);
      }

      this.notEmpty.signal();
    } finally {
      this.lock.unlock();
    }
  }
}
