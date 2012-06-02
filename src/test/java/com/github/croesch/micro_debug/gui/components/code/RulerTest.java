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
package com.github.croesch.micro_debug.gui.components.code;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

import org.fest.swing.core.MouseButton;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Test;

import com.github.croesch.micro_debug.gui.DefaultGUITestCase;
import com.github.croesch.micro_debug.gui.components.api.ILineBreakPointManager;
import com.github.croesch.micro_debug.gui.debug.LineBreakPointHandler;
import com.github.croesch.micro_debug.gui.debug.LineNumberMapper;

/**
 * Provides test cases for {@link Ruler}.
 * 
 * @author croesch
 * @since Date: Mar 21, 2012
 */
public class RulerTest extends DefaultGUITestCase {

  private LineNumberMapper lineMapper;

  @Override
  protected void setUpTestCase() throws Exception {
    this.lineMapper = new LineNumberMapper();
  }

  private FrameFixture showFrame(final Ruler r, final JTextComponent ta) {
    final FrameFixture frameFixture = new FrameFixture(robot(), GuiActionRunner.execute(new GuiQuery<JFrame>() {

      @Override
      protected JFrame executeInEDT() {
        final JFrame frame = new JFrame();
        frame.setLayout(new MigLayout("fill", "[][grow,fill]", "[grow,fill]"));
        frame.add(r);
        frame.add(ta);
        return frame;
      }
    }));
    frameFixture.show(new Dimension(500, 500));
    robot().waitForIdle();
    return frameFixture;
  }

  private ACodeArea getTA(final String name, final Object text) {
    return GuiActionRunner.execute(new GuiQuery<ACodeArea>() {

      @Override
      protected ACodeArea executeInEDT() {
        final ACodeArea aCodeArea = new ACodeArea(name, null) {

          /** default */
          private static final long serialVersionUID = 1L;
        };
        aCodeArea.setText(text.toString());
        return aCodeArea;
      }
    });
  }

  private Ruler getRuler(final ACodeArea area, final ILineBreakPointManager bpm) {
    return GuiActionRunner.execute(new GuiQuery<Ruler>() {

      @Override
      protected Ruler executeInEDT() {
        return new Ruler("ruler", area, bpm, RulerTest.this.lineMapper);
      }
    });
  }

  private Ruler getRuler(final String name,
                         final ACodeArea area,
                         final ILineBreakPointManager bpm,
                         final LineNumberMapper mapper) {
    return GuiActionRunner.execute(new GuiQuery<Ruler>() {

      @Override
      protected Ruler executeInEDT() {
        return new Ruler(name, area, bpm, mapper);
      }
    });
  }

  @Test
  public void testName() {
    printlnMethodName();

    final LineBreakPointHandler bpm = new LineBreakPointHandler();
    final ACodeArea ta = getTA("ta", "a\nb\nc\nd\ne\nf\ng\nh\ni\n\n\n");

    Ruler r = getRuler("ruler", ta, bpm, this.lineMapper);
    assertThat(r.getName()).isEqualTo("ruler");

    r = getRuler("", ta, bpm, this.lineMapper);
    assertThat(r.getName()).isEqualTo("");

    r = getRuler(" ", ta, bpm, this.lineMapper);
    assertThat(r.getName()).isEqualTo(" ");

    r = getRuler(null, ta, bpm, this.lineMapper);
    assertThat(r.getName()).isEqualTo(null);
  }

  @Test
  public void testSynchronisationWithManager() {
    printlnMethodName();

    final LineBreakPointHandler bpm = new LineBreakPointHandler();
    final ACodeArea ta = getTA("ta", "a\nb\nc\nd\ne\nf\ng\nh\ni\n\n\n");
    final Ruler r = getRuler(ta, bpm);

    for (int line = 1; line <= ta.getLineCount(); ++line) {
      assertThat(r.isBreakpoint(line)).isEqualTo(bpm.isBreakpoint(line));
      bpm.toggleBreakpoint(line);
      assertThat(r.isBreakpoint(line)).isEqualTo(bpm.isBreakpoint(line));
      r.toggleBreakpoint(line);
      assertThat(r.isBreakpoint(line)).isEqualTo(bpm.isBreakpoint(line));
    }
  }

