package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author vincent
 */
public class Project {
	private HashMap<Task,List<Task>> dependencies; //Key depends on value
	private String name;
	private String description;
	private String dueTime;
	private List<Task> tasks;
	private ProjectStatus status;
	private long creationTime;

	/**
	 * @param The project will be instantiated with the given name.
	 * @param The project will be instantiated with the given description.
	 * @param The project will be instantiated with the given due time.
	 */
	public Project(String name, String desc, String dueTime) {
		this.name = name;
		this.description = desc;
		this.dueTime = dueTime;
		this.status = new ProjectStatus(this);
		this.creationTime = System.currentTimeMillis();
	};
	
	/**
	 * Checks every task associated with this project; if one isn't marked as finished the project can't be
	 * 	finished so returns false; if every task is finished the project will be finished and this will 
	 * 	return true.
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
		HashMap<Task,List<Task>> newDependencies = (HashMap<Task,List<Task>>) this.dependencies.clone();
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
	
	
	//DEPENDENCIES AANPASSEN?
	
	/**
	 * This function replaces the dependencies from old to new; In both the dependencies where the old task is depenendent
	 * 	as where the old task is the dependant.
	 * @param oldTask is the task that was marked as failed
	 * @param newTask is the task that replaces the failed task
	 */
	public void replace(Task oldTask, Task newTask) {
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
	 * This function returns all the tasks in this project.
	 */
	public List<Task> getTasks() {return this.tasks;}
}
