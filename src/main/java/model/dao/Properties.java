package model.dao;


/*
 * This class stores sql statements in a properties
 * It decouple the sql logic from the application business logic
 * This makes modification easier, by
   eliminating the need to search for database statements within application logic
 */
public class Properties {
	
	//private variables for table
	
	private static final String CATEGORY_TABLE = "category";
	private static final String MATERIAL_TABLE = "material";
	private static final String PROJECT_TABLE = "projects";
	private static final String PROJECT_CATEGORY_TABLE = "project_category";
	private static final String STEP_TABLE = "step";
	
	//private variables for queries
	
	protected static String ADD_PROJECT;
	protected static String LIST_PROJECT;
	protected static String FETCH_PROJECT;
	protected static String FETCH_PROJECT_BY_ID;
	protected static String UPDATE_PROJECT;
	protected static String DELETE_PROJECT;
	
	protected static String FETCH_CATEGORY;
	protected static String FETCH_STEPS;
	protected static String FETCH_MATERIALS;
	
	
	/*
	 * M E T H O D S 
	 */
	
	//method to add new project
	static String ADD_PROJECT() {
		ADD_PROJECT = "INSERT INTO " 
				+ PROJECT_TABLE + ""
				+ "(project_name, estimated_hours, actual_hours, difficulty, notes) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)";
		return ADD_PROJECT;
	}
	
	
	//method to fetch project
	static String LIST_PROJECT() {
		FETCH_STEPS = "SELECT * FROM "
				+ PROJECT_TABLE + ""
				+ " ORDER BY project_name";
		return FETCH_STEPS;
	}
	
	
	//method to fetch project by Id
	static String FETCH_PROJECT_BY_ID() {
		FETCH_PROJECT_BY_ID = "SELECT * FROM " 
				+ PROJECT_TABLE + ""
				+ " WHERE project_id = ?";
		return FETCH_PROJECT_BY_ID;
	}
	
	
	//method to fetch project category by Id
	static String FETCH_CATEGORY() {
		FETCH_CATEGORY = "SELECT c.* FROM "
				+ CATEGORY_TABLE + " c "
				+ "JOIN " + PROJECT_CATEGORY_TABLE + " pc USING (category_id)"
				+ " WHERE project_id = ?";
		return FETCH_CATEGORY;
		}
	
	
	//method to fetch steps by project Id
	static String FETCH_STEPS() {
		FETCH_STEPS = "SELECT * FROM "
				+ STEP_TABLE + ""
				+ " WHERE project_id = ?";
		return FETCH_STEPS;
		}
	
	
	//method to fetch materials by project Id
	static String FETCH_MATERIALS() {
		FETCH_MATERIALS = "SELECT * FROM "
				+ MATERIAL_TABLE + ""
				+ " WHERE project_id = ?";
		return FETCH_MATERIALS;
		}
	


}
