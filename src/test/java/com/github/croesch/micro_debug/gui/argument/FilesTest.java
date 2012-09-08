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

import org.junit.Test;

import com.github.croesch.micro_debug.argument.AArgument;
import com.github.croesch.micro_debug.argument.UnknownArgument;
import com.github.croesch.micro_debug.argument.WrongParameterNumberArgument;
import com.github.croesch.micro_debug.gui.DefaultTestCase;
import com.github.croesch.micro_debug.gui.MicroDebug;
import com.github.croesch.micro_debug.gui.components.start.Mic1Starter;

/**
 * Provides test cases for {@link Files}.
 * 
 * @author croesch
 * @since Date: Sep 8, 2012
 */
public class FilesTest extends DefaultTestCase {

  @Test
  public final void testExecuteFiles() {
    printlnMethodName();
    assertThat(Files.getInstance().execute()).isTrue();

    final Mic1Starter starter = new Mic1Starter();
    assertThat(starter.getMicroFilePath()).isEmpty();
    assertThat(starter.getMacroFilePath()).isEmpty();

    assertThat(Files.getInstance().execute(starter, "a", null)).isTrue();
    assertThat(starter.getMicroFilePath()).isEqualTo("a");
    assertThat(starter.getMacroFilePath()).isEmpty();

    assertThat(Files.getInstance().execute(starter, null, "b")).isTrue();
    assertThat(starter.getMicroFilePath()).isEmpty();
    assertThat(starter.getMacroFilePath()).isEqualTo("b");

    assertThat(Files.getInstance().execute(starter, "c", "d")).isTrue();
    assertThat(starter.getMicroFilePath()).isEqualTo("c");
    assertThat(starter.getMacroFilePath()).isEqualTo("d");
  }

  @Test
  public final void testCreateArgumentList_FilesInArray() {
    printlnMethodName();
    String[] args = new String[] { "-f", "a", "b" };
    MicroDebug.createListOfPossibleArguments();

    assertThat(AArgument.createArgumentList(args).keySet()).containsOnly(Files.getInstance());
    assertThat(AArgument.createArgumentList(args).get(Files.getInstance())).isEqualTo(new String[] { "a", "b" });

    args = new String[] { "--files", "c", "d" };
    assertThat(AArgument.createArgumentList(args).keySet()).containsOnly(Files.getInstance());
    assertThat(AArgument.createArgumentList(args).get(Files.getInstance())).isEqualTo(new String[] { "c", "d" });

    args = new String[] { "--files", "c" };
    assertThat(AArgument.createArgumentList(args).keySet()).containsOnly(WrongParameterNumberArgument.getInstance(),
                                                                         UnknownArgument.getInstance());
    assertThat(AArgument.createArgumentList(args).get(WrongParameterNumberArgument.getInstance())).isEqualTo(new String[] { "--files" });
    assertThat(AArgument.createArgumentList(args).get(UnknownArgument.getInstance())).isEqualTo(new String[] { "c" });
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testAIOOBE1() {
    Files.getInstance().execute(new Mic1Starter(), new String[] {});
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testAIOOBE2() {
    Files.getInstance().execute(new Mic1Starter(), new String[] { null });
  }

  @Test(expected = NullPointerException.class)
  public void testNPE() {
    Files.getInstance().execute(new Mic1Starter(), (String[]) null);
  }
}
