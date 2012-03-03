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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.commons.Utils;
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

  /**
   * Constructs the frame that contains information about the debugger and its GUI.
   * 
   * @since Date: Mar 3, 2012
   */
  public AboutFrame() {
    super(GuiText.GUI_ABOUT_TITLE, new Dimension(ABOUT_WIDTH, ABOUT_HEIGHT));

    final String space = "20lp";
    setLayout(new MigLayout("wrap, fill", "[fill]", "[][]" + space + "[]" + space + "[grow][]"));

    add(generateAboutArea());
    add(generateDescriptionArea());
    add(generateLicenseArea());
    add(generateCopyrightArea(), "skip 1");
  }

  /**
   * Reads the file with the given path and returns its content.
   * 
   * @since Date: Mar 3, 2012
   * @param file the path to the file to read
   * @return the content of the file
   * @throws IOException if the file cannot be read
   */
  private String getFileText(final String file) throws IOException {
    final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(file);
    if (resourceAsStream == null) {
      LOGGER.warning("file '" + file + "' requested, but not found ..");
    } else {
      try {
        return readFile(resourceAsStream);
      } finally {
        resourceAsStream.close();
      }
    }

    return "";
  }

  /**
   * Reads the content from the given stream, assuming that it is not <code>null</code>.
   * 
   * @since Date: Mar 3, 2012
   * @param resourceAsStream the stream to read from
   * @return the content of the stream
   * @throws IOException if the stream cannot be read properly.
   */
  private String readFile(final InputStream resourceAsStream) throws IOException {
    final StringBuilder text = new StringBuilder();
    final Reader r = new InputStreamReader(resourceAsStream);
    int c;
    while ((c = r.read()) != -1) {
      if (c == '\n') {
        text.append(Utils.getLineSeparator());
      } else {
        text.append((char) c);
      }
    }
    return text.toString();
  }

  /**
   * Returns the content of the file, or an empty {@link String}, if an error occurred.
   * 
   * @since Date: Mar 3, 2012
   * @param file the path to the file to read the content from.
   * @return the content of the file, or an empty {@link String}, if an error occurred.
   */
  private String getFileContent(final String file) {
    try {
      return getFileText(file);
    } catch (final IOException e) {
      Utils.logThrownThrowable(e);
    }
    return "";
  }

  /**
   * Builds the component visualising the main information about the program: the name and version.
   * 
   * @since Date: Mar 3, 2012
   * @return the component that visualises the name and version information.
   */
  private MDLabel generateAboutArea() {
    final String text = "<html><h1>" + InternalSettings.NAME + "</h1>\n<b>" + InternalSettings.VERSION + "</b>";
    return new MDLabel("name-and-version", text);
  }

  /**
   * Builds the component visualising the copyright information.
   * 
   * @since Date: Mar 3, 2012
   * @return the component that visualises the copyright information.
   */
  private MDLabel generateCopyrightArea() {
    final String text = getFileContent(COPYRIGHT_FILE);
    return new MDLabel("copyright-text", text);
  }

  /**
   * Builds the component visualising the descriptioin of the program.
   * 
   * @since Date: Mar 3, 2012
   * @return the component that visualises the programs description.
   */
  private MDLabel generateDescriptionArea() {
    return new MDLabel("description", GuiText.GUI_ABOUT_DESCRIPTION.text(InternalSettings.NAME));
  }

  /**
   * Builds the component visualising the license information.
   * 
   * @since Date: Mar 3, 2012
   * @return the component that visualises the license information.
   */
  private JComponent generateLicenseArea() {
    final String text = getFileContent(LICENSE_FILE);
    final MDLabel textArea = new MDLabel("license-text", text);
    final MDLabel licenseTitle = new MDLabel("license-title", GuiText.GUI_ABOUT_LICENSE);

    final JPanel licenseArea = new JPanel(new MigLayout("fill,wrap", "[fill]", "[][]"));
    licenseArea.add(licenseTitle);
    licenseArea.add(textArea);

    return licenseArea;
  }
}
