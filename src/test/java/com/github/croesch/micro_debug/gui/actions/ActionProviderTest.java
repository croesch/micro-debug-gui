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

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link ActionProvider}.
 * 
 * @author croesch
 * @since Date: May 13, 2012
 */
public class ActionProviderTest extends DefaultGUITestCase {

  private ActionProvider provider;

  @Override
  protected void setUpTestCase() throws Exception {
    this.provider = GuiActionRunner.execute(new GuiQuery<ActionProvider>() {
      @Override
      protected ActionProvider executeInEDT() throws Throwable {
        return new ActionProvider(null);
      }
    });
  }

  @Test
  public void testGetAction() {
    printlnMethodName();

    assertThat(this.provider.getAction(Actions.ABOUT)).isInstanceOf(AboutAction.class);
    assertThat(this.provider.getAction(Actions.MICRO_STEP)).isInstanceOf(MicroStepAction.class);
    assertThat(this.provider.getAction(Actions.RESET)).isInstanceOf(ResetAction.class);
    assertThat(this.provider.getAction(Actions.RUN)).isInstanceOf(RunAction.class);
    assertThat(this.provider.getAction(Actions.STEP)).isInstanceOf(StepAction.class);
  }
}
