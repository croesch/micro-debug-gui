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

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * Provides test cases for {@link NumberStyleSwitcher}.
 * 
 * @author croesch
 * @since Date: May 27, 2012
 */
public class NumberStyleSwitcherTest extends DefaultGUITestCase {

  private FrameFixture showFrameWithPanel() {
    final FrameFixture frameFixture = new FrameFixture(robot(), GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() {
        final JFrame frame = new JFrame();
        frame.setLayout(new MigLayout("fill"));
        frame.add(getPanel("switcher"), "grow");
        return frame;
      }
    }));
    frameFixture.show(new Dimension(500, 500));
    return frameFixture;
  }

  public static MDPanel getPanel(final String name) {
    return GuiActionRunner.execute(new GuiQuery<MDPanel>() {
      @Override
      protected MDPanel executeInEDT() {
        return new NumberStyleSwitcher(name);
      }
    });
  }

  @Test
  public void testPanel() {
    printlnMethodName();

    JPanel panel = getPanel("panel");
    assertThat(panel.getName()).isEqualTo("panel");

    panel = getPanel(" .. .. ");
    assertThat(panel.getName()).isEqualTo(" .. .. ");

    panel = getPanel("");
    assertThat(panel.getName()).isEmpty();

    panel = getPanel(null);
    assertThat(panel.getName()).isNull();
  }

  @Test
  public void testSwitcher() {
    printlnMethodName();

    final FrameFixture frame = showFrameWithPanel();
    frame.radioButton("binary").requireText(GuiText.GUI_MAIN_BINARY.text());
    frame.radioButton("decimal").requireText(GuiText.GUI_MAIN_DECIMAL.text());
    frame.radioButton("hexadecimal").requireText(GuiText.GUI_MAIN_HEXADECIMAL.text());

    final NumberStyleSwitcher numberSwitcher = frame.panel("switcher").targetCastedTo(NumberStyleSwitcher.class);
    assertThat(numberSwitcher.getBinaryBtn()).isEqualTo(frame.radioButton("binary").component());
    assertThat(numberSwitcher.getDecimalBtn()).isEqualTo(frame.radioButton("decimal").component());
    assertThat(numberSwitcher.getHexadecimalBtn()).isEqualTo(frame.radioButton("hexadecimal").component());

    frame.radioButton("binary").check();
    frame.radioButton("decimal").requireNotSelected();
    frame.radioButton("hexadecimal").requireNotSelected();

    frame.radioButton("decimal").check();
    frame.radioButton("binary").requireNotSelected();
    frame.radioButton("hexadecimal").requireNotSelected();

    frame.radioButton("hexadecimal").check();
    frame.radioButton("decimal").requireNotSelected();
    frame.radioButton("binary").requireNotSelected();
  }
}
