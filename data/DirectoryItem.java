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
package data;

/**
 * Represents a DirectoryItem, either a Folder item or a File item.
 */

public abstract class DirectoryItem {

  /**
   * Name of the directory item.
   */

  protected String name;

  /**
   * Initialize a new DirectoryItem object.
   * 
   * @param name This is the name of the DirectoryItem.
   */

  public DirectoryItem(String name) {
    setName(name);
  }

  /**
   * Getter for the name of a DirectoryItem.
   * 
   * @return A string of the name of the DirectoryItem.
   */

  public String getName() {
    return name;
  }

  /**
   * Setter for the name of a DirectoryItem.
   */

  public void setName(String name) {
    this.name = name;
  }

  public abstract DirectoryItem clone();
}

