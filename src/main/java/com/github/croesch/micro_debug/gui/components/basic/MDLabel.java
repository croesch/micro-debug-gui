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

import javax.swing.Icon;

import com.github.croesch.components.CLabel;
import com.github.croesch.micro_debug.commons.Utils;

/**
 * An extension of {@link CLabel} that contains some default behavior that not each client should have to implement.
 * 
 * @author croesch
 * @since Date: Mar 3, 2012
 */
public class MDLabel extends CLabel {

  /** generated serial version UID */
  private static final long serialVersionUID = -3518553239851224436L;

  /**
   * Constructs a {@link MDLabel} with the given name and the given text.
   * 
   * @since Date: Mar 3, 2012
   * @param name the name of this {@link MDLabel}.
   * @param text the {@link Object} thats {@link String} representation will be set as the text of the label
   * @see #setName(String)
   */
  public MDLabel(final String name, final Object text) {
    super(name, Utils.toString(text));
  }

  /**
   * Constructs a {@link MDLabel} with the given name, the given image and the horizontal alignment as specified.
   * 
   * @since Date: Apr 12, 2012
   * @param name the name of this {@link MDLabel}.
   * @param image the image to be displayed by the label
   * @param horizontalAlignment the horizontal alignment of this label, one of the following:
   *        {@link javax.swing.SwingConstants#LEFT}, {@link javax.swing.SwingConstants#CENTER},
   *        {@link javax.swing.SwingConstants#RIGHT}, {@link javax.swing.SwingConstants#LEADING} or
   *        {@link javax.swing.SwingConstants#TRAILING}
   */
  public MDLabel(final String name, final Icon image, final int horizontalAlignment) {
    super(name, image, horizontalAlignment);
  }

  /**
   * Constructs a {@link MDLabel} with the given name and the given image.
   * 
   * @since Date: Apr 12, 2012
   * @param name the name of this {@link MDLabel}.
   * @param image the image to be displayed by the label
   */
  public MDLabel(final String name, final Icon image) {
    super(name, image);
  }

  /**
   * Constructs a {@link MDLabel} with the given name, the given text, the given image and the horizontal alignment as
   * specified.
   * 
   * @since Date: Apr 12, 2012
   * @param name the name of this {@link MDLabel}.
   * @param text the {@link Object} thats {@link String} representation will be set as the text of the label
   * @param image the image to be displayed by the label
   * @param horizontalAlignment the horizontal alignment of this label, one of the following:
   *        {@link javax.swing.SwingConstants#LEFT}, {@link javax.swing.SwingConstants#CENTER},
   *        {@link javax.swing.SwingConstants#RIGHT}, {@link javax.swing.SwingConstants#LEADING} or
   *        {@link javax.swing.SwingConstants#TRAILING}
   */
  public MDLabel(final String name, final Object text, final Icon image, final int horizontalAlignment) {
    super(name, Utils.toString(text), image, horizontalAlignment);
  }

  /**
   * Constructs a {@link MDLabel} with the given name, the given text and the horizontal alignment as specified.
   * 
   * @since Date: Apr 12, 2012
   * @param name the name of this {@link MDLabel}.
   * @param text the {@link Object} thats {@link String} representation will be set as the text of the label
   * @param horizontalAlignment the horizontal alignment of this label, one of the following:
   *        {@link javax.swing.SwingConstants#LEFT}, {@link javax.swing.SwingConstants#CENTER},
   *        {@link javax.swing.SwingConstants#RIGHT}, {@link javax.swing.SwingConstants#LEADING} or
   *        {@link javax.swing.SwingConstants#TRAILING}
   */
  public MDLabel(final String name, final Object text, final int horizontalAlignment) {
    super(name, Utils.toString(text), horizontalAlignment);
  }

  /**
   * Constructs a {@link MDLabel} with the given name.
   * 
   * @since Date: Apr 12, 2012
   * @param name the name of this {@link MDLabel}.
   */
  public MDLabel(final String name) {
    super(name);
  }
}
