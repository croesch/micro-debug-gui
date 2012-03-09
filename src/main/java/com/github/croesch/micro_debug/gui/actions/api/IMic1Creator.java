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
package com.github.croesch.micro_debug.gui.actions.api;

/**
 * Is able to create the micro processor from the given file paths.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
public interface IMic1Creator {

  /**
   * Passes the file paths of the micro assembler and macro assembler files to the creator of the processor.
   * 
   * @since Date: Mar 9, 2012
   * @param microFilePath the file path to the binary micro assembler file.
   * @param macroFilePath the file path to the binary macro assembler file.
   */
  void create(String microFilePath, String macroFilePath);

}
