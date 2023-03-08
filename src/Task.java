package src;

import java.util.List;

/**
 * TODO: failedTask(); executingTask(); waitingTask()
 * 
 * @author vincent
 */
public class Task {
	
	/**
	 * @contains the project that this task is a part of
	 * @contains the description of the task
	 * @contains the estimated duration of execution of the task
	 * @contains the acceptable deviation of the time of execution of the task
	 * @contains the task's status
	 * @contatins the developer that is performing this task (is null at creation, but initialized after the function startTask())
	 * @contains the timeSpan of this task (is null at creation, but initialized after the function startTask())
	 * @contains a list of all the tasks that this task depends on
	 * @contains a list of the tasks that are waitingFor this task, i.e. the tasks that depend on this task
	 * 
	 */
	private Project project;
	private String description;
	private int esitmatedDuration;
	private float acceptableDeviation; // ->. A task can be finished early (even before the earliest acceptable
		// deviation from the estimated duration), on time (within the acceptable deviation) or
		// with a delay (even later than the latest acceptable deviation).
	private TaskStatus status;
	private Developer performedBy;
	private TimeSpan timeSpan;
	private List<Task> dependsOn;
	private List<Task> waitingFor;
	
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
	 * @param dependsOn are the tasks that this task depend on, if one of these tasks isn't marked as finished, this task can't be started
	 * @param waitingFor are the tasks that depend on this task.
	 * 	the status of this task will be set as unavailable
	 */
	public Task(Project project, String description, int duration, float deviation, List<Task> dependsOn, List<Task> waitingFor) {		
		if (project.finishedProject() || deviation >= 1.0 || deviation <= 0 || project.addTask(this, dependsOn)) {
			throw new IllegalArgumentException();
		}
		else {
			this.project = project; 
			this.description = description;
			this.esitmatedDuration = duration;
			this.acceptableDeviation = deviation;
			this.status = new TaskStatus();
			this.dependsOn = dependsOn;
			this.waitingFor = waitingFor;
			boolean once = true;
			for (Task task : dependsOn) {
				if (once) {
					this.waitingFor.add(task);
					this.status.setStatus("waiting");
					this.status.setAvailability(false);
					once  = false;
				}
				else if (task.getStatus().makeString() != "finished") {
					this.waitingFor.add(task);
				}				
			}
		}
	}
	
	/**
	 * Getter that returns the task that must be completed before this task can be executed;
	 * 	In other words this task depends on these tasks.
	 */
	public List<Task> dependsOn() {return this.dependsOn;}
	
	/**
	 * Getter that returns the tasks that depend on this task;
	 *  In other words the tasks that are waiting for this task to be finished.
	 */
	public List<Task> waitingFor() {return this.waitingFor;}
	
	/**
	 * This function will be called when a developer chooses to execute this task and will initialize the field performedBy with the given developer. 
	 *  It also starts the timer and sets the status to unavailable & executing.
	 *  
	 * @pre can only be executed by a developer
	 * @param developer is the developer who chose to execute this task.
	 */
	public boolean startTask(Developer developer) {
		boolean validStart = true;
		for (Task t : waitingFor) {
			if (!t.finishedTask()) {
				validStart = false;
			}
		}
		if (!validStart) {
			return false;
		}
		else {
			this.timeSpan = new TimeSpan(this);
			this.performedBy = developer;
			this.status.setAvailability(false);
			this.status.setStatus("executing");
			developer.addTask(this);
			return true;
		}
	}
	
	/**
	 * Getter that indicates if this task is finished.
	 */
	public boolean finishedTask() {
		if ( this.getStatus().isFinished()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This function is called when a developer indicates a task as finished. It will update the statuses of the tasks
	 * 	that are waiting for this task to be finished to available and calculate the duration of this task so it can 
	 * 	return if the task is finished early, on time or late.
	 * 
	 * @pre The task is indicated as finished
	 */
	public void finishTask() {
		this.timeSpan.endTask();
		updateDepStatus();
			
		String span = this.timeSpan.getTimeSpan();
		String[] arrOfStr = span.split("h");
		int duration = Integer.parseInt(arrOfStr[0]) * 60;
		duration += Integer.parseInt(arrOfStr[1]);
		float minDeviation = this.esitmatedDuration - (this.esitmatedDuration*this.acceptableDeviation);
		if (duration < minDeviation) {
			System.out.println("Task is finished early"); // Should be sent to the client-side
		}
		float maxDeviation = this.esitmatedDuration + (this.esitmatedDuration*this.acceptableDeviation);
		if (duration > maxDeviation) {
			System.out.println("Task is finished with a delay"); // Should be sent to the client-side
		}
		else {
			System.out.println("Task is finished on time"); // Should be sent to the client-side
		}
	}
	
	/**
	 * This function is called when a developer indicates a task as failed. It will create a timestamp and mark
	 * 	the task as failed.
	 */
	public void failTask() {
		this.timeSpan.endTask();
		this.status.setStatus("failed");
	}
	
	/**
	 * This function updates the status of all the tasks that depend on this task.
	 * 
	 * @pre This task is finished
	 * @param tasks is the list of tasks that depend on this task.
	 */
	public void updateDepStatus() {
		for (Task waitingTask : waitingFor) {
			boolean temp = true;
			//if all tasks that waitinTask is waiting for are complete
			for (Task dependentTask : waitingTask.dependsOn()) {
				if (!dependentTask.finishedTask()) {
					temp = false;
				}
			}
			if (temp) { 
				waitingTask.getStatus().setAvailability(true);;
			}
		}
	}
	
	/**
	 * This function returns the status of this task
	 */
	public TaskStatus getStatus() {return this.status;}
	
	/**
	 * This function returns the time that has been spent on this task. If the task is still being executed (2nd clause)
	 * 	this time will not be final (will be different later on). If this task is finished or failed this time is final.
	 */
	public String spentTime() {
		if (this.finishedTask() || this.getStatus().makeString() == "failed") {
			return this.timeSpan.getTimeSpan();
		}
		else if (this.getStatus().makeString() == "executing") {
			return this.timeSpan.getElapsedTime(this);
		}
		else {
			return "This task hasn't been started yet.";
		}
	}
	
	/**
	 * This function updates the time of a replaced task.
	 * @param time is the time the old task took until failed, so it must be added to the new time.
	 */
	public void updateTime(String time) {this.timeSpan.newTaskTime(time);}
	
}
