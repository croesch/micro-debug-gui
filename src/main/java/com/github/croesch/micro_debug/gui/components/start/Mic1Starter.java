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
package com.github.croesch.micro_debug.gui.components.start;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.gui.actions.api.IMic1Creator;
import com.github.croesch.micro_debug.gui.components.MainFrame;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Helper for starting the application. Opens the {@link StartFrame} until it returns valid data and creates the
 * processor with the retrieved data.
 * 
 * @author croesch
 * @since Date: Mar 10, 2012
 */
public final class Mic1Starter implements IMic1Creator {

  /**
   * Tries to create the processor and the main view. Will open the a {@link StartFrame} again, if the creation of the
   * processor fails.
   * 
   * @since Date: Mar 10, 2012
   * @param microFilePath the file path to the binary micro assembler file.
   * @param macroFilePath the file path to the binary macro assembler file.
   */
  public void create(final String microFilePath, final String macroFilePath) {
    try {
      createMainFrame(microFilePath, macroFilePath);
    } catch (final IOException e) {
      Utils.logThrownThrowable(e);
      // TODO pass strings to frame
      final JFrame frame = new StartFrame(this);
      showFrame(frame);
    }
  }

  /**
   * Creates the {@link Mic1} and with that the {@link MainFrame} that is the main part of the application.
   * 
   * @since Date: Mar 10, 2012
   * @param microFilePath the file path to the binary micro assembler file.
   * @param macroFilePath the file path to the binary macro assembler file.
   * @throws IOException if something went wrong reading the files.
   */
  private void createMainFrame(final String microFilePath, final String macroFilePath) throws IOException {
    final FileInputStream micFis = new FileInputStream(microFilePath);
    final FileInputStream macFis = new FileInputStream(macroFilePath);

    final JFrame frame = new MainFrame(new Mic1(micFis, macFis));
    showFrame(frame);
  }

  /**
   * Sets the given frame visible with default close operation {@link javax.swing.WindowConstants#DISPOSE_ON_CLOSE}
   * 
   * @since Date: Mar 10, 2012
   * @param frame the frame to show
   */
  private void showFrame(final JFrame frame) {
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setVisible(true);
  }

  /**
   * Makes the {@link StartFrame} visible.
   * 
   * @since Date: Mar 10, 2012
   */
  public void start() {
    try {
      SwingUtilities.invokeAndWait(new Runnable() {
        public void run() {
          final JFrame frame = new StartFrame(Mic1Starter.this);
          showFrame(frame);
        }
      });
    } catch (final InterruptedException e) {
      Utils.logThrownThrowable(e);
    } catch (final InvocationTargetException e) {
      Utils.logThrownThrowable(e);
    }
  }
}
