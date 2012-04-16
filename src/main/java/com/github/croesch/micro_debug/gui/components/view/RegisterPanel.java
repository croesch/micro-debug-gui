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

import java.util.EnumMap;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.annotation.Nullable;
import com.github.croesch.micro_debug.gui.components.basic.MDCheckBox;
import com.github.croesch.micro_debug.gui.components.basic.MDLabel;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.NumberLabel;
import com.github.croesch.micro_debug.mic1.register.Register;

/**
 * The panel that visualises the different {@link Register}s with their values.
 * 
 * @author croesch
 * @since Date: Apr 9, 2012
 */
public class RegisterPanel extends MDPanel {

  /** generated serial version UID */
  private static final long serialVersionUID = 5614777767524193566L;

  /** the stored numbered labels */
  @NotNull
  private final EnumMap<Register, NumberLabel> labels = new EnumMap<Register, NumberLabel>(Register.class);

  /** the stored checkboxes */
  @NotNull
  private final EnumMap<Register, MDCheckBox> checkBoxes = new EnumMap<Register, MDCheckBox>(Register.class);

  /**
   * Constructs a new {@link RegisterPanel} with a line for each register presenting its name and value.
   * 
   * @since Date: Apr 9, 2012
   * @param name the name of this {@link RegisterPanel}.
   */
  public RegisterPanel(final String name) {
    super(name);
    buildUI();
  }

  /**
   * Builds the panel and the components for it and assembles them.
   * 
   * @since Date: Apr 9, 2012
   */
  private void buildUI() {
    final MDPanel compPanel = new MDPanel(getName() + "-inner");
    compPanel.setLayout(new MigLayout("fill, wrap 5", "[]0![]0![grow]0![]0![grow]"));

    for (int i = 0; i < Register.values().length; ++i) {
      final Register r = Register.values()[i];

      final NumberLabel label = new NumberLabel("regValue-" + r.name(), "{0}");
      label.setNumber(r.getValue());
      final MDLabel descLabel = new MDLabel("regDesc-" + r.name(), r);
      final MDLabel spaceL = new MDLabel("spacerL-" + r.name(), null);
      final MDLabel spaceR = new MDLabel("spacerR-" + r.name(), null);
      final MDCheckBox bpCB = new MDCheckBox("bpCB-" + r.name());

      this.labels.put(r, label);
      this.checkBoxes.put(r, bpCB);

      if (i % 2 == 0) {
        bpCB.invert();
        label.setOpaque(true);
        label.invert();
        descLabel.setOpaque(true);
        descLabel.invert();
        spaceL.setOpaque(true);
        spaceL.invert();
        spaceR.setOpaque(true);
        spaceR.invert();
      }

      compPanel.add(bpCB, new CC().grow());
      compPanel.add(descLabel, new CC().grow());
      compPanel.add(spaceL, new CC().grow());
      compPanel.add(label, new CC().grow());
      compPanel.add(spaceR, new CC().grow());
    }

    setLayout(new MigLayout("fill", "0![grow]0!", "0![]0![grow]0!"));
    add(compPanel, "growx");
  }

  /**
   * Performs an update of the values of the labels.
   * 
   * @since Date: Apr 9, 2012
   */
  public final void update() {
    for (final Register r : Register.values()) {
      if (this.labels.get(r).getNumber() != r.getValue()) {
        this.labels.get(r).setNumber(r.getValue());
      }
    }
  }

  /**
   * Returns the check box that is responsible for setting break points for the given {@link Register}.
   * 
   * @since Date: Apr 9, 2012
   * @param r the {@link Register}
   * @return the check box that to select/unselect breakpoints for the given {@link Register}.
   */
  @Nullable
  public final MDCheckBox getCheckBox(final Register r) {
    return this.checkBoxes.get(r);
  }
}
