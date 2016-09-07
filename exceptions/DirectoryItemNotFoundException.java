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
package exceptions;

/**
 * An exception thrown whenever a Directory Item is not found.
 */

public class DirectoryItemNotFoundException extends Exception {

  /**
   * The error message for the exception
   */

  private String message = "";

  /**
   * Constructor for the exception if error message is given
   */

  public DirectoryItemNotFoundException(String message) {
    this.message = message;
  }

  /**
   * Constructor for the exception if no error message is given
   */

  public DirectoryItemNotFoundException() {}

  /**
   * Getter for the error message
   */

  public String getMessage() {
    return message;
  }

}
