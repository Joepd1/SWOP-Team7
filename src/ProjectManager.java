package src;

import java.time.Duration;
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
	 * @contains the list of all projects this user is managing.
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
	 * Getter that returns all the projects this user is associated with.
	 * @pre the user is logged in.
	 */
	public List<Project> getProjects() {return this.projects;}
	
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
	 * Setter to advance the system time.
	 * @pre The Project Manager must be logged in.
	 * @param time is the time this project manager wants to advance the system time with.
	 */
	public void advanceTime(int time) {
		if (!super.loggedIn) {
			throw new IllegalArgumentException();
		}
		else {
			Duration timeToAdvanceInMinutes = Duration.ofMinutes(time);
			Clock.advanceTime(timeToAdvanceInMinutes);
		}
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
