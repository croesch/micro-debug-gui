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

import com.github.croesch.micro_debug.gui.actions.ActionProvider;
import com.github.croesch.micro_debug.gui.actions.Actions;
import com.github.croesch.micro_debug.gui.components.basic.MDMenu;
import com.github.croesch.micro_debug.gui.components.basic.MDMenuItem;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

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
   * @param provider the {@link ActionProvider} providing the actions for the menu.
   */
  public MainMenuBar(final ActionProvider provider) {
    add(createMicroDebugMenu(provider));
    add(createProcessorMenu(provider));
    add(createHelpMenu(provider));
  }

  /**
   * Constructs the help menu and its items.
   * 
   * @since Date: May 14, 2012
   * @param provider the {@link ActionProvider} providing the actions for the menu.
   * @return the constructed help menu
   */
  private JMenu createHelpMenu(final ActionProvider provider) {
    final MDMenu menu = new MDMenu("help", GuiText.GUI_MENU_HELP.text());
    menu.add(new MDMenuItem("help-item", provider.getAction(Actions.HELP)));
    menu.addSeparator();
    menu.add(new MDMenuItem("about", provider.getAction(Actions.ABOUT)));
    return menu;
  }

  /**
   * Constructs the processor menu and its items.
   * 
   * @since Date: May 14, 2012
   * @param provider the {@link ActionProvider} providing the actions for the menu.
   * @return the constructed processor menu
   */
  private JMenu createProcessorMenu(final ActionProvider provider) {
    final MDMenu menu = new MDMenu("processor", GuiText.GUI_MENU_PROCESSOR.text());
    menu.add(new MDMenuItem("micro-step", provider.getAction(Actions.MICRO_STEP)));
    menu.add(new MDMenuItem("step", provider.getAction(Actions.STEP)));
    menu.add(new MDMenuItem("run", provider.getAction(Actions.RUN)));
    menu.addSeparator();
    menu.add(new MDMenuItem("reset", provider.getAction(Actions.RESET)));
    return menu;
  }

  /**
   * Constructs the micro-debug menu and its items.
   * 
   * @since Date: May 14, 2012
   * @param provider the {@link ActionProvider} providing the actions for the menu.
   * @return the constructed micro-debug menu
   */
  private JMenu createMicroDebugMenu(final ActionProvider provider) {
    final MDMenu menu = new MDMenu("micro-debug", GuiText.GUI_MENU_MICRODEBUG.text());
    menu.add(new MDMenuItem("exit", provider.getAction(Actions.EXIT)));
    return menu;
  }
}
