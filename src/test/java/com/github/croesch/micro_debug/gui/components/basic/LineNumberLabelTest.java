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
package com.github.croesch.micro_debug.gui.components.basic;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JLabelFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.junit.Ignore;
import org.junit.Test;

import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.gui.DefaultGUITestCase;

/**
 * Provides test cases for {@link LineNumberLabel}.
 * 
 * @author croesch
 * @since Date: Mar 19, 2012
 */
public class LineNumberLabelTest extends DefaultGUITestCase {

  private static final String HIGHLIGHT = Integer
    .toHexString(UIManager.getColor("Label.background").darker().getRGB() & 0xFFFFFF);

  private LineNumberLabel getLabel(final JTextComponent ta) {
    return GuiActionRunner.execute(new GuiQuery<LineNumberLabel>() {
      @Override
      protected LineNumberLabel executeInEDT() {
        return new LineNumberLabel(ta);
      }
    });
  }

  private MDTextArea getTA(final String name, final String text) {
    return GuiActionRunner.execute(new GuiQuery<MDTextArea>() {
      @Override
      protected MDTextArea executeInEDT() {
        return new MDTextArea(name, text);
      }
    });
  }

  private JEditorPane getEP(final String name, final String text) {
    return GuiActionRunner.execute(new GuiQuery<JEditorPane>() {
      @Override
      protected JEditorPane executeInEDT() {
        final JEditorPane pane = new JEditorPane();
        pane.setName(name);
        pane.setText(text);
        return pane;
      }
    });
  }

