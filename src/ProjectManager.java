package src;

import java.time.LocalDate;
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
	
	/**
	 * Setter called by the controller to create a new project.
	 * @param name contains the name of the to be created project; There can't exist a project with the same name.
	 * @param desc contains the description of the to be created project.
	 * @param dueTime contains the due time of the to be created project.
	 */
	public void createProject(String name, String desc, LocalDate dueTime) {
		if (!super.loggedIn) {
			throw new IllegalArgumentException();
		}
		for (Project project : this.projects) {
			if (project.getName() == name) {
				throw new IllegalArgumentException();
			}
		}
		Project project = new Project(name, desc, dueTime);
		this.projects.add(project);
	}
}
