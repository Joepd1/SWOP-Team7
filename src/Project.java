package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//THIS CLASS IS NOT UP TO DATE YET

public class Project {
	private HashMap<Task,List<Task>> dependencies; 
	private String name;
	private String description;
	private int creationTime;
	private int dueTime;
	private List<Task> tasks;

	public Project(String name, String desc, int dueTime) {
		this.name = name;
		this.description = desc;
		this.dueTime = dueTime;
		//this.creationTime = System.currentTimeMillis();
	};
	
	public boolean isFinished() {
		for (Task t : this.tasks) {
			if ( t.getStatus().getStatus() != "finished") {return false;}
		}
		return false;
	}
	
	public boolean addTask(Task task, List<Task> dependsOn) { 
		HashMap<Task,List<Task>> newDependencies = (HashMap<Task,List<Task>>) this.dependencies.clone();
		newDependencies.put(task, dependsOn);
		if (!checkCycles(task, newDependencies)) {return false;}
		else {
			this.dependencies = newDependencies;
			return true;
		}
	}
	
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
		return false;		
	}
	
	public List<Task> getDependencies(Task task) {
		return this.dependencies.get(task);
	}
	
	public void replace(Task oldTask, Task newTask) {
		List <Task> dep = this.dependencies.get(oldTask);
		this.dependencies.remove(oldTask);
		this.dependencies.put(newTask, dep);
	}
}
