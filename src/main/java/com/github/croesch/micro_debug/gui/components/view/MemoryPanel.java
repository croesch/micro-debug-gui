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
package com.github.croesch.micro_debug.gui.components.view;

import java.awt.GridLayout;

import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.NumberLabel;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * The panel that visualises the content of the main memory.
 * 
 * @author croesch
 * @since Date: Apr 11, 2012
 */
public class MemoryPanel extends MDPanel {

  /** generated serial version UID */
  private static final long serialVersionUID = -3144367254666894663L;

  /** the stored numbered labels */
  private final NumberLabel[] labels;

  /** number of labels in the panel for testing purpose - TODO remove this workaround when FEST works */
  private static final int TEST_SIZE = 100;

  /** the processor being debugged */
  private final Mic1 processor;

  /**
   * Constructs a new {@link MemoryPanel} with a line for each word in the memory.
   * 
   * @since Date: Apr 11, 2012
   * @param name the name of this {@link MemoryPanel}.
   * @param proc the processor being debugged
   */
  public MemoryPanel(final String name, final Mic1 proc) {
    super(name);
    this.processor = proc;
    if (this.processor == null) {
      this.labels = new NumberLabel[TEST_SIZE];
    } else {
      this.labels = new NumberLabel[this.processor.getMemory().getSize()];
    }
    buildUI();
  }

  /**
   * Builds the panel and the components for it and assembles them.
   * 
   * @since Date: Apr 11, 2012
   */
  private void buildUI() {
    final int size;
    if (this.processor == null) {
      size = TEST_SIZE;
    } else {
      size = this.processor.getMemory().getSize();
    }

    setLayout(new GridLayout(size, 2, 0, 0));

    for (int i = 0; i < size; ++i) {
      final NumberLabel label;
      if (this.processor == null) {
        label = new NumberLabel("memValue-" + i, TEST_SIZE);
      } else {
        label = new NumberLabel("memValue-" + i, this.processor.getMemoryValue(i));
      }
      final NumberLabel descLabel = new NumberLabel("memDesc-" + i, "{0}:");
      descLabel.setNumber(i);

      this.labels[i] = label;

      if (i % 2 == 0) {
        label.setOpaque(true);
        label.invert();
        descLabel.setOpaque(true);
        descLabel.invert();
      }

      add(descLabel);
      add(label);
    }
  }

  /**
   * Performs an update of the values of the labels.
   * 
   * @since Date: Apr 9, 2012
   */
  public final void update() {
    if (this.processor != null) {
      for (int i = 0; i < this.labels.length; ++i) {
        if (this.labels[i].getNumber() != this.processor.getMemoryValue(i)) {
          this.labels[i].setNumber(this.processor.getMemoryValue(i));
        }
      }
    }
  }

  /**
   * Returns the value label for the given memory address.
   * 
   * @since Date: Apr 16, 2012
   * @param number the label represents the value at the memory address with this number
   * @return the label showing the value of the memory at the given address.
   */
  public final NumberLabel getLabel(final int number) {
    if (number < 0 || number >= this.labels.length) {
      throw new IllegalArgumentException();
    }
    return this.labels[number];
  }
}