  @Test
  public void testTogglingBreakpoints() throws BadLocationException, InterruptedException {
    printlnMethodName();

    final LineBreakPointHandler bpm = new LineBreakPointHandler();
    final ACodeArea ta = getTA("ta", "a\nb\nc\nd\ne\nf\ng\nh\ni\n\n\n");
    final Ruler r = getRuler(ta, bpm);

    showFrame(r, ta);

    final int line = 3;
    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(3));
    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), null);

    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.RIGHT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), null);

    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.LEFT_BUTTON, 1);
    assertBreakpoints(bpm, r, ta.getLineCount(), null);

    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.MIDDLE_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), null);

    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.LEFT_BUTTON, 4);
    assertBreakpoints(bpm, r, ta.getLineCount(), null);

    robot().click(r, new Point(2, getYOfLine(ta, 4)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(4));

    robot().click(r, new Point(6, getYOfLine(ta, 5)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(4, 5));

    robot().click(r, new Point(8, getYOfLine(ta, 6)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(4, 5, 6));

    robot().click(r, new Point(6, 2 + getYOfLine(ta, 4)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(5, 6));

    robot().click(r, new Point(6, 4 + getYOfLine(ta, 5)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(6));

    robot().click(r, new Point(6, 6 + getYOfLine(ta, 1)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(1, 6));

    robot().click(r, new Point(6, 8 + getYOfLine(ta, 3)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(1, 3, 6));

    // click last line
    robot().click(r, new Point(6, getYOfLine(ta, 11)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(1, 3, 6, 11));

    robot().click(r, new Point(6, getYOfLine(ta, 0)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(0, 1, 3, 6, 11));

    // click below last line is same as last line 
    robot().click(r, new Point(6, getYOfLine(ta, 11)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(0, 1, 3, 6));

    bpm.toggleBreakpoint(11);

    robot().click(r, new Point(6, getYOfLine(ta, 11)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(0, 1, 3, 6));
  }

  @Test
  public void testTogglingBreakpoints_WithLineNumbersChanged() throws BadLocationException, InterruptedException {
    printlnMethodName();

    this.lineMapper.setNewLines(1, 3, 5, 7, 8, 10, 11, 12, 13, 15, 16, 18, 20, 21, 50);

    final LineBreakPointHandler bpm = new LineBreakPointHandler();
    final ACodeArea ta = getTA("ta", "a\nb\nc\nd\ne\nf\ng\nh\ni\n\n\n");
    final Ruler r = getRuler(ta, bpm);

    showFrame(r, ta);

    slowDownRobot();

    final int line = 3;
    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(this.lineMapper.getLineForNumber(3)));
    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), null);

    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.RIGHT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), null);

    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.LEFT_BUTTON, 1);
    assertBreakpoints(bpm, r, ta.getLineCount(), null);

    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.MIDDLE_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), null);

    robot().click(r, new Point(0, getYOfLine(ta, line)), MouseButton.LEFT_BUTTON, 4);
    assertBreakpoints(bpm, r, ta.getLineCount(), null);

    robot().click(r, new Point(2, getYOfLine(ta, 4)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(this.lineMapper.getLineForNumber(4)));

    robot().click(r, new Point(6, getYOfLine(ta, 5)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(8, 10));

    robot().click(r, new Point(8, getYOfLine(ta, 6)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(8, 10, this.lineMapper.getLineForNumber(6)));

    robot().click(r, new Point(6, 2 + getYOfLine(ta, 4)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(10, 11));

    robot().click(r, new Point(6, 4 + getYOfLine(ta, 5)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(11));

    robot().click(r, new Point(6, 6 + getYOfLine(ta, 1)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(3, 11));

    robot().click(r, new Point(6, 8 + getYOfLine(ta, 3)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(this.lineMapper.getLineForNumber(1),
                                                               this.lineMapper.getLineForNumber(3),
                                                               this.lineMapper.getLineForNumber(6)));

    // click last line
    robot().click(r, new Point(6, getYOfLine(ta, 11)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(this.lineMapper.getLineForNumber(1),
                                                               this.lineMapper.getLineForNumber(3),
                                                               this.lineMapper.getLineForNumber(6),
                                                               this.lineMapper.getLineForNumber(11)));

    robot().click(r, new Point(6, getYOfLine(ta, 0)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(this.lineMapper.getLineForNumber(0),
                                                               this.lineMapper.getLineForNumber(1),
                                                               this.lineMapper.getLineForNumber(3),
                                                               this.lineMapper.getLineForNumber(6),
                                                               this.lineMapper.getLineForNumber(11)));

    // click below last line is same as last line 
    robot().click(r, new Point(6, getYOfLine(ta, 11)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(this.lineMapper.getLineForNumber(0),
                                                               this.lineMapper.getLineForNumber(1),
                                                               this.lineMapper.getLineForNumber(3),
                                                               this.lineMapper.getLineForNumber(6)));

    bpm.toggleBreakpoint(18);

    robot().click(r, new Point(6, getYOfLine(ta, 11)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(this.lineMapper.getLineForNumber(0),
                                                               this.lineMapper.getLineForNumber(1),
                                                               this.lineMapper.getLineForNumber(3),
                                                               this.lineMapper.getLineForNumber(6)));

    bpm.toggleBreakpoint(this.lineMapper.getLineForNumber(11));

    robot().click(r, new Point(6, getYOfLine(ta, 11)), MouseButton.LEFT_BUTTON, 2);
    assertBreakpoints(bpm, r, ta.getLineCount(), Arrays.asList(this.lineMapper.getLineForNumber(0),
                                                               this.lineMapper.getLineForNumber(1),
                                                               this.lineMapper.getLineForNumber(3),
                                                               this.lineMapper.getLineForNumber(6)));
  }

  public static int getYOfLine(final ACodeArea ta, final int line) throws BadLocationException {
    return ta.modelToView(ta.getLineStartOffset(line)).y;
  }

  private void assertBreakpoints(final LineBreakPointHandler bpm,
                                 final Ruler r,
                                 final int lineCount,
                                 final List<Integer> lines) {
    for (int i = 0; i < lineCount; ++i) {
      if (lines != null && lines.contains(Integer.valueOf(i))) {
        assertThatThereIsABreakPoint(bpm, r, i);
      } else {
        assertThatThereIsNoBreakPoint(bpm, r, i);
      }
    }
  }

  private void assertThatThereIsABreakPoint(final LineBreakPointHandler bpm, final Ruler r, final int line) {
    assertThat(bpm.isBreakpoint(line)).isTrue();
    assertThat(r.isBreakpoint(line)).isTrue();
  }

  private void assertThatThereIsNoBreakPoint(final LineBreakPointHandler bpm, final Ruler r, final int line) {
    assertThat(bpm.isBreakpoint(line)).isFalse();
    assertThat(r.isBreakpoint(line)).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_TA() {
    final LineBreakPointHandler bpm = new LineBreakPointHandler();
    getRuler(null, bpm);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_BPM() {
    getRuler(getTA("ta", "hi"), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull_Mapper() {
    final LineBreakPointHandler bpm = new LineBreakPointHandler();
    getRuler("ruler", getTA("ta", "hi"), bpm, null);
  }
}
