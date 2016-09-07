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
import commands.Mkdir;
import commands.Cd;
import commands.Command;
import exceptions.*;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * This class tests the file system structure of SystemHierarchy.
 */
public class SystemHierarchyTest {
  private static SystemHierarchy simulation;

  /**
   * Initializes the file system.
   */
  @Before
  public void setUp() {
    simulation = SystemHierarchy.makeFileSystemSimulation();
  }

  /**
   * Resets the file system.
   */
  @After
  public void tearDown() {
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * This method checks to ensure that no two SystemHierarchy instances can be
   * made.
   */

  @Test
  public void testSingletonDesign() {
    SystemHierarchy simulation = SystemHierarchy.makeFileSystemSimulation();
    SystemHierarchy simulation2 = SystemHierarchy.makeFileSystemSimulation();
    assertEquals(simulation, simulation2);
  }

  /**
   * This method checks to ensure the full path is correct when no items are in
   * the path.
   */
  @Test
  public void testGetFullPathJustRootDirectory() {
    String expected = "/";
    String actual = simulation.getFullPath(simulation.getCurrFolder());
    assertEquals(expected, actual);
  }

  /**
   * This method ensures that the full path is correct when there are multiple
   * items in the path.
   */
  @Test
  public void testGetFullPathMultipleItems() {
    Mkdir mkdir = new Mkdir();
    String[] command = {"mkdir", "bin", "bin/bash", "bin/bash/bunny", "baby"};
    mkdir.execute(command, simulation);
    Cd cd = new Cd();
    String[] command1 = {"cd", "/bin/bash/"};
    cd.execute(command1, simulation);
    String expected = "/bin/bash";
    String actual = simulation.getFullPath(simulation.getCurrFolder());
    assertEquals(expected, actual);
  }

  /**
   * Test to get an existing item in SystemHierarchy.
   */
  @Test
  public void testGetLastItemInPath() throws InvalidPathException {
    Mkdir mkdir = new Mkdir();
    String[] command = {"mkdir", "cube", "bin", "bin/bash", "bin/bash/bunny",
        "baby", "ruby", "ruby/dummy", "cube/tube", "./cube/tube/noob"};
    mkdir.execute(command, simulation);
    Cd cd = new Cd();
    String[] command1 = {"cd", "/bin/"};
    cd.execute(command1, simulation);
    String[] command2 = {"bash", "..", "..", "cube", "tube", "noob", "."};
    Folder sourceFolder = simulation.getCurrFolder();
    DirectoryItem item = simulation.getLastItemInPath(sourceFolder, command2);
    String expected = "noob";
    String actual = item.getName();
    assertEquals(expected, actual);
  }

  /**
   * Test with valid path but with last item in path not pointing to a file or
   * folder in the system (should return null).
   */
  @Test
  public void testGetLastItemInPathNull() throws InvalidPathException {
    Mkdir mkdir = new Mkdir();
    String[] command = {"mkdir", "cube", "bin", "bin/bash", "bin/bash/bunny",
        "baby", "ruby", "ruby/dummy", "cube/tube", "./cube/tube/noob"};
    mkdir.execute(command, simulation);
    Folder sourceFolder = simulation.getCurrFolder();
    String[] path = {"./././../../ruby/../ruby/dummy/box/"};
    DirectoryItem item = simulation.getLastItemInPath(sourceFolder, path);
    assertEquals(null, item);
  }

  /**
   * Test with invalid path (should raise error).
   */

  @Test
  public void testGetLastItemInPathInvalid() {
    Mkdir mkdir = new Mkdir();
    String[] command = {"mkdir", "cube", "bin", "bin/bash", "bin/bash/bunny",
        "baby", "ruby", "ruby/dummy", "cube/tube", "./cube/tube/noob"};
    mkdir.execute(command, simulation);
    Folder sourceFolder = simulation.getCurrFolder();
    String[] path = {"bin", "bash", "betelgeuse", "goose"};
    String expected = "The given path is invalid.";
    String actual = "";
    try {
      DirectoryItem item = simulation.getLastItemInPath(sourceFolder, path);
    } catch (InvalidPathException e) {
      actual += e.getMessage();
    }
    assertEquals(expected, actual);
  }



}
