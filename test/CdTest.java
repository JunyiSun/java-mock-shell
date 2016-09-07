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

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.SystemHierarchy;
import commands.Mkdir;

/**
 * A JUnit test that checks various edge cases with cd command.
 *
 */

public class CdTest {

  /**
   * outContent used to compare output on console to the String result expected.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Instance of file system simulation.
   */

  private SystemHierarchy simulation;

  /**
   * Initializing the OutputStream before each test to check what is printed to
   * the console. Also creates simulation according to Singleton Design
   * Principle.
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
   * Tests error message when too many arguments given to cd.
   */

  @Test
  public void testCheckNumArgs() {
    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "/dog", "fish"};
    changeDir.execute(cdCommand, simulation);
    String result = "Specify exactly one directory path to change to.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message when too few arguments given to cd.
   */

  @Test
  public void testCheckNumArgs2() {
    Cd changeDir = new Cd();
    String cdCommand2[] = {"cd"};
    changeDir.execute(cdCommand2, simulation);
    String result = "Specify exactly one directory path to change to.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests changing the current directory into an absolute path given by the
   * user.
   */

  @Test
  public void testAbsolutePath() {
    String[] input = {"mkdir", "newDir", "/newDir/oldDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "/newDir/oldDir"};
    changeDir.execute(cdCommand, simulation);

    String pwdCommand[] = {"pwd"};
    Pwd printDir = new Pwd();
    printDir.execute(pwdCommand, simulation);

    String result = "/newDir/oldDir\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests changing the current directory into a relative path given by the
   * user.
   */

  @Test
  public void testRelativePath() {
    String[] input = {"mkdir", "newDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "newDir"};
    changeDir.execute(cdCommand, simulation);

    String pwdCommand[] = {"pwd"};
    Pwd printDir = new Pwd();
    printDir.execute(pwdCommand, simulation);

    String result = "/newDir\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message when input given by the user is an incorrect relative
   * path.
   */

  @Test
  public void testIncorrectRelativePath() {
    String[] input = {"mkdir", "newDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "oldDir"};
    changeDir.execute(cdCommand, simulation);

    String result = "Path does not point to a directory in the system.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message when the input given by the user is incorrect absolute
   * path.
   */

  @Test
  public void testIncorrectAbsolutePath() {

    String[] input = {"mkdir", "newDir", "/newDir/oldDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "/newDir/hello"};
    changeDir.execute(cdCommand, simulation);

    String result = "Path does not point to a directory in the system.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether cd works when the output is redirected to a file.
   */

  @Test
  public void testRedirect() {
    String[] input = {"mkdir", "newDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "newDir", ">", "file"};
    changeDir.execute(cdCommand, simulation);

    String pwdCommand[] = {"pwd"};
    Pwd printDir = new Pwd();
    printDir.execute(pwdCommand, simulation);

    String result = "/newDir\n";
    assertEquals(result, outContent.toString());
  }
}
