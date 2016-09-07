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
package testdriver;

import driver.JShell;
import data.SystemHierarchy;
import static org.junit.Assert.*;

import org.junit.*;

/**
 * JUnit test that checks whether valid or invalid commands called.
 */

public class JShellTest {

  /**
   * Creating an instance of JShell for testing.
   */

  private JShell shell = new JShell();

  /**
   * Creating a simulation with Singleton Design Principle.
   */

  @Before
  public void setUp() {
    shell.setSimulation(SystemHierarchy.makeFileSystemSimulation());
  }

  /**
   * Resetting SystemHierarchy so that instance of SystemHierarchy is now equal
   * to null.
   */

  @After
  public void tearDown() {
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * This tests that an appropriate error message is called if the first command
   * in a user's line of input is not a valid command.
   */

  @Test
  public void testInvalidCommand() {
    String actual = shell.processInput("wrgyz");
    String expected = "Invalid command name, please try again.\n";
    assertEquals(expected, actual);
  }

  /**
   * This tests to ensure no error messages are called if the first command in a
   * user's line of input is valid.
   */

  @Test
  public void testValidCommand() {
    String actual = shell.processInput("mkdir /bin/");
    String expected = "";
    assertEquals(expected, actual);
  }

  /**
   * This tests to ensure a command can be split up by spaces but still be
   * considered valid by JShell.
   */

  @Test
  public void testValidWithSpaces() {
    String actual = shell.processInput("   pushd            ./");
    String expected = "";
    assertEquals(expected, actual);
  }

  /**
   * This tests to ensure that a command entry that has spaces within the word
   * is considered invalid.
   */

  @Test
  public void testInvalidBecauseOfSpaces() {
    String actual = shell.processInput("hist ory");
    String expected = "Invalid command name, please try again.\n";
    assertEquals(expected, actual);
  }
}
