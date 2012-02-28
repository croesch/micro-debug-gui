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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import com.github.croesch.micro_debug.commons.Printer;
import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;
import com.github.croesch.micro_debug.i18n.Text;

/**
 * Enumeration of all possible command line arguments for the debugger.
 * 
 * @author croesch
 * @since Date: Feb 28, 2012
 */
enum Argument {

  /** argument to view a help about usage of the debugger */
  HELP {
    /** path to the file containing the help text */
    private static final String HELP_FILE = "help-gui.txt";

    @Override
    public boolean execute(final String ... params) {
      final InputStream fileStream = Utils.class.getClassLoader().getResourceAsStream(HELP_FILE);
      Printer.printReader(new InputStreamReader(fileStream));
      try {
        fileStream.close();
      } catch (final IOException e) {
        Utils.logThrownThrowable(e);
      }
      return false;
    }
  },

  /** argument that signalizes an unknown argument */
  ERROR_UNKNOWN {
    @Override
    public boolean execute(final String ... params) {
      return printError(params, Text.UNKNOWN_ARGUMENT, this);
    }
  },

  /** argument that signalizes an argument with the wrong number of parameters */
  ERROR_PARAM_NUMBER {
    @Override
    public boolean execute(final String ... params) {
      return printError(params, Text.ARGUMENT_WITH_WRONG_PARAM_NUMBER, this);
    }
  },

  /** argument to view the version of the debugger */
  VERSION {
    @Override
    public boolean execute(final String ... params) {
      Printer.println(InternalSettings.VERSION);
      return false;
    }
  };

  /** the different ways this argument can be called */
  private final String[] args = new String[2];

  /**
   * Constructs a new argument with its name as long argument and its first letter as short argument. For example
   * ARGUMENT will result in:
   * <ul>
   * <li><code>--argument</code></li>
   * <li><code>-a</code></li>
   * </ul>
   * <br>
   * <b>Note:</b> A <code>_</code> in the name will be translated to a <code>-</code>.<br>
   * This argument will require no parameters.
   * 
   * @since Date: Aug 13, 2011
   * @see #Argument(int)
   */
  private Argument() {
    this.args[0] = "--" + this.name().toLowerCase(Locale.getDefault()).replaceAll("_", "-");
    this.args[1] = "-" + this.name().substring(0, 1).toLowerCase(Locale.getDefault());
  }

  /**
   * Returns whether this argument can be called with the given {@link String}. Will return <code>false</code>, if the
   * given {@link String} is <code>null</code> or if the {@link Argument} is a pseudo-argument that cannot be called.
   * 
   * @since Date: Aug 13, 2011
   * @param argStr the {@link String} to test if it's a possible call for this argument
   * @return <code>true</code>, if this argument can be called with the given {@link String}.<br>
   *         For example <code>--argument</code> will return <code>true</code> for the argument <code>ARGUMENT</code>.
   */
  private boolean matches(final String argStr) {
    if (this == ERROR_PARAM_NUMBER || this == ERROR_UNKNOWN) {
      // make sure we cannot call pseudo-arguments
      return false;
    }
    return argStr != null && (argStr.equals(this.args[0]) || argStr.equals(this.args[1]));
  }

  /**
   * Returns the {@link Argument} that matches with the given {@link String}.
   * 
   * @since Date: Aug 13, 2011
   * @param s the {@link String} that is able to call the returned {@link Argument}.
   * @return the {@link Argument} that matches the given {@link String}, or <code>null</code> if no argument can be
   *         called with the given {@link String}.
   * @see Argument#matches(String)
   */
  static Argument of(final String s) {
    for (final Argument a : values()) {
      if (a.matches(s)) {
        return a;
      }
    }
    return null;
  }

