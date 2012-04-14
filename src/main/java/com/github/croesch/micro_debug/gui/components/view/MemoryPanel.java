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

  /**
   * Constructs a new {@link MemoryPanel} with a line for each word in the memory.
   * 
   * @since Date: Apr 11, 2012
   * @param name the name of this {@link MemoryPanel}.
   * @param proc the processor being debugged
   */
  public MemoryPanel(final String name, final Mic1 proc) {
    super(name);
    if (proc == null) {
      this.labels = new NumberLabel[TEST_SIZE];
    } else {
      this.labels = new NumberLabel[proc.getMemory().getSize()];
    }
    buildUI(proc);
  }

  /**
   * Builds the panel and the components for it and assembles them.
   * 
   * @since Date: Apr 11, 2012
   * @param proc the processor being debugged
   */
  private void buildUI(final Mic1 proc) {
    final int size;
    if (proc == null) {
      size = TEST_SIZE;
    } else {
      size = proc.getMemory().getSize();
    }

    setLayout(new GridLayout(size, 2, 0, 0));

    for (int i = 0; i < size; ++i) {
      final NumberLabel label;
      if (proc == null) {
        label = new NumberLabel("memValue-" + i, TEST_SIZE);
      } else {
        label = new NumberLabel("memValue-" + i, proc.getMemoryValue(i));
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
    // TODO
  }
}
