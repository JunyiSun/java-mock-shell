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

/**
 * Represents a exit instance. Terminates the program when this command is
 * called.
 */

public class Exit extends Command {

  /**
   * Returns documentation for this command when man exit is called.
   * 
   * @return A string array containing the command documentation.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "exit -- terminate the program";
    manual[1] = "exit";
    manual[2] = "Quit the program";
    return manual;
  }

  /**
   * Checks if the user input is valid by checking the number of arguments.
   * 
   * @param command The command entered by the user. Should contain only the
   *        string exit.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    if (command.length != 1) {
      throw new InvalidArgCountException("Too many arguments provided");
    }
  }

  /**
   * Exits or terminates the program if valid input is given by the user.
   * 
   * @param command The command entered by the user. Should contain only the
   *        string exit.
   * @param simulation The particular instance of SystemHierarchy containing
   *        previously saved working directory paths.
   */

  public void execute(String[] userInput, SystemHierarchy simulation) {
    try {
      checkNumArgs(userInput);
    } catch (InvalidArgCountException e) {
      System.out.println(e.getMessage());
      return;
    }
    System.exit(0);
  }
}
