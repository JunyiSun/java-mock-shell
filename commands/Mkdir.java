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
 * Represents a mkdir instance that makes a directory inside a folder.
 */

public class Mkdir extends Command {

  /**
   * Returns a string array of documentation. Will be printed when man mkdir is
   * called.
   * 
   * @return A string array with the synopsis, name and description of the
   *         command.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "mkdir -- create new folders";
    manual[1] = "mkdir DIR ...";

    manual[2] = "Create directories, each of which may be relative to\n"
        + "the current directory or may be a full path.\n"
        + "Note that > and >> are invalid directory names.";

    return manual;
  }

  /**
   * Checks that the correct number of arguments have been entered by the user
   * for this command.
   * 
   * @param command First element is mkdir and following are any arguments
   *        entered by user.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);

    if (justCommand.length < 2) {
      throw new InvalidArgCountException("Specify a directory name.");
    }
  }

  /**
   * Makes a new directory in the simulation relative to an absolute or full
   * path. Finds the parent directory and adds the new directory to its
   * subfolders
   * 
   * @param command Represents the command entered by the user. The mkdir
   *        command followed by paths for where the new directories are to be
   *        made.
   * @param simulation Holds all the files and folders currently in the system.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    String[] justCommand = null;
    int index = 0;
    try {
      checkNumArgs(command);
      justCommand = noRedirectSymbols(command);
    } catch (InvalidArgCountException e) {
      System.out.println(e.getMessage());
      return;
    }
    for (int i = 1; i < justCommand.length; i++) {
      try {
        index = i;
        executeMkdir(justCommand, simulation, i);       
      } catch (IdenticalNameException | InvalidPathException
          | InvalidNameException e) {
        System.out.println(e.getMessage());
      } catch (ClassCastException | NullPointerException e) {
        System.out.println(
            command[index] + ": Invalid directory pathname specified.");
      }
    }
  }

  /**
   * Helper function of execute.
   * Makes a new directory in the simulation relative to an absolute or full
   * path
   * 
   * @param justCommand The command without redirect features
   * @param simulation The file system
   * @param i  The index of argument
   * @throws InvalidPathException
   * @throws InvalidNameException
   * @throws IdenticalNameException
   */
  private void executeMkdir(String[] justCommand, SystemHierarchy simulation, 
      int i) throws InvalidPathException, InvalidNameException, 
      IdenticalNameException {
 // gets the parent DirectoryItem in the path given by the user
    DirectoryItem parentItem = getParentObject(justCommand[i], simulation);
    // it parent is a folder, can add a subfolder to it
    Folder parentFolder = (Folder) parentItem;
    String newDirName = getNameOfItem(justCommand[i]);
    // after getting name of new directory, creating a new folder for it
    // adding it to the arraylist of subfolders for parentFolder
    parentFolder.add(new Folder(newDirName, parentFolder));
    
  }
}
