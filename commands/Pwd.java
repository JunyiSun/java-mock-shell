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
package commands;

import data.*;
import exceptions.InvalidArgCountException;
import exceptions.RedirectFailureException;

/**
 * Represents a pwd instance that prints the absolute path for the
 * currentWorkingDirectory.
 */

public class Pwd extends Command {

  /**
   * Returns a string array of documentation. Will be printed when man pwd is
   * called.
   * 
   * @return A string array with the synopsis, name and description of the
   *         command.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "pwd -- Display the current working directory";
    manual[1] = "pwd";
    manual[2] =
        "Print the current working directory (including the whole path).";
    return manual;
  }

  /**
   * Checks that the correct number of arguments have been entered by the user
   * for this command.
   * 
   * @param command First element is pwd and following are any arguments entered
   *        by user.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);

    if (justCommand.length != 1) {
      throw new InvalidArgCountException("Too many arguments.");
    }
  }

  /**
   * Prints out the absolute path for the currentWorkingDirectory.
   * 
   * @param command Represents the command and arguments entered by the user.
   * @param simulation Holds all files and folders in the system. Also holds a
   *        pointer to the current working directory.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    try {
      checkNumArgs(command);
      String output = simulation.getFullPath(simulation.getCurrFolder());
      if (checkRedirect(command) == false) {
        System.out.println(output);
      } else {
        redirect(output, command, simulation);
      }
    } catch (InvalidArgCountException | RedirectFailureException e) {
      System.out.println(e.getMessage());
    }
  }
}
