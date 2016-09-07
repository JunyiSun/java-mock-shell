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
 * A JUnit test that checks various edge cases with echo command.
 *
 */

public class EchoTest {

  /**
   * outContent used to compare output on console to the String result expected.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * This instance of the file system simulation.
   */

  private static SystemHierarchy simulation;

  /**
   * Initializes simulation and PrintStream before each test method.
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
   * Tests echo for no quotation mark provided.
   */

  @Test
  public void testEchoNoQuotationMark() {
    Echo echoString = new Echo();
    String echoCommand[] = {"echo", "No quotation marks"};
    echoString.execute(echoCommand, simulation);

    String result = "Use double quotation marks in your string argument.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests echo for wrong number of arguments provided.
   */

  @Test
  public void testEchoWrongArgument() {
    Echo echoString = new Echo();
    String echoCommand[] = {"echo", "\"This is a string\"", ">"};
    echoString.execute(echoCommand, simulation);

    String result = "Invalid number of arguments. Specify a string.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests echo for wrong path of file provided.
   */

  @Test
  public void testEchoWrongPath() {
    Echo echo = new Echo();
    String echoCommand[] = {"echo", "\"This is a string\"", ">", "newfile"};
    echo.execute(echoCommand, simulation);

    Echo echo2 = new Echo();
    String echoCommand2[] =
        {"echo", "\"This is a new string in the file\"", ">", "/b/newfile"};
    echo2.execute(echoCommand2, simulation);

    String result = "The given path is invalid.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests echo for printing a given string to the console.
   */

  @Test
  public void testEchoString() {
    Echo echoString = new Echo();
    String echoCommand[] = {"echo", "\"This is a string\""};
    echoString.execute(echoCommand, simulation);

    String result = "This is a string\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests echo's ability to create a new file if it does not exist, and
   * overwrite contents of the file.
   */

  @Test
  public void testEchoErase() {
    Echo echoErase = new Echo();
    String echoCommand[] =
        {"echo", "\"This is a string in the file\"", ">", "newfile"};
    echoErase.execute(echoCommand, simulation);

    String[] input = {"ls"};
    Ls command = new Ls();
    command.execute(input, simulation);

    String result = "newfile\n";
    assertEquals(result, outContent.toString());

    Cat command1 = new Cat();
    String input1[] = {"cat", "newfile"};
    command1.execute(input1, simulation);
    String result1 = "newfile\nThis is a string in the file\n";
    assertEquals(result1, outContent.toString());

    String echoCommand2[] =
        {"echo", "\"This is a new string in the file\"", ">", "newfile"};
    echoErase.execute(echoCommand2, simulation);

    command1.execute(input1, simulation);
    String result2 = "newfile\nThis is a string in the file\nThis is a new "
        + "string in the file\n";
    assertEquals(result2, outContent.toString());
  }

  /**
   * Tests echo's ability to append content to the content of an existing file,
   * and to create a file if it does not exist.
   */

  @Test
  public void testEchoAppend() {
    Echo echoErase = new Echo();
    String echoCommand[] =
        {"echo", "\"This is a string in the file\"", ">", "newfile"};
    echoErase.execute(echoCommand, simulation);

    Echo echoAppend = new Echo();
    String echoCommand2[] =
        {"echo", "\"This is a new string in the file\"", ">>", "newfile"};
    echoAppend.execute(echoCommand2, simulation);

    Cat command2 = new Cat();
    String input2[] = {"cat", "newfile"};
    command2.execute(input2, simulation);
    String result2 =
        "This is a string in the fileThis is a new string in the file\n";
    assertEquals(result2, outContent.toString());
  }
}
