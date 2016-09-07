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

import java.util.ArrayList;

/**
 * Represents a history instance. Prints out the recent commands.
 */

public class History extends Command {

  /**
   * Returns documentation for this command when man history is called.
   * 
   * @return A string array containing the command documentation.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "history -- shows previous commands";
    manual[1] = "history [number]";

    manual[2] =
        "Prints all previous commands in a shell, unless a natural\n"
            + "number n is specified as an argument, in which case the last n\n"
            + "commands are printed to the console.";

    return manual;
  }

  /**
   * Checks if the user input is valid by checking the number of arguments.
   * 
   * @param command The command entered by the user. Contains the string history
   *        followed by an integer which is an optional argument.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command) 
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);

    if (justCommand.length != 2 && justCommand.length != 1) {
      throw new InvalidArgCountException("Please only give one number or none");
    }
  }

  /**
   * Checks if commands already executed are at least greater than the number
   * entered by the user.
   * 
   * @param numberPastCommands The number of past commands entered by the user
   * @param comparison The number of commands actually executed in the specific
   *        instance of SystemHierarchy.
   * @return True if at least <numberPastCommands> commands have been executed.
   */

  private boolean inBounds(int numberPastCommands, int comparison) {
    return numberPastCommands <= comparison && numberPastCommands >= 0;
  }

  /**
   * Prints either all the previous commands executed or the number of last few
   * commands as specified by the user.
   * 
   * @param command The command entered by the user. Contains the string history
   *        followed by an integer which is an optional argument.
   * @param simulation The particular instance of SystemHierarchy containing
   *        previously saved working directory paths.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    String output = "";
    try {
      checkNumArgs(command);
      String[] justCommands = noRedirectSymbols(command);
      ArrayList<String> pastCommands = simulation.getAllUserInputs();
      int num = 0;
      if (justCommands.length == 1) {
        num = 0;
        for (int i = num; i >= 0 && i < pastCommands.size(); i++) {
          output += (i + 1) + ". " + pastCommands.get(i) + "\n";
        }
      } else {
        output += executeHistoryNumber(justCommands, pastCommands, num);
      }
      if (checkRedirect(command) == false) {
        System.out.print(output);
      } else {
        redirect(output, command, simulation);
      }
    } catch (InvalidArgCountException | RedirectFailureException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Helper function that returns the number of last few commands 
   * as specified by the user.
   * 
   * @param justCommands The command without redirect features
   * @param pastCommands The list of previously input commands
   * @param num  The number of starting counted command
   * @return The string of output
   */
  private String executeHistoryNumber(String[] justCommands,
      ArrayList<String> pastCommands, int num) {
    String output = "";
    try {
      int size = Integer.parseInt(justCommands[1]);
      if (!inBounds(size, pastCommands.size())) {
        System.out.println("The integer must be between 1 and the number "
            + "of commands that have been typed into this system.");
        return output;
      } else {
        num = pastCommands.size() - size;
        for (int i = num; i >= 0 && i < pastCommands.size(); i++) {
          output += (i + 1) + ". " + pastCommands.get(i) + "\n";
        }
      }
    } catch (NumberFormatException e) {
      System.out.println("Specify an integer.");
      return output;
    }
    return output;

  }
}
