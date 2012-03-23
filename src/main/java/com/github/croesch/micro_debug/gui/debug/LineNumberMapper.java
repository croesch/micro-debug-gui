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
package com.github.croesch.micro_debug.gui.debug;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstraction layer to be able to have a special line numbering available but can this internal handle with normal line
 * numbers. When constructed the real line numbers and the line numbers available for the user are the same.<br>
 * The method {@link #setNewLines(int...)} allows you to add your own scheme of line numbers. These numbers are the
 * numbers available for the user and will be mapped to the real line numbers.<br>
 * <br>
 * The methods {@link #getLineForNumber(int)} and {@link #getNumberForLine(int)} let you get the other representation of
 * the line number you have.<br>
 * <br>
 * <b>line:</b> the line number for the user (may be 2,4,8,10,11,..)<br>
 * <b>number:</b> the real line number, zero-based: 0,1,2,3,..
 * 
 * @author croesch
 * @since Date: Mar 24, 2012
 */
public class LineNumberMapper {

  /** Stores for each real line number the users representation of the line number - <em>the line</em> */
  private final HashMap<Integer, Integer> linesMap = new HashMap<Integer, Integer>();

  /** for each <em>line</em> this map stores the entry of the real line - <em>the number</em> */
  private final HashMap<Integer, Integer> numbersMap = new HashMap<Integer, Integer>();

  /**
   * Set the new scheme of line numbers. The given lines are the representation for the user in the given order. Each
   * line will be mapped to a real line number, according to its position in the array.<br>
   * A <code>null</code>-array or an empty array means to reset the line numbers scheme to the default behavior - lines
   * (representation for the user: e.g. 2,6,7,8,10,..) and numbers (real line number: 0,1,2,3,...) are the same.
   * 
   * @since Date: Mar 24, 2012
   * @param lines the lines available for the user, <b>note:</b> may <em>NOT</em> contain duplicate entries!
   */
  public final void setNewLines(final int ... lines) {
    this.linesMap.clear();
    this.numbersMap.clear();

    if (lines != null && lines.length > 0) {
      for (int number = 0; number < lines.length; ++number) {
        // check if we had this value already
        if (this.linesMap.containsValue(Integer.valueOf(Integer.valueOf(lines[number])))) {
          throw new IllegalStateException("Element already known");
        }

        // put the pair into both maps
        this.linesMap.put(Integer.valueOf(number), Integer.valueOf(lines[number]));
        this.numbersMap.put(Integer.valueOf(lines[number]), Integer.valueOf(number));
      }
    }
  }

  /**
   * Returns the representation for the user of the given real line number.
   * 
   * @since Date: Mar 24, 2012
   * @param number the internal line number: 0,1,2,3,..
   * @return the users representation of the given line number<br>
   *         or -1 if no mapping is found for the given line number
   */
  public final int getLineForNumber(final int number) {
    return getValueFromMap(this.linesMap, number);
  }

  /**
   * Returns the <em>internal</em> representation for the given line number (users view).
   * 
   * @since Date: Mar 24, 2012
   * @param line the line number visible to the user, e.g. 4,5,7,12,..
   * @return the real line number this line belongs to, result is greater than or equal zero.<br>
   *         or -1 if no mapping is found for the given line
   */
  public final int getNumberForLine(final int line) {
    return getValueFromMap(this.numbersMap, line);
  }

  /**
   * Returns the key from the given map. Will return the key if the map contains nothing.
   * 
   * @since Date: Mar 24, 2012
   * @param map the map to fetch the value from
   * @param key the key that specifies the value in the map
   * @return the given key, if the map is empty<br>
   *         the value stored in the map for the given key<br>
   *         -1 if the map contains entries but no one for the given key
   */
  private int getValueFromMap(final Map<Integer, Integer> map, final int key) {
    if (map.isEmpty()) {
      // the line number is the same as the counted number
      return key;
    }

    final Integer retValue = map.get(Integer.valueOf(key));
    if (retValue != null) {
      return retValue.intValue();
    }

    return -1;
  }
}
