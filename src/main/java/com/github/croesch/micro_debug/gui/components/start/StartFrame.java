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
package com.github.croesch.micro_debug.gui.components.start;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Action;

import net.miginfocom.swing.MigLayout;

import com.github.croesch.micro_debug.annotation.NotNull;
import com.github.croesch.micro_debug.commons.Utils;
import com.github.croesch.micro_debug.gui.actions.BrowsePathForTextFieldAction;
import com.github.croesch.micro_debug.gui.actions.Mic1CreatingAction;
import com.github.croesch.micro_debug.gui.actions.api.IBinaryFilePathProvider;
import com.github.croesch.micro_debug.gui.actions.api.IMic1Creator;
import com.github.croesch.micro_debug.gui.components.basic.MDButton;
import com.github.croesch.micro_debug.gui.components.basic.MDLabel;
import com.github.croesch.micro_debug.gui.components.basic.MDTextField;
import com.github.croesch.micro_debug.gui.components.basic.SizedFrame;
import com.github.croesch.micro_debug.gui.i18n.GuiText;
import com.github.croesch.micro_debug.gui.listener.DoubleClickActivatingListener;
import com.github.croesch.micro_debug.gui.listener.WindowDisposer;
import com.github.croesch.micro_debug.gui.settings.InternalSettings;

/**
 * Frame to select binary files to create a {@link com.github.croesch.micro_debug.mic1.Mic1} instance.
 * 
 * @author croesch
 * @since Date: Mar 9, 2012
 */
final class StartFrame extends SizedFrame implements IBinaryFilePathProvider {

  /** generated serial version UID */
  private static final long serialVersionUID = -2795433471888532957L;

  /** the height of this frame */
  private static final int FRAME_HEIGHT = 300;

  /** the width of this frame */
  private static final int FRAME_WIDTH = 485;

  /** the text field that contains the path to the binary assembler file */
  @NotNull
  private final MDTextField macroPathField = new MDTextField("macro-assembler-file-path");

  /** the text field that contains the path to the binary micro assembler file */
  @NotNull
  private final MDTextField microPathField = new MDTextField("micro-assembler-file-path");

  /** the object that is able to create the {@link com.github.croesch.micro_debug.mic1.Mic1} */
  @NotNull
  private final transient IMic1Creator mic1Creator;

  /** the label containing a description for the user */
  @NotNull
  private final MDLabel descriptionLabel = new MDLabel("description");;

  /**
   * Constructs a frame to select the binary files for creating a {@link com.github.croesch.micro_debug.mic1.Mic1}.
   * 
   * @since Date: Mar 9, 2012
   * @param creator the {@link IMic1Creator} that should receive the file paths entered via this frame.
   */
  public StartFrame(final IMic1Creator creator) {
    super(GuiText.GUI_START_TITLE.text(InternalSettings.NAME), new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    setResizable(true); // fix for unexplainable painting behavior..
    setName("start-frame");

    this.mic1Creator = creator;

    setLayout(new MigLayout("wrap 2, fill",
                            "[grow,fill][fill]",
                            "[grow][sg spc][sg tf][sg spc][sg tf][sg spc][sg tf][sg spc][]"));

    addDescriptionLabels();
    addMicroAssemblerSection();
    addAssemblerSection();
    addButtons();
  }

  /**
   * Constructs a frame to select the binary files for creating a {@link com.github.croesch.micro_debug.mic1.Mic1}. The
   * given paths will be set to the specific text field.
   * 
   * @since Date: Mar 9, 2012
   * @param microAssemblerPath preset value for the path of the binary micro assembler file, may be <code>null</code>
   * @param assemblerPath preset value for the path of the binary assembler file, may be <code>null</code>
   * @param creator the {@link IMic1Creator} that should receive the file paths entered via this frame.
   */
  public StartFrame(final String microAssemblerPath, final String assemblerPath, final IMic1Creator creator) {
    this(creator);
    setTextToTextFieldAndEnableIt(this.microPathField, microAssemblerPath);
    setTextToTextFieldAndEnableIt(this.macroPathField, assemblerPath);

    if (!Utils.isNullOrEmpty(microAssemblerPath) ^ !Utils.isNullOrEmpty(assemblerPath)) {
      this.descriptionLabel.setForeground(Color.RED);

      if (Utils.isNullOrEmpty(microAssemblerPath)) {
        this.descriptionLabel.setText(GuiText.GUI_START_MICRO_WFF.text());
      } else {
        this.descriptionLabel.setText(GuiText.GUI_START_MACRO_WFF.text());
      }
    }
  }

  /**
   * Sets the given text to the given {@link MDTextField} and disables the text field. If the text to set is
   * <code>null</code> or empty, nothing will be done.
   * 
   * @since Date: Mar 9, 2012
   * @param field the text field to set the given text to
   * @param text the text to set to the text field, may be <code>null</code>
   * @see Utils#isNullOrEmpty(String)
   */
  private static void setTextToTextFieldAndEnableIt(final MDTextField field, final String text) {
    if (!Utils.isNullOrEmpty(text)) {
      field.setText(text.trim());
      field.setEnabled(false);
      field.setEditable(false);
    }
  }

  /**
   * Creates descriptive labels and adds them to the frame.
   * 
   * @since Date: Jul 15, 2012
   */
  private void addDescriptionLabels() {
    this.descriptionLabel.setText(GuiText.GUI_START_DESCRIPTION.text());

    add(this.descriptionLabel, "skip 2, wrap");
  }

  /**
   * Creates the buttons that are important for this frame and adds them to the frame.
   * 
   * @since Date: Mar 9, 2012
   */
  private void addButtons() {
    final Action okayAction = new Mic1CreatingAction(this.mic1Creator, this, GuiText.GUI_START_OKAY);
    final MDButton btn = new MDButton("okay", okayAction);
    new TextFieldsDocumentListener(this.microPathField, this.macroPathField, okayAction);
    btn.addActionListener(new WindowDisposer(this));
    add(btn, "skip 3");

    getRootPane().setDefaultButton(btn);
  }

  /**
   * Creates the components to select the assembler file and adds them to the frame.
   * 
   * @since Date: Mar 9, 2012
   */
  private void addAssemblerSection() {
    final MDLabel label = new MDLabel("macro-assembler-file", GuiText.GUI_START_MACRO);
    final MDButton btn = new MDButton("macro-assembler-file-browse",
                                      new BrowsePathForTextFieldAction(this.macroPathField, this));

    this.macroPathField.addMouseListener(new DoubleClickActivatingListener());

    add(label, "wrap");
    add(this.macroPathField);
    add(btn);
  }

  /**
   * Creates the components to select the micro assembler file and adds them to the frame.
   * 
   * @since Date: Mar 9, 2012
   */
  private void addMicroAssemblerSection() {
    final MDLabel label = new MDLabel("micro-assembler-file", GuiText.GUI_START_MICRO);
    final MDButton btn = new MDButton("micro-assembler-file-browse",
                                      new BrowsePathForTextFieldAction(this.microPathField, this));

    this.microPathField.addMouseListener(new DoubleClickActivatingListener());

    add(label, "skip 2, wrap");
    add(this.microPathField);
    add(btn);
  }

  /** {@inheritDoc} */
  public String getMacroAssemblerFilePath() {
    return this.macroPathField.getText();
  }

  /** {@inheritDoc} */
  public String getMicroAssemblerFilePath() {
    return this.microPathField.getText();
  }
}
