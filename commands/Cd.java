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
import exceptions.*;

/**
 * Represents a cd instance that changes the currentWorkingDirectory via an
 * absolute or relative path.
 */

public class Cd extends Command {

  /**
   * Returns a string array of documentation. Will be printed when man cd is
   * called.
   * 
   * @return A string array with the synopsis, name and description of the
   *         command.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "cd -- change directory";
    manual[1] = "cd [PATH...]";

    manual[2] = "Changes directory to the path specified, which may be\n"
        + "a relative path or from the home directory. Prints an error\n"
        + " message if no directory is found in the specified location.";

    return manual;
  }

  /**
   * Checks that the correct number of arguments have been entered by the user
   * for this command.
   * 
   * @param command First element is cat and following are any arguments entered
   *        by user.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);

    if (justCommand.length != 2) {
      throw new InvalidArgCountException(
          "Specify exactly one directory path to change to.");
    }
  }

  /**
   * Change the current working directory in a folder system.
   * 
   * @param command The name of this command followed by a pathname.
   * @param simulation The instance of a folder system whose working directory
   *        Cd changes.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    try {
      checkNumArgs(command);
      String[] justCommand = noRedirectSymbols(command);
      Folder newWorkingDirectory =
          (Folder) getPathObject(justCommand[1], simulation);
      simulation.setPresentWorkingDirectory(newWorkingDirectory);
    } catch (InvalidArgCountException | InvalidPathException e) {
      System.out.println(e.getMessage());
    } catch (ClassCastException | NullPointerException e) {
      System.out.println("Path does not point to a directory in the system.");
    }
  }
}
