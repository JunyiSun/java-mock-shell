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
 * A JUnit test that tests the performance of the Grep command.
 *
 */

public class GrepTest {

  /**
   * outContent used to compare output on console to the String result expected
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Instance of file system simulation.
   */

  private static SystemHierarchy simulation;

  /**
   * Initializes PrintStream before each test method. Also creates instance of
   * SystemHierarchy in accordance with Singleton Design Principle.
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
   * Tests for error message when grep is called with an invalid number of
   * arguments.
   */

  @Test
  public void testInvalidNumberOfArguments() {
    // should have 3 or more arguments
    String[] input = {"grep"};
    Grep command = new Grep();
    command.execute(input, simulation);
    String result = "Specify a regex and a path.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests to ensure error message when incorrect number of arguments is given
   * when the optional parameter -r is given.
   */

  @Test
  public void testInvalidNumberOfArgumentsOptionalParameter() {
    // should have 4 or more arguments
    String[] input = {"grep", "-r", "m"};
    Grep command = new Grep();
    command.execute(input, simulation);
    String result = "Specify a regex and a path.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests to ensure error message when a regex is not passed with quotation
   * marks.
   */
  @Test
  public void testDoubleQuotationMarks() {
    String[] input = {"echo", "\"\"", ">", "toyota"};
    Echo echo = new Echo();
    echo.execute(input, simulation);
    String[] input2 = {"grep", "abc'", "toyota"};
    Grep grep = new Grep();
    grep.execute(input2, simulation);
    String result =
        "Use double quotation marks in" + " your regular expression.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests for error message with invalid pathname.
   */
  @Test
  public void testInvalidPath() {
    String[] input = {"grep", "\"abc\"", "/h/j/k/l"};
    Grep grep = new Grep();
    String expected = "/h/j/k/l: The given path is invalid.\n";
    grep.execute(input, simulation);
    assertEquals(expected, outContent.toString());
  }

  /**
   * Tests to find regular expression in a file via a pathname.
   */
  @Test
  public void testExecuteNoOptionalParameter() {
    String[] input =
        {"echo", "\"b \na \n12 43\nsdffjpiweot23879\"", ">>", "./abc"};
    Echo echo = new Echo();
    echo.execute(input, simulation);
    // equivalent to grep "\d{3}|([^\d]+\s)" abc
    String[] input2 = {"grep", "\"\\d{3}|[^0-9]\\s\"", "abc"};
    Grep grep = new Grep();
    grep.execute(input2, simulation);
    String expected = "b \na \nsdffjpiweot23879\n";
    assertEquals(expected, outContent.toString());
  }

  /**
   * Tests for error message if folder is specified without -r.
   */
  @Test
  public void testFolderNonRecursive() {
    String[] input2 = {"grep", "\"\\d{3}|[^0-9]\\s\"", "./"};
    Grep grep = new Grep();
    grep.execute(input2, simulation);
    String expected = "./: The given path is not a file. Use -r or -R as an"
        + " optional argument.\n";
    assertEquals(expected, outContent.toString());
  }

  /**
   * Tests to find regular expression within a folder. Only the lines that have
   * the valid expression should be printed.
   */
  @Test
  public void testExecuteRecursive() {
    Echo echo = new Echo();
    Mkdir mkdir = new Mkdir();
    Cp cp = new Cp();
    Grep grep = new Grep();
    String[] input1 = {"echo", "\"aab7\n   4908ii2brownfox\nJava\"", ">", "f"};
    String[] input2 = {"mkdir", "a", "b", "c", "a/b", "/a/b/d", "c/f"};
    String[] input3 = {"cp", "f", "a/b/d/l"};
    String[] input4 = {"echo", "\"15555555555brow\"", ">>", "c/f/m"};
    String[] input5 = {"echo", "\"brown >+ 2222\"", ">", "f"};
    String[] input6 = {"grep", "-R", "\"\\d{5}|brown\"", "../"};
    echo.execute(input1, simulation);
    mkdir.execute(input2, simulation);
    cp.execute(input3, simulation);
    echo.execute(input4, simulation);
    echo.execute(input5, simulation);
    grep.execute(input6, simulation);
    String expected = "/f: brown >+ 2222\n" + "/a/b/d/l:    4908ii2brownfox\n"
        + "/c/f/m: 15555555555brow\n";
    assertEquals(expected, outContent.toString());
  }
  
  /**
   * Tests to find regular expression within a folder using redirect. 
   * Only the lines that have the valid expression should be printed.
   */
  @Test
  public void testExecuteRecursiveRedirect() {
    Echo echo = new Echo();
    Mkdir mkdir = new Mkdir();
    Cp cp = new Cp();
    Grep grep = new Grep();
    String[] input1 = {"echo", "\"aab7\n   4908ii2brownfox\nJava\"", ">", "f"};
    String[] input2 = {"mkdir", "a", "b", "c", "a/b", "/a/b/d", "c/f"};
    String[] input3 = {"cp", "f", "a/b/d/l"};
    String[] input4 = {"echo", "\"15555555555brow\"", ">>", "c/f/m"};
    String[] input5 = {"echo", "\"brown >+ 2222\"", ">", "f"};
    String[] input6 = {"grep", "-R", "\"\\d{5}|brown\"", "../", ">>", "file1"};
    echo.execute(input1, simulation);
    mkdir.execute(input2, simulation);
    cp.execute(input3, simulation);
    echo.execute(input4, simulation);
    echo.execute(input5, simulation);
    grep.execute(input6, simulation);
    String[] input = {"cat", "file1"};
    Cat command1 = new Cat();
    command1.execute(input, simulation);
    String expected = "/f: brown >+ 2222\n" + "/a/b/d/l:    4908ii2brownfox\n"
        + "/c/f/m: 15555555555brow\n";
    assertEquals(expected, outContent.toString());
  }


}
