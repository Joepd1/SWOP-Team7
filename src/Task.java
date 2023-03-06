package src;

import java.util.List;

public class Task {
	private Project project; //==>Unfinnished
	private String description;
	private int esitmatedDuration;
	private float acceptableDeviation; // ->. A task can be finished early (even before the earliest acceptable
		// deviation from the estimated duration), on time (within the acceptable deviation) or
		// with a delay (even later than the latest acceptable deviation).
	private Status status;
	private Task alternative;
	private TimeSpan timeSpan;
	private Developer performedBy;
	private TimeSpan span;
	
	public Task(Project project, String description, int duration, float deviation, List<Task> dependencies, Developer performedBy) {
		//User must be a dev.
		
		if (project.isFinished() || deviation > 1.0 || project.addTask(this, dependencies)) {
			// Throw error
		}
		else {
			this.project = project; 
			this.description = description;
			this.esitmatedDuration = duration;
			this.acceptableDeviation = deviation;
			this.performedBy = performedBy;
			this.timeSpan = new TimeSpan();
		}
	}
	
	public String whenFinished() {
		if (status.getStatus() != "finished") {
			return "Task is unfinished or failed";
		}
		else {
			String span = this.span.getTimeSpan();
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
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public List<Task> dependsOn() {
		return project.getDependencies(this);
	}
	
	public void replace(Task newTask) {
		Task thisTask = this.clone();
	}
}
