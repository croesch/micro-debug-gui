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

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.error.MacroFileFormatException;
import com.github.croesch.micro_debug.error.MicroFileFormatException;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.actions.ActionProvider;
import com.github.croesch.micro_debug.gui.actions.ActionProviderTest;
import com.github.croesch.micro_debug.gui.actions.api.IActionProvider.Actions;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.KeyStrokes;
import com.github.croesch.micro_debug.mic1.Mic1;

/**
 * Provides test cases for {@link MainMenuBar}.
 * 
 * @author croesch
 * @since Date: May 14, 2012
 */
public class MainMenuBarTest extends DefaultGUITestCase {

  public static MainMenuBar getMenuBar(final ActionProvider provider) {
    return GuiActionRunner.execute(new GuiQuery<MainMenuBar>() {
      @Override
      protected MainMenuBar executeInEDT() {
        return new MainMenuBar(provider);
      }
    });
  }

  public static JFrame putIntoFrame(final MainMenuBar menuBar) {
    return GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() {
        final JFrame f = new JFrame();
        f.setJMenuBar(menuBar);
        return f;
      }
    });
  }

  @Test
  public void testMenuBar() throws MacroFileFormatException, MicroFileFormatException, FileNotFoundException {
    printlnMethodName();

    final String micFile = getClass().getClassLoader().getResource("mic1/mic1ijvm.mic1").getPath();
    final String macFile = getClass().getClassLoader().getResource("mic1/add.ijvm").getPath();
    final Mic1 processor = new Mic1(new FileInputStream(micFile), new FileInputStream(macFile));
    final ActionProvider provider = ActionProviderTest.getProvider(processor);
    final MainMenuBar mb = getMenuBar(provider);
    final JFrame f = putIntoFrame(mb);

    final FrameFixture frame = new FrameFixture(robot(), f);
    frame.show(new Dimension(500, 300));

    frame.menuItem("micro-debug").requireEnabled();
    frame.menuItem("processor").requireEnabled();
    frame.menuItem("help").requireEnabled();

    frame.menuItem("exit").requireEnabled();
    frame.menuItem("micro-step").requireEnabled();
    frame.menuItem("step").requireEnabled();
    frame.menuItem("run").requireEnabled();
    frame.menuItem("reset").requireEnabled();
    frame.menuItem("about").requireEnabled();
    frame.menuItem("help-item").requireEnabled();

    assertThat(frame.menuItem("micro-debug").component().getText()).isEqualTo(GuiText.GUI_MENU_MICRODEBUG.text());
    assertThat(frame.menuItem("processor").component().getText()).isEqualTo(GuiText.GUI_MENU_PROCESSOR.text());
    assertThat(frame.menuItem("help").component().getText()).isEqualTo(GuiText.GUI_MENU_HELP.text());

    assertThat(frame.menuItem("exit").component().getText()).isEqualTo(GuiText.GUI_ACTIONS_EXIT.text());
    assertThat(frame.menuItem("micro-step").component().getText()).isEqualTo(GuiText.GUI_ACTIONS_MICRO_STEP.text());
    assertThat(frame.menuItem("step").component().getText()).isEqualTo(GuiText.GUI_ACTIONS_STEP.text());
    assertThat(frame.menuItem("run").component().getText()).isEqualTo(GuiText.GUI_ACTIONS_RUN.text());
    assertThat(frame.menuItem("reset").component().getText()).isEqualTo(GuiText.GUI_ACTIONS_RESET.text());
    assertThat(frame.menuItem("about").component().getText()).isEqualTo(GuiText.GUI_ACTIONS_ABOUT.text());
    assertThat(frame.menuItem("help-item").component().getText()).isEqualTo(GuiText.GUI_ACTIONS_HELP.text());

    assertThat(frame.menuItem("exit").component().getAction()).isSameAs(provider.getAction(Actions.EXIT));
    assertThat(frame.menuItem("micro-step").component().getAction()).isSameAs(provider.getAction(Actions.MICRO_STEP));
    assertThat(frame.menuItem("step").component().getAction()).isSameAs(provider.getAction(Actions.STEP));
    assertThat(frame.menuItem("run").component().getAction()).isSameAs(provider.getAction(Actions.RUN));
    assertThat(frame.menuItem("reset").component().getAction()).isSameAs(provider.getAction(Actions.RESET));
    assertThat(frame.menuItem("about").component().getAction()).isSameAs(provider.getAction(Actions.ABOUT));
    assertThat(frame.menuItem("help-item").component().getAction()).isSameAs(provider.getAction(Actions.HELP));

    assertThat(frame.menuItem("exit").component().getAccelerator()).isSameAs(KeyStrokes.EXIT.stroke());
    assertThat(frame.menuItem("micro-step").component().getAccelerator()).isSameAs(KeyStrokes.MICRO_STEP.stroke());
    assertThat(frame.menuItem("step").component().getAccelerator()).isSameAs(KeyStrokes.STEP.stroke());
    assertThat(frame.menuItem("run").component().getAccelerator()).isSameAs(KeyStrokes.RUN.stroke());
    assertThat(frame.menuItem("reset").component().getAccelerator()).isSameAs(KeyStrokes.RESET.stroke());
    assertThat(frame.menuItem("about").component().getAccelerator()).isSameAs(KeyStrokes.ABOUT.stroke());
    assertThat(frame.menuItem("help-item").component().getAccelerator()).isSameAs(KeyStrokes.HELP.stroke());
  }
}