  /**
   * Converts a given array of {@link String}s into a {@link Map} that contains an entry for each valid {@link Argument}
   * and the possible parameters belonging to it.
   * 
   * @since Date: Aug 17, 2011
   * @param args the array of {@link String}s
   * @return the {@link Map} that contains pairs of {@link Argument}s and arrays of strings that contain all parameters
   *         for that argument.
   */
  static Map<Argument, String[]> createArgumentList(final String[] args) {
    // map that'll contain the parsed arguments
    final Map<Argument, String[]> map = new EnumMap<Argument, String[]>(Argument.class);

    if (args != null) {
      // arguments are a valid array, so start iterating
      for (int i = 0; i < args.length; ++i) {

        // handle only non-null-values
        if (args[i] != null) {
          // parse the current argument
          final Argument arg = of(args[i]);
          if (arg == null) {
            // unknown argument, add it to the map as parameter to ERROR_UNKNOWN
            addErrorArgument(map, ERROR_UNKNOWN, args[i]);
          } else {
            // put argument with parameters to the map
            map.put(arg, null);
          }
        }
      }
    }
    return map;
  }

  /**
   * Appends the given value to the array belonging to the given argument in the given map.
   * 
   * @since Date: Dec 2, 2011
   * @param map the map, that'll contain a key argument and an array that contains at least the given value
   * @param arg the argument that is the key in the given map
   * @param value the new value to append to the array, belonging to the key in the map.
   */
  private static void addErrorArgument(final Map<Argument, String[]> map, final Argument arg, final String value) {
    if (!map.containsKey(arg)) {
      // key not in map -> create it
      map.put(arg, new String[] { value });
    } else {
      // key already in map -> append value to the array
      final String[] newArray = appendValueToArray(map.get(arg), value);
      map.put(arg, newArray);
    }
  }

  /**
   * Appends a value to the given array and returns the new created array.
   * 
   * @since Date: Dec 2, 2011
   * @param old the array to append the value to
   * @param value the value to append
   * @return the new array, containing elements from the old array and the new value.
   */
  private static String[] appendValueToArray(final String[] old, final String value) {
    final String[] newArray = new String[old.length + 1];
    // copy array
    System.arraycopy(old, 0, newArray, 0, old.length);
    // set new value
    newArray[old.length] = value;

    return newArray;
  }

  /**
   * Executes commands that result in the specific argument.
   * 
   * @since Date: Dec 2, 2011
   * @param params the parameters of that argument, in case of the pseudo arguments (prefix ERROR_) this array contains
   *        the causes for the error.
   * @return <code>false</code>, if the argument enforces the application to stop
   */
  public abstract boolean execute(String ... params);

  /**
   * Prints the given array of arguments as an error. Returns whether the application can continue.
   * 
   * @since Date: Jan 14, 2012
   * @param params the arguments to be printed as an error
   * @param errorText the {@link Text} that is used to print each argument
   * @param errorArgument the type of error that the arguments are
   * @return <code>true</code>, if the application can start,<br>
   *         <code>false</code> otherwise
   */
  boolean printError(final String[] params, final Text errorText, final Argument errorArgument) {
    if (params == null || params.length == 0) {
      // if there are no arguments then calling this method might be a mistake
      Logger.getLogger(getClass().getName()).warning("No parameters passed to execution of '" + errorArgument.name()
                                                             + "'");
      return true;
    }

    // flag, because we still might have a corrupt array
    boolean argumentFound = false;
    for (final String param : params) {
      if (param != null) {
        // we have the argument, so print it
        Printer.printErrorln(errorText.text(param));
        argumentFound = true;
      }
    }
    if (argumentFound) {
      HELP.execute();
    }
    return !argumentFound;
  }

  /**
   * Releases important references.
   * 
   * @since Date: Feb 15, 2012
   */
  static void releaseAllResources() {
    for (final Argument arg : values()) {
      arg.releaseResources();
    }
  }

  /**
   * Releases important references.
   * 
   * @since Date: Feb 15, 2012
   */
  void releaseResources() {
    // to be overridden by the arguments that decide to release something
  }
}
