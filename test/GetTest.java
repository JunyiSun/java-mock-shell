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

import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A JUnit test that checks various edge cases with get command.
 *
 */

public class GetTest {

  /**
   * outContent used to compare output on console to the String result expected.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Initializes PrintStream before each test method.
   */

  @Before
  public void setUp() throws Exception {
    System.setOut(new PrintStream(outContent));
  }

  /**
   * Deletes OutputStream after each test method.
   */

  @After
  public void tearDown() throws Exception {
    System.setOut(null);
  }

  /**
   * Tests error message of giving wrong number of arguments to Get.
   */

  @Test
  public void testCheckNumArgs() {
    SystemHierarchy simulation = SystemHierarchy.makeFileSystemSimulation();
    String[] input = {"get"};
    Get command = new Get();
    command.execute(input, simulation);
    String result = "Invalid number of arguments.\n";
    assertEquals(result, outContent.toString());
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests error message printed when cannot connect to URL.
   */

  @Test
  public void testCannotConnect() {
    SystemHierarchy simulation = SystemHierarchy.makeFileSystemSimulation();
    String[] input = {"get", "https://hello"};
    Get command = new Get();
    command.execute(input, simulation);
    String result = "Could not connect to URL.\n";
    assertEquals(result, outContent.toString());
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests whether file from URL is added.
   */

  @Test
  public void testCorrectURL() {
    SystemHierarchy simulation = SystemHierarchy.makeFileSystemSimulation();
    String[] input = {"get", "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt"};
    Get command = new Get();
    command.execute(input, simulation);
    String[] lsCommand = {"ls"};
    Ls lsExecute = new Ls();
    lsExecute.execute(lsCommand, simulation);
    String result = "073.txt\n";
    assertEquals(result, outContent.toString());
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests error message when incorrect URL is given.
   */

  @Test
  public void testIncorrectURL() {
    SystemHierarchy simulation = SystemHierarchy.makeFileSystemSimulation();
    String[] input = {"get", "hello"};
    Get command = new Get();
    command.execute(input, simulation);
    String result = "URL is not valid.\n";
    assertEquals(result, outContent.toString());
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests error message when incorrect URL is given.
   */

  @Test
  public void testBadURL() {
    SystemHierarchy simulation = SystemHierarchy.makeFileSystemSimulation();
    String[] input = {"get", "https://"};
    Get command = new Get();
    command.execute(input, simulation);
    String result = "URL is not valid.\n";
    assertEquals(result, outContent.toString());
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests whether get works when the output is redirected to a file.
   */

  @Test
  public void testCheckRedirect() {
    SystemHierarchy simulation = SystemHierarchy.makeFileSystemSimulation();
    String[] input =
        {"get", "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt", ">", "hello"};
    Get command = new Get();
    command.execute(input, simulation);
    String result = "";
    assertEquals(result, outContent.toString());
    SystemHierarchy.resetFileSystemSimulation();
  }
}
