package src;

import java.time.Duration;
import java.util.List;

/**
 * @author vincent
 */
public class Task {
	
	/**
	 * @contains the project that this task is a part of
	 * @contains the description of the task
	 * @contains the estimated duration of execution of the task
	 * @contains the acceptable deviation of the time of execution of the task
	 * @contains the task's status
	 * @contains the developer that is performing this task (is null at creation, but initialized after the function startTask())
	 * @contains the timeSpan of this task (is null at creation, but initialized after the function startTask())
	 * @contains a list of all the tasks that this task depends on
	 * @contains a list of the tasks that are waitingFor this task, i.e. the tasks that depend on this task
	 * 
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
	 * @pre The User calling this must be a ProjectManager
	 * 
	 * @pre The given project must be unfinished
	 * @pre The given deviation must be a percentage; so greater than or equal to zero and smaller than or
	 * 	equal to one.
	 * @pre The task can't give a cycle in the dependency graph
	 * 
	 * @param project is the project that this task is a part of
	 * @param description is the description of the task
	 * @param duration is the time the PM thinks is needed for the task
	 * @param deviation is the percentage of acceptable deviation in completion time
	 * @param imWaitingFor are the tasks that this task depend on, if one of these tasks isn't marked as finished, this task can't be started
	 * 	the status of this task will be set as unavailable
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
	 * Getter that returns the task that must be completed before this task can be executed;
	 * 	In other words this task depends on these tasks.
	 */
	public List<Task> waitingFor() {return this.imWaitingFor;}
	
	/**
	 * Getter that returns the tasks that depend on this task;
	 *  In other words the tasks that are waiting for this task to be finished.
	 */
	public List<Task> dependsOn() {return this.dependsOnMe;}
	
	/**
	 * Setter to update the list of tasks that depend on this task.
	 * @param dep are the tasks that have to be added to this list.
	 */
	public void addDepending(List<Task> dep) {
		for (Task t : dep) {
			if (!this.dependsOnMe.contains(t)) {
				this.dependsOnMe.add(t);
			}
		}
	}
	
	/**
	 * Setter to update the list of tasks that are waiting for this task to be
	 * 	finished.
	 * @param wait are the tasks that have to be added to this list.
	 */
	public void addWaiting(List<Task> wait) {
		for (Task t : wait) {
			if (!this.imWaitingFor.contains(t)) {
				this.imWaitingFor.add(t);
			}
		}
	}
	
	/**
	 * This function will be called when a developer chooses to execute this task and will initialize the field performedBy with the given developer. 
	 *  It also starts the timer and sets the status to unavailable & executing.
	 *  
	 * @pre can only be executed by a developer
	 * @param developer is the developer who chose to execute this task.
	 */
	public boolean startTask(Developer developer) {
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
	
	/**
	 * Getter that indicates if this task is finished.
	 */
	public boolean finishedTask() {return this.status.isFinished();}
	
	/**
	 * Getter that indicates if this task is failed.
	 */
	public boolean failedTask() {return this.status.isFailed();}
	
	/**
	 * Getter that indicates if this task is executing.
	 */
	public boolean executingTask() {return this.status.isExecuting();}
	
	/**
	 * Getter that indicates if this task is waiting.
	 */
	public boolean waitingTask() {return this.status.isWaiting();}
	
	/**
	 * Getter that indicates if this task is available.
	 */
	public boolean pendingTask() {return this.status.isPending();}
	
	/**
	 * This function is called when a developer indicates a task as finished. It will update the statuses of the tasks
	 * 	that are waiting for this task to be finished to available (if this is possible) and update the timeSpan.
	 * 
	 * @pre The task is indicated as finished
	 */
	public void finishTask() {
		this.timeSpan.endTime();
		this.status.finishStatus();
		updateDepStatus();
	}
	
	/**
	 * This function is called when a developer indicates a task as failed. It will update the timeSpan and mark
	 * 	the task as failed.
	 */
	public void failTask() {
		this.timeSpan.endTime();
		this.status.failStatus();
	}
	
	/**
	 * This function updates the status of all the tasks that depend on this task.
	 * 
	 * @pre This task is finished
	 * @param tasks is the list of tasks that depend on this task.
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
	 * This function returns the status of this task
	 */
	public TaskStatus getStatus() {return this.status;}

	/**
	 * This function returns the description of this task
	 */
	public String getDescription() {return this.description;}

	/**
	 * This function returns the estimated duration of this task
	 */
	public int getEstimatedDuration() {return this.estimatedDuration;}

	/**
	 * This function returns the acceptable deviation of this task
	 */
	public double getAcceptableDeviation() {return this.acceptableDeviation;}

	/**
	 * This function returns the developer who is performing this task.
	 * 	Returns null if the status of this task is PENDING or WAITING
	 */
	public Developer getDeveloper() {return this.performedBy;}

	/**
	 * This function returns the time span of this task
	 */
	public TimeSpan getTimeSpan() {return this.timeSpan;}

	/**
	 * This function returns the tasks that depend on this task
	 */
	public List<Task> getDependecies() {return this.dependsOnMe;}
	
	/**
	 * This function returns the project that is associated with this task
	 */
	public Project getProject() {return this.project;}
	
	
	/**
	 * This function returns the time that has been spent on this task. If the task is still being executed (2nd clause)
	 * 	this time will not be final (will be different later on). If this task is finished or failed this time is final.
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
