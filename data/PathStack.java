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

import java.util.Stack;
import java.util.EmptyStackException;

/**
 * Represents a pathstack item which holds paths in a LIFO order.
 */

public class PathStack {

  /**
   * A stack that holds string representations of paths. Paths are pushed into
   * the stack the user. Holds the directories in a LIFo order.
   */

  Stack<String> pathStack = new Stack<String>();

  /**
   * Adds a path representing the currentWorkingDirectory into the stack.
   * 
   * @param currDir String representation of the path for the currentWorking
   *        Directory.
   */

  public void push(String currDir) {
    pathStack.push(currDir);
  }

  /**
   * Removes last path added into the stack.
   * 
   * @return Either a string representing the most recent path or null if the
   *         stack is empty.
   */

  public String pop() {
    try {
      // removing most recent path and returning it
      return (String) pathStack.pop();
      // if the stack is empty, an exception is thrown
    } catch (EmptyStackException e) {
      return null;
    }
  }
}
