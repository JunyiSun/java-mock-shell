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
 * 
 * Represents a command that can copy a folder or file to another location.
 */

public class Cp extends Command {

  /**
   * Returns documentation for this command.
   * 
   * @return A string array containing the name, description and synopsis 
   *         of the command.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "cp -- copies a file or folder";
    manual[1] = "cp OLDPATH NEWPATH";
    manual[2] =
        "Copy item OLDPATH to NEWPATH. Both OLDPATH and NEWPATH may be"
            + " relative to the current directory or may be absolute paths. "
            + "If NEWPATH is a directory, copy the item into that directory.";
    return manual;
  }

  /**
   * Checks that the correct number of arguments have been entered by the user
   * for this command.
   * 
   * @param command First element is cp and following are any arguments entered
   *        by user.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command) 
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);

    if (justCommand.length != 3) {
      throw new InvalidArgCountException("Specify an oldpath and a newpath.");
    }
  }

  /**
   * Creates a Deepcopy of the item given by the user. If the item is a
   * directory, recursively copies all the contents to the new location
   * specified by the user.
   * 
   * @param command The user input where the first element is cp and following
   *        are any arguments entered by user.
   * @param simulation The file system.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    try {
      checkNumArgs(command);
      executeCopy(command, simulation);
    } catch (InvalidArgCountException | InvalidPathException
        | IdenticalNameException | InvalidNameException e) {
      System.out.println(e.getMessage());
    } catch (ClassCastException e) {
      System.out.println("Proposed pathname does not "
          + "point to a folder in the system.");
    } catch (NullPointerException e) {
      System.out.println("Folder or file could not be found in this system.");
    }
  }

  /**
   * Helper function for creating a deepcopy of the item given by the user.
   * @param command The user input where the first element is cp and following
   *        are any arguments entered by user.
   * @param simulation  The file system.
   * @throws InvalidPathException
   * @throws InvalidNameException
   * @throws IdenticalNameException
   */
  private void executeCopy(String[] command, SystemHierarchy simulation)
     throws InvalidPathException, InvalidNameException, IdenticalNameException {
    DirectoryItem copyOrigin = getPathObject(command[1], simulation);
    Folder copyDestinationParent =
        (Folder) getParentObject(command[2], simulation);
    // ensure copyDestinationParent is enclosed by copyOrigin
    if (copyOrigin instanceof Folder) {
      if (((Folder) copyOrigin).containsFolder(copyDestinationParent)) {
        System.out.println("Cannot copy into a subfolder.");
        return;
      }
    }
    copyDirectory(command, simulation, copyDestinationParent, copyOrigin);
  }

  /**
   * Helper function of executeCopy. 
   * Copy a directory to a different path in the system.
   * 
   * @param command The user input command
   * @param simulation The file system
   * @param copyDestinationParent The parent folder of the destination path
   * @param copyOrigin The original copy
   * @throws InvalidPathException
   * @throws InvalidNameException
   * @throws IdenticalNameException
   */
  private void copyDirectory(String[] command, SystemHierarchy simulation, 
      Folder copyDestinationParent, DirectoryItem copyOrigin) 
          throws InvalidPathException, InvalidNameException, 
          IdenticalNameException {
    String originalItemName = getNameOfItem(command[1]);
    String itemName = getNameOfItem(command[2]);
    Folder mysteryItem = (Folder) getPathObject(command[2], simulation);
    // if the pathname is a folder, change the parent folder to mysteryItem
    if (!(mysteryItem == null)) {
      copyDestinationParent = mysteryItem;
      itemName = originalItemName;
    }
    DirectoryItem clone = copyOrigin.clone();
    clone.setName(itemName);
    copyDestinationParent.add(clone);
    // change parent following successful add to copyDestinationParent
    if (clone instanceof Folder) {
      ((Folder) clone).setParent(copyDestinationParent);
    }
    
  }
}
