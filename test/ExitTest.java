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

import org.junit.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A JUnit test that checks cases with exit command.
 */

public class ExitTest {

  /**
   * since the exit method is called when the exit command is executed, this
   * functionality is not tested here; rather, it is tested manually by starting
   * the JShell and typing "exit".
   */

  /**
   * outContent used to compare output on console to the String result expected.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Creating an instance of folder system.
   */

  private SystemHierarchy simulation;

  /**
   * Initializes SystemHierarchy and PrintStream before each test method.
   */

  @Before
  public void setUp() throws Exception {
    System.setOut(new PrintStream(outContent));
    simulation = SystemHierarchy.makeFileSystemSimulation();
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
   * Test for error message if too many arguments are given to exit command.
   */

  @Test
  public void testCheckNumArgs() {
    String[] input = {"exit", "hello"};
    Exit command = new Exit();
    command.execute(input, simulation);
    String result = "Too many arguments provided\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether exit prints an error message when output is redirected to a
   * file.
   */

  @Test
  public void testRedirect() {
    String[] input = {"exit", ">", "hello"};
    Exit command = new Exit();
    command.execute(input, simulation);
    String result = "Too many arguments provided\n";
    assertEquals(result, outContent.toString());
  }
}
