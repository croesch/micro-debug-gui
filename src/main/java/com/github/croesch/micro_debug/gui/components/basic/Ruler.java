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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

/**
 * TODO Comment here ...
 * 
 * @author croesch
 * @since Date: Mar 20, 2012
 */
public class Ruler extends JPanel {

  private final JTextComponent textComponent;

  public Ruler(final JTextComponent tc) {
    this.textComponent = tc;

    this.textComponent.getDocument().addDocumentListener(new DocumentListener() {

      public void removeUpdate(final DocumentEvent e) {
        repaint();
      }

      public void insertUpdate(final DocumentEvent e) {
        repaint();
      }

      public void changedUpdate(final DocumentEvent e) {
        repaint();
      }
    });
  }

  /**
   * Returns the current line numbers of this text component. Based on {@link javax.swing.JTextArea#getLineCount()}.
   * 
   * @since Date: Mar 20, 2012
   * @return the number of lines the underlying text component currently has
   */
  private int getLineCount() {
    return this.textComponent.getDocument().getDefaultRootElement().getElementCount();
  }

  @Override
  public void paint(final Graphics g) {
    super.paint(g);
    setPreferredHeight(this.textComponent.getHeight());

    final int circleSize = 6;
    final int space = 2;
    final int smallCircleSize = (int) (circleSize * 0.4);

    int lineHeight;
    if (getLineCount() > 1) {
      try {
        final Rectangle r1 = this.textComponent.modelToView(0);
        final Rectangle r2 = this.textComponent.modelToView(this.textComponent.getDocument().getDefaultRootElement()
          .getElement(1).getStartOffset());
        lineHeight = r2.y - r1.y;
      } catch (final BadLocationException blex) {
        lineHeight = this.textComponent.getFont().getSize() + 2;
      }
    } else {
      lineHeight = this.textComponent.getFont().getSize() + 2;
    }

    for (int i = -1; i < getLineCount(); i += 1) {
      g.setColor(getForeground());
      g.fillOval(space, i * lineHeight + (lineHeight - circleSize) / 2, circleSize, circleSize);
      g.setColor(getForeground().brighter());
      g.fillOval(space, i * lineHeight + (lineHeight - circleSize) / 2, circleSize, smallCircleSize);
      g.setColor(getBackground().brighter());
      g.drawOval(space - 1, i * lineHeight + (lineHeight - circleSize) / 2 - 1, circleSize + 2, circleSize + 2);
      g.setColor(getForeground().darker());
      g.drawOval(space, i * lineHeight + (lineHeight - circleSize) / 2, circleSize, circleSize);
    }
  }

  public void setPreferredHeight(final int ph) {
    setPreferredSize(new Dimension(10, ph));
  }

  /**
   * TODO Comment here ...
   * 
   * @since Date: Mar 20, 2012
   * @param args
   */
  public static void main(final String[] args) {
    final JFrame f = new JFrame();
    final JScrollPane sp = new JScrollPane();
    final JTextArea ta = new JTextArea();
    ta.setText("1\n2\n3\n4\n5\n6\n....");
    final Ruler r = new Ruler(ta);
    r.setForeground(new Color(115, 169, 204));

    sp.setViewportView(ta);
    sp.setRowHeaderView(r);
    f.setLayout(new MigLayout("fill", "[grow, fill]", "[grow,fill]"));
    f.add(sp);
    f.setSize(400, 400);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
    f.repaint();
  }
}
