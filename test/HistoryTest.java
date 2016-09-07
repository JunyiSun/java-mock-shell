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

import data.*;
import commands.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test that checks various edge cases with history command.
 */

public class HistoryTest {

  /**
   * outContent used to compare output on console to the String result expected.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Instance of file system simulation.
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
   * Deletes OutputStream after each test method and resets simulation.
   */

  @After
  public void tearDown() throws Exception {
    System.setOut(null);
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests whether all the history is printed when history command called.
   */

  @Test
  public void testOne() {
    String[] input = {"history"};
    simulation.addUserInput("history");
    History command = new History();
    command.execute(input, simulation);

    String result = "1. history\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests result when history called after user calls multiple commands.
   */

  @Test
  public void testAllCommands() {
    String[] input = {"mkdir", "newDir"};
    simulation.addUserInput("mkdir newDir");
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] input1 = {"ls"};
    simulation.addUserInput("ls");
    Ls command1 = new Ls();
    command1.execute(input1, simulation);

    String[] input2 = {"cd", "newDir"};
    simulation.addUserInput("cd newDir");
    Cd command2 = new Cd();
    command2.execute(input2, simulation);

    String[] input3 = {"pwd"};
    simulation.addUserInput("pwd");
    Pwd command3 = new Pwd();
    command3.execute(input3, simulation);

    String[] input4 = {"history"};
    simulation.addUserInput("history");
    History command4 = new History();
    command4.execute(input4, simulation);

    String result =
        "newDir\n/newDir\n1. mkdir newDir\n2. ls\n3. cd newDir\n4. pwd\n5. "
            + "history\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether certain number of commands are printed after calling hitory
   * followed by a certain number.
   */

  @Test
  public void testGiveNumber() {
    String[] input = {"mkdir", "newDir"};
    simulation.addUserInput("mkdir newDir");
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] input1 = {"ls"};
    simulation.addUserInput("ls");
    Ls command1 = new Ls();
    command1.execute(input1, simulation);

    String[] input2 = {"cd", "newDir"};
    simulation.addUserInput("cd newDir");
    Cd command2 = new Cd();
    command2.execute(input2, simulation);

    String[] input3 = {"pwd"};
    simulation.addUserInput("pwd");
    Pwd command3 = new Pwd();
    command3.execute(input3, simulation);

    String[] input4 = {"history", "3"};
    simulation.addUserInput("history 3");
    History command4 = new History();
    command4.execute(input4, simulation);

    String result = "newDir\n/newDir\n3. cd newDir\n4. pwd\n5. history 3\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether history works when output is redirected to a file.
   */

  @Test
  public void testcheckRedirect() {
    String[] input = {"history", ">", "bye.txt"};
    simulation.addUserInput("history");
    History command = new History();
    command.execute(input, simulation);

    String[] catCommand = {"cat", "bye.txt"};
    Cat readCommand = new Cat();
    readCommand.execute(catCommand, simulation);

    String result = "1. history\n\n";
    assertEquals(result, outContent.toString());
  }
}
