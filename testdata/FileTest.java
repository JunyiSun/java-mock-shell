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
 * JUnit test for a file.
 *
 */

public class FileTest {

  /**
   * Creating a name for a file.
   */

  String newName = "roger.txt";

  /**
   * Creating an instance of a file.
   */

  File newFile;

  /**
   * Creating the file with the name given by newName.
   */

  @Before
  public void setUp() {
    newFile = new File(newName);
  }

  /**
   * Tests that contents of a file are printed when calling toString.
   */

  @Test
  public void testToStringNewFile() {
    String newName = "roger.txt";
    File newFile = new File(newName);
    assertEquals("", newFile.toString());
  }

  /**
   * This test ensures that a file can append content to the back of the
   * existing content.
   */

  @Test
  public void testAppendContent() {
    newFile.appendContent("john      hello");
    newFile.appendContent("\\\toblerone");
    assertEquals("john      hello\\\toblerone", newFile.toString());
  }

  /**
   * This test ensures that a file can erase all existing content, and add more
   * content after erasing.
   */

  @Test
  public void testEraseContent() {
    newFile.appendContent("john      hello");
    newFile.appendContent("\\\toblerone");
    newFile.eraseContent();
    assertEquals("", newFile.toString());
    newFile.appendContent("hello");
    assertEquals("hello", newFile.toString());
  }

  /**
   * This test ensures that a file is cloned with the same name and file
   * contents. The files should not refer to the same instance.
   */
  @Test
  public void testClone() {
    newFile.appendContent("boing");
    File item = newFile.clone();
    assertEquals(newFile.getName(), item.getName());
    assertEquals(newFile.toString(), item.toString());
    assertNotEquals(newFile, item);
  }
}
