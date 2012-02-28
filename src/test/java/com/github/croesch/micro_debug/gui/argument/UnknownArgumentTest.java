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
package com.github.croesch.micro_debug.gui.argument;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.github.croesch.micro_debug.argument.AArgument;
import com.github.croesch.micro_debug.argument.UnknownArgument;
import com.github.croesch.micro_debug.gui.DefaultTestCase;
import com.github.croesch.micro_debug.gui.MicroDebug;
import com.github.croesch.micro_debug.i18n.Text;

/**
 * Provides test cases for {@link UnknownArgument}.
 * 
 * @author croesch
 * @since Date: Feb 28, 2012
 */
public class UnknownArgumentTest extends DefaultTestCase {

  @Test
  public final void testCreateArgumentList_UnknownArgument() {
    printlnMethodName();
    String[] args = new String[] { "" };
    assertThat(AArgument.createArgumentList(args).keySet()).containsOnly(UnknownArgument.getInstance());
    assertThat(AArgument.createArgumentList(args).get(UnknownArgument.getInstance())).containsOnly("");

    args = new String[] { " " };
    assertThat(AArgument.createArgumentList(args).keySet()).containsOnly(UnknownArgument.getInstance());
    assertThat(AArgument.createArgumentList(args).get(UnknownArgument.getInstance())).containsOnly(" ");

    args = new String[] { "-help" };
    assertThat(AArgument.createArgumentList(args).keySet()).containsOnly(UnknownArgument.getInstance());
    assertThat(AArgument.createArgumentList(args).get(UnknownArgument.getInstance())).containsOnly("-help");

    args = new String[] { "-help", "asd" };
    assertThat(AArgument.createArgumentList(args).keySet()).containsOnly(UnknownArgument.getInstance());
    assertThat(AArgument.createArgumentList(args).get(UnknownArgument.getInstance())).containsOnly("asd", "-help");
  }

  @Test
  public final void testExecuteUnknownArgument() throws IOException {
    printlnMethodName();
    MicroDebug.createListOfPossibleArguments();

    assertThat(UnknownArgument.getInstance().execute()).isTrue();
    assertThat(out.toString()).isEmpty();

    assertThat(UnknownArgument.getInstance().execute(new String[] {})).isTrue();
    assertThat(out.toString()).isEmpty();

    assertThat(UnknownArgument.getInstance().execute(new String[] { null })).isTrue();
    assertThat(out.toString()).isEmpty();

    assertThat(UnknownArgument.getInstance().execute(new String[] { "bla" })).isFalse();
    assertThat(out.toString()).isEqualTo(Text.ERROR.text(Text.UNKNOWN_ARGUMENT.text("bla")) + getLineSeparator()
                                                 + getHelpFileText());

    out.reset();

    assertThat(UnknownArgument.getInstance().execute(new String[] { "bla", "--bla", "-wow" })).isFalse();
    assertThat(out.toString()).isEqualTo(Text.ERROR.text(Text.UNKNOWN_ARGUMENT.text("bla")) + getLineSeparator()
                                                 + Text.ERROR.text(Text.UNKNOWN_ARGUMENT.text("--bla"))
                                                 + getLineSeparator()
                                                 + Text.ERROR.text(Text.UNKNOWN_ARGUMENT.text("-wow"))
                                                 + getLineSeparator() + getHelpFileText());
  }
}
