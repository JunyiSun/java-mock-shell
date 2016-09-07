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

/**
 * A JUnit test that checks various edge cases with mkdir command.
 *
 */

public class MkdirTest {

  /**
   * This instance of the file system simulation.
   */

  private SystemHierarchy simulation;

  /**
   * outContent used to compare output on console to the String result expected.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Initializes PrintStream and a new version of simulation before each test
   * method.
   */

  @Before
  public void setUp() throws Exception {
    System.setOut(new PrintStream(outContent));
    simulation = SystemHierarchy.makeFileSystemSimulation();
  }

  /**
   * Deletes OutputStream after each test method, and resets simulation
   */

  @After
  public void tearDown() throws Exception {
    System.setOut(null);
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests if error message is given when no arguments are given to mkdir
   * command.
   */

  @Test
  public void testCheckNumArgs() {
    Mkdir makeDir = new Mkdir();
    String mkdirCommand[] = {"mkdir"};
    makeDir.execute(mkdirCommand, simulation);
    String result = "Specify a directory name.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests making a folder via a relative path.
   */

  @Test
  public void testRelativePath() {
    String[] input = {"mkdir", "newDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] input1 = {"cd", "newDir"};
    Cd command1 = new Cd();
    command1.execute(input1, simulation);

    String[] input2 = {"mkdir", "../././newDir/newDir2"};
    Mkdir command2 = new Mkdir();
    command2.execute(input2, simulation);

    String[] lsCommand = {"ls"};
    Ls listDir = new Ls();
    listDir.execute(lsCommand, simulation);

    String result = "newDir2\n";

    assertEquals(result, outContent.toString());
  }

  /**
   * Tests making a folder by absolute path.
   */

  @Test
  public void testAbsolutePath() {
    String[] input = {"mkdir", "newDir", "/newDir/oldDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] lsCommand = {"ls", "newDir"};
    Ls listDir = new Ls();
    listDir.execute(lsCommand, simulation);

    String result = "/newDir:\noldDir\n\n";

    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message when incorrect absolute path given as an argument.
   */

  @Test
  public void testInCorrectAbsolutePath() {
    String[] input = {"mkdir", "newDir", "/hello/oldDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String result = input[2] + ": Invalid directory pathname specified.\n";

    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message when creating a folder with a name that already exists
   * in the current directory.
   */

  @Test
  public void testDuplicateName() {
    String[] input = {"mkdir", "newDir", "newDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String result = "You may not add an item with the same name to the same "
        + "location.\n";

    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message when making a folder with an invalid name.
   */

  @Test
  public void testInvalidName() {
    String[] input = {"mkdir", "new?Dir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String result = "Invalid character in file or folder.\n";

    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message when pathname provided points to file instead of
   * folder.
   */

  @Test
  public void testMakeDirInFile() {
    String[] echo = {"echo", "\"hello.txt\"", ">>", "bye.txt"};
    Echo makeFile = new Echo();
    makeFile.execute(echo, simulation);

    String[] input = {"mkdir", "/bye.txt/newDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String result = input[1] + ": Invalid directory pathname specified.\n";

    assertEquals(result, outContent.toString());
  }

  /**
   * Tests that no outfile is created when redirect is used with mkdir.
   */

  @Test
  public void testCheckRedirect() {
    String[] input = {"mkdir", "a", ">", "b"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] lsInput = {"ls"};
    Ls lsCommand = new Ls();
    lsCommand.execute(lsInput, simulation);

    String result = "a\n";
    assertEquals(result, outContent.toString());
  }
}

