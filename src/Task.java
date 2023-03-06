package src;

import java.util.List;

public class Task {
	private Project project;
	private String description;
	private int esitmatedDuration;
	private float acceptableDeviation; // ->. A task can be finished early (even before the earliest acceptable
		// deviation from the estimated duration), on time (within the acceptable deviation) or
		// with a delay (even later than the latest acceptable deviation).
	private Status status;
	private Developer performedBy;
	private TimeSpan timeSpan;
	private List<Task> waitingFor;
	
	/**
	 * @pre The User calling this must be a ProjectManager
	 * 
	 * @param project is the project that this task is a part of
	 * @param description is the description of the task
	 * @param duration is the time the PM thinks is needed for the task
	 * @param deviation is the percentage of acceptable deviation in completion time
	 * @param dependencies are the tasks that this task depend on, if one of these tasks isn't marked as finished,
	 * 	the status of this task will be set as unavailable
	 */
	public Task(Project project, String description, int duration, float deviation, List<Task> dependencies) {		
		if (project.isFinished() || deviation > 1.0 || project.addTask(this, dependencies)) {
			throw new IllegalArgumentException();
		}
		else {
			this.project = project; 
			this.description = description;
			this.esitmatedDuration = duration;
			this.acceptableDeviation = deviation;
			this.status = new Status();
			for (Task task : dependencies) {
				if (task.getStatus().makeString() != "finished") {
					this.status.setStatus("waiting");
					this.status.setUnavailable();
					this.waitingFor.add(task);
				}
			}
		}
	}
	
	/**
	 * This function will be called when a developer chooses to execute this task and will initialize the field performed 
	 *  by with the given developer. It also starts the timer.
	 * @param developer is the developer who chose to execute this task.
	 */
	public void startTask(Developer developer) {
		if (!this.getStatus().isAvailable()) {
			throw new IllegalArgumentException();
		}
		else {
			this.timeSpan = new TimeSpan();
			this.performedBy = developer;
			this.status.setUnavailable();
		}
	}
	
	/**
	 * Getter that indicates if this task is finished.
	 */
	public boolean isFinished() {
		if ( this.getStatus().makeString() == "finished") {return true;}
		else {return false;}
	}
	
	/**
	 * This function is called when a developer indicates a task as finished. It will update the statuses of the tasks
	 * 	that are waiting for this task to be finished to available and calculate the duration of this task so it can 
	 * 	return if the task is finished early, on time or late.
	 * 
	 * @pre The task is indicated as finished
	 */
	public String finishTask() {
		updateStatus(this.project.getDependencies(this));
			
		String span = this.timeSpan.getTimeSpan();
		String[] arrOfStr = span.split("h");
		int duration = Integer.parseInt(arrOfStr[0]) * 60;
		duration += Integer.parseInt(arrOfStr[1]);
		float minDeviation = this.esitmatedDuration - (this.esitmatedDuration*this.acceptableDeviation);
		if (duration < minDeviation) {
			return "Task is finished early";
		}
		float maxDeviation = this.esitmatedDuration + (this.esitmatedDuration*this.acceptableDeviation);
		if (duration > maxDeviation) {
			return "Task is finished with a delay";
		}
		else {
			return "Task is finished on time";
		}
	}
	
	/**
	 * This function updates the status of all the tasks that depend on this task.
	 * 
	 * @pre This task is finished
	 * @param tasks is the list of tasks that depend on this task.
	 */
	public void updateStatus(List<Task> tasks) {
		for (Task waitingTask : tasks) {
			boolean temp = true;
			for (Task dependentTask : this.project.getDependencies(waitingTask)) {
				if (dependentTask.getStatus().makeString() != "finished") {
					temp = false;
				}
			}
			if (temp) { 
				waitingTask.status.setAvailable();
			}
		}
	}
	
	/**
	 * TO BE UPDATED
	 * 
	 * This function returns the status of this task
	 * @return
	 */
	public Status getStatus() {return this.status;}
	
	/**
	 * This function returns the time that has been spent on this task. If the task is still being executed (2nd clause)
	 * 	this time will not be final. If this task is finished or failed this time is final.
	 */
	public String spentTime() {
		if (this.getStatus().makeString() == "finished" || this.getStatus().makeString() == "failed") {
			return this.timeSpan.getTimeSpan();
		}
		else if (this.getStatus().isAvailable()){
			return this.timeSpan.getElapsedTime(this);
		}
		else {
			return "This task hasn't been started yet.";
		}
	}
	
	/**
	 * Setter to update the status of a Task
	 * 
	 * @pre Can only be performed by a developer
	 * 
	 * @param status is the new status of this task
	 */
	public void setStatus(Status status) {
		if (status.makeString() == "finished" || status.makeString() == "failed") {
			this.getStatus().setUnavailable();
			this.getStatus().setStatus(status.makeString());
		}
		this.status = status;
	}
	
	/**
	 * This function updates the time of a replaced task.
	 * @param time is the time the old task took until failed, so it must be added to the new time.
	 */
	public void updateTime(String time) {this.timeSpan.newTaskTime(time);}
	
}