  private JFrame getFrame(final JComponent comp, final JLabel label) {
    return GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() {
        final JFrame frame = new JFrame();
        frame.setLayout(new MigLayout("fill", "[][grow,fill]", "[grow,fill]"));
        frame.add(label);
        frame.add(comp);
        return frame;
      }
    });
  }

  @Test
  public void testLabel() {
    printlnMethodName();
    JTextArea ta = getTA("ta", "Dies ist ein Text ...");
    JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel(ta));
    labelFixture.requireText(getTextAsserted(1));
    assertThat(labelFixture.component().getName()).isEqualTo("ta-line-numbers");

    ta = getTA("", "");
    labelFixture = new JLabelFixture(robot(), getLabel(ta));
    labelFixture.requireText(getTextAsserted(1));
    assertThat(labelFixture.component().getName()).isEqualTo("-line-numbers");

    ta = getTA("ta", "\n");
    labelFixture = new JLabelFixture(robot(), getLabel(ta));
    labelFixture.requireText(getTextAsserted(2));
    assertThat(labelFixture.component().getName()).isEqualTo("ta-line-numbers");

    ta = getTA("ta", "\r\n");
    labelFixture = new JLabelFixture(robot(), getLabel(ta));
    labelFixture.requireText(getTextAsserted(2));
    assertThat(labelFixture.component().getName()).isEqualTo("ta-line-numbers");

    ta = getTA("ta", getLineSeparator() + "..\n\nvierte Zeile\n");
    labelFixture = new JLabelFixture(robot(), getLabel(ta));
    labelFixture.requireText(getTextAsserted(5));
    assertThat(labelFixture.component().getName()).isEqualTo("ta-line-numbers");

    ta = getTA(null, "");
    labelFixture = new JLabelFixture(robot(), getLabel(ta));
    labelFixture.requireText(getTextAsserted(1));
    assertThat(labelFixture.component().getName()).isEqualTo("null-line-numbers");

    ta = getTA("", null);
    labelFixture = new JLabelFixture(robot(), getLabel(ta));
    labelFixture.requireText(getTextAsserted(1));
    assertThat(labelFixture.component().getName()).isEqualTo("-line-numbers");

    final JEditorPane pane = getEP("ta", getLineSeparator() + "..\n\r\nvierte Zeile\n");
    labelFixture = new JLabelFixture(robot(), getLabel(pane));
    labelFixture.requireText(getTextAsserted(5));
    assertThat(labelFixture.component().getName()).isEqualTo("ta-line-numbers");

    // nothing happens
    labelFixture.targetCastedTo(LineNumberLabel.class).changedUpdate(null);
  }

  @Test
  public void testLabelEnterTextInTA() {
    printlnMethodName();
    final JTextComponentFixture ta = new JTextComponentFixture(robot(), getTA("ta", ""));
    final JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel(ta.targetCastedTo(JTextArea.class)));

    final FrameFixture frame = new FrameFixture(robot(), getFrame(ta.component(), labelFixture.component()));
    frame.show(new Dimension(500, 500));

    labelFixture.requireText(getTextAsserted(1));
    assertThat(labelFixture.component().getName()).isEqualTo("ta-line-numbers");

    ta.enterText("1 2 3 4");
    labelFixture.requireText(getTextAsserted(1));

    ta.selectAll();
    ta.enterText(" 5 6 7 8 9 10");
    labelFixture.requireText(getTextAsserted(1));

    ta.enterText("\n");
    labelFixture.requireText(getTextAsserted(2));

    ta.enterText("zweite Zeile");
    labelFixture.requireText(getTextAsserted(2));

    ta.enterText("\n");
    labelFixture.requireText(getTextAsserted(3));
    ta.enterText("\n");
    labelFixture.requireText(getTextAsserted(4));
    ta.enterText("\n");
    labelFixture.requireText(getTextAsserted(5));
    ta.enterText("\n");
    labelFixture.requireText(getTextAsserted(6));
    ta.enterText("\n");
    labelFixture.requireText(getTextAsserted(7));
    ta.enterText("\n");
    labelFixture.requireText(getTextAsserted(8));
    ta.enterText("\n");
    labelFixture.requireText(getTextAsserted(9));
    ta.enterText("\n");
    labelFixture.requireText(getTextAsserted(10));
    ta.enterText("hihi");
    labelFixture.requireText(getTextAsserted(10));
  }

  @Test
  public void testHighlight() {
    printlnMethodName();
    final JTextComponentFixture ta = new JTextComponentFixture(robot(), getTA("light", "Dies ist ein Text ..."));
    final JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel(ta.targetCastedTo(JTextArea.class)));

    final FrameFixture frame = new FrameFixture(robot(), getFrame(ta.component(), labelFixture.component()));
    frame.show(new Dimension(500, 500));
    assertThat(labelFixture.component().getName()).isEqualTo("light-line-numbers");

    // assert highlight has no effect
    labelFixture.requireText(getTextAsserted(1));
    highlight(labelFixture, -10);
    labelFixture.requireText(getTextAsserted(1));
    highlight(labelFixture, -1);
    labelFixture.requireText(getTextAsserted(1));
    highlight(labelFixture, 0);
    labelFixture.requireText(getTextAsserted(1));
    highlight(labelFixture, 2);
    labelFixture.requireText(getTextAsserted(1));
    highlight(labelFixture, 10);
    labelFixture.requireText(getTextAsserted(1));

    highlight(labelFixture, 1);
    labelFixture.requireText(getTextAsserted(1, 1));

    ta.deleteText();
    labelFixture.requireText(getTextAsserted(1, 1));
    ta.enterText(getLineSeparator() + "..\r\n\nvierte Zeile\n");
    labelFixture.requireText(getTextAsserted(5, 1));

    highlight(labelFixture, 3);
    labelFixture.requireText(getTextAsserted(5, 3));

    highlight(labelFixture, 6);
    labelFixture.requireText(getTextAsserted(5));

    highlight(labelFixture, 5);
    labelFixture.requireText(getTextAsserted(5, 5));

    ta.selectText(0, getLineSeparator().length());
    ta.pressAndReleaseKeys(KeyEvent.VK_DELETE);
    labelFixture.requireText(getTextAsserted(4));
  }

  @Test
  @Ignore("just for manual testing purpose to become a feeling of how long things take")
  public void testHighlightPerformance() {
    printlnMethodName();
    final JTextComponentFixture ta = new JTextComponentFixture(robot(), getTA("light", "Dies ist ein Text ..."));
    final JLabelFixture labelFixture = new JLabelFixture(robot(), getLabel(ta.targetCastedTo(JTextArea.class)));

    final FrameFixture frame = new FrameFixture(robot(), getFrame(ta.component(), labelFixture.component()));
    frame.show(new Dimension(500, 500));

    final StringBuilder sb = new StringBuilder();
    for (int i = 1; i < 100; ++i) {
      sb.append(i).append(" ...\n");
    }

    ta.setText(sb.toString());

    for (int i = 0; i < 100; ++i) {
      highlight(labelFixture, i);
      labelFixture.requireText(getTextAsserted(100, i));
    }
  }

  @Test(expected = NullPointerException.class)
  public void testNullTA() {
    new JLabelFixture(robot(), getLabel(null));
  }

  private String getTextAsserted(final int lines) {
    final StringBuilder sb = new StringBuilder("<html>");
    for (int line = 0; line < lines; ++line) {
      sb.append(Utils.toHexString(line)).append("<br>");
    }
    return sb.toString();
  }

  private String getTextAsserted(final int lines, final int highlighted) {
    final String toReplaceSuffix = Utils.toHexString(highlighted - 1) + "<br>";
    final String replacementSuffix = "<font bgcolor='#" + HIGHLIGHT + "'>" + Utils.toHexString(highlighted - 1)
                                     + "</font><br>";
    switch (highlighted) {
      case 1:
        return getTextAsserted(lines).replace("<html>" + toReplaceSuffix, "<html>" + replacementSuffix);
      default:
        return getTextAsserted(lines).replace("<br>" + toReplaceSuffix, "<br>" + replacementSuffix);
    }
  }

  private void highlight(final JLabelFixture labelFixture, final int line) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        labelFixture.targetCastedTo(LineNumberLabel.class).highlight(line - 1);
      }
    });
  }
}
