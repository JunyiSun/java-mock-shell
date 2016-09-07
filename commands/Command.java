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

import java.util.Arrays;
import java.util.regex.*;

/**
 * An abstract class serving as the parent for all the command classes. Contains
 * methods overridden by all subclasses such as getDocumentation, execute and
 * checkNumArgs. Also, has several methods inherited by all subclasses. This
 * includes returning certain path objects and names of objects as well as
 * deciding if paths are absolute or relative.
 *
 */

public abstract class Command {

  /**
   * Returns the documentation printed by man command for all subclasses. All
   * subclasses override this method to return specific documentation for their
   * own commands.It is a static method because an instance of the subclass is
   * not needed to get its documentation.
   * 
   * @return String array consisting of synopsis, name and description of each
   *         command.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    return manual;
  }

  /**
   * Checks if user entered current number of arguments for a command.
   * 
   * @param userInput Contains command name followed by any arguments user
   *        provides.
   * @throws InvalidArgCountException
   */

  protected abstract void checkNumArgs(String[] userInput)
      throws InvalidArgCountException;

  /**
   * Executes the command specified by the user with the arguments supplied.
   * First calls checkNumArgs method to ensure correct number of arguments
   * given.
   * 
   * @param userInput A string array with the command name followed by arguments
   *        given by user.
   * @param simulation Holds all the folders and files currently in system along
   *        with current working directory.
   */

  public abstract void execute(String[] userInput, SystemHierarchy simulation);

  /**
   * Returns the appropriate folder for a path, based on whether it is a
   * relative or absolute pathname.
   * 
   * @param pathname A string representing either a full or relative path.
   * @param simulation Holds all the folders and files currently in system along
   *        with current working directory and root folder which is the parent
   *        of all directories.
   * @return Returns the rootFolder if path is absolute and the currFolder if
   *         the path is relative.
   */

  protected Folder getSourceFolderForPath(String pathname,
      SystemHierarchy simulation) {
    Folder sourceFolder;
    // if it's an absolute path, the source folder is the rootFolder (the
    // parent of all folders)
    if (isAbsolutePath(pathname)) {
      sourceFolder = simulation.getRootFolder();
    } else {
      // if not then the sourceFolder is the current working directory
      sourceFolder = simulation.getCurrFolder();
    }
    return sourceFolder;
  }

  /**
   * Given a string representing an absolute/relative path, this method returns
   * the last DirectoryItem in the path.
   * 
   * @param path Either an absolute/relative path entered by the user.
   * @param simulation Holds all files and folders currently in system.
   * @return Returns DirectoryItem representing last item in path given by user
   * @throws InvalidPathException
   */

  protected DirectoryItem getPathObject(String path, SystemHierarchy simulation)
      throws InvalidPathException {
    // separates path into its separate folders by separating around "/"
    String[] pathArray = returnPathAsArray(path);
    Folder sourceFolder = getSourceFolderForPath(path, simulation);
    // to get last item, need to know where path starts(i.e. sourceFolder)
    return simulation.getLastItemInPath(sourceFolder, pathArray);
  }

  /**
   * A method that throws an InvalidNameException if the last character in a
   * proposed file name ends with a backslash.
   * 
   * @param pathname The proposed pathname to a file.
   * @throws InvalidPathException
   */

  protected void validateLastFileCharacter(String path)
      throws InvalidNameException {
    if (path.charAt(path.length() - 1) == '/') {
      throw new InvalidNameException("Invalid character in file or folder.");
    }
  }

  /**
   * Returns the proposed name of the the last item in a path.
   * 
   * @param path A String either representing the absolute or relative path
   *        given by the user.
   * @throws InvalidPathException
   * @return The name of the last item in the path.
   */

  protected String getNameOfItem(String path) throws InvalidPathException {
    // splitting path by "/" to separate separate folders into an array
    String[] pathArray = returnPathAsArray(path);
    int size = pathArray.length;
    if (size >= 1) {
      return pathArray[size - 1];
    } else {
      return path;
    }
  }

  /**
   * Determines whether user has entered an absolute or relative path. Absolute
   * paths start with "/".
   * 
   * @param path The path given by the user.
   * @return True if it is an absolute path and false otherwise.
   */

