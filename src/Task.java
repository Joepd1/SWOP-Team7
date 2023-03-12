package src;

import java.time.Duration;
import java.util.List;

/**
 * Each instance of this class represents a task.

 * @invar | getStatus() != null
 * @invar | getDescription() != null
 * @invar | getProject() != null
 * @invar | waitingFor() != null
 * @author vincent
 */
public class Task {
	
	/**
	 * @invar | status != null
	 * @invar | description != null
	 * @invar | project != null
	 * @invar | imWaitingFor != null
	 */
	private final Project project;
	private final String description;
	private final int estimatedDuration;
	private final double acceptableDeviation; 
	private TaskStatus status;
	private Developer performedBy;
	private TimeSpan timeSpan;
	private List<Task> dependsOnMe; // list that wait for me to finish
	private List<Task> imWaitingFor; // list to be finished before i an begin
	
	/**
	 * Initializes this object so it can represent a task, whose associated project is the given project, description is the given description,
	 * 	duration is the given duration, deviation is the given deviation, status is waiting or pending and whose tasks that must be completed
	 * 	before this task can be executed the given imWaitingFor is.
	 * @pre The User calling this must be a ProjectManager
	 * @throws IllegalArgumentException | project == null
	 * @throws IllegalArgumentException | description == null
	 * @throws IllegalArgumentException | deviation < 0 || deviation > 1
	 * @throws IllegalArgumentException | imWaitingFor == null
	 * @post | getStatus().isPending() == true || getStatus().isWaiting() == true
	 * @post | getDescription() == description
	 * @post | getEstimatedDuration() == duration
	 * @post | getAcceptableDeviation() == deviation
	 * @post | getProject() == project
	 * @post | waitingFor() == imWaitingFor
	 */
	public Task(Project project, String description, int duration, double deviation, List<Task> imWaitingFor) {		
		if (deviation >= 1.0 || deviation <= 0 || project.addTask(this, imWaitingFor)) {
			throw new IllegalArgumentException();
		}
		else {
			this.project = project; 
			this.description = description;
			this.estimatedDuration = duration;
			this.acceptableDeviation = deviation;
			this.status = new TaskStatus();
			this.imWaitingFor = imWaitingFor;
			for (Task task : this.imWaitingFor) {
				if (!task.finishedTask()) {
					this.status.haltTask();
				}				
			}
			for (Task task : project.getTasks()) {
				if (task.waitingFor().contains(this)) {
					this.dependsOnMe.add(task);
				}
			}		
		}
	}
	
	/**
	 * Setter to update the list of tasks that depend on this task.
	 * @throws IllegalArgumentException() | dep == null
	 * @post | this.dependsOn().equals(old(this.dependsOn()).addAll(dep))
	 */
	public void addDepending(List<Task> dep) {
		if (dep == null) {
			throw new IllegalArgumentException();
		}
		else {
			this.dependsOnMe.addAll(dep);
		}
	}
	
	/**
	 * Setter to update the list of tasks that are waiting for this task to be finished.
	 * @throws IllegalArgumentException() | wait == null
	 * @post | this.waitingFor().equals(old(waitingFor()).addAll(wait))
	 */
	public void addWaiting(List<Task> wait) {
		if (wait == null) {
			throw new IllegalArgumentException();
		}
		else {
			this.imWaitingFor.addAll(wait);
		}
	}
	
	/**
	 * This function will be called when a developer chooses to execute this task and will initialize the field performedBy with the given developer. 
	 * @pre can only be executed by a developer
	 * @throws IllegalArgumentException | developer == null
	 * @post | executingTask() || waitingTask()
	 * @post | getTimeSpan() != null
	 * @post | performedBy() != null
	 */
	public boolean startTask(Developer developer) {
		if (developer == null) {
			throw new IllegalArgumentException();
		}
		else {
			for (Task t : imWaitingFor) {
				if (!t.finishedTask()) {
					return false;
				}
			}
			this.timeSpan = new TimeSpan();
			this.performedBy = developer;
			this.status.startTask();
			return true;
		}
	}
	
	/**
	 * @basic
	 */
	public boolean finishedTask() {return this.status.isFinished();}
	
	/**
	 * @basic
	 */
	public boolean failedTask() {return this.status.isFailed();}
	
	/**
	 * @basic
	 */
	public boolean executingTask() {return this.status.isExecuting();}
	
	/**
	 * @basic
	 */
	public boolean waitingTask() {return this.status.isWaiting();}
	
	/**
	 * @basic
	 */
	public boolean pendingTask() {return this.status.isPending();}
	
	/**
	 * This function is called when a developer indicates a task as finished. It will check if it's possible to 
	 * 	free up tasks that depend on this task.
	 * @pre The task is indicated as finished
	 * @pre This function is called by a developer.
	 * @post | this.getStatus().isFailed()
	 */
	public void finishTask() {
		this.timeSpan.endTime();
		this.status.finishStatus();
		updateDepStatus();
	}
	
	/**
	 * This function is called when a developer indicates a task as failed.
	 * @pre The task is indicated as failed.
	 * @pre This function is called by a developer.
	 * @post | this.getStatus().isFailed()
	 */
	public void failTask() {
		this.timeSpan.endTime();
		this.status.failStatus();
	}
	
	/**
	 * This function updates the status of all the tasks that depend on this task, if this task is finished.
	 * @pre This task is finished
	 */
	public void updateDepStatus() {
		for (Task waitingTask : dependsOnMe) {
			boolean temp = true;
			for (Task dependentTask : waitingTask.waitingFor()) {
				if (!dependentTask.finishedTask()) {
					temp = false;
				}
			}
			if (temp) { 
				waitingTask.status.freeTask();
			}
		}
	}
	
	/**
	 * @basic
	 */
	public TaskStatus getStatus() {return this.status;}

	/**
	 * @basic
	 */
	public String getDescription() {return this.description;}

	/**
	 * @basic
	 */
	public int getEstimatedDuration() {return this.estimatedDuration;}

	/**
	 * @basic
	 */
	public double getAcceptableDeviation() {return this.acceptableDeviation;}

	/**
	 * @basic
	 */
	public Developer getDeveloper() {return this.performedBy;}

	/**
	 * @basic
	 */
	public TimeSpan getTimeSpan() {return this.timeSpan;}
	
	/**
	 * @basic
	 */
	public Developer performedBy() {return this.performedBy;}
	
	/**
	 * @basic
	 */
	public Project getProject() {return this.project;}
	
	/**
	 * @basic
	 */
	public List<Task> waitingFor() {return this.imWaitingFor;}
	
	/**
	 * @basic
	 */
	public List<Task> dependsOn() {return this.dependsOnMe;}
	
	/**
	 * This function returns the time that has been spent on this task. 
	 * @throws IllegalArgumentException | !waitingTask() || !pendingTask()
	 */
	public Duration spentTime() {
		if (!this.waitingTask() || !this.pendingTask() ) {
			return this.timeSpan.getElapsedTime(this.status);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
}
