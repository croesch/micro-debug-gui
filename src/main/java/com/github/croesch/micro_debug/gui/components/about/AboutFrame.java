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

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.gui.components.basic.MDLabel;
import com.github.croesch.micro_debug.gui.components.basic.SizedFrame;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;

/**
 * A frame that visualises information about micro-debug.
 * 
 * @author croesch
 * @since Date: Mar 2, 2012
 */
public class AboutFrame extends SizedFrame {

  /** the {@link Logger} for this class */
  private static final Logger LOGGER = Logger.getLogger(AboutFrame.class.getName());

  /** the path to the file containing the license information */
  private static final String LICENSE_FILE = "gui/about/license.txt";

  /** the path to the file containing the copyright information */
  private static final String COPYRIGHT_FILE = "gui/about/copyright.txt";

  /** the height of this frame */
  private static final int ABOUT_HEIGHT = 647;

  /** the width of this frame */
  private static final int ABOUT_WIDTH = 400;

  /** generated serial version UID */
  private static final long serialVersionUID = -602544618675042722L;

  public AboutFrame() throws IOException {
    super(GuiText.GUI_ABOUT_TITLE, new Dimension(ABOUT_WIDTH, ABOUT_HEIGHT));

    final String space = "20lp";
    setLayout(new MigLayout("wrap, fill", "[fill]", "[][]" + space + "[]" + space + "[grow][]"));

    add(generateAboutArea());
    add(generateDescriptionArea());
    add(generateLicenseArea());
    add(generateCopyrightArea(), "skip 1");
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

  private MDLabel generateAboutArea() throws IOException {
    final String text = "<html><h1>" + InternalSettings.NAME + "</h1>\n<b>" + InternalSettings.VERSION + "</b>";
    final MDLabel aboutLabel = new MDLabel("name-and-version", text);
    return aboutLabel;
  }

  private MDLabel generateCopyrightArea() throws IOException {
    final String text = getFileText(COPYRIGHT_FILE);
    final MDLabel copyLabel = new MDLabel("copyright-text", text);
    return copyLabel;
  }

  private MDLabel generateDescriptionArea() throws IOException {
    final MDLabel descriptionLabel = new MDLabel("description",
                                                 GuiText.GUI_ABOUT_DESCRIPTION.text(InternalSettings.NAME));
    return descriptionLabel;
  }

  private JComponent generateLicenseArea() throws IOException {
    final String text = getFileText(LICENSE_FILE);
    final MDLabel textArea = new MDLabel("license-text", text);

    final JPanel licenseArea = new JPanel(new MigLayout("fill,wrap", "[fill]", "[][]"));
    final MDLabel licenseTitle = new MDLabel("license-title", GuiText.GUI_ABOUT_LICENSE);
    licenseArea.add(licenseTitle);
    licenseArea.add(textArea);

    return licenseArea;
  }

  public static void main(final String[] args) throws IOException, ClassNotFoundException, InstantiationException,
                                              IllegalAccessException, UnsupportedLookAndFeelException {
    final AboutFrame a = new AboutFrame();
    a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    a.repaint();
    a.setVisible(true);
  }

}
