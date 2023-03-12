package src;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Each instance of this class represents a project manager.
 * @invar | getProjects() != null
 * @invar | getCreatedTasks() != null
 * @author vincent
 */
public class ProjectManager extends User {
	
	/**
	 * @invar | projects != null
	 * @invar | createdTasks != null
	 */
	private List<Project> projects;
	private List<Task> createdTasks;
	
	
	/**
	 * Initializes this object so that it's name is the given name.
	 * @post | getProjects().size() == 0
	 * @post | getCreatedTasks().size() == 0
	 */
	public ProjectManager(String name) {
		super.name = name;
		this.createdTasks = new ArrayList<Task>();
		this.projects = new ArrayList<Project>();
	}

	/**
	 * @basic
	 */
	public List<Project> getProjects() {return this.projects;}
	
	/**
	 * @basic
	 */
	public List<Task> getCreatedTasks() {return this.createdTasks;}
	
	/**
	 * This function will update the given project; It will get the necessary info of the failed task and update the new
	 * 	one accordingly.
	 * @throws IllegalArgumentException | !failedTask.failedTask() || !this.getProjects().contains(project) || (!newTask.waitingTask() || !newTask.pendingTask())
	 * @post | failedTask.dependsOn() == newTask.dependsOn()
	 * @post | failedTask.waitingFor() == newTask.waitingFor()
	 */
	public void replaceTask (Project project, Task failedTask, Task newTask) {
		if (!failedTask.failedTask() || !this.projects.contains(project) || (!newTask.waitingTask() || !newTask.pendingTask())) {
			throw new IllegalArgumentException();
		}
		else {
			project.replace(failedTask, newTask);
		}
	}
	
	/**
	 * Setter to advance the system time.
	 * @pre The Project Manager must be logged in.
	 * @throws IllegalArgumentException | !super.isLoggedIn()
	 * @post | Clock.getSystemTime() == old(Clock.getSystemTime()).plus(Duration.ofMinutes(time))
	 */
	public void advanceTime(int time) {
		if (!super.loggedIn) {
			throw new IllegalArgumentException();
		}
		else {
			Duration timeToAdvanceInMinutes = Duration.ofMinutes(time);
			Clock.advanceTime(timeToAdvanceInMinutes);
		}
	}
	
	/**
	 * Setter called by the controller to create a new project with the given parameters.
	 * @throws IllegalArgumentException | !super.isLoggedIn()
	 * @throws IllegalArgumentException | getProjects().stream().anyMatch(project -> project.getName() == name)

	 */
	
	// Geen idee hoe je dit juist doet
	// * @post | project != null
	// * @post | project.getName() == name
	// * @post | project.getDescription() == desc
	// * @post | project.getDueTime() == dueTime
	// * @post | getProjects() == old(projects).add(project)
	public void createProject(String name, String desc, Duration dueTime) {
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
	
	/**
	 * Setter called by the controller to create a new task with the given parameters.
	 * @throws IllegalArgumentException | !super.isLoggedIn() || !project.getStatus().isExecuting() || project == null
	 
	 */
	
	// Geen idee hoe je dit juist doet
	// * @post | task != null
	// * @post | task.getProject() == project
	// * @post | task.getDescription() == description
	// * @post | task.getEstimatedDuration() == duration
	// * @post | task.getAcceptableDeviation() == deviation
	// * @post | task.waitingFor() == imWaitingFor
	// * @post | getCreatedTasks() == old(createdTasks).add(task)
	public void createTask(Project project, String description, int duration, double deviation, List<Task> imWaitingFor) {
		if (!super.loggedIn || project == null || !project.getStatus().isExecuting()) {
			throw new IllegalArgumentException();
		}
		Task task = new Task(project, description, duration, deviation, imWaitingFor);
		this.createdTasks.add(task);
	}
}
