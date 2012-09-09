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

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.gui.components.basic.InputTextField;
import com.github.croesch.micro_debug.gui.components.basic.MDButton;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.MDScrollPane;
import com.github.croesch.micro_debug.gui.components.basic.OutputTextArea;

/**
 * A view visualising in- and output of the processor. Simply contains text components showing all output and
 * possibility to enter input for {@link com.github.croesch.micro_debug.mic1.Mic1}.
 * 
 * @author croesch
 * @since Date: Apr 17, 2012
 */
final class ProcessorTAView extends MDPanel {

  /** generated serial version UID */
  private static final long serialVersionUID = -1013712443121192156L;

  /**
   * Creates the view that visualises in- and output of the processor. Simply contains text components showing all
   * output and possibility to enter input for {@link com.github.croesch.micro_debug.mic1.Mic1}.
   * 
   * @since Date: Apr 17, 2012
   * @param name the name of this {@link ProcessorTAView}.
   */
  public ProcessorTAView(final String name) {
    super(name);

    final OutputTextArea ta = new OutputTextArea("output-ta");
    ta.activate();

    final InputTextField tf = new InputTextField("input-tf");
    tf.activate();

    setLayout(new MigLayout("fill, wrap 2", "[grow,fill][]", "[grow,fill][]"));
    add(new MDScrollPane("output-scrollpane", ta), "span 2");
    add(tf);
    add(new MDButton("input-tf-button", tf.getAction()));
  }
}
