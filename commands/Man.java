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

import java.util.Hashtable;
import java.lang.reflect.*;

/**
 * Represents a Man instance. Gives the required documentation for each command.
 */

public class Man extends Command {

  /**
   * Returns documentation for this command when man man is called.
   * 
   * @return A string array containing the command documentation.
   */

  public static String[] getDocumentation() {

    String[] manual = new String[3];
    manual[0] = "man -- format and print documentation";
    manual[1] = "man CMD";
    manual[2] = "Print the documentation for the specific command CMD";
    return manual;
  }

  /**
   * Creates a Hashtable to get instances of the required commands.
   * 
   * @return A hashtable of all commands.
   */

  private static Hashtable<String, String> getCommandClasses() {
    Hashtable<String, String> commandClasses = new Hashtable<String, String>();
    commandClasses.put("cat", "Cat");
    commandClasses.put("cd", "Cd");
    commandClasses.put("cp", "Cp");
    commandClasses.put("echo", "Echo");
    commandClasses.put("exit", "Exit");
    commandClasses.put("get", "Get");
    commandClasses.put("grep", "Grep");
    commandClasses.put("!", "ExclamationPoint");
    commandClasses.put("history", "History");
    commandClasses.put("ls", "Ls");
    commandClasses.put("man", "Man");
    commandClasses.put("mkdir", "Mkdir");
    commandClasses.put("mv", "Mv");
    commandClasses.put("popd", "Popd");
    commandClasses.put("pushd", "Pushd");
    commandClasses.put("pwd", "Pwd");

    return commandClasses;
  }

  /**
   * Checks if the user input is valid by checking the number of arguments.
   * 
   * @param command The command entered by the user. Contains the string man
   *        followed by the command for which documentation is required.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);

    if (justCommand.length != 2) {
      throw new InvalidArgCountException(
          "Specify exactly one command to retrieve its documentation.");
    }
  }

  /**
   * Print documentation for a certain command.
   * 
   * @param command The array specifying which command documentation needs to be
   *        printed.
   * @param simulation The default argument for the execute methods in children
   *        of Command, even though this command does not require it.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    String output = "";
    try {
      checkNumArgs(command);
    } catch (InvalidArgCountException e) {
      System.out.println(e.getMessage());
      return;
    }
    Hashtable<String, String> commandClasses = getCommandClasses();
    try {
      output += executeMan(commandClasses, command);    
      if (checkRedirect(command) == false) {
        System.out.println(output);
      } else {
        redirect(output, command, simulation);
      }
    } catch (RedirectFailureException e) {
      System.out.println(e.getMessage());
    } catch (IllegalAccessException | ClassNotFoundException
        | InvocationTargetException | NoSuchMethodException e) {
      System.out.println(
          "This command has no documentation because it is invalid.");
    }
  }

  /**
   * Helper funtion of execute. Print documentation for a certain command.
   * 
   * @param commandClasses The hashtable of the pool of all commands
   * @param command The user input command
   * @return The output string
   * @throws ClassNotFoundException
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  private String executeMan(Hashtable<String, String> commandClasses, 
      String[] command) throws ClassNotFoundException, 
      NoSuchMethodException, SecurityException, IllegalAccessException, 
      IllegalArgumentException, InvocationTargetException {
    String output = "";
    String opener = commandClasses.get(command[1]);
    Class<?> commandClass = Class.forName("commands." + opener);
    Method docMethod = commandClass.getMethod("getDocumentation");
    Object manualObject = docMethod.invoke((Class<?>[]) null);
    String[] manual = (String[]) manualObject;
    output += ("\nNAME\n" + manual[0] + "\n\nSYNOPSIS\n" + manual[1]
        + "\n\nDESCRIPTION\n" + manual[2] + "\n");
    return output;
  }

  
}