  private boolean isAbsolutePath(String path) {
    return path.startsWith("/");
  }

  /**
   * Splits a string around "/" so folders can be put into an array (one folder
   * per index). First folder in path goes in first index, second folder in path
   * goes in second index and so on.
   * 
   * @param path The string representing the path given by the user.
   * @throws InvalidPathException if there are multiple consecutive forward
   *         slashes
   * @return An array of String containing the names of the files and folders
   *         given in the path.
   */

  private String[] returnPathAsArray(String path) throws InvalidPathException {
    Matcher m = Pattern.compile("//").matcher(path);
    if (m.find()) {
      throw new InvalidPathException(
          "Pathname may not have consecutive" + " forward slashes.");
    }
    return path.split("[/]");
  }

  /**
   * Returns the parentObject of either a file or a folder in the
   * SystemHierarchy.
   * 
   * @param path A string given by the user either a relative or absolute path.
   * @param simulation Holds all the files and folders currently in the program
   * @throws InvalidPathException
   */

  protected DirectoryItem getParentObject(String path,
      SystemHierarchy simulation) throws InvalidPathException {
    Folder sourceFolder = getSourceFolderForPath(path, simulation);
    String[] pathToParentFolder = returnPathToParentFolder(path);
    // traverses pathToParentFolder array from the sourceFolder to the
    // parentFolder and returns it
    return simulation.getLastItemInPath(sourceFolder, pathToParentFolder);
  }

  /**
   * Returns the path (either absolute or full, depending on user input) to the
   * parent folder. Gives back a string array of the path but without the last
   * item in the path.
   * 
   * @param path A string given by the user either a relative or absolute path.
   * @throws InvalidPathException
   */

  private String[] returnPathToParentFolder(String path)
      throws InvalidPathException {
    String[] pathArray = returnPathAsArray(path);
    // if only one element in the path, it has no path to the parent
    if (pathArray.length < 2) {
      return new String[0];
    } else {
      // returning a copy of the array without the last element
      return Arrays.copyOf(pathArray, pathArray.length - 1);
    }
  }

  /**
   * Checks and redirects standard output (i.e. non-error messages) into a file
   * specified if required.
   * 
   * @param output The output redirected to a file.
   * @param userInput Command entered by the user.
   * @param simulation The SystemHierarchy instance.
   * @throws RedirectFailureException
   */

  protected void redirect(String output, String[] userInput,
      SystemHierarchy simulation) throws RedirectFailureException {
    try {
      String newContents = output;
      String pathname = userInput[userInput.length - 1];
      validateLastFileCharacter(pathname);
      DirectoryItem item = getPathObject(pathname, simulation);
      if (item instanceof File) {
        File outfile = (File) item;
        if (userInput[userInput.length - 2].equals(">")) {
          outfile.eraseContent();
        }
        outfile.appendContent(newContents);
      } // otherwise, need to make the file
      else {
        DirectoryItem parentItem = getParentObject(pathname, simulation);
        if (parentItem instanceof Folder) {
          Folder parentFolder = (Folder) parentItem;
          String newFileName = getNameOfItem(pathname);
          File newFile = new File(newFileName);
          newFile.appendContent(newContents);
          parentFolder.add(newFile);
        } else {
          System.out.println("Invalid directory pathname specified.");
        }
      }
    } catch (InvalidPathException | InvalidNameException
        | IdenticalNameException e) {
      throw new RedirectFailureException(e.getMessage());
    }
  }

  /**
   * Checks whether or not user would like to redirect the output.
   * 
   * @param commands The list of arguments entered by the user.
   * @return True if user wants a redirect and false otherwise.
   */

  protected boolean checkRedirect(String[] commands) {
    if (commands.length >= 2) {
      return commands[commands.length - 2].equals(">")
          || commands[commands.length - 2].equals(">>");
    } else {
      return false;
    }
  }

  /**
   * Returns an array containing just the command with any arguments and no
   * arguments for the redirect.
   * 
   * @param command The command entered by the user.
   * @return A String array containing only the command and arguments.
   */

  public String[] noRedirectSymbols(String[] command) {
    String[] justCommand = command;
    if (checkRedirect(command)) {
      justCommand = Arrays.copyOfRange(command, 0, command.length - 2);
    }
    return justCommand;
  }
}


