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
 * Represents a command that can move a folder or file to another location.
 */

public class Mv extends Command {
  
  /**
   * Returns documentation for this command.
   * 
   * @return A string array containing the name, description and synopsis of the
   *         command.
   */
  
  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "mv -- moves a file or folder";
    manual[1] = "mv OLDPATH NEWPATH";
    manual[2] = "Move item OLDPATH to NEWPATH. Both OLDPATH and NEWPATH may be "
        + "relative to the current directory or may be absolute paths. If "
        + "NEWPATH is a directory, move the item into that directory.";
    return manual;
  }

  /**
   * Checks that the correct number of arguments have been entered by the user
   * for this command.
   * 
   * @param command First element is mv and following are any arguments entered
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
   * Moves the given directory in the simulation relative to an absolute or full
   * path to the directory specified by the user.
   * 
   * @param command Represents the command entered by the user. The mv command
   *        followed by paths for the directories that are to be moved.
   * @param simulation Holds all the files and folders currently in the system.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    try {
      checkNumArgs(command);
      executeMove(command, simulation);
    } catch (InvalidArgCountException | InvalidPathException
        | InvalidNameException | IdenticalNameException
        | DirectoryItemNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (ClassCastException e) {
      System.out.println("Proposed pathname is invalid or already points to an"
          + " existing item in the system with the same name.");
    }
  }

  /**
   * Helper function of execute.
   * Moves the given directory in the simulation relative to an absolute or full
   * path to the directory specified by the user.
   * 
   * @param command The user input command
   * @param simulation The file system
   * @throws InvalidPathException
   * @throws DirectoryItemNotFoundException
   * @throws InvalidNameException
   * @throws IdenticalNameException
   */
  private void executeMove(String[] command, SystemHierarchy simulation) 
      throws InvalidPathException, DirectoryItemNotFoundException, 
      InvalidNameException, IdenticalNameException {
    Folder mvDestinationParent =
        (Folder) getParentObject(command[2], simulation);
    DirectoryItem original = getPathObject(command[1], simulation);
    // testing to ensure no moving folders can move into their subdirectories
    if (original instanceof Folder) {
      if (((Folder) original).containsFolder(mvDestinationParent)) {
        System.out.println("Cannot move into a subfolder.");
        return;
      }
    }
    moveDirectory(command, simulation, mvDestinationParent);
  }

  /**
   * Helper function of executeMove.
   * Move the directory to different path in the file system.
   * @param command The user input command
   * @param simulation The file system
   * @param mvDestinationParent The parent folder of destination of the move
   * @throws InvalidPathException
   * @throws DirectoryItemNotFoundException
   * @throws InvalidNameException
   * @throws IdenticalNameException
   */
  private void moveDirectory(String[] command, SystemHierarchy simulation, 
      Folder mvDestinationParent) 
          throws InvalidPathException, DirectoryItemNotFoundException, 
          InvalidNameException, IdenticalNameException{
    String originalItemName = getNameOfItem(command[1]);
    String itemName = getNameOfItem(command[2]);
    Folder mysteryItem = (Folder) getPathObject(command[2], simulation);
    // if the mysteryItem is a pre-existing folder, change the parent folder
    // to mysteryItem
    if (!(mysteryItem == null)) {
      mvDestinationParent = mysteryItem;
      itemName = originalItemName;
    }
    if (mvDestinationParent.canAdd(itemName)) {
      Folder originalParent =
          (Folder) getParentObject(command[1], simulation);
      DirectoryItem movingItem =
          originalParent.removeSubItem(originalItemName);
      movingItem.setName(itemName);
      if (movingItem instanceof Folder) {
        ((Folder) movingItem).setParent(mvDestinationParent);
      }
      mvDestinationParent.add(movingItem);   
    } else {
      System.out.println("Proposed pathname already points to an existing "
          + "item in the system with the same name, or the name of the item "
          + "is invalid.");
    }       
  }
}

