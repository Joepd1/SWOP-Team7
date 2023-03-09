package src;

import java.util.List;


//TODO: 	
// ADVANCE TIME
// START UP
// CREATE PROJECT	
// CREATE TASK
// (LOG IN & OUT ?)
// SHOW PROJECTS

/**
 * 
 * @author vincent
 */
public class ProjectManager extends User {
	
	/**
	 * Contains the list of all projects this user is managing.
	 */
	private List<Project> projects;

	/**
	 * The constructor instantiates the super class (User) with the correct name.
	 * @param name is the name of the user.
	 */
	public ProjectManager(String name) {
		super.name = name;
	}

	// USE CASE:CREATE TASK
	/**
	 * This function will update the given project; It will get the necessary info of the failed task and update the new
	 * 	one accordingly.
	 * @param project is the project that must be updated
	 * @param failedTask is the task that has failed and therefore must be updated
	 * @param newTask is the task that must replace the failed task in the given project.
	 */
	public void replaceTask (Project project, Task failedTask, Task newTask) {
		if (!failedTask.failedTask() || !this.projects.contains(failedTask)) {
			throw new IllegalArgumentException();
		}
		else {
			project.replace(failedTask, newTask);
			
			// This alternative task replaces the failed task with respect to
			// dependency management or determining the project status (ongoing or finished).
			// The time spent on the failed task is however counted for the total execution time of
			// the project. 
		}
	}
}
