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
 * Provider for file paths of binary assembler files: micro assembler and macro assembler.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
public interface IBinaryFilePathProvider {

  /**
   * Returns the file path to the binary macro assembler file.
   * 
   * @since Date: Mar 9, 2012
   * @return the file path to the binary macro assembler file.
   */
  String getMacroAssemblerFilePath();

  /**
   * Returns the file path to the binary micro assembler file.
   * 
   * @since Date: Mar 9, 2012
   * @return the file path to the binary micro assembler file.
   */
  String getMicroAssemblerFilePath();

}
