package controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.Project;
import model.dao.Dao;
import model.dao.ProjectDao;
import projects.exception.DbException;
import projects.service.ProjectService;

/*
 * Controller layer class handle code separately
 * that handle code separately.
 * The controller class will take in a request
 * and return a response...
 * does not do anything else
 * 
 * @author Paul Technology
 */
public class Controller {
	
	//dao
	ProjectDao pDao;

	//scanner to get user input
	Scanner sc = new Scanner(System.in);
	
	//project service instance
	private ProjectService p_service = new ProjectService();
	
	//current project
	private Project curProject;
	
	
	
	/* ------------------ */
	/*                   */
	/*   M E T H O D S  */
	/*                 */
	/* ---------------*/
	
	
	//Method to store a list of operation
	
	// @formatter:off
	@SuppressWarnings("unused")
	private List<String> operations = List.of(
			"1) Add a project",
			"2) List projects",
			"3) Select a project"
	);
	// @formatter:on
	
	
	/**
	  * Method to display the menu selections (available operations), 
	  * and gets the user menu selection, and acts on that selection.
	  */

	public void processUserSelections() {
		boolean done = false;
		
		while (!done) {
			try {
				//calling get operation method
				int op = getUserSelection();
				
				switch (op) {
				  case -1:
					  done = exitMenu();
					  break;
				  case 1:
					  addProject();
					  break;
				  case 2:
					  listProjects();
					  break;
				  case 3:
					  selectProject();
					  break;
				  default:
					  System.out.println("\n" + op+ " is not valid. Try again!");
				}
			} catch(Exception e) {
				System.out.println("\nError: " + e.toString() + " Try again!");
			}
		}
	}
	
	/*
	 * Method to gather user input for a project row
	 * then call the project service to 
	 * create the new project row.
	 */
	private void addProject() {
		// get project input from user
		String projectName = getStringInput("Enter project name");
		BigDecimal estimateHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		//project instance
		Project p = new Project();
		
		p.setProjectName(projectName);
		p.setEstimatedHours(estimateHours);
		p.setActualHours(actualHours);
		p.setDifficulty(difficulty);
		p.setNotes(notes);
		
		//calling project service to add the project info
		Project dbProject = p_service.addProject(p);
		
		//inform user that add was successful
		System.out.println("You have successfully created project! ");
		//System.out.println("You have successfully created project: " + dbProject);
	}
	
	
	/*
	 * Method to fetch project info in the Db by ID
	 */
	private void selectProject() {
		listProjects();
		
		//project id
		Integer ID = getIntInput("Enter a project ID to select a project");
		
		//un-select current project
		curProject = null;
		
		//handle exception if an invalid ID is entered
		curProject = p_service.fetchProjectById(ID);
	}
	
	
	/*
	 * Method to fetch project info in the Db
	 */
	
	private void listProjects() {
		
		List<Project> p = p_service.fetchAllProjects();;
		//pDao = new ProjectDao();
		
		//printing project
		System.out.println("\nProjects: ");
		
		/*
		 * using Lambda expression to get each type of project field
		 * I declared another project variable p2 inside
		 * the Lambda expression
		 */
//		pDao.getAll().forEach(p2 ->  System.out.println
//				(" " + p2.getProjectId() + ": " + p2.getProjectName()));
		p.forEach(p2 -> System.out.println
				(" " + p2.getProjectId() + ": " + p2.getProjectName()));
	}
	
	
	
	/*
	 * convert user input to a Big Decimal.
	 */
	private BigDecimal getDecimalInput(String prompt) {
		//a input variable
		String input = getStringInput(prompt);
		
		//check if input is null
		if(Objects.isNull(input)) {
			return null;
		}
		
		//create big decimal object and set it to 2
		//Object bgDec = new BigDecimal(input);
		try {
			return new BigDecimal(input).setScale(2);
			//return String.valueOf(bgDec);
		} catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}



	/*
	 * Method to interact with the user
	 * it ask user input menu selection and print available option
	 */
	private int getUserSelection() {
		//calling print operation method
		printOperations();
		
		//an integer collection to get user input and call the get int input method
		Integer input = getIntInput("\nEnter a menu selection");
		
		//return object using lambda expression
		return Objects.isNull(input) ? -1 : input;
	}
	
	
	/*
	 * Method that takes user's input and convert it to an Integer
	 */
	private Integer getIntInput(String string) {
		
		// getting user input using the get string input method
		String input = getStringInput(string);
		
		if (Objects.isNull(input)) {
			return null;
		}
		
		try {
			//convert strings into integers easily
			return Integer.valueOf(input);
			
		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
		
	}
	
	
	/*
	 * Method that takes user's input and convert it to a Double
	 */
	
	@SuppressWarnings("unused")
	private Double getDoubleInput(String string) {
		
		//calling get string input method
		String input = getStringInput(string);
		
		//empty string return null value
		if (Objects.isNull(input)) {
			return null;
		}
		
		try {
			return Double.parseDouble(input);
		} catch(NumberFormatException e) {
			throw new DbException(input + " is not valid number.");
		}
	}

	
	/*
	 * Method that print the prompt to the console
	 * and get the user's input
	 */
	private String getStringInput(String prompt) {
		// prompt to user
		System.out.print(prompt + ": ");
		
		//getting user input
		String input = sc.nextLine();
		
		//return a lambda boolean expression
		return input.isBlank() ? null : input.trim();

	}
	


	/*
	 * Method to print the available menu on separate line 
	 */
	private void printOperations() {
		System.out.println();
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");
		
		//using a Lambda expression and ForEach method 
		//from the list
		operations.forEach(line -> System.out.println(" " + line));
		
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project.");
		} else {
			System.out.println("\nYou are working with project: " + curProject);
		}
	}
	
	
	/*
	 * Method to exit the menu
	 */
	private boolean exitMenu() {
		System.out.println("\nExiting the menu. TTFN!");
		return true;
		
	}

}
