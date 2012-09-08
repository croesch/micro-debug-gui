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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.github.croesch.micro_debug.argument.AArgument;
import com.github.croesch.micro_debug.commons.Printer;
import com.github.croesch.micro_debug.gui.argument.Files;
import com.github.croesch.micro_debug.gui.argument.Help;
import com.github.croesch.micro_debug.gui.argument.Version;
import com.github.croesch.micro_debug.gui.components.start.Mic1Starter;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;

/**
 * TODO Comment here ...
 * 
 * @author croesch
 * @since Date: Feb 25, 2012
 */
public final class MicroDebug {

  /** the {@link Logger} for this class */
  private static final Logger LOGGER = Logger.getLogger(MicroDebug.class.getName());

  /**
   * Hidden constructor of utility class.
   * 
   * @since Date: Feb 25, 2012
   */
  private MicroDebug() {
    // hidden constructor
  }

  /**
   * TODO Comment here ...
   * 
   * @since Date: Feb 25, 2012
   * @param args the program arguments
   */
  public static void main(final String[] args) {
    Printer.println(GuiText.GREETING.text(InternalSettings.NAME));
    Printer.println(GuiText.BORDER);
    createListOfPossibleArguments();

    final Mic1Starter starter = new Mic1Starter();

    // handle the arguments
    final boolean startApplication = executeTheArguments(starter, AArgument.createArgumentList(args));
    if (startApplication) {
      starter.start();
    }
  }

  /**
   * Creates the list of all known arguments.
   * 
   * @since Date: Feb 28, 2012
   */
  public static void createListOfPossibleArguments() {
    final List<AArgument> arguments = AArgument.values();
    arguments.clear();
    arguments.add(Help.getInstance());
    arguments.add(Version.getInstance());
    arguments.add(Files.getInstance());
  }

  /**
   * Executes all {@link Argument}s in the given {@link Map} with the parameters stored in the map.
   * 
   * @since Date: Dec 3, 2011
   * @param starter the {@link Mic1Starter} that'll be used to start the processor and the debugger
   * @param map the map that contains the {@link Argument}s and the {@link String[]} as parameter for the argument.
   * @return <code>true</code> if the application can be started, <code>false</code> otherwise
   */
  private static boolean executeTheArguments(final Mic1Starter starter, final Map<AArgument, String[]> map) {
    boolean startApplication = true;

    for (final Entry<AArgument, String[]> argumentEntry : map.entrySet()) {
      final AArgument arg = argumentEntry.getKey();
      final String[] params = argumentEntry.getValue();

      LOGGER.fine("Executing argument: " + arg);
      startApplication &= executeArgument(starter, arg, params);
    }

    return startApplication;
  }

  /**
   * Executes the given {@link Argument}s with the given parameters.
   * 
   * @since Date: Sep 8, 2011
   * @param starter the {@link Mic1Starter} that'll be used to start the processor and the debugger
   * @param arg the {@link AArgument} to execute
   * @param params the parameters of the argument
   * @return <code>true</code> if the application can be started, <code>false</code> otherwise
   */
  private static boolean executeArgument(final Mic1Starter starter, final AArgument arg, final String[] params) {
    if (arg instanceof Files) {
      return ((Files) arg).execute(starter, params);
    }
    return arg.execute(params);
  }
}
