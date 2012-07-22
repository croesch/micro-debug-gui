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
package com.github.croesch.micro_debug.gui.components.help;

import java.awt.Dimension;

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.gui.components.basic.MDLabel;
import com.github.croesch.micro_debug.gui.components.basic.MDPanel;
import com.github.croesch.micro_debug.gui.components.basic.MDScrollPane;
import com.github.croesch.micro_debug.gui.components.basic.SizedFrame;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;

/**
 * A frame that visualises help information.
 * 
 * @author croesch
 * @since Date: Jul 22, 2012
 */
public class HelpFrame extends SizedFrame {

  /** the ratio for the maximum height of content regarding to the frame height */
  private static final double HEIGHT_RATIO_FOR_CONTENT = 10;

  /** the ratio for the maximum width of content regarding to the frame width */
  private static final double WIDTH_RATIO_FOR_CONTENT = 0.85;

  /** the height of this frame */
  private static final int FRAME_HEIGHT = 647;

  /** the width of this frame */
  private static final int FRAME_WIDTH = 400;

  /** generated serial version UID */
  private static final long serialVersionUID = 5351450481982328600L;

  /**
   * Constructs the frame that visualises help information.
   * 
   * @since Date: Jul 22, 2012
   */
  public HelpFrame() {
    super(GuiText.GUI_HELP_TITLE, new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    setResizable(true);

    setLayout(new MigLayout("wrap, fill", "[fill]", "[][fill,grow]"));

    add(generateTitleArea());
    final MDPanel scrollPane = new MDPanel("scroll-content", new MigLayout("fill,wrap 1", "[fill]"));
    final MDScrollPane scrollArea = new MDScrollPane("scroll-area", scrollPane);

    scrollPane.add(setSizeForLabel(generateComponentsDescriptionArea()));
    scrollPane.add(setSizeForLabel(generateIssueTrackingDescriptionArea()));
    scrollPane.add(setSizeForLabel(generateDevelopmentDescriptionArea()));

    add(scrollArea);
  }

  /**
   * Sets the maximum size for the given label, to gain wrapping behavior.
   * 
   * @since Date: Jul 22, 2012
   * @param label the label to set the maximum size of.
   * @return the instance of the label
   */
  private MDLabel setSizeForLabel(final MDLabel label) {
    final Dimension size = getSize();
    size.width *= WIDTH_RATIO_FOR_CONTENT;
    size.height *= HEIGHT_RATIO_FOR_CONTENT;
    label.setMaximumSize(size);
    return label;
  }

  /**
   * Generates the component visualising the title of the frame and the subtitle.
   * 
   * @since Date: Jul 22, 2012
   * @return the component visualising the title of the frame and the subtitle.
   */
  @NotNull
  private MDLabel generateTitleArea() {
    final String text = "<html><h1>" + InternalSettings.NAME + "</h1>\n<h2>" + GuiText.GUI_HELP_TITLE + "</h2>";
    return new MDLabel("name-and-version", text);
  }

  /**
   * Creates the component visualising the description about the main frame and its components.
   * 
   * @since Date: Jul 22, 2012
   * @return the component visualising the description about the main frame and its components.
   */
  @NotNull
  private MDLabel generateComponentsDescriptionArea() {
    return new MDLabel("component-description", GuiText.GUI_HELP_COMP_DESCR);
  }

  /**
   * Creates the component visualising the description about issue-tracking-system of micro-debug-gui.
   * 
   * @since Date: Jul 22, 2012
   * @return the component visualising the description about issue-tracking-system of micro-debug-gui.
   */
  @NotNull
  private MDLabel generateIssueTrackingDescriptionArea() {
    return new MDLabel("issue-tracking-description", GuiText.GUI_HELP_ISSUE_DESCR);
  }

  /**
   * Creates the component visualising the description about development of micro-debug-gui.
   * 
   * @since Date: Jul 22, 2012
   * @return the component visualising the description about development of micro-debug-gui.
   */
  @NotNull
  private MDLabel generateDevelopmentDescriptionArea() {
    return new MDLabel("development-description", GuiText.GUI_HELP_DEV_DESCR);
  }
}
