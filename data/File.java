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
 * Represents a file item, which can contain content as a string.
 */

public class File extends DirectoryItem {

  /**
   * Holds all the content inside this file.
   */

  private String contents = "";

  /**
   * Initialize a new File object with the contents as an empty string.
   * 
   * @param name This is the name of the file.
   */

  public File(String name) {
    super(name);
    contents = "";
  }

  /**
   * Getter for the contents of the file.
   * 
   * @return A string of the contents in the file
   */

  public String toString() {
    return contents;
  }

  /**
   * Adds the given string to the contents of the file.
   * 
   * @param new_content A string of content to be added to the file.
   */

  public void appendContent(String new_content) {
    this.contents += new_content;
  }

  /**
   * Erases the contents of a file.
   */

  public void eraseContent() {
    this.contents = "";
  }

  /**
   * Returns a deepcopy of the File object.
   */

  public File clone() {
    File duplicate = new File(name);
    duplicate.appendContent(contents);
    return duplicate;
  }
}
