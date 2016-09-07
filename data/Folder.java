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
 * Represents a folder item, which can contain other instances of DirectoryItem.
 */

public class Folder extends DirectoryItem {

  /**
   * Holds all the files and folders contained inside this folder.
   */

  private ArrayList<DirectoryItem> subContents;

  /**
   * Holds the folder in which this folder is contained.
   */

  private Folder parent;

  /**
   * Initialize a new Folder object with a pointer to the parent of this folder.
   * 
   * @param name This is the name of the folder.
   * @param parent This is the parent of the folder.
   */

  public Folder(String name, Folder parent) {
    super(name);
    this.parent = parent;
    this.subContents = new ArrayList<DirectoryItem>(0);
  }

  /**
   * Checks if the name for a folder given by the user is valid.
   * 
   * @param newName Holds the name chosen by the user.
   * @return True if it is a valid name and false otherwise.
   */

  private boolean isValidName(String newName) {
    // splitting name based around invalid characters
    String[] newNameNoInvalidChars =
        newName.split("[\\[\\]!@$&*()?:\\\\\"<>\'`|={}/,;]", 2);
    // if name did not get split and the name is not "." or "..", the name
    // is valid
    return (newNameNoInvalidChars.length == 1 && !newName.equals(".")
        && !newName.equals(".."));
  }

  /**
   * Adds a DirectoryItem to this folder instance. If this DirectoryItem has
   * invalid characters or if something of the same name already appears in this
   * folder instance, this method prints an error message.
   * 
   * @param item This is the item that is to be added to the folder.
   * @throws InvalidNameException, IdenticalNameException
   */

  public void add(DirectoryItem item)
      throws InvalidNameException, IdenticalNameException {
    String newItemName = item.getName();
    if (isValidName(newItemName)) {
      if (findSubItem(newItemName) == -1) {
        subContents.add(item);
      } else {
        throw new IdenticalNameException("You may not add an item with the "
            + "same name to the same location.");
      }
    } else {
      throw new InvalidNameException("Invalid character in file or folder.");
    }
  }

  /**
   * This method verifies that an item can be added to this folder.
   * 
   * @param possibleName This is name of the item that is to be added to the
   *        folder.
   * @return True iff the item can be added to this folder.
   */

  public boolean canAdd(String possibleName) {
    return isValidName(possibleName) && findSubItem(possibleName) == -1;
  }

  /**
   * A method that removes and returns an item with a certain name. Throws an
   * exception if the name is not present.
   * 
   * @param identifier return The DirectoryItem in subContents that has the same
   *        name as name.
   * @throws DirectoryItemNotFoundException
   */

  public DirectoryItem removeSubItem(String identifier)
      throws DirectoryItemNotFoundException {
    for (int i = 0; i < subContents.size(); i++) {
      if (subContents.get(i).name.equals(identifier)) {
        return subContents.remove(i);
      }
    }
    throw new DirectoryItemNotFoundException(
        "Folder or file could not be " + "found in this path.");
  }


  /**
   * Getter for private instance variable subContents. Returns all the
   * DirectoryItems inside this folder instance.
   * 
   * @return An arraylist of all the DirectoryItems inside the current folder.
   */

  public ArrayList<DirectoryItem> getSubContents() {
    if (subContents.size() == 0) {
      // if folder does not have any subContents, have to first initialize
      // arraylist with 0 items and then return it
      return new ArrayList<DirectoryItem>(0);
    } else {
      return subContents;
    }
  }

  /**
   * Getter for private instance variable parent. Returns the parent folder of
   * the current folder instance.
   * 
   * @return Folder object representing parent of current folder.
   */

  public Folder getParent() {
    return parent;
  }

  /**
   * Setter for private instance variable parent.
   */

  public void setParent(Folder newParent) {
    this.parent = newParent;
  }

  /**
   * Searches through a folder's subContents for a specific file or folder. If
   * it finds it, returns its index in the subContents arraylist. Otherwise
   * returns -1.
   * 
   * @param name The name of the file/folder being searched for in the
   *        subContents of the current folder instance.
   * @return A number either representing the index of the item in the
   *         subContents arraylist or -1 if it is not in the subContents.
   */

  private int findSubItem(String name) {
    DirectoryItem curr;
    for (int i = 0; i < subContents.size(); i++) {
      curr = subContents.get(i);
      // if the name of the DirectoryItem matches the name being searched for
      if (curr.getName().equals(name)) {
        return i;
      }
    }
    // name not in subContents
    return -1;
  }

  /**
   * Looks through subContents for a specific file/folder. If it finds it,
   * returns the DirectoryItem object. Otherwise returns null.
   * 
   * @param itemName The name of the item being searched for in subContents.
   * @throws DirectoryItemNotFoundException
   * @return Either the DirectoryItem represented by that name or null if
   *         itemName is not in subContents.
   */

  public DirectoryItem getSubItem(String itemName)
      throws DirectoryItemNotFoundException {
    int indexOfSubItem = findSubItem(itemName);
    if (indexOfSubItem == -1) {
      throw new DirectoryItemNotFoundException(
          "Folder or file could not be" + " found in this path.");
    } else {
      return subContents.get(indexOfSubItem);
    }
  }

  /**
   * Returns a deepcopy of the Folder object.
   */

  public Folder clone() {
    try {
      Folder duplicate = new Folder(name, parent);
      for (DirectoryItem dir : subContents) {
        duplicate.add(dir.clone());
      }
      return duplicate;
    } catch (InvalidNameException | IdenticalNameException e) {
      // this will never happen, since any item in
      // subContents will have a valid name.
      return null;
    }
  }

  /**
   * Returns whether this Folder instance contains an item in any of its
   * subcontents or if the item points to the same object as this Folder
   * instance.
   * 
   * @param f The folder whose presence the method checks.
   * @return Returns true if the folder is located anywhere within the
   *         subcontents of this folder or is the same object as this folder.
   */

  public boolean containsFolder(Folder f) {
    for (DirectoryItem dir : subContents) {
      if (dir instanceof Folder) {
        if (((Folder) dir).containsFolder(f) == true || (Folder) dir == f) {
          return true;
        }
      }
    }
    return this == f;
  }
}
