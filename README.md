# Java-Week10-Assignment--PromineoTech-DB-Development--MySQL-JDBC

Using MVC design pattern approach to create a CRUD application that demonstrates we perform CRUD operation on a MySQL Database using JAVA - JDBC, and also how to read SQL queries in external JAVA class.

![Screenshot 2024-04-14 054034](https://github.com/paulBit3/Java-Week10-Assignment--PromineoTech-DB-Development--MySQL-JDBC/assets/43505777/9fdbf446-5454-4d4e-8279-8cf0baa2eefa)

![Screenshot 2024-04-24 180017](https://github.com/paulBit3/Java-Week10-Assignment--PromineoTech-DB-Development--MySQL-JDBC/assets/43505777/67124124-6fdb-44f1-a3ee-fccdc76e8ff7)

![Screenshot 2024-05-06 181633](https://github.com/paulBit3/Java-Week10-Assignment--PromineoTech-DB-Development--MySQL-JDBC/assets/43505777/d8d30da0-fda6-41c4-a618-0b1c8ef29bd3)



Once download or cloned the repo, lookat the src folder. The database and sample data are in the main/resources folder
Here is a sample
--------------

```SQL
use projects;

--		 --
--  project data --
--               --



-- Insert into the category table
INSERT INTO category (category_name) VALUES ('House');
INSERT INTO category (category_name) VALUES ('Software Development');
INSERT INTO category (category_name) VALUES ('Rideshare');
INSERT INTO category (category_name) VALUES ('Study');
INSERT INTO category (category_name) VALUES ('Car');
INSERT INTO category (category_name) VALUES ('Social Life');
INSERT INTO category (category_name) VALUES ('Education');



-- Insert into the step table
INSERT INTO step (project_id, step_text, step_order) VALUES (1, 'Remove old sink, and use glue to tie it against the wall.', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (2, 'Move sofa to the left, and put coffee table in the middle.', 2);
INSERT INTO step (project_id, step_text, step_order) VALUES (6, 'Remove bathroom trash bag and replace it with a new one.', 3);
INSERT INTO step (project_id, step_text, step_order) VALUES (7, 'Grab a Babrbecue grille.', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (7, 'Put your meat on the grille, make sure fire is good.', 2);

INSERT INTO step (project_id, step_text, step_order) VALUES (9, 'Download the Uber driver app, if you are a driver, and create an account', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (9, 'Download the Uber app, if you are a rider, and create an account', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (9, 'Create a username and a password, and enter your email and phone number to receive a temporary code and newsletter', 2);

INSERT INTO step (project_id, step_text, step_order) VALUES (13, 'You could buy a coding book and self-taught learn yourself how to code', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (13, 'You could earn a 4 year Bachelor degree in Computer Science or Infromation Technology or Engineering, where they will teach you how to code', 2);
INSERT INTO step (project_id, step_text, step_order) VALUES (12, 'Learn Java, JavaScript, Python, C#, Swift, Goland, SQL, Html,Css', 1);



-- Insert into the material table

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (1, 'new sink', 3, 23.89);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (1, 'glue', 3, 3.89);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (1, 'harmer', 1, 2.99);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (9, 'a car', 2, 2000.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (9, 'Insurance', 1, 200.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (9, 'driver license', 1, null);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (12, 'computer and Internet', 5, 400.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (12, 'table', 2, 100.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (13, 'computer', 4, 300);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (13, 'chair', 1, 500);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (7, 'grille', 2, 500.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (7, 'characol', 5, 50.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (7, 'barbecue sauce', 1, 2.99);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (13, 'onion and tomatoes', 6, 10.00);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (5, 'dish washer', 1, 199.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (5, 'fridge', 2, 399.40);

-- Insert into the join  project_category table
INSERT INTO project_category (project_id, category_id) VALUES (1, 1);
INSERT INTO project_category (project_id, category_id) VALUES (2, 1);
INSERT INTO project_category (project_id, category_id) VALUES (3, 1);
INSERT INTO project_category (project_id, category_id) VALUES (5, 1);
INSERT INTO project_category (project_id, category_id) VALUES (6, 1);
INSERT INTO project_category (project_id, category_id) VALUES (10, 1);

INSERT INTO project_category (project_id, category_id) VALUES (7, 6);
INSERT INTO project_category (project_id, category_id) VALUES (12, 2);
INSERT INTO project_category (project_id, category_id) VALUES (13, 2);


INSERT INTO project_category (project_id, category_id) VALUES (11, 7);
INSERT INTO project_category (project_id, category_id) VALUES (8, 5);
INSERT INTO project_category (project_id, category_id) VALUES (9, 3);

```

--------------



my Controller layer class handle code separately that handle code separately.
--------------------

```Java

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


```

----------------

my Model that defines Project table in the database.
--------------------

```Java

package model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


/*
 * this class defines Project table in the database
 * it is our model layer
 */

public class Project {
	
	  private Integer projectId;
	  private String projectName;
	  private BigDecimal estimatedHours;
	  private BigDecimal actualHours;
	  private Integer difficulty;
	  private String notes;

	  private List<Material> materials = new LinkedList<>();
	  private List<Step> steps = new LinkedList<>();
	  private List<Category> categories = new LinkedList<>();
      


	//constructor
	public Project(int project_id, String project_name, BigDecimal estimated_hours, BigDecimal actual_hours, int difficulty, String notes) {
		this.projectId = project_id;
		this.projectName = project_name;
		this.estimatedHours = estimated_hours;
		this.actualHours = actual_hours;
		this.difficulty = difficulty;
		this.notes = notes;
	}

	public Project() {
		// TODO Auto-generated constructor stub
	}

	public Integer getProjectId() {
	    return projectId;
	  }

	  public void setProjectId(Integer projectId) {
	    this.projectId = projectId;
	  }

	  public String getProjectName() {
	    return projectName;
	  }

	  public void setProjectName(String projectName) {
	    this.projectName = projectName;
	  }

	  public BigDecimal getEstimatedHours() {
	    return estimatedHours;
	  }

	  public void setEstimatedHours(BigDecimal estimatedHours) {
	    this.estimatedHours = estimatedHours;
	  }

	  public BigDecimal getActualHours() {
	    return actualHours;
	  }

	  public void setActualHours(BigDecimal actualHours) {
	    this.actualHours = actualHours;
	  }

	  public Integer getDifficulty() {
	    return difficulty;
	  }

	  public void setDifficulty(Integer difficulty) {
	    this.difficulty = difficulty;
	  }

	  public String getNotes() {
	    return notes;
	  }

	  public void setNotes(String notes) {
	    this.notes = notes;
	  }

	  public List<Material> getMaterials() {
	    return materials;
	  }

	  public List<Step> getSteps() {
	    return steps;
	  }

	  public List<Category> getCategories() {
	    return categories;
	  }

	  @Override
	  public String toString() {
	    String result = "";
	    
	    result += "\n   ID=" + projectId;
	    result += "\n   name=" + projectName;
	    result += "\n   estimatedHours=" + estimatedHours;
	    result += "\n   actualHours=" + actualHours;
	    result += "\n   difficulty=" + difficulty;
	    result += "\n   notes=" + notes;
	    
	    result += "\n   Materials:";
	    
	    for(Material material : materials) {
	      result += "\n      " + material;
	    }
	    
	    result += "\n   Steps:";
	    
	    for(Step step : steps) {
	      result += "\n      " + step;
	    }
	    
	    result += "\n   Categories:";
	    
	    for(Category category : categories) {
	      result += "\n      " + category;
	    }
	    
	    return result;
	  }

}

```


Dao interface defines an abstract API that performs CRUD operations on objects of type T.
--------------------

```Java

package model.dao;



import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



/*
 * Here is our DAO API
 * 
 * This Dao interface defines an abstract API 
 * that performs CRUD operations on objects of type T.
 */

public interface Dao<T> {
	Optional<T> getById(int id) throws SQLException;
    
    List<T> getAll();
    
    void save(T t);
    
    void update(T t, String[] params);
    
    void delete(T t);

}


```
