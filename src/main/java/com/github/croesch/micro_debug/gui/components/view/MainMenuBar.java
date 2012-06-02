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

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.actions.api.IActionProvider;
import com.github.croesch.micro_debug.gui.actions.api.IActionProvider.Actions;
import com.github.croesch.micro_debug.gui.components.basic.MDMenu;
import com.github.croesch.micro_debug.gui.components.basic.MDMenuItem;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.KeyStrokes;

/**
 * The menu bar for the main frame.
 * 
 * @author croesch
 * @since Date: May 14, 2012
 */
public final class MainMenuBar extends JMenuBar {

  /** generated serial version UID */
  private static final long serialVersionUID = -6237620875510916508L;

  /**
   * Constructs the menu bar for the main frame.
   * 
   * @since Date: May 14, 2012
   * @param provider the {@link IActionProvider} providing the actions for the menu.
   */
  public MainMenuBar(final IActionProvider provider) {
    add(createMicroDebugMenu(provider));
    add(createProcessorMenu(provider));
    add(createHelpMenu(provider));
  }

  /**
   * Constructs the help menu and its items.
   * 
   * @since Date: May 14, 2012
   * @param provider the {@link IActionProvider} providing the actions for the menu.
   * @return the constructed help menu
   */
  @NotNull
  private JMenu createHelpMenu(final IActionProvider provider) {
    final MDMenu menu = new MDMenu("help", GuiText.GUI_MENU_HELP.text());

    final MDMenuItem help = new MDMenuItem("help-item", provider.getAction(Actions.HELP));
    help.setAccelerator(KeyStrokes.HELP.stroke());
    final MDMenuItem about = new MDMenuItem("about", provider.getAction(Actions.ABOUT));
    about.setAccelerator(KeyStrokes.ABOUT.stroke());

    menu.add(help);
    menu.addSeparator();
    menu.add(about);
    return menu;
  }

  /**
   * Constructs the processor menu and its items.
   * 
   * @since Date: May 14, 2012
   * @param provider the {@link IActionProvider} providing the actions for the menu.
   * @return the constructed processor menu
   */
  @NotNull
  private JMenu createProcessorMenu(final IActionProvider provider) {
    final MDMenu menu = new MDMenu("processor", GuiText.GUI_MENU_PROCESSOR.text());

    final MDMenuItem microStep = new MDMenuItem("micro-step", provider.getAction(Actions.MICRO_STEP));
    microStep.setAccelerator(KeyStrokes.MICRO_STEP.stroke());
    final MDMenuItem step = new MDMenuItem("step", provider.getAction(Actions.STEP));
    step.setAccelerator(KeyStrokes.STEP.stroke());
    final MDMenuItem run = new MDMenuItem("run", provider.getAction(Actions.RUN));
    run.setAccelerator(KeyStrokes.RUN.stroke());
    final MDMenuItem reset = new MDMenuItem("reset", provider.getAction(Actions.RESET));
    reset.setAccelerator(KeyStrokes.RESET.stroke());

    menu.add(microStep);
    menu.add(step);
    menu.add(run);
    menu.addSeparator();
    menu.add(reset);
    return menu;
  }

  /**
   * Constructs the micro-debug menu and its items.
   * 
   * @since Date: May 14, 2012
   * @param provider the {@link IActionProvider} providing the actions for the menu.
   * @return the constructed micro-debug menu
   */
  @NotNull
  private JMenu createMicroDebugMenu(final IActionProvider provider) {
    final MDMenu menu = new MDMenu("micro-debug", GuiText.GUI_MENU_MICRODEBUG.text());

    final MDMenuItem exit = new MDMenuItem("exit", provider.getAction(Actions.EXIT));
    exit.setAccelerator(KeyStrokes.EXIT.stroke());

    menu.add(exit);
    return menu;
  }
}
