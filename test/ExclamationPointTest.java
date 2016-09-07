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

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.*;
import data.SystemHierarchy;

/**
 * A JUnit test that checks various edge cases with ! command.
 *
 */

public class ExclamationPointTest {

  /**
   * outContent used to compare output on console to the String result expected.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  private static SystemHierarchy simulation;

  /**
   * Initializes PrintStream before each test method.
   */

  @Before
  public void setUp() throws Exception {
    simulation = SystemHierarchy.makeFileSystemSimulation();
    System.setOut(new PrintStream(outContent));
  }

  /**
   * Deletes OutputStream after each test method.
   */

  @After
  public void tearDown() throws Exception {
    System.setOut(null);
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests error message of giving wrong number of arguments to !.
   */

  @Test
  public void testCheckNumArgs() {
    String[] input = {"!"};
    ExclamationPoint command = new ExclamationPoint();
    command.execute(input, simulation);
    String result = "There should be exactly one number following the !.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message of giving too large or too small an argument to !.
   */

  @Test
  public void testPastCommandOutOfBounds() {
    String[] input = {"!", "0"};
    ExclamationPoint command = new ExclamationPoint();
    command.execute(input, simulation);
    String result = "The integer must be between 1 and the number "
        + "of commands that have been typed into this system.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests for error message if a non-integer is given.
   */

  @Test
  public void testIntegerOnly() {
    String[] input = {"!", "0.36"};
    ExclamationPoint command = new ExclamationPoint();
    command.execute(input, simulation);
    String result = "Specify an integer.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests ! with valid inputs.
   */

  @Test
  public void testCorrectCommand() {
    String[] input = {"pwd"};
    Pwd command = new Pwd();
    command.execute(input, simulation);
    simulation.addUserInput("pwd");
    String[] input2 = {"!", "1"};
    ExclamationPoint command2 = new ExclamationPoint();
    command2.execute(input2, simulation);
    String result = "/\n/\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests that nothing is done when redirect is given for !.
   */

  @Test
  public void testRedirect() {
    String[] input = {"pwd"};
    Pwd command = new Pwd();
    command.execute(input, simulation);
    simulation.addUserInput("pwd");
    String[] input2 = {"!", "1", ">", "hello"};
    ExclamationPoint command2 = new ExclamationPoint();
    command2.execute(input2, simulation);
    Cat readHello = new Cat();
    String[] catInput = {"cat", "hello"};
    readHello.execute(catInput, simulation);
    String result = "/\n/\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests for stack overflow exception error message if ! refers to a !
   * command.
   */

  @Test
  public void testRecursiveDepthExceeding() {
    String[] input = {"!", "1"};
    simulation.addUserInput("!1");
    ExclamationPoint command = new ExclamationPoint();
    command.execute(input, simulation);
    String expected = "You have reached the maximum recursion depth limit."
        + " This is likely due to a call on ! that refers to a past call on "
        + "!\n";
    assertEquals(expected, outContent.toString());
  }
}
