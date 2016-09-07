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
package data;

import exceptions.*;
import java.util.ArrayList;

/**
 * Represents an instance of SystemHierarchy. Holds all files and folders
 * currently in the JShell. Also holds pointer to presentWorkingDirectory.
 *
 */

public class SystemHierarchy {

  /**
   * A folder object representing the current directory JShell is in.
   */

  private Folder presentWorkingDirectory;

  /**
   * The root directory is the parent of all the directories that will be
   * created in JShell.
   */

  private final Folder rootDirectory;

  /**
   * A PathStack object holding directories users may add to or remove by using
   * the pushd and popd commands.
   */

  private final PathStack dirStack = new PathStack();

  /**
   * An ArrayList containing all the commands a user has entered
   */

  private final ArrayList<String> allUserInputs = new ArrayList<String>();

  /**
   * Used with Singleton Design Principle factory method below
   */

  private static SystemHierarchy fileSystemSimulation = null;

  /**
   * Constructor for SystemHierarchy. Initializes rootDirectory as a folder with
   * no name or parent. The presentWorkingDirectory is root and users can then
   * start to add directories to it.
   */

  private SystemHierarchy() {
    this.rootDirectory = new Folder("", null);
    this.presentWorkingDirectory = this.rootDirectory;
  }

  /**
   * This factory method produces a file system in accordance with the Singleton
   * Design principle, so that only one instance of SystemHierarchy is present.
   * 
   * @return Returns the SystemHierarchy
   */

  public static SystemHierarchy makeFileSystemSimulation() {
    if (fileSystemSimulation == null) {
      fileSystemSimulation = new SystemHierarchy();
    }
    return fileSystemSimulation;
  }

  /**
   * This method resets the fileSystemSimulation so that a new instance can be
   * created.
   */

  public static void resetFileSystemSimulation() {
    fileSystemSimulation = null;
  }

  /**
   * Setter for the private instance variable allUserInputs. Adds another
   * command that a user has entered to the ArrayList.
   * 
   * @param userInput The non-empty string of input that a user has typed.
   */

  public void addUserInput(String userInput) {
    allUserInputs.add(userInput);
  }

  /**
   * Getter for private instance variable allUserInputs.
   * 
   * @return Returns the ArrayList containing all the commands a user has
   *         entered.
   */

  public ArrayList<String> getAllUserInputs() {
    return allUserInputs;
  }

  /**
   * Getter for private instance variable rootDirectory.
   * 
   * @return Returns the folder object representing the rootDirectory.
   */

  public Folder getRootFolder() {
    return rootDirectory;
  }

  /**
   * Getter for private instance variable presentWorkingDirectory
   * 
   * @return Returns the folder object representing the presentWorkingDirectory
   */

  public Folder getCurrFolder() {
    return presentWorkingDirectory;
  }

  /**
   * Returns last item (folder or file) of a path, or null if the path is valid
   * up until its final path item but a file of that name in that location is
   * not found.
   * 
   * @param sourceFolder The folder the given path starts at. Either root folder
   *        if absolute path or presentWorkingDirectory if relative path.
   * @param path The path entered by the user (either absolute or relative)
   * 
   * @throws InvalidPathException
   * @return The last item in the path (either File or Folder)
   */

  public DirectoryItem getLastItemInPath(Folder sourceFolder, String[] path)
      throws InvalidPathException {
    Folder currDir = sourceFolder;
    DirectoryItem curr = currDir;
    for (int i = 0; i < path.length; i++) {
      if (path[i].equals("")) {
        curr = sourceFolder;
      } else if (path[i].equals(".")) {
        curr = currDir;
      } else if (path[i].equals("..")) {
        currDir = currDir.getParent();
        if (currDir == null) {
          currDir = rootDirectory;
        }
        curr = currDir;
      } else {
        if (i < path.length - 1) {
          try {
            curr = currDir.getSubItem(path[i]);
            // must be folder to get sub item of later path items
            currDir = (Folder) curr;
          } catch (DirectoryItemNotFoundException | ClassCastException e) {
            throw new InvalidPathException("The given path is invalid.");
          }
        } else {
          try { // can be either file or folder
            curr = currDir.getSubItem(path[i]);
          } catch (DirectoryItemNotFoundException e) {
            curr = null;
          }
        }
      }
    }
    return curr;
  }


  /**
   * Setter for private instance variable presentWorkingDirectory.
   * 
   * @param newWorkingDirectory Folder object representing the new
   *        presentWorkingDirectory.
   */

  public void setPresentWorkingDirectory(Folder newWorkingDirectory) {
    if (newWorkingDirectory == null) {
      throw new NullPointerException();
    } else {
      presentWorkingDirectory = newWorkingDirectory;
    }
  }

  /**
   * Returns a string representation of the full path to a folder.
   * 
   * @return The absolute path to the folder.
   */

  public String getFullPath(Folder directory) {
    Folder curr = directory;;
    String path = "";

    // root folder's parent has name null so while loop stops when curr == null
    while (curr != null) {
      path = curr.getName() + "/" + path;
      curr = curr.getParent();
    }
    // all paths are stripped of final / at end
    // unless path is only /, which points to the rootDirectory
    if (path.length() == 1) {
      return path;
    } else {
      return path.substring(0, path.length() - 1);
    }
  }

  /**
   * Getter for private instance variable dirStack.
   * 
   * @return Returns all the directories added to stack by user.
   */

  public PathStack getDirStack() {
    return dirStack;
  }
}
