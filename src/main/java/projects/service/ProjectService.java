package projects.service;




import java.util.List;
import java.util.NoSuchElementException;

import model.Project;
import model.dao.ProjectDao;
import projects.exception.DbException;

/*
 * this class handle the business layer of our application
 * it is our model layer
 * 
 * @author Paul Technology
 */
public class ProjectService {
	
	//creating new instance of a data access object
	private ProjectDao projectDao  = new ProjectDao();
	
	//method that calls the DAO class to insert a project row.
	 public Project addProject(Project p) {
		 projectDao.save(p);
		return p; 
     }

	
	
	//retrieve all project details

	public List<Project> fetchAllProjects() {
		return projectDao.getAll();

	}
	
	
	public Project fetchProjectById(Integer iD) {
		/* 
		 * If a value is present, returns the value, 
		 * otherwise throws Exception.
		 * we use lambda function to handle the error
		 */
		try {
			return projectDao.getById(iD).orElseThrow(() -> new NoSuchElementException (""
					+ "Project with project ID= " + iD + " does not exist."));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new DbException(e);
		}
		
	}




}
