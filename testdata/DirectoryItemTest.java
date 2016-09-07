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
import org.junit.Test;

/**
 * JUnit test to check naming of DirectoryItems.
 */

public class DirectoryItemTest {

  /**
   * This test ensures a file is initialized with a given name.
   */

  @Test
  public void testGetName() {
    String newName = "roger.txt";
    File newFile = new File(newName);
    assertEquals(newName, newFile.getName());
  }
}
