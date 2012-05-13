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

import java.util.EnumMap;

import javax.swing.Action;

import com.github.croesch.micro_debug.annotation.Nullable;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides the actions used by the debugger.
 * 
 * @author croesch
 * @since Date: May 13, 2012
 */
public final class ActionProvider {

  /** the map that holds the action for each key */
  private final EnumMap<Actions, Action> actions = new EnumMap<Actions, Action>(Actions.class);

  /**
   * Constructs this provider that holds references for the actions used by the debugger.
   * 
   * @since Date: May 13, 2012
   * @param processor the {@link Mic1} processor being debugged
   */
  public ActionProvider(final Mic1 processor) {
    this.actions.put(Actions.ABOUT, new AboutAction());
    this.actions.put(Actions.MICRO_STEP, new MicroStepAction(processor));
    this.actions.put(Actions.RESET, new ResetAction(processor));
    this.actions.put(Actions.RUN, new RunAction(processor));
  }

  /**
   * Returns the {@link Action} that is stored for this key.
   * 
   * @since Date: May 13, 2012
   * @param key the key that identifies the action to fetch
   * @return the stored {@link Action} for the given key
   */
  @Nullable
  public Action getAction(final Actions key) {
    return this.actions.get(key);
  }
}
