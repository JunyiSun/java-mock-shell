// **********************************************************
// Assignment2:
// Student1:
// CDF user_name: c5laljia
// UT Student #: 1002297338
// Author: Alisia Lalji
//
// Student2:
// CDF user_name: c5mogala
// UT Student #: 1001672022
// Author: Shweta Mogalapalli
//
// Student3:
// CDF user_name: c5zungjo
// UT Student #: 1001250175
// Author: Joshua Zung
//
// Student4:
// CDF user_name:c5sunjun
// UT Student #:1001350183
// Author:Junyi(Katherine) Sun
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package test;

import commands.*;

import data.SystemHierarchy;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A JUnit test that checks various edge cases with popd command.
 *
 */

public class PopdTest {

  /**
   * outContent used to compare output on console to the String result expected.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * This instance of the file system simulation.
   */

  private SystemHierarchy simulation;

  /**
   * Initializes PrintStream and simulation before each test method.
   */

  @Before
  public void setUp() throws Exception {
    System.setOut(new PrintStream(outContent));
    simulation = SystemHierarchy.makeFileSystemSimulation();
  }

  /**
   * Deletes OutputStream after each test method, and resets simulation.
   */

  @After
  public void tearDown() throws Exception {
    System.setOut(null);
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests error message when popd is given arguments.
   */

  @Test
  public void testCheckNumArgs() {
    String[] input = {"popd", "newDir"};
    Popd oldDir = new Popd();

    oldDir.execute(input, simulation);

    String result = "Popd takes no additional arguments.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests correct arguments given for a popd.
   */

  @Test
  public void testValidPopd() {
    String[] makeDirCommand = {"mkdir", "newDir"};
    Mkdir makeDir = new Mkdir();
    makeDir.execute(makeDirCommand, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "newDir"};
    changeDir.execute(cdCommand, simulation);

    Pushd saveDir = new Pushd();
    String pushdCommand[] = {"pushd", ".."};
    saveDir.execute(pushdCommand, simulation);

    Popd command = new Popd();
    String[] input = {"popd"};
    command.execute(input, simulation);

    Pwd printCurrDir = new Pwd();
    String pwdCommand[] = {"pwd"};
    printCurrDir.execute(pwdCommand, simulation);

    String result = "/\n/newDir\n/newDir\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message when popd is called on an empty directory stack.
   */

  @Test
  public void testEmptyStack() {
    String[] makeDirCommand = {"mkdir", "newDir"};
    Mkdir makeDir = new Mkdir();
    makeDir.execute(makeDirCommand, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "newDir"};
    changeDir.execute(cdCommand, simulation);

    Pushd saveDir = new Pushd();
    String pushdCommand[] = {"pushd", ".."};
    saveDir.execute(pushdCommand, simulation);

    Popd command = new Popd();
    String[] input = {"popd"};
    command.execute(input, simulation);
    command.execute(input, simulation);

    String result = "/\n/newDir\nThe Directory Stack is empty.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether popd works when output is redirected to a file.
   */

  @Test
  public void testCheckRedirect() {
    String[] makeDirCommand = {"mkdir", "newDir"};
    Mkdir makeDir = new Mkdir();
    makeDir.execute(makeDirCommand, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "newDir"};
    changeDir.execute(cdCommand, simulation);

    Pushd saveDir = new Pushd();
    String pushdCommand[] = {"pushd", ".."};
    saveDir.execute(pushdCommand, simulation);

    Popd command = new Popd();
    String[] input = {"popd", ">", "a.txt"};
    command.execute(input, simulation);

    String result = "/\n";
    assertEquals(result, outContent.toString());
  }
}

