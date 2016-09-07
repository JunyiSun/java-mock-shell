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
 * A JUnit test that tests the performance of the Ls command.
 *
 */

public class LsTest {

  /**
   * outContent used to compare output on console to the String result expected
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Instance of file system simulation.
   */

  private SystemHierarchy simulation;

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
   * Tests that nothing is printed when ls is called on an empty folder.
   */

  @Test
  public void testEmpty() {
    String[] input = {"ls"};
    Ls command = new Ls();
    command.execute(input, simulation);
    String result = "";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests ls with one folder being created.
   */

  @Test
  public void testOneItem() {
    String[] input = {"mkdir", "newDir"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] input1 = {"ls"};
    Ls command1 = new Ls();
    command1.execute(input1, simulation);
    String result = "newDir\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests ls with two folders being created.
   */

  @Test
  public void testTwoItem() {
    String[] input = {"mkdir", "newDir1", "newDir2"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] input1 = {"ls"};
    Ls command1 = new Ls();
    command1.execute(input1, simulation);
    String result = "newDir1\nnewDir2\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests ls command when full path is given for a folder.
   */

  @Test
  public void testPathOfDir() {
    String[] input = {"mkdir", "newDir1", "newDir2"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] input1 = {"ls", "/newDir1"};
    Ls command1 = new Ls();
    command1.execute(input1, simulation);
    String result = "/newDir1:\n\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests ls on a file created through echo and given with full path by user.
   */

  @Test
  public void testPathOfFile() {
    String[] input = {"echo", "\"stringoutput\"", ">", "newfile"};
    Echo command = new Echo();
    command.execute(input, simulation);

    String[] input1 = {"ls", "/newfile"};
    Ls command1 = new Ls();
    command1.execute(input1, simulation);
    String result = "newfile\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests error message printed when ls called on a directory that doesn't
   * exist.
   */

  @Test
  public void testDoesNotExist() {
    String[] input = {"mkdir", "newDir1", "newDir2"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] input1 = {"ls", "/newDir3"};
    Ls command1 = new Ls();
    command1.execute(input1, simulation);
    String result = input1[1] + ":Path does not exist.\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether ls works when the output is redirected to a file.
   */

  @Test
  public void testCheckRedirect() {
    String[] input = {"mkdir", "newDir1", "newDir2"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    String[] input1 = {"ls", ">", "a.txt"};
    Ls command1 = new Ls();
    command1.execute(input1, simulation);

    String[] catCommand = {"cat", "a.txt"};
    Cat executeCat = new Cat();
    executeCat.execute(catCommand, simulation);
    String result = "newDir1\nnewDir2\n\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether ls prints all sub directories recursively.
   */

  @Test
  public void testRecursionAll() {
    String[] input = {"mkdir", "newDir1", "newDir2"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "/newDir1"};
    changeDir.execute(cdCommand, simulation);

    String[] input2 = {"mkdir", "newDir3", "newDir4"};
    command.execute(input2, simulation);

    String cdCommand2[] = {"cd", "/"};
    changeDir.execute(cdCommand2, simulation);

    String[] input3 = {"ls", "-R"};
    Ls command3 = new Ls();
    command3.execute(input3, simulation);
    String result = "newDir1\nnewDir2\n\n/newDir1:\nnewDir3\nnewDir4\n\n"
        + "/newDir1/newDir3:\n/newDir1/newDir4:\n/newDir2:\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether ls prints all sub directories recursively.
   */

  @Test
  public void testRecursionAll2() {
    String[] input = {"mkdir", "newDir1", "newDir2"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "/newDir1"};
    changeDir.execute(cdCommand, simulation);

    String[] input2 = {"mkdir", "newDir3", "newDir4"};
    command.execute(input2, simulation);

    Echo echo = new Echo();
    String echoCommand[] = {"echo", "\"This is a string\"", ">", "newfile"};
    echo.execute(echoCommand, simulation);

    String cdCommand3[] = {"cd", "newDir3"};
    changeDir.execute(cdCommand3, simulation);

    String[] input4 = {"mkdir", "newDir3", "newDir4"};
    command.execute(input4, simulation);

    String echoCommand2[] = {"echo", "\"This is a sentence\"", ">", "oldfile"};
    echo.execute(echoCommand2, simulation);

    String cdCommand2[] = {"cd", "/"};
    changeDir.execute(cdCommand2, simulation);

    String[] input3 = {"ls", "-R"};
    Ls command3 = new Ls();
    command3.execute(input3, simulation);
    String result =
        "newDir1\nnewDir2\n\n/newDir1:\nnewDir3\nnewDir4\nnewfile\n\n"
            + "/newDir1/newDir3:\nnewDir3\nnewDir4\noldfile\n\n"
            + "/newDir1/newDir3/newDir3:\n/newDir1/newDir3/newDir4:"
            + "\n/newDir1/newDir4:\n/newDir2:\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether ls prints all sub directories recursively.
   */

  @Test
  public void testRecursionPath() {
    String[] input = {"mkdir", "newDir1", "newDir2"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "/newDir1"};
    changeDir.execute(cdCommand, simulation);

    String[] input2 = {"mkdir", "newDir3", "newDir4"};
    command.execute(input2, simulation);

    Echo echo = new Echo();
    String echoCommand[] = {"echo", "\"This is a string\"", ">", "newfile"};
    echo.execute(echoCommand, simulation);

    String cdCommand3[] = {"cd", "newDir3"};
    changeDir.execute(cdCommand3, simulation);

    String[] input4 = {"mkdir", "newDir3", "newDir4"};
    command.execute(input4, simulation);

    String echoCommand2[] = {"echo", "\"This is a sentence\"", ">", "oldfile"};
    echo.execute(echoCommand2, simulation);

    String cdCommand2[] = {"cd", "/"};
    changeDir.execute(cdCommand2, simulation);

    String[] input3 = {"ls", "-R", "/newDir1/"};
    Ls command3 = new Ls();
    command3.execute(input3, simulation);
    String result = "/newDir1:\nnewDir3\nnewDir4\nnewfile\n\n"
        + "/newDir1/newDir3:\nnewDir3\nnewDir4\noldfile\n\n"
        + "/newDir1/newDir3/newDir3:\n/newDir1/newDir3/newDir4:"
        + "\n/newDir1/newDir4:\n\n";
    assertEquals(result, outContent.toString());
  }
  
  @Test
  public void testRecursionPathRedirect() {
    String[] input = {"mkdir", "newDir1", "newDir2"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);

    Cd changeDir = new Cd();
    String cdCommand[] = {"cd", "/newDir1"};
    changeDir.execute(cdCommand, simulation);

    String[] input2 = {"mkdir", "newDir3", "newDir4"};
    command.execute(input2, simulation);

    Echo echo = new Echo();
    String echoCommand[] = {"echo", "\"This is a string\"", ">", "newfile"};
    echo.execute(echoCommand, simulation);

    String cdCommand3[] = {"cd", "newDir3"};
    changeDir.execute(cdCommand3, simulation);

    String[] input4 = {"mkdir", "newDir3", "newDir4"};
    command.execute(input4, simulation);

    String echoCommand2[] = {"echo", "\"This is a sentence\"", ">", "oldfile"};
    echo.execute(echoCommand2, simulation);

    String cdCommand2[] = {"cd", "/"};
    changeDir.execute(cdCommand2, simulation);

    String[] input3 = {"ls", "-R", "/newDir1/", ">>", "file"};
    Ls command3 = new Ls();
    command3.execute(input3, simulation);
    
    Cat command1 = new Cat();
    String[] input1 = {"cat", "file"};
    command1.execute(input1, simulation);
    
    String result = "/newDir1:\nnewDir3\nnewDir4\nnewfile\n\n"
        + "/newDir1/newDir3:\nnewDir3\nnewDir4\noldfile\n\n"
        + "/newDir1/newDir3/newDir3:\n/newDir1/newDir3/newDir4:"
        + "\n/newDir1/newDir4:\n\n\n";
    assertEquals(result, outContent.toString());
  }
}
