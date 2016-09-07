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
package driver;

import data.SystemHierarchy;
import commands.*;

import java.util.Scanner;
import java.util.Hashtable;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a JShell instance which the controls the program. Reads the user
 * input and calls the required commands.
 */

public class JShell {

  /**
   * A hashtable that contains all commands that we need for the program.
   */

  private static Hashtable<String, String> commandClasses = getCommandClasses();

  /**
   * A instance of the simulation of the program.
   */

  private SystemHierarchy simulation;

  /**
   * Main method and starting point of program.
   * 
   * @param args
   */

  public static void main(String[] args) {
    JShell shell = new JShell();
    shell.startSimulation();
  }

  /**
   * Constructor for JShell instance. Creates a file system.
   */

  public JShell() {
    setSimulation(SystemHierarchy.makeFileSystemSimulation());
  }

  /**
   * Creates a Hashtable to get instances of the required commands.
   * 
   * @return A hashtable of all commands.
   */

  private static Hashtable<String, String> getCommandClasses() {
    Hashtable<String, String> commandClasses = new Hashtable<String, String>();
    commandClasses.put("cat", "Cat");
    commandClasses.put("cd", "Cd");
    commandClasses.put("cp", "Cp");
    commandClasses.put("echo", "Echo");
    commandClasses.put("exit", "Exit");
    commandClasses.put("get", "Get");
    commandClasses.put("history", "History");
    commandClasses.put("ls", "Ls");
    commandClasses.put("man", "Man");
    commandClasses.put("mkdir", "Mkdir");
    commandClasses.put("mv", "Mv");
    commandClasses.put("popd", "Popd");
    commandClasses.put("pushd", "Pushd");
    commandClasses.put("pwd", "Pwd");
    commandClasses.put("!", "ExclamationPoint");
    commandClasses.put("grep", "Grep");
    return commandClasses;
  }

  /**
   * Start the simulation and read user's inputs.
   */

  private void startSimulation() {
    while (true) {
      System.out.print(simulation.getFullPath(simulation.getCurrFolder())
          + "# ");
      Scanner sc = new Scanner(System.in);
      String line = sc.nextLine();
      simulation = simulation.makeFileSystemSimulation();
      if (!line.equals("")) {
        simulation.addUserInput(line);
      }
      System.out.print(processInput(line));
    }
  }

  /**
   * A helper method to process a user's input. Uses regular expressions to
   * separate line into substrings of either items enclosed in quotes, or
   * strings separated by no spaces.
   * 
   * @param line The string of text a user types into the JShell console.
   * @return An error message if there is one, else the empty string.
   */

  public String processInput(String line) {
    if (line.startsWith("!")) {
      line = line.substring(0, 1) + " " + line.substring(1);
    }
    // splitting by sequences of characters that are non-spaces, or characters
    // that are enclosed by quotation marks, which are in turn surrounded by
    // spaces
    Matcher m = Pattern.compile("(\\s+\"[^\"]*\"\\s*)|([^\\s]+)").matcher(line);
    List<String> parsedInput = new ArrayList<String>();
    while (m.find()) {
      parsedInput.add(m.group().trim());
    }
    String[] userInput = parsedInput.toArray(new String[parsedInput.size()]);
    if (!(userInput.length == 0)) {
      try {
        String opener = commandClasses.get(userInput[0]);
        Class commandClass = Class.forName("commands." + opener);
        Object instance = commandClass.newInstance();
        Command commandInstance = (Command) instance;
        commandInstance.execute(userInput, simulation);

      } catch (IllegalAccessException | ClassNotFoundException
          | InstantiationException | NullPointerException e) {
        return "Invalid command name, please try again.\n";
      }
    }
    return "";
  }

  /**
   * A getter to retrieve the simulation.
   * 
   * @return Returns the simulation
   */

  public SystemHierarchy getSimulation() {
    return simulation;
  }

  /**
   * A setter of simulation.
   */

  public void setSimulation(SystemHierarchy newSimulation) {
    this.simulation = newSimulation;
  }
}
