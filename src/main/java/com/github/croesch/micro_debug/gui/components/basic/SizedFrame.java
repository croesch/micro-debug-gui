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

import java.awt.Dimension;

import javax.swing.JFrame;

import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * A frame that has a predefined size.
 * 
 * @author croesch
 * @since Date: Mar 3, 2012
 */
public abstract class SizedFrame extends JFrame {

  /** generated serial version UID */
  private static final long serialVersionUID = 2169320640431540381L;

  /**
   * Constructs a frame with the given title and the given size. The size will be set and the frame won't be resizable.
   * 
   * @since Date: Mar 3, 2012
   * @param title the title of the frame.
   * @param size the size of the frame
   */
  public SizedFrame(final GuiText title, final Dimension size) {
    super(title.text());
    setPreferredSize(size);
    setSize(size);
    setMaximumSize(size);
    setMinimumSize(size);

    setResizable(false);
  }
}
