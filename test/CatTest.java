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
package test;

import data.*;
import commands.*;
import static org.junit.Assert.*;

import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A JUnit test that checks various edge cases with cat command.
 *
 */

public class CatTest {

  /**
   * outContent used to compare output on console to the String result expected.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Instance of file system simulation.
   */

  private SystemHierarchy simulation;

  /**
   * Initializes PrintStream before each test method.
   */

  @Before
  public void setUp() throws Exception {
    System.setOut(new PrintStream(outContent));
    simulation = SystemHierarchy.makeFileSystemSimulation();
  }

  /**
   * Deletes OutputStream after each test method.
   */

  @After
  public void tearDown() throws Exception {
    System.setOut(null);
    SystemHierarchy.resetFileSystemSimulation();
  }

  /**
   * Tests error message of giving wrong number of arguments to Cat.
   */

  @Test
  public void testCheckNumArgs() {
    String[] input = {"cat"};
    Cat command = new Cat();
    command.execute(input, simulation);
    String result = "Specify at least one path to a file.\n";
    assertEquals(result, outContent.toString());
    
  }

  /**
   * Tests whether cat still works when given a file as a full path.
   */

  @Test
  public void testAbsolutePath() {
    String[] input = {"echo", "\"wow\"", ">>", "wolf"};
    Echo command = new Echo();
    command.execute(input, simulation);
    String[] input1 = {"cat", "/wolf"};
    Cat command1 = new Cat();
    command1.execute(input1, simulation);
    String result = "wow\n";
    assertEquals(result, outContent.toString());
    
  }

  /**
   * Tests whether cat can print contents of multiple files.
   */

  @Test
  public void testMultipleFiles() {
    String[] input1 = {"echo", "\"cat\"", ">", "/./dog"};
    String[] input2 = {"echo", "\"leopard\"", ">>", "canine"};
    String[] input3 = {"echo", "\"panther\"", ">>", "wolf"};
    Echo command = new Echo();
    command.execute(input1, simulation);
    command.execute(input2, simulation);
    command.execute(input3, simulation);
    String[] input4 = {"cat", "wolf", "canine", "dog", "iceCream"};
    Cat command1 = new Cat();
    command1.execute(input4, simulation);
    String result =
        "iceCream is not a file\npanther\n\n\nleopard\n\n\ncat\n\n\n\n";
    assertEquals(result, outContent.toString());
    
  }
  
  /**
   * Tests whether cat can print contents of multiple files.
   */

  @Test
  public void testMultipleFiles2() {
    String[] input1 = {"echo", "\"cat\"", ">", "/./dog"};
    String[] input2 = {"echo", "\"leopard\"", ">>", "canine"};
    String[] input3 = {"echo", "\"panther\"", ">>", "wolf"};
    Echo command = new Echo();
    command.execute(input1, simulation);
    command.execute(input2, simulation);
    command.execute(input3, simulation);
    String[] input4 = {"cat", "wolf", "iceCream", "canine", "dog"};
    Cat command1 = new Cat();
    command1.execute(input4, simulation);
    String result = "iceCream is not a file\npanther\n\n\nleopard\n\n\ncat\n";
    assertEquals(result, outContent.toString());
    
  }

  /**
   * Tests whether cat works when file given as a relative path.
   */

  @Test
  public void testRelativePath() {
    String[] input = {"mkdir", "animal"};
    String[] input1 = {"mkdir", "animal/mammal"};
    Mkdir command = new Mkdir();
    command.execute(input, simulation);
    command.execute(input1, simulation);
    String[] input2 = {"cd", "/animal/"};
    Cd command1 = new Cd();
    command1.execute(input2, simulation);
    String[] input3 = {"echo", "\"panther\"", ">>", "wolf"};
    Echo command2 = new Echo();
    command2.execute(input3, simulation);
    String[] input4 = {"cat", "wolf"};
    Cat command3 = new Cat();
    command3.execute(input4, simulation);
    String result = "panther\n";
    assertEquals(result, outContent.toString());
    
  }

  /**
   * Tests whether cat works when output is redirected to a file.
   */

  @Test
  public void testRedirect() {
    String[] input = {"echo", "\"hello\"", ">>", "wolf"};
    Echo command = new Echo();
    command.execute(input, simulation);
    String[] input1 = {"cat", "/wolf", ">", "dog"};
    Cat command1 = new Cat();
    command1.execute(input1, simulation);
    String result = "";
    assertEquals(result, outContent.toString());
    String[] input2 = {"cat", "dog"};
    command1.execute(input2, simulation);
    String result2 = "hello\n";
    assertEquals(result2, outContent.toString());
    
  }
  
  /**
   * Tests whether cat can print contents of multiple files using redirect.
   */

  @Test
  public void testMultipleFilesRedirect() {
    String[] input1 = {"echo", "\"cat\"", ">", "/./dog"};
    String[] input2 = {"echo", "\"leopard\"", ">>", "canine"};
    String[] input3 = {"echo", "\"panther\"", ">>", "wolf"};
    Echo command = new Echo();
    command.execute(input1, simulation);
    command.execute(input2, simulation);
    command.execute(input3, simulation);
    String[] input4 = {"cat", "wolf", "iceCream", "canine", "dog", ">", "file"};
    String[] input5 = {"cat", "file"};
    Cat command1 = new Cat();
    command1.execute(input4, simulation);   
    String result1 = "iceCream is not a file\n";
    assertEquals(result1, outContent.toString());
    command1.execute(input5, simulation);
    String result2 = "iceCream is not a file\npanther\n\n\nleopard\n\n\ncat\n";
    assertEquals(result2, outContent.toString());
    
  }
}
