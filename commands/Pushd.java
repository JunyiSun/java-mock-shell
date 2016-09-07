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
 * Represents a pushd instance. Add currentWorkingDirectory onto the top of the
 * stack and changes the currentWorkingDirectory into the given Directory.
 *
 */

public class Pushd extends Command {

  /**
   * Prints documentation for this command when man pushd is called.
   * 
   * @return A string array with required documentation
   */

  public static String[] getDocumentation() {

    String[] manual = new String[3];
    manual[0] = "pushd -- Saves the current working directory by pushing onto"
        + "directory\nstack and then changes the new current working directory"
        + " to DIR.";
    manual[1] = "pushd DIR";
    manual[2] = "The pushd command saves the old current working directory in "
        + "directory\nstack so that it can be returned to at any time "
        + "via the popd command).\nThe size of the directory stack is dynamic"
        + " and dependent on the pushd and the popd commands.";
    return manual;

  }

  /**
   * Checks if the user input is valid by checking the number of arguments.
   * 
   * @param command The command entered by the user. Contains the string pushd
   *        followed by other arguments.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);

    if (justCommand.length != 2) {
      throw new InvalidArgCountException(
          "You need to specify one path name for the " + "new directory.");
    }
  }

  /**
   * Pushes the current working directory onto the top of the DirectoryStack and
   * changes the current working directory to given directory.
   * 
   * @param command The command entered by the user. Contains the string pushd
   *        followed by the Directory pathname.
   * @param simulation The particular instance of SystemHierarchy containing
   *        previously saved working directory paths.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    String output = "";
    try {
      checkNumArgs(command);
      output += executePushd(command, simulation);     
    } catch (InvalidArgCountException | InvalidPathException e) {
      System.out.println(e.getMessage());
    }
    try {
      if (checkRedirect(command) == false) {
        System.out.print(output);
      } else {
        redirect(output, command, simulation);
      }
    } catch (RedirectFailureException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Helper function of execute.
   * Pushes the current working directory onto the top of the DirectoryStack and
   * changes the current working directory to given directory.
   * @param command The user input command
   * @param simulation The file system
   * @return output string
   * @throws InvalidPathException
   */
  private String executePushd(String[] command, SystemHierarchy simulation) 
      throws InvalidPathException {
    String output = "";
    PathStack traceback = simulation.getDirStack();
    String oldPath = simulation.getFullPath(simulation.getCurrFolder());
    DirectoryItem newWorkingDirectory = getPathObject(command[1], simulation);
    if (newWorkingDirectory instanceof Folder) {
      simulation.setPresentWorkingDirectory((Folder) newWorkingDirectory);
      traceback.push(oldPath);
      output += simulation.getFullPath(simulation.getCurrFolder())+"\n";
    } else {
      throw new InvalidPathException(
          "Path does not point to a directory in the system.");
    }
    return output;
  }
}
