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
import exceptions.IdenticalNameException;
import exceptions.InvalidNameException;
import exceptions.DirectoryItemNotFoundException;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

/**
 * JUnit test that checks various methods for Folder.
 *
 */

public class FolderTest {

  /**
   * Creating an instance of Folder.
   */

  Folder parentFolder;

  /**
   * Initializes a folder.
   */

  @Before
  public void setUp() throws Exception {
    parentFolder = new Folder("parent", null);
  }

  /**
   * This test checks to ensure a newly created folder is initialized as an
   * empty arraylist.
   */

  @Test
  public void testGetSubContentsNoItems() {
    String newName = "computer";
    Folder newFolder = new Folder(newName, null);
    List<DirectoryItem> subContents = new ArrayList<DirectoryItem>(0);
    assertEquals(subContents, newFolder.getSubContents());
  }

  /**
   * Checks error message when creating a folder with the same name as one that
   * already exists in the parent folder.
   */

  @Test
  public void testGetSubContentsOneItem() {

    File file1 = new File("file1");
    List<DirectoryItem> expected = new ArrayList<DirectoryItem>(0);
    try {
      parentFolder.add(file1);
      expected.add(file1);
    } catch (InvalidNameException | IdenticalNameException e) {
      fail("Invalid or identical name exception found.");
    }
    List<DirectoryItem> actual = parentFolder.getSubContents();
    assertTrue(actual.size() == 1 && actual.get(0) == expected.get(0));
  }

  /**
   * This test ensures the sub contents are returned in the same order as they
   * were inserted.
   */

