package src;

import java.util.List;

import src.Status.status;


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
	 * Function to let a PM create a task.
	 * @pre This PM must be logged in
	 * @pre The project this PM wants to associate this task with, must be in executing state.
	 * 
	 * @param project is the project this PM wants to associate this task with.
	 * @param description is the description of this task.
	 * @param duration is the estimated duration of the task.
	 * @param deviation is the acceptable deviation of the time of execution of the task.
	 * @param dependsOnMe are the tasks that depend on this task.
	 * @param imWaitingFor are the tasks that this task depends on; If one isn't finished, this task can't start.
	 */
	public void createTask(Project project, String description, int duration, float deviation, List<Task> imWaitingFor) {
		if (!super.loggedIn || project.getStatus() != status.EXECUTING) {
			throw new IllegalArgumentException();
		}
		Task task = new Task(project, description, duration, deviation, imWaitingFor);
	}
}
