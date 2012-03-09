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
package com.github.croesch.micro_debug.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.github.croesch.micro_debug.gui.actions.api.IBinaryFilePathProvider;
import com.github.croesch.micro_debug.gui.actions.api.IMic1Creator;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Action that passes the file paths fetched from an {@link IBinaryFilePathProvider} to an {@link IMic1Creator}.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
public final class Mic1CreatingAction extends AbstractAction {

  /** generated serial version UID */
  private static final long serialVersionUID = -5379485412067390144L;

  /** the creator of the processor that should receive the file paths */
  private final IMic1Creator processorCreator;

  /** the provider of the file paths */
  private final IBinaryFilePathProvider binFilePathProvider;

  /**
   * Creates an action that'll fetch the file paths from the given {@link IBinaryFilePathProvider} and pass them to the
   * given {@link IMic1Creator}.
   * 
   * @since Date: Mar 9, 2012
   * @param creator the {@link IMic1Creator} to receive the file paths
   * @param filePathProvider the {@link IBinaryFilePathProvider} to fetch the file paths from
   * @param text the text of the action
   */
  public Mic1CreatingAction(final IMic1Creator creator,
                            final IBinaryFilePathProvider filePathProvider,
                            final GuiText text) {
    super(text.text());
    if (creator == null || filePathProvider == null) {
      throw new IllegalArgumentException();
    }
    this.processorCreator = creator;
    this.binFilePathProvider = filePathProvider;
  }

  /**
   * Fetches the file paths from {@link IBinaryFilePathProvider} and passes them to the {@link IMic1Creator}.
   * 
   * @since Date: Mar 9, 2012
   * @param e the {@link ActionEvent} that caused this action
   */
  public void actionPerformed(final ActionEvent e) {
    this.processorCreator.create(this.binFilePathProvider.getMicroAssemblerFilePath(),
                                 this.binFilePathProvider.getMacroAssemblerFilePath());
  }
}
