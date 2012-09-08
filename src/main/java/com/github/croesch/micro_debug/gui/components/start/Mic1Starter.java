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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.error.MacroFileFormatException;
import com.github.croesch.micro_debug.error.MicroFileFormatException;
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

  /** the initial file path of the macro code file */
  @NotNull
  private String initialMacroPath = "";

  /** the initial file path of the micro code file */
  @NotNull
  private String initialMicroPath = "";

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
    } catch (final MicroFileFormatException e) {
      Utils.logThrownThrowable(e);
      final JFrame frame = new StartFrame(null, macroFilePath, this);
      showFrame(frame);
    } catch (final MacroFileFormatException e) {
      Utils.logThrownThrowable(e);
      final JFrame frame = new StartFrame(microFilePath, null, this);
      showFrame(frame);
    }
  }

  /**
   * Creates the {@link Mic1} and with that the {@link MainFrame} that is the main part of the application.
   * 
   * @since Date: Mar 10, 2012
   * @param microFilePath the file path to the binary micro assembler file.
   * @param macroFilePath the file path to the binary macro assembler file.
   * @throws MicroFileFormatException if something went wrong reading the micro file.
   * @throws MacroFileFormatException if something went wrong reading the macro file.
   */
  private void createMainFrame(final String microFilePath, final String macroFilePath) throws MicroFileFormatException,
                                                                                      MacroFileFormatException {
    FileInputStream micFis;
    try {
      micFis = new FileInputStream(microFilePath);
    } catch (final FileNotFoundException e) {
      throw new MicroFileFormatException(e.getMessage(), e);
    }

    FileInputStream macFis;
    try {
      macFis = new FileInputStream(macroFilePath);
    } catch (final FileNotFoundException e) {
      if (micFis != null) {
        try {
          micFis.close();
        } catch (final IOException e1) {
          Utils.logThrownThrowable(e1);
        }
      }
      throw new MacroFileFormatException(e.getMessage(), e);
    }

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
          if (!Mic1Starter.this.initialMicroPath.trim().equals("")
              || !Mic1Starter.this.initialMacroPath.trim().equals("")) {
            create(Mic1Starter.this.initialMicroPath, Mic1Starter.this.initialMacroPath);
          } else {
            final JFrame frame = new StartFrame(Mic1Starter.this);
            showFrame(frame);
          }
        }
      });
    } catch (final InterruptedException e) {
      Utils.logThrownThrowable(e);
    } catch (final InvocationTargetException e) {
      Utils.logThrownThrowable(e);
    }
  }

  /**
   * Sets the initial file path of the micro code file. If both files (macro and micro code) are specified, the
   * {@link StartFrame} won't appear.
   * 
   * @since Date: Sep 8, 2012
   * @param micFile the file path of the micro code
   */
  public void setMicroFilePath(final String micFile) {
    if (micFile == null) {
      this.initialMicroPath = "";
    } else {
      this.initialMicroPath = micFile;
    }
  }

  /**
   * Sets the initial file path of the macro code file. If both files (macro and micro code) are specified, the
   * {@link StartFrame} won't appear.
   * 
   * @since Date: Sep 8, 2012
   * @param macFile the file path of the macro code
   */
  public void setMacroFilePath(final String macFile) {
    if (macFile == null) {
      this.initialMacroPath = "";
    } else {
      this.initialMacroPath = macFile;
    }
  }

  /**
   * Returns the initial file path of the micro code file.
   * 
   * @since Date: Sep 8, 2012
   * @return the file path of the micro code
   */
  @NotNull
  public String getMicroFilePath() {
    return this.initialMicroPath;
  }

  /**
   * Returns the initial file path of the macro code file.
   * 
   * @since Date: Sep 8, 2012
   * @return the file path of the macro code
   */
  @NotNull
  public String getMacroFilePath() {
    return this.initialMacroPath;
  }
}
