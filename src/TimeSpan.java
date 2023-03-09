package src;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

/**
 * TODO The time spans can be used to assess the	estimated time allocated to tasks (to improve future project management).
 * 
 * @author vincent
 */
public class TimeSpan {
	
	/**
	 * @contains the time the associated task was started; i.e. when a developer changed it's status to executing, is null before this.
	 * @contains the time the associated task was ended; i.e. when a developer changed it's status to finished or failed, is null before this.
	 * @contains the time that was worked on previous versions of the associated task.
	 * @contains the associated task.
	 */
	private long extraTime;
	private LocalTime startTime;
	private LocalTime endTime;
	private Task task;

	/**
	 * The constructor will start the clock and set the the time-collector for replaced tasks to zero.
	 */
	public TimeSpan(Task task) {
		this.startTime = LocalTime.now();
		this.extraTime = 0;
		this.task = task;
	}
	
	/* 
	 * This function will calculate the time that was needed to execute a task and will update the timestamp accordingly.
	 * 
	 * @pre task must be finished or failed
	 */
	public void endTask() {
		this.endTime = LocalTime.now();
	}

	/** 
	 * This function will calculate the time that was needed until now to try and execute or execute the associated task 
	 *  and will return the according Duration.
	 * 
	 * @pre task has to be in executing, finished or failed state
	 */
	public Duration getElapsedTime() {
		if (this.task.executingTask()) {
			LocalTime timeNow = LocalTime.now();
			Duration elapsedTime = Duration.between(startTime, timeNow);
			return elapsedTime;
		}
		else if (this.task.failedTask() || this.task.finishedTask()) {
			return Duration.between(this.startTime, this.endTime);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
}
