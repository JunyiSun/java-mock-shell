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
 * JUnit test that checks edge cases for pwd command.
 */

public class PwdTest {

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
   * Tests directory printed when presentWorkingDirectory is root folder or
   * second level of directories.
   */

  @Test
  public void testSecondDirectory() {
    String[] input = {"mkdir", "newDir1"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] input1 = {"pwd"};
    Pwd command1 = new Pwd();
    command1.execute(input1, simulation);

    String result1 = "/\n";
    assertEquals(result1, outContent.toString());

    String[] input2 = {"cd", "newDir1"};
    Cd command2 = new Cd();
    command2.execute(input2, simulation);

    String[] input3 = {"pwd"};
    Pwd command3 = new Pwd();
    command3.execute(input3, simulation);

    String result = "/\n/newDir1\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests that only "/" is printed when pwd called on root folder.
   */

  @Test
  public void testRoot() {
    String[] input = {"pwd"};
    Pwd command = new Pwd();
    command.execute(input, simulation);
    String result = "/\n";
    assertEquals(result, outContent.toString());
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests whether pwd works when output is redirected to a file.
   */

  @Test
  public void testCheckRedirect() {
    String[] input = {"pwd", ">", "a.txt"};
    Pwd command = new Pwd();
    command.execute(input, simulation);

    String[] catCommand = {"cat", "a.txt"};
    Cat catExecute = new Cat();
    catExecute.execute(catCommand, simulation);
    String result = "/\n";
    assertEquals(result, outContent.toString());
    SystemHierarchy.resetFileSystemSimulation();
  }
}
