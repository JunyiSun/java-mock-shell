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
package testdata;

import data.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test that checks various methods in PathStack class.
 *
 */

public class PathStackTest {

  /**
   * Declaring a PathStack object for testing.
   */

  PathStack workingDirectoryList;

  /**
   * Initializing PathStack object.
   */

  @Before
  public void setUp() {
    workingDirectoryList = new PathStack();
  }

  /**
   * Checks that the contents of a pop are the same as what was just pushd onto
   * the stack.
   */

  @Test
  public void testPushPopOneItem() {
    String newWorkingDirectoryPath = "/src/command";
    workingDirectoryList.push(newWorkingDirectoryPath);
    assertEquals(newWorkingDirectoryPath, workingDirectoryList.pop());
  }

  /**
   * A test to see whether the stack follows first in, first out behaviour.
   */

  @Test
  public void testPushPopMultipleItems() {
    String newWorkingDirectoryPath = "/src/command";
    String newWorkingDirectoryPath2 = "/src/data/";
    workingDirectoryList.push(newWorkingDirectoryPath);
    workingDirectoryList.push(newWorkingDirectoryPath2);
    assertEquals(newWorkingDirectoryPath2, workingDirectoryList.pop());
    assertEquals(newWorkingDirectoryPath, workingDirectoryList.pop());
  }

  /**
   * On an empty stack, the error should be handled and the stack should return
   * null.
   */

  @Test
  public void testPopErrorReturnValue() {
    assertNull(workingDirectoryList.pop());
  }
}
