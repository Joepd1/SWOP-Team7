package src;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;

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
	//private HashMap<Task,List<Task>> dependencies; //Key depends on value
	private DefaultDirectedGraph<Task,Task> dependencies;
	private final String name;
	private final String description;
	private final Duration dueTime;
	private Set<Task> tasks;
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
		this.dependencies = new DefaultDirectedGraph<Task,Task>(null);
		this.tasks = new HashSet<Task>();
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
	 * this function adds a task and it's dependencies to the graph of all tasks & their dependencies. If the updated graph
	 * 	contains a loop, then the function returns false and nothing changes; if it doesn't contain a loop, then the updated
	 * 	graph will be saved, the new task will be added and the function will return true. 
	 * @post | 
	 * @post | 
	 */
	public boolean addTask(Task newTask, Set<Task> dependsOn) { 
		DefaultDirectedGraph<Task, Task> newDirectedGraph = new DefaultDirectedGraph<Task, Task>(null);
		Set<Task> tasks = this.dependencies.vertexSet();
		for (Task task : tasks) {
			newDirectedGraph.addVertex(task);
			Set<Task> myDep = this.dependencies.edgesOf(task);
			for (Task dep : myDep) {
				newDirectedGraph.addEdge(task, dep);
			}
		}
		newDirectedGraph.addVertex(newTask);
		for (Task t : dependsOn) {
			newDirectedGraph.addEdge(newTask, t);
		}
		CycleDetector<Task, Task> cycleDetector = new CycleDetector<>(newDirectedGraph);
		if (cycleDetector.detectCycles()) {return false;}
		else {
			this.dependencies = newDirectedGraph;
			this.tasks.add(newTask);
			return true;
		}
	}	
		
	/**
	 * This function replaces the dependencies from old to new; In both the dependencies where the old task is depenendent
	 * 	as where the old task is the dependant.
	 * @post | newTask.dependsOn() == oldTask.dependsOn()
	 * @post | newTask.waitingFor() == oldTask.waitingFor()
	 * @post | Every occurrence of oldTask is changed to newTask in dependencies
	 */
	public void replace(Task oldTask, Task newTask) {
		newTask.addDepending(oldTask.dependsOn());
		newTask.addWaiting(oldTask.waitingFor());
		
		Set<Task> dep = this.dependencies.edgesOf(oldTask);
		this.dependencies.removeAllEdges(dep);
		for (Task t : dep) {
			this.dependencies.addEdge(newTask, t);
		}
		Set<Task> sources = this.dependencies.incomingEdgesOf(oldTask);
		for (Task source : sources) {
			this.dependencies.removeEdge(source, oldTask);
			this.dependencies.addEdge(source, newTask);
		}
	}
	
	/**
	 * @basic
	 */
	public Set<Task> getTasks() {return this.tasks;}
	
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
	public DefaultDirectedGraph<Task,Task> getDependencies() {return this.dependencies;}
	
	/**
	 * Set's the status of this project as finished.
	 * @throws IllegalArgumentException | !finishedProject()
	 * @post | getStatus() == status.FINISHED
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
