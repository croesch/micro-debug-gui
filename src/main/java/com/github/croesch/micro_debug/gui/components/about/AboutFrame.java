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
package com.github.croesch.micro_debug.gui.components.about;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.gui.i18n.GuiText;

/**
 * TODO Comment here ...
 * 
 * @author croesch
 * @since Date: Mar 2, 2012
 */
public class AboutFrame extends JFrame {

  private static final int ABOUT_HEIGHT = 647;

  private static final int ABOUT_WIDTH = 400;

  /** the {@link Logger} for this class */
  private static final Logger LOGGER = Logger.getLogger(AboutFrame.class.getName());

  private static final String LICENSE_FILE = "gui/about/license.txt";

  private static final String COPYRIGHT_FILE = "gui/about/copyright.txt";

  private static final String ABOUT_FILE = "gui/about/about.txt";

  /** generated serial version UID */
  private static final long serialVersionUID = -602544618675042722L;

  public AboutFrame() throws IOException {
    super(GuiText.GUI_ABOUT.text());

    final String space = "20lp";
    setLayout(new MigLayout("wrap, fill", "[fill]", "[][]" + space + "[]" + space + "[grow][]"));

    add(generateAboutArea());
    add(generateDescriptionArea());
    add(generateLicenseArea());
    add(generateCopyrightArea(), "skip 1");

    setSize(ABOUT_WIDTH, ABOUT_HEIGHT);
    setResizable(false);
  }

  private String getFileText(final String file) throws IOException {
    String text = "";
    final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(file);
    if (resourceAsStream == null) {
      LOGGER.warning("file '" + file + "' requested, but not found ..");
    } else {
      final BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
      String line;
      while ((line = reader.readLine()) != null) {
        text += line + "\n";
      }
      reader.close();
    }
    return text;
  }

  private JLabel generateAboutArea() throws IOException {
    final String text = getFileText(ABOUT_FILE);
    return new JLabel(text);
  }

  private JLabel generateCopyrightArea() throws IOException {
    final String text = getFileText(COPYRIGHT_FILE);
    return new JLabel(text);
  }

  private JLabel generateDescriptionArea() throws IOException {
    return new JLabel(GuiText.GUI_ABOUT_DESCRIPTION.text());
  }

  private JComponent generateLicenseArea() throws IOException {
    final String text = getFileText(LICENSE_FILE);
    final JLabel textArea = new JLabel(text);

    final JPanel licenseArea = new JPanel(new MigLayout("fill,wrap", "[fill]", "[][]"));
    licenseArea.add(new JLabel(GuiText.GUI_ABOUT_LICENSE.text()));
    licenseArea.add(textArea);

    return licenseArea;
  }

  public static void main(final String[] args) throws IOException, ClassNotFoundException, InstantiationException,
                                              IllegalAccessException, UnsupportedLookAndFeelException {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    final AboutFrame a = new AboutFrame();
    a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    a.repaint();
    a.setVisible(true);
  }

}
