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

import data.SystemHierarchy;
import commands.*;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test that checks the ability of a command to move a file or folder
 * into either an existing directory or to create a copy of a name.
 *
 */

public class MvTest {

  /**
   * outContent used to compare output on console to the String result expected.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * File system simulation.
   */

  private static SystemHierarchy simulation;

  /**
   * Initializing the OutputStream before each test to check what is printed to
   * the console. Also creates simulation according to Singleton Design
   * Principle.
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
   * Tests error message when too few arguments given to cp.
   */

  @Test
  public void testCheckNumArgs() {
    Mv mvDir = new Mv();
    String mvCommand[] = {"mv", "/dog"};
    mvDir.execute(mvCommand, simulation);
    String result = "Specify an oldpath and a newpath.\n";
    assertEquals(result, outContent.toString());
  }

  @Test
  public void testMoveSimple() {
    Mkdir mkdir = new Mkdir();
    String mkdirCommand[] = {"mkdir", "1", "2", "/1/new"};
    mkdir.execute(mkdirCommand, simulation);
    Mv mvDir = new Mv();
    String mvCommand[] = {"mv", "/1/new", "/2"};
    mvDir.execute(mvCommand, simulation);
    Ls ls = new Ls();
    String lsCommand[] = {"ls", "/", "/1", "/2"};
    ls.execute(lsCommand, simulation);
    String result = "/:\n1\n2\n\n/1:\n\n/2:\nnew\n\n";
    assertEquals(result, outContent.toString());
  }

  @Test
  public void testMoveComplex() {
    Mkdir mkdir = new Mkdir();
    String mkdirCommand[] = {"mkdir", "1", "/1/new", "/1/new/hi", "2"};
    mkdir.execute(mkdirCommand, simulation);
    Mv mvDir = new Mv();
    String mvCommand[] = {"mv", "/1/new", "2"};
    mvDir.execute(mvCommand, simulation);
    Ls ls = new Ls();
    String lsCommand[] = {"ls", "/", "/1", "/2", "/2/new"};
    ls.execute(lsCommand, simulation);
    String result = "/:\n1\n2\n\n/1:\n\n/2:\nnew\n\n/2/new:\nhi\n\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether man works when folder or file is moved to a non-existing
   * path.
   */

  @Test
  public void testMoveNonExist() {
    Mkdir mkdir = new Mkdir();
    String mkdirCommand[] = {"mkdir", "1", "/1/new"};
    mkdir.execute(mkdirCommand, simulation);
    Mv mvDir = new Mv();
    String mvCommand[] = {"mv", "/1/new", "/2"};
    mvDir.execute(mvCommand, simulation);
    Ls ls = new Ls();
    String lsCommand[] = {"ls", "/", "/1", "/2"};
    ls.execute(lsCommand, simulation);
    String result = "/:\n1\n2\n\n/1:\n\n/2:\n\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether man works when folder or file is moved to a non-existing
   * path.
   */

  @Test
  public void testMoveNonExistComplex() {
    Mkdir mkdir = new Mkdir();
    String mkdirCommand[] = {"mkdir", "1", "/1/new", "/1/new/hi"};
    mkdir.execute(mkdirCommand, simulation);
    Mv mvDir = new Mv();
    String mvCommand[] = {"mv", "/1/new", "2"};
    mvDir.execute(mvCommand, simulation);
    Ls ls = new Ls();
    String lsCommand[] = {"ls", "/", "/1", "/2"};
    ls.execute(lsCommand, simulation);
    String result = "/:\n1\n2\n\n/1:\n\n/2:\nhi\n\n";
    assertEquals(result, outContent.toString());
  }

  /**
   * Tests whether man works when folder or file is moved to an invalid path.
   */

  @Test
  public void testMoveNonExistInvalidName() {
    Mkdir mkdir = new Mkdir();
    String mkdirCommand[] = {"mkdir", "1", "/1/new", "/1/new/hi"};
    mkdir.execute(mkdirCommand, simulation);
    Mv mvDir = new Mv();
    String mvCommand[] = {"mv", "/1/new", ">"};
    mvDir.execute(mvCommand, simulation);
    String result = "Proposed pathname already points to an existing "
        + "item in the system with the same name, or the name of "
        + "the item is invalid.\n";
    assertEquals(result, outContent.toString());
  }

}
