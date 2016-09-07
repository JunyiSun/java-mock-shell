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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import data.DirectoryItem;
import data.File;
import data.Folder;
import data.SystemHierarchy;
import exceptions.*;

/**
 * Represents an instance of get. Gets a file at a URL and adds it to the
 * current working directory.
 */

public class Get extends Command {

  /**
   * Returns documentation for this command when man get is called.
   * 
   * @return A string array containing the name, description and synopsis of the
   *         command.
   */

  public static String[] getDocumentation() {
    String[] manual = new String[3];
    manual[0] = "get -- gets the file at a URL";
    manual[1] = "get URL";
    manual[2] = "Retrieves the file at URL and adds it to the current\n"
        + "working directory.";
    return manual;
  }

  /**
   * Checks whether the number of arguments supplied to the get command are
   * correct
   * 
   * @param command An array of the arguments provided when get is called.
   * @throws InvalidArgCountException
   */

  protected void checkNumArgs(String[] command)
      throws InvalidArgCountException {
    String[] justCommand = noRedirectSymbols(command);

    if (justCommand.length != 2) {
      throw new InvalidArgCountException("Invalid number of arguments.");
    }
  }

  /**
   * Takes in the arguments given by user and creates file with same content as
   * the file from URL
   * 
   * @param userInput The arguments supplied by the user
   * @param simulation The current state of the files and folders
   */

  public void execute(String[] userInput, SystemHierarchy simulation) {
    File newFile = null;
    String[] getFileName = null;
    try {
      checkNumArgs(userInput);
      BufferedReader in = checkValidURL(userInput);
      if (in != null) {
        getFileName = userInput[1].split("[/]");
        Folder currDir = simulation.getCurrFolder();
        newFile = new File(getFileName[getFileName.length - 1]);
        String inputLine;
        while ((inputLine = in.readLine()) != null)
          newFile.appendContent(inputLine + "\n");
        currDir.add(newFile);
        in.close();
      }
    } catch (IdenticalNameException e) {
      // Overwriting contents of file with same name
      eraseContentSameNameFile(getFileName, simulation, newFile);
    } catch (InvalidArgCountException | InvalidNameException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * The file being added has the same name as one already in the system. The
   * file in the system has it's contents erased and appends the contents of the
   * new file.
   * 
   * @param getFileName URL split by "/".
   * @param simulation The current state of the file system.
   * @param newFile The file from the URL.
   */

  private void eraseContentSameNameFile(String[] getFileName,
      SystemHierarchy simulation, File newFile) {
    try {
      DirectoryItem item =
          getPathObject(getFileName[getFileName.length - 1], simulation);
      if (item instanceof File) {
        ((File) item).eraseContent();
        ((File) item).appendContent(newFile.toString());
      } else {
        System.out.println("You may not add an item with the "
            + "same name to the same location.");
      }
    } catch (InvalidPathException e1) {
      // this won't happen since we know file with same name exists
      e1.printStackTrace();
    }
  }

  /**
   * Tries to open a connection with the URL. If fails, prints an error message
   * to the console.
   * 
   * @param userInput The arguments supplied by the user.
   * @return A BufferedReader which can read the contents from the URL
   */

  private BufferedReader checkValidURL(String[] userInput) {
    try {
      URL fileURL = new URL(userInput[1]);
      URLConnection fileURLConnection = fileURL.openConnection();
      fileURLConnection.connect();
      BufferedReader in = new BufferedReader(
          new InputStreamReader(fileURLConnection.getInputStream()));
      return in;
    } catch (MalformedURLException | IllegalArgumentException e) {
      System.out.println("URL is not valid.");
      return null;
    } catch (IOException e) {
      System.out.println("Could not connect to URL.");
      return null;
    }
  }
}
