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
import driver.JShell;
import exceptions.*;

import java.util.List;

/**
 * Represents an ExclamationPoint instance. Performs a past command.
 */

public class ExclamationPoint extends Command {

  /**
   * Returns documentation for this command when man ! is called.
   * 
   * @return A string array containing the command documentation.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "! -- executes previous command";
    manual[1] = "!number";

    manual[2] = "Executes a previous command again. The past command is\n"
        + "specified by the user by a number, number >= 1.";

    return manual;
  }

  /**
   * Checks if the user input is valid by checking the number of arguments.
   * 
   * @param command The command entered by the user. Contains the exclamation
   *        mark followed by a number.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);
    if (justCommand.length != 2) {
      throw new InvalidArgCountException(
          "There should be exactly one number following the !.");
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
    return numberPastCommands <= comparison && numberPastCommands > 0;
  }

  /**
   * Performs a past command again specified by a user entered number.
   * 
   * @param command The command entered by the user. Contains the exclamation
   *        mark followed by a number.
   * 
   * @param simulation The particular instance of SystemHierarchy containing
   *        previously saved working directory paths.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    try {
      checkNumArgs(command);
      executeRedo(command, simulation);   
    } catch (InvalidArgCountException e) {
      System.out.println(e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("Specify an integer.");
    } catch (StackOverflowError e) {
      System.out.println("You have reached the maximum recursion depth limit. "
          + "This is likely due to a call on ! that refers to a "
          + "past call on !");
    }
  }

  /**
   * Helper function of execute. 
   * Performs a past command again specified by a user entered number.
   * 
   * @param command The user input command
   * @param simulation The file system
   */
  private void executeRedo(String[] command, SystemHierarchy simulation) {
    List<String> pastCommands = simulation.getAllUserInputs();
    String commandExecute = "";
    int size = Integer.parseInt(command[1]);
    if (inBounds(size, pastCommands.size())) {
      commandExecute = pastCommands.get(size - 1);
      JShell executeAgain = new JShell();
      if (checkRedirect(command)) {
        commandExecute += " " + command[command.length - 2] + " "
            + command[command.length - 1];
      }
      executeAgain.processInput(commandExecute);
    } else {
      System.out.println("The integer must be between 1 and the number "
          + "of commands that have been typed into this system.");
    }  
  }
}
