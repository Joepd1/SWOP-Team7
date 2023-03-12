package src;

import java.time.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Each instance of this class represents a project.
 * @invar | getName() != null
 * @invar | getDescription() != null
 * @invar | getDueTime() != null
 * @invar | getStatus() != null
 * @invar | getTimeSpan() != null
 * @invar | getDependencies() != null
 * @invar | getTasks() != null
 * @author vincent
 */
public class Project {
	
	/**
	 * @invar | name != null
	 * @invar | description != null
	 * @invar | dueTime != null
	 * @invar | status != null
	 * @invar | timeSpan != null
	 * @invar | dependencies != null
	 * @invar | tasks != null
	 */
	private HashMap<Task,List<Task>> dependencies; //Key depends on value
	private final String name;
	private final String description;
	private final Duration dueTime;
	private List<Task> tasks;
	private ProjectStatus status;
	private TimeSpan timeSpan;

	/**
	 * Initializes this object so it can represent a project, whose name is the given name, description is th given description
	 * 	and due time is the given due time.
	 * @pre The User calling this must be a project manager.
	 * Each instance of this class represents a project.
	 * @invar | getName() == name
	 * @invar | getDescription() == desc
	 * @invar | getDueTime() == dueTime
	 * @invar | getStatus() == status.EXECUTING
	 * @invar | totalTimeProject().toMinutes() == 0
	 * @invar | totalTimeTasks().toMinuts() == 0
	 * @invar | getDependencies().size() == 0
	 * @invar | getTasks().size() == 0
	 */
	public Project(String name, String desc, Duration dueTime) {
		this.name = name;
		this.description = desc;
		this.dueTime = dueTime;
		this.status = new ProjectStatus();
		this.timeSpan = new TimeSpan();
		this.dependencies = new HashMap<Task, List<Task>>();
		this.tasks = new ArrayList<Task>();
	};
	
	/**
	 * @basic
	 */
	public boolean finishedProject() {
		for (Task t : this.tasks) {
			if ( !t.finishedTask()) {return false;}
		}
		return true;
	}
	
	/**
	 * this function adds a task and it's dependencies to the Map of all tasks & their dependencies. The function 
	 * 	checkCycles is called to check if there are cycles in the updated 'graph'; if not then the 'graph' will be updated
	 * 	and the function will return true, but of there is a cycle, the Map will not be updated and the function will 
	 *  return false.
	 * @param task is the new task
	 * @param dependsOn are the dependencies of the new (previous parameter) task
	 */
	public boolean addTask(Task task, List<Task> dependsOn) { 
		HashMap<Task, List<Task>> newDependencies = new HashMap<Task, List<Task>>();
		for (Map.Entry<Task, List<Task>> entry : this.dependencies.entrySet()) {
			newDependencies.put(entry.getKey(), entry.getValue());
		}
		newDependencies.put(task, dependsOn);
		if (!checkCycles(task, newDependencies)) {return false;}
		else {
			this.dependencies = newDependencies;
			this.tasks.add(task);
			return true;
		}
	}
	
	/**
	 * THIS FUNCTION DOESN'T WORK AS WANTED -> TO IMPLEMENT WITH BACKTRACKING OR DFS (?)
	 * 
	 * This function checks if there are cycles in the dependency 'graph'. It will start by checking the new task (the one 
	 * 	added in addTask) and will check for a cycle by keeping a list with all encountered tasks and checking if a new task 
	 * 	already is in this list.
	 * @param task
	 * @param dependencies
	 */
	private boolean checkCycles(Task task, HashMap<Task,List<Task>> dependencies) {
		ArrayList<Task> allTasks = new ArrayList<Task>();
		allTasks.add(task);
		for (int i = 0; i < allTasks.size(); i++) {
			List<Task> deps = dependencies.get(allTasks.get(i));
			for (Task t : deps) {
				if (!allTasks.contains(t)) {allTasks.add(t);}
				else {return false;}
			}					
		}
		return true;		
	}
	
		
	/**
	 * This function replaces the dependencies from old to new; In both the dependencies where the old task is depenendent
	 * 	as where the old task is the dependant.
	 * @post | newTask.dependsOn() == oldTask.dependsOn()
	 * @post | newTask.waitingFor() == oldTask.waitingFor()
	 * 
	 */
	// weggedaan
	// @post | Every occurrence of oldTask is changed to newTask in dependencies
	
	public void replace(Task oldTask, Task newTask) {
		newTask.addDepending(oldTask.dependsOn());
		newTask.addWaiting(oldTask.waitingFor());
		
		List <Task> dep = this.dependencies.get(oldTask);
		this.dependencies.remove(oldTask, dep);
		this.dependencies.put(newTask, dep);
		for (Map.Entry<Task, List<Task>> entry : this.dependencies.entrySet()) {
			List<Task> tempList = entry.getValue();
			Task tempTask = entry.getKey();
			if (tempList.contains(oldTask)) {
				tempList.remove(oldTask);
				tempList.add(newTask);
				this.dependencies.put(tempTask, tempList);
			}
		}
	}
	
	/**
	 * @basic
	 */
	public List<Task> getTasks() {return this.tasks;}
	
	/**
	 * @basic
	 */
	public String getName() {return this.name;}

	/**
	 * @basic
	 */
	public String getDescription() {return this.description;}

	/**
	 * @basic
	 */
	public Duration getDueTime() {return this.dueTime;}

	/**
	 * @basic
	 */
	public ProjectStatus getStatus() {return this.status;}

	/**
	 * @basic
	 */
	public TimeSpan getTimeSpan() {return this.timeSpan;}
	
	/**
	 * @basic
	 */
	public HashMap<Task, List<Task>> getDependencies() {return this.dependencies;}
	
	/**
	 * Set's the status of this project as finished.
	 * @throws IllegalArgumentException | !finishedProject()
	 * @post | getStatus().isFinished() == true
	 */
	public void finishProject() {
		if (finishedProject()) {
			this.timeSpan.endTime();
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Getter that returns the time the project was being executed.
	 * @basic
	 */
	public Duration totalTimeProject() {return this.timeSpan.getElapsedTime(this.status);}
	
	/**
	 * Getter that returns the total time spent on all tasks.
	 * @basic
	 */
	public Duration totalTimeTasks() {
		Duration temp = Duration.ZERO;
		for (Task t : this.tasks) {
			temp.plus(t.spentTime());
		}
		return temp;
	}
}
