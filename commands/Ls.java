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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * Represents a ls item which can print the contents of a directory, the name of
 * a file or an error message.
 */

public class Ls extends Command {

  /**
   * Returns a string array of documentation. Will be printed when man ls is
   * called.
   * 
   * @return A string array with the synopsis, name and description of the
   *         command.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "ls -- Display the contents of the folder";
    manual[1] = "ls [-R][PATH ...]";
    manual[2] = "If no paths are given, print the contents "
        + "(file or directory) of the\ncurrent directory, with "
        + "a new line following each of the content (file or directory).\n"
        + "Otherwise, for each path p, the order listed:\n"
        + "If p specifies a file, print p\n"
        + "If p specifies a directory, print p, a colon, then the contents "
        + "of that directory, then an extra new line.\n"
        + "If p does not exist, print a suitable message.";
    return manual;
  }

  /**
   * Checks that the correct number of arguments have been entered by the user
   * for this command.
   * 
   * @param command First element is ls and following are any arguments entered
   *        by user.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);
    if (justCommand.length < 1) {
      throw new InvalidArgCountException();
    }
  }

  /**
   * Returns an array of all the DirectoryItems in a folder sorted in
   * alphabetical order.
   * 
   * @param sourceFolder Sorting the subContents of this folder.
   * @return A string array representing the names of the DirectoryItems in
   *         sorted order.
   */

  private String[] sortContents(Folder sourceFolder) {
    ArrayList<DirectoryItem> allItems = sourceFolder.getSubContents();
    String[] sorter = new String[allItems.size()];
    // Initializing all names of DirectoryItems of subContents into new
    // array that will be sorted
    for (int i = 0; i < allItems.size(); i++) {
      sorter[i] = allItems.get(i).getName();
    }
    Arrays.sort(sorter);
    return sorter;
  }

  /**
   * Prints the subContents of a folder in a sorted order.
   * 
   * @param sortedItems The names of the DirectoryItems in the subContents of a
   *        file. Names already in sorted order.
   */

  private String printItems(String[] sortedItems) {
    String output = "";
    for (String item : sortedItems) {
      output += item + "\n";
    }
    return output;
  }

  /**
   * Returns the items inside a folder recursively.
   * 
   * @param currFolder The folder we are looking in.
   * @return The String contents of the folder.
   */

  private String printItemsRec(Folder currFolder, SystemHierarchy simulation) {
    String output = "";
    if (currFolder.getSubContents().size() == 0) {
      return "";
    } else {
      ArrayList<DirectoryItem> allItems = currFolder.getSubContents();
      for (DirectoryItem item : allItems) {
        output += item.getName() + "\n";
      }
      output += "\n";
      for (DirectoryItem item : allItems) {
        if (item instanceof Folder) {
          output += simulation.getFullPath((Folder) item) + ":\n";
          output += printItemsRec((Folder) item, simulation);
        }
      }
    }
    return output;
  }

  /**
   * Adding the output that will be printed to console if user enters ls
   * command.
   * 
   * @param justCommand Command entered by user.
   * @param index The index in the command being processed.
   * @param isRecursive Whether or not it is recursive.
   * @param simulation The current state of the files/folders.
   * @return A String representing the output.
   * @throws InvalidPathException The path given is invalid.
   */

  private String addOutput(String[] justCommand, int index, boolean isRecursive,
      SystemHierarchy simulation) throws InvalidPathException {
    // getting last DirectoryItem specified by user path
    DirectoryItem item = getPathObject(justCommand[index], simulation);
    String output = "";
    if (item == null) {
      throw new InvalidPathException(
          justCommand[index] + ":Path does not exist" + ".");
    } else {
      if (item instanceof Folder) {
        output += (simulation.getFullPath((Folder) item));
        output += (":\n");
        // if folder, also print out subContents in sorted order
        if (isRecursive) {
          output += printItemsRec((Folder) item, simulation);
        } else {
          output += printItems(sortContents((Folder) item));
        }
        output += ("\n");
        return output;
      } else {
        // if file, print out its name
        output += (item.getName() + "\n");
        return output;
      }
    }
  }

  
  /**
   * Helper function to execute command ls.If no arguments given, 
   * will print contents of currentWorking Directory. 
   * If a file is given, prints the name of the file.
   * If a folder is given, prints name of folder followed by contents. 
   * If path given is incorrect, prints error message.
   * 
   * @param justCommand The command without redirect features.
   * @param simulation The file system.
   * @return output string for executing ls command
   * @throws InvalidPathException
   */
  private String executeList(String[] justCommand, SystemHierarchy simulation) 
      throws InvalidPathException {
    String output = "";
    // when no specific pathnames have been specified
    if (justCommand.length == 1) {
      output += printItems(sortContents(simulation.getCurrFolder()));
    } else {
      if (justCommand[1].equals("-R") || justCommand[1].equals("-r")) {
        // recursive
        if (justCommand.length == 2) {
          output += printItemsRec(simulation.getCurrFolder(), simulation);
        } else {
          for (int j = 2; j < justCommand.length; j++) {
            output += addOutput(justCommand, j, true, simulation);
          }
        }
      } else { // non-recursive
        for (int i = 1; i < justCommand.length; i++) {
          output += addOutput(justCommand, i, false, simulation);
        }
      }
    }
    return output;
  }
  
  
  /**
   * Executes the user command ls. If no arguments given, will print contents of
   * currentWorking Directory. If a file is given, prints the name of the file.
   * If a folder is given, prints name of folder followed by contents. If path
   * given is incorrect, prints error message.
   * 
   * @param command Represents the command the user entered. Has the string ls
   *        followed by optional arguments.
   * @param simulation Holds all the files and folders currently in the system.
   *        Also holds pointer to currentWorkingDirectory.
   */

  public void execute(String[] command, SystemHierarchy simulation) {
    String[] justCommand;
    String output = "";
    try {
      checkNumArgs(command);
      justCommand = noRedirectSymbols(command);
    } catch (InvalidArgCountException e) {
      return;
    }
    try {
      output += executeList(justCommand, simulation);  
    } catch (InvalidPathException e) {
      System.out.println(e.getMessage());
      return;
    }
    try {
      if (checkRedirect(command) == false) {
        System.out.print(output);
      } else {
        redirect(output, command, simulation);
      }
    } catch (RedirectFailureException e) {
      System.out.println(e.getMessage());
    }
  }

}
