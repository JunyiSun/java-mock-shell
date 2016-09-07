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

import data.SystemHierarchy;
import exceptions.*;
import java.util.regex.*;

/**
 * Represents an instance of echo that can print a string to a file or to the
 * console.
 *
 */

public class Echo extends Command {

  /**
   * Returns documentation for the two echo commands.
   * 
   * @return A string array containing the command documentation
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "echo -- write in content in the file";
    manual[1] = "echo STRING";

    manual[2] = "Print STRING on the shell. \n"
        + "STRING is a string of characters surrounded by double "
        + "quotation\n" + "marks. Supports redirection of string output.";

    return manual;
  }

  /**
   * Checks if the user input is valid by checking the number of arguments and
   * type of arguments.
   * 
   * @param userInput The command entered by the user. Contains the string echo
   *        followed by other arguments.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] userInput)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(userInput);

    if (justCommand.length != 2) {
      throw new InvalidArgCountException(
          "Invalid number of arguments. Specify a string.");
    }

    // error if string is not surrounded by quotation marks.
    Matcher m = Pattern.compile("\"[^\"]*\"").matcher(justCommand[1]);
    if (!m.matches()) {
      throw new IllegalArgumentException(
          "Use double quotation marks in your string argument.");
    }
  }

  /**
   * Either prints the message or enters the message in a file by erasing or
   * appending depending on the user input.
   * 
   * @param userInput The command entered by the user. Contains the string echo
   *        followed by the other arguments.
   * @param simulation The particular instance of SystemHierarchy containing
   *        previously saved working directory paths.
   */

  public void execute(String[] userInput, SystemHierarchy simulation) {
    try {
      checkNumArgs(userInput);
      if (!checkRedirect(userInput)) {
        String printString =
            userInput[1].substring(1, userInput[1].length() - 1);
        System.out.println(printString);
      } else {
        String newContents =
            userInput[1].substring(1, userInput[1].length() - 1);
        redirect(newContents, userInput, simulation);
      }
    } catch (InvalidArgCountException | IllegalArgumentException
        | RedirectFailureException e) {
      System.out.println(e.getMessage());
    }
  }
}
