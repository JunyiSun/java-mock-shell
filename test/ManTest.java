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
 * JUnit test that checks various edge cases with man command.
 *
 */

public class ManTest {

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
   * Deletes OutputStream after each test method, and resets simulation.
   */

  @After
  public void tearDown() throws Exception {
    System.setOut(null);
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Testing result of entering man man as a command.
   */

  @Test
  public void testManofMan() {
    String[] input = {"man", "man"};
    Man command = new Man();
    command.execute(input, simulation);
    String result = "\nNAME\nman -- format and print documentation\n\nSYNOPSIS"
        + "\nman CMD\n\nDESCRIPTION\nPrint the documentation for the specific "
        + "command CMD\n\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message when man is called on an invalid command.
   */

  @Test
  public void testManOfInvalidCommand() {
    String[] input = {"man", "lala"};
    Man command = new Man();
    command.execute(input, simulation);
    String result =
        "This command has no documentation because it is invalid.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether man works when output is redirected to a file.
   */

  @Test
  public void testCheckRedirect() {
    String[] input = {"man", "man", ">>", "a.txt"};
    Man command = new Man();
    command.execute(input, simulation);

    String[] catCommand = {"cat", "a.txt"};
    Cat executeCat = new Cat();
    executeCat.execute(catCommand, simulation);
    String result = "\nNAME\nman -- format and print documentation\n\nSYNOPSIS"
        + "\nman CMD\n\nDESCRIPTION\nPrint the documentation for the specific "
        + "command CMD\n\n";
    assertEquals(result, outContent.toString());
  }
}
