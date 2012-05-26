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
package com.github.croesch.micro_debug.gui.components;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.annotation.Nullable;
import com.github.croesch.micro_debug.debug.BreakpointManager;
import com.github.croesch.micro_debug.gui.actions.ActionProvider;
import com.github.croesch.micro_debug.gui.actions.Actions;
import com.github.croesch.micro_debug.gui.components.basic.SizedFrame;
import com.github.croesch.micro_debug.gui.components.controller.MainController;
import com.github.croesch.micro_debug.gui.components.view.MainMenuBar;
import com.github.croesch.micro_debug.gui.components.view.MainView;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.IntegerSettings;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * The main frame of the debugger.
 * 
 * @author croesch
 * @since Date: Mar 10, 2012
 */
public final class MainFrame extends SizedFrame {

  /** generated serial version UID */
  private static final long serialVersionUID = 888748757383386602L;

  /** the processor to debug */
  @Nullable
  private final transient Mic1 processor;

  /** the controller of the debugger */
  @NotNull
  private final transient MainController controller;

  /**
   * Constructs the main frame of the application to debug the given processor.
   * 
   * @since Date: Mar 10, 2012
   * @param proc the processor to debug with this frame.
   */
  public MainFrame(final Mic1 proc) {
    super(GuiText.GUI_MAIN_TITLE.text(InternalSettings.NAME, InternalSettings.VERSION),
          new Dimension(IntegerSettings.MAIN_FRAME_WIDTH.getValue(), IntegerSettings.MAIN_FRAME_HEIGHT.getValue()));

    this.processor = proc;

    setLayout(new MigLayout("fill"));

    final BreakpointManager bpm = new BreakpointManager();
    final MainView view = new MainView("main-view", this.processor, bpm);
    this.controller = new MainController(this.processor, view, bpm);

    add(view.getViewComponent(), "grow");

    final ActionProvider actionProvider = new ActionProvider(this.processor, this);
    setJMenuBar(new MainMenuBar(actionProvider));

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        actionProvider.getAction(Actions.EXIT).actionPerformed(null);
      }
    });
  }

  /**
   * Returns the processor that is debugged in this frame.
   * 
   * @since Date: Mar 10, 2012
   * @return the {@link Mic1} that is debugged.
   */
  @Nullable
  public Mic1 getProcessor() {
    return this.processor;
  }

  /**
   * Returns the main controller of the debugger, that controls user input for the GUI.
   * 
   * @since Date: Apr 11, 2012
   * @return the {@link MainController} controlling the input of the user.
   */
  @NotNull
  public MainController getController() {
    return this.controller;
  }
}
