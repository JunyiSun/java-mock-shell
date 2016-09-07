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
 * Represents a cat instance that prints the contents of a file to the console.
 *
 */

public class Cat extends Command {

  /**
   * Returns a string array of documentation. Will be printed when man cat is
   * called.
   * 
   * @return A string array with the synopsis, name and description of the
   *         command.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "cat -- Display the contents of files";
    manual[1] = "cat File1 [File2...]";

    manual[2] =
        "Display the contents of FILE1 and other files"
            + "(i.e. File2 ...)\nconcatenated in the shell. Use three line "
            + "breaks to separate the\ncontents "
            + "of one file from the other file.";

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

    if (justCommand.length < 2) {
      throw new InvalidArgCountException("Specify at least one "
          + "path to a file.");
    }
  }

  /**
   * Using methods in SystemHierarchy, finds file specified by user arguments.
   * Then prints contents of file onto console or a file specified by the user
   * followed by three new lines.
   * 
   * @param command The cat command followed by an arbitrary number of
   *        arguments.
   * @param simulation The storage area for the files that Cat reads.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    String output = "";
    try {
      checkNumArgs(command);
      String[] justCommands = noRedirectSymbols(command);      
      
      for (int i = 1; i < justCommands.length; i++) {
        output += executeCat(justCommands, simulation, i);        
      }
      if (checkRedirect(command)) {
        redirect(output, command, simulation);
      } else {
        System.out.println(output);
      }
    } catch (InvalidArgCountException | RedirectFailureException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Helper function that print contents of file onto console or a file 
   * specified by the user followed by three new lines.
   * 
   * @param justCommands Command without redirect features
   * @param simulation The file system
   * @param i The index of argument
   * @return output string
   */
  private String executeCat(String[] justCommands, SystemHierarchy simulation, 
      int i) {
    String output = "";
    try {
      // for each argument specified by user in command[], find the last
      // item in the path (it would refer to the file they want printed)
      validateLastFileCharacter(justCommands[i]);
      DirectoryItem lastItem = getPathObject(justCommands[i], simulation);
      if (lastItem instanceof File) {
        output += ((File) lastItem).toString();
        // printing out newline characters between file contents
        if (i < justCommands.length - 1) {
          output += "\n\n\n";
        }
      } else {
        System.out.println(justCommands[i] + " is not a file");
      }
    } catch (InvalidNameException | InvalidPathException e) {
      System.out.println(e.getMessage());
    }
    return output;
  }
}
