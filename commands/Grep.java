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

import data.DirectoryItem;
import data.File;
import data.Folder;
import data.SystemHierarchy;
import exceptions.*;

import java.util.List;
import java.util.regex.*;

public class Grep extends Command {

  /**
   * Returns a string array of documentation. Will be printed when man grep is
   * called.
   * 
   * @return A string array with the synopsis, name and description of the
   *         command.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "grep -- Display the contents of the folder";
    manual[1] = "grep [-R] REGEX PATH …";
    manual[2] =
        "If –R is not supplied, print any lines containing REGEX in PATH, \n"
            + "which must be a file. If –R is supplied, and PATH is a  \n"
            + "directory, recursively traverse the directory and,  \n"
            + "for all lines in all files that contain REGEX, print  \n"
            + "the path to the file (including the filename), then a colon, \n"
            + "then the line that contained REGEX. \n";
    return manual;
  }


  /**
   * Checks that the correct number of arguments have been entered by the user
   * for this command. Also checks that the regular expression is enclosed by
   * quotation marks.
   * 
   * @param command First element is grep and following are any arguments
   *        entered by user.
   * @throws InvalidArgCountException
   * @return True if user entered correct number of arguments.
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);
    String regex = "";
    if (justCommand.length < 3) {
      throw new InvalidArgCountException("Specify a regex and a path.");
    }
    if (justCommand[1].equals("-R") || justCommand[1].equals("-r")) {
      if (justCommand.length < 4) {
        throw new InvalidArgCountException("Specify a regex and a path.");
      }
      regex = command[2];
    } else {
      regex = command[1];
    }
    Matcher m = Pattern.compile("\"[^\"]*\"").matcher(regex);
    if (!m.matches()) {
      throw new IllegalArgumentException(
          "Use double quotation marks in" + " your regular expression.");
    }
  }

  /**
   * Executes grep. Searches for any patterns that match text in a file and
   * prints or redirects these lines.
   * 
   * @param command The user input.
   * @param simulation The file system.
   */

  @Override
  public void execute(String[] command, SystemHierarchy simulation) {
    try {
      checkNumArgs(command);
      String[] justCommand = noRedirectSymbols(command);
      boolean recursive = false;
      String regex = justCommand[1];
      int startPos = 2;
      if (justCommand[1].equals("-R") || justCommand[1].equals("-r")) {
        recursive = true;
        regex = justCommand[2];
        startPos++;
      }
      String result = executeGrep(startPos, justCommand, regex, 
          recursive, simulation, checkRedirect(command), justCommand);
      if (checkRedirect(command)) {
      	if (!result.equals("")) {
      	  result = result.substring(0, result.length()-1);
        }
        redirect(result, command, simulation);
      } else {
        System.out.print(result);
      }
    } catch (InvalidArgCountException | IllegalArgumentException 
    	    | RedirectFailureException e) {
      System.out.println(e.getMessage());
    }
  }
  
  
  /**
   * Helper function to execute grep. Searches for any patterns 
   * that match text in a file and prints or redirects these lines.
   * 
   * @param startPos The start position of justCommand
   * @param justCommand The command without redirect features
   * @param regex   The combination of characters that will be searched
   * @param recursive    If the command is recursive
   * @param simulation   The file system
   * @param redirect    If the command needs redirect
   * @param command    The actual user input command
   * @return The lines found, or the empty string
   */
  private String executeGrep(int startPos, String[] justCommand, 
      String regex, boolean recursive, SystemHierarchy simulation, 
      boolean redirect, String[] command) {
    String result = "";
    for (int j = startPos; j < justCommand.length; j++) {
      try {
        result += grepSinglePath(regex, justCommand[j], recursive, simulation);
      } catch (InvalidPathException | ClassCastException e) {
        System.out.println(justCommand[j] + ": " + e.getMessage());
      } catch (NullPointerException e) {
        System.out.println(justCommand[j] + ": " 
      + "Path does not point to an item in the system.");
      }
    }
    return result;
    
  }

  /**
   * Searches for regex in a single path. Looks for regex in either file or
   * folder provided.
   * 
   * @param regex Regex expression being searched for.
   * @param path Path given by user.
   * @param recursive Whether or not path is a folder.
   * @param simulation Current state of files and folders in simulation.
   * @return Result from searching for regex in file or folder.
   * @throws InvalidPathException Path supplied by user is incorrect.
   * @throws RedirectFailureException Redirect could not be performed.
   */
  private String grepSinglePath(String regex, String path, boolean recursive,
      SystemHierarchy simulation)
          throws InvalidPathException {
    DirectoryItem item = getPathObject(path, simulation);
    String result = "";
    if (item instanceof File) {
      result = searchRegexInFile(regex, (File) item);
    } else if (recursive) { // item is folder
      result = searchRegexInFolder(regex, (Folder) item, simulation);
    } else {
      throw new ClassCastException("The given path"
          + " is not a file. Use -r or -R as an optional argument.");
    }
    return result;
  }

  /**
   * Searches for regex in subcontents of a folder. If subcontent is a file and
   * has regex, adds full path to returned String. Otherwise, searches
   * recursively in folder for regex.
   * 
   * @param regex Regex expression being searched for.
   * @param item The current folder being searched in.
   * @param simulation The current state of files and folders.
   * @return Returns a string with full path of directory items containing
   *         regex.
   */
  private static String searchRegexInFolder(String regex, Folder item,
      SystemHierarchy simulation) {
    List<DirectoryItem> subcontents = item.getSubContents();
    String result = "";
    for (DirectoryItem subItem : subcontents) {
      if (subItem instanceof File) {
        String recursiveResult = searchRegexInFile(regex, (File) subItem);
        if (!recursiveResult.equals("")) {
          String fullPath = simulation.getFullPath(item);
          result += fullPath;
          // only the root directory's full path ends in a forward slash
          if (!fullPath.equals("/")) {
            result += "/";
          }
          result += subItem.getName() + ": " + recursiveResult;
        }
      } else {
        result += searchRegexInFolder(regex, (Folder) subItem, simulation);
      }
    }
    return result;
  }

  /**
   * Searches for regex in a file.
   * 
   * @param regex Regex expression being searched for.
   * @param item The file being searched in.
   * @return Returns lines containing regex.
   */
  private static String searchRegexInFile(String regex, File item) {
    String content = item.toString();
    return checkRegex(regex, content);
  }

  /**
   * Returns lines in a file containing the regex expression.
   * 
   * @param regex The regex expression being searched for.
   * @param stringToCheck The string currently being checked.
   * @return The lines containing the regex.
   */
  private static String checkRegex(String regex, String stringToCheck) {
    regex = regex.substring(1, regex.length() - 1);
    String[] lines = stringToCheck.split("\\n");
    // You define your regular expression (REGEX) using Pattern
    Pattern regexPattern = Pattern.compile(regex);
    // Cycle through the positive matches and store them in result
    // According to man grep on bash, any empty expression is matched.
    String result = "";
    for (String line : lines) {
      Matcher regexMatcher = regexPattern.matcher(line);
      if (regexMatcher.find()) {
        result += line + "\n";
      }
    }
    return result;
  }
}