  @Test
  public void testGetSubContentsInOrderOfInsertion() {

    File file1 = new File("file1");
    Folder folder1 = new Folder("folder1", parentFolder);
    File file2 = new File("file2");
    List<DirectoryItem> expected = new ArrayList<DirectoryItem>(0);
    try {
      parentFolder.add(file1);
      parentFolder.add(folder1);
      parentFolder.add(file2);
      expected.add(file1);
      expected.add(folder1);
      expected.add(file2);
    } catch (InvalidNameException | IdenticalNameException e) {
      fail(e.getMessage());
    }
    ArrayList<DirectoryItem> actual = parentFolder.getSubContents();
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), actual.get(i));
    }
  }

  /**
   * This test ensures no items with the same name can be added to the same
   * folder.
   */

  @Test
  public void testAddItemWithNameAlreadyPresentInFolder()
      throws InvalidNameException {
    String actual = "";
    String expected =
        "You may not add an item with the same name to the same" + " location.";
    try {
      parentFolder.add(new File("child1"));
      parentFolder.add(new Folder("child1", parentFolder));
    } catch (IdenticalNameException e) {
      actual += e.getMessage();
    }
    assertEquals(expected, actual);
  }

  /**
   * This test ensures no item is added that has any illegal characters.
   */

  @Test
  public void testAddItemIllegalCharacters() throws IdenticalNameException {
    String[] invalids = new String[] {", ", "]", ">", "'", "\"", "|", ",", ";",
        "sdfsa{f", "sdfasf}", "$", "(", "}", "/abc/", "&", "*"};
    String actual = "";
    for (String name : invalids) {
      try {
        parentFolder.add(new Folder(name, parentFolder));
      } catch (InvalidNameException e) {
        actual += e.getMessage();
      }
    }
    String expected = "";
    for (int i = 0; i < invalids.length; i++) {
      expected += "Invalid character in file or folder.";
    }
    assertEquals(expected, actual);
  }

  /**
   * Checks that no output printed when folders correctly added as SubContents
   * to parent Folder.
   */

  @Test
  public void testAddItemValidCharacters() throws IdenticalNameException {
    String newName = "…";
    String newName2 = "abacadabra";
    String newName3 = "filee—";
    String newName4 = "0+.txt";
    String newName5 = "~~~";
    String actual = "";
    try {
      parentFolder.add(new Folder(newName, parentFolder));
      parentFolder.add(new Folder(newName2, parentFolder));
      parentFolder.add(new Folder(newName3, parentFolder));
      parentFolder.add(new Folder(newName4, parentFolder));
      parentFolder.add(new Folder(newName5, parentFolder));

    } catch (InvalidNameException e) {
      actual += e.getMessage();
    }
    assertEquals("", actual);
  }

  @Test
  public void testCloneNonRecursive()
      throws IdenticalNameException, InvalidNameException {
    Folder newFolder = new Folder("Bob", parentFolder);
    Folder clone = newFolder.clone();
    assertEquals(newFolder.getName(), clone.getName());
    assertEquals(newFolder.getSubContents().size(),
        clone.getSubContents().size());
    assertTrue(newFolder != clone);
  }

  @Test
  public void testCloneRecursive()
      throws IdenticalNameException, InvalidNameException {
    Folder newFolder = new Folder("Bob", parentFolder);
    newFolder.add(new Folder("Jane", newFolder));
    Folder clone = newFolder.clone();
    assertEquals(newFolder.getName(), clone.getName());
    assertEquals(newFolder.getSubContents().size(),
        clone.getSubContents().size());
    assertTrue(newFolder != clone);
  }

  /**
   * A test of the error raised when no item is present.
   */

  @Test
  public void testGetItemNotPresent() {
    String actual = "";
    String expected = "Folder or file could not be found in this path.";
    try {
      parentFolder.getSubItem("bridge");
    } catch (DirectoryItemNotFoundException e) {
      actual = e.getMessage();
    }
    assertEquals(expected, actual);
  }

  /**
   * A test of getSubItem when the item is present.
   */

  @Test
  public void testGetItemPresent()
      throws InvalidNameException, IdenticalNameException {
    parentFolder.add(new Folder("boing", parentFolder));
    String actual = "";
    String expected = "";
    try {
      parentFolder.getSubItem("boing");
    } catch (DirectoryItemNotFoundException e) {
      actual = e.getMessage();
    }
    assertEquals(expected, actual);
  }

  /**
   * A test of removeSubItem when the item is or is not present.
   */
  @Test
  public void testRemoveItemPresent()
      throws InvalidNameException, IdenticalNameException {
    Folder boing = new Folder("boing", parentFolder);
    parentFolder.add(boing);
    String actual = "";
    String expected = "";
    DirectoryItem item = null;
    try {
      item = parentFolder.removeSubItem("boing");
    } catch (DirectoryItemNotFoundException e) {
      actual = e.getMessage();
    }
    assertEquals(expected, actual);
    assertEquals(boing, item);
    expected = "Folder or file could not be found in this path.";
    try {
      item = parentFolder.removeSubItem("boing");
    } catch (DirectoryItemNotFoundException e) {
      actual = e.getMessage();
    }
    assertEquals(expected, actual);
  }

  /**
   * A test of canAdd on invalid or valid arguments.
   */
  @Test
  public void testCanAdd() throws InvalidNameException, IdenticalNameException {
    parentFolder.add(new Folder("hello", parentFolder));
    boolean actual = parentFolder.canAdd("jj");
    boolean actual2 = parentFolder.canAdd("\\ 0");
    boolean actual3 = parentFolder.canAdd("hello");

    assertTrue(actual3 == false && actual3 == actual2);
    assertTrue(actual == true);
  }

  /**
   * Tests to ensure that a folder returns true if it is equal to or contains
   * certain other folder.
   */
  @Test
  public void testContainsFolder()
      throws InvalidNameException, IdenticalNameException {
    Folder boing = new Folder("john", parentFolder);
    Folder boingy = new Folder("hellokitty", boing);
    boing.add(new File("hello"));
    boing.add(boingy);
    parentFolder.add(boing);
    boolean t = parentFolder.containsFolder(boingy);
    boolean t2 = parentFolder.containsFolder(parentFolder);
    boolean f = parentFolder.containsFolder(new Folder("hellokitty", boing));
    assertTrue(t && t == t2);
    assertFalse(f);
  }



}
