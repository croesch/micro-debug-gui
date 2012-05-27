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

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.NumberLabel;
import com.github.croesch.micro_debug.gui.settings.IntegerSettings;
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

  /** the labels presenting the content of the memory */
  @NotNull
  private final NumberLabel[] labels = new NumberLabel[IntegerSettings.MEMORY_WORDS_VISIBLE.getValue()];

  /** the labels describing the content of the memory - the adresses of the word values */
  @NotNull
  private final NumberLabel[] descLabels = new NumberLabel[IntegerSettings.MEMORY_WORDS_VISIBLE.getValue()];

  /**
   * the processor being debugged
   */
  @NotNull
  private final transient Mic1 processor;

  /** the scroll bar to scroll through the values of the memory */
  @NotNull
  private JScrollBar scrollBar;

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
    buildUI();
  }

  /**
   * Builds the panel and the components for it and assembles them.
   * 
   * @since Date: Apr 11, 2012
   */
  private void buildUI() {
    final JPanel panel = new JPanel(new MigLayout("fill, wrap 2", "[fill, 30%]0![grow,fill]"));
    setLayout(new MigLayout("fill", "[grow,fill][]", "[fill][grow]"));

    for (int i = 0; i < this.labels.length; ++i) {
      final NumberLabel label = new NumberLabel("memValue-" + i, this.processor.getMemoryValue(i));
      final NumberLabel descLabel = new NumberLabel("memDesc-" + i, "{0}:");
      descLabel.setNumber(i);
      descLabel.viewHexadecimal();

      this.labels[i] = label;
      this.descLabels[i] = descLabel;

      if (i % 2 == 0) {
        label.setOpaque(true);
        label.invert();
        descLabel.setOpaque(true);
        descLabel.invert();
      }

      panel.add(descLabel);
      panel.add(label);
    }

    this.scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, this.labels.length, 0, this.processor.getMemory().getSize());
    this.scrollBar.addAdjustmentListener(new AdjustmentListener() {

      public void adjustmentValueChanged(final AdjustmentEvent e) {
        update();
      }
    });

    add(panel);
    add(this.scrollBar);
  }

  /**
   * Performs an update of the values of the labels.
   * 
   * @since Date: Apr 9, 2012
   */
  public final void update() {
    final int value = this.scrollBar.getValue();
    for (int i = 0; i < this.labels.length; ++i) {
      this.labels[i].setText("" + this.processor.getMemoryValue(i + value));
    }
    for (int i = 0; i < this.descLabels.length; ++i) {
      this.descLabels[i].setText("" + (i + value));
    }
  }

  /**
   * Changes the representation of the memory's values to hexadecimal style.
   * 
   * @since Date: May 27, 2012
   */
  public final void viewHexadecimal() {
    for (int i = 0; i < this.labels.length; ++i) {
      this.labels[i].viewHexadecimal();
    }
  }

  /**
   * Changes the representation of the memory's values to decimal style.
   * 
   * @since Date: May 27, 2012
   */
  public final void viewDecimal() {
    for (int i = 0; i < this.labels.length; ++i) {
      this.labels[i].viewDecimal();
    }
  }

  /**
   * Changes the representation of the memory's values to binary style.
   * 
   * @since Date: May 27, 2012
   */
  public final void viewBinary() {
    for (int i = 0; i < this.labels.length; ++i) {
      this.labels[i].viewBinary();
    }
  }
}
