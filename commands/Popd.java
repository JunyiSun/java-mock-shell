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
 * Represents a popd instance. Changes currentWorkingDirectory into directory
 * removed from top of stack.
 *
 */

public class Popd extends Command {

  /**
   * Prints documentation for this command when man popd is called.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] =
        "popd -- Remove the top entry from the directory stack,cd into it";
    manual[1] = "popd";
    manual[2] = "The popd command removes the top most directory from the\n"
        + "directory stack and makes it the current working directory.\n"
        + "If there is no directory onto the stack, then give\n"
        + "appropriate error message.";
    return manual;
  }

  /**
   * Checks if user has given correct number of arguments
   * 
   * @param command The command entered by the user. Contains the string popd
   *        followed by any arguments.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);

    if (justCommand.length != 1) {
      throw new InvalidArgCountException("Popd takes no additional arguments.");
    }
  }

  /**
   * Changes the current working directory to the topmost item from the
   * directory stack. Prints an error message if the stack is empty.
   * 
   * @param command The command to be executed.
   * @param simulation The particular user instance of JShell containing
   *        previously saved working directory paths.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    String output = "";
    try {
      checkNumArgs(command);
    } catch (InvalidArgCountException e) {
      System.out.println(e.getMessage());
      return;
    }
    output += executePopd(simulation);   
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
   * Helper function for execute method.
   * Changes the current working directory to the topmost item from the
   * directory stack. Prints an error message if the stack is empty.
   * 
   * @param simulation The file system
   * @return output string
   */
  private String executePopd(SystemHierarchy simulation) {
    String output = "";
 // access the directory stack, remove the last item to access a pathname
    PathStack traceback = simulation.getDirStack();
    String lastPath = traceback.pop();
    try {
      Folder newWorkingDirectory = (Folder) getPathObject(lastPath, simulation);
      simulation.setPresentWorkingDirectory(newWorkingDirectory);
      output += simulation.getFullPath(simulation.getCurrFolder())+"\n";
    } catch (NullPointerException e) {
      System.out.println("The Directory Stack is empty.");
    } catch (Exception e) {
      // if the file system has changed, an error message is printed since
      // failed to enter previous working directory
      traceback.push(lastPath);
      System.out.print("File system has since changed, cannot return to "
          + "previous directory.");
    }
    return output;
  }

}

