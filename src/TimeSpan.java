package src;

import java.time.Duration;
import java.time.LocalDateTime;

import src.Status.status;

/**
 * TODO The time spans can be used to assess the	estimated time allocated to tasks (to improve future project management).
 * 
 * @author vincent
 */
public class TimeSpan {
	
	/**
	 * @contains the time the associated task was started; i.e. when a developer changed it's status to executing, is null before this.
	 * @contains the time the associated task was ended; i.e. when a developer changed it's status to finished or failed, is null before this.
	 */
	private final LocalDateTime startTime;
	private LocalDateTime endTime;

	/**
	 * The constructor will start the clock and set the the time-collector for replaced tasks to zero.
	 */
	public TimeSpan() {
		this.startTime = LocalDateTime.now();
	}
	
	/* 
	 * This function will calculate the time that was needed to execute a task and will update the timestamp accordingly.
	 * 
	 * @pre task must be finished or failed
	 */
	public void endTask() {
		this.endTime = LocalDateTime.now();
	}

	/** 
	 * This function will calculate the time that was needed until now to try and execute or execute the associated task 
	 *  and will return the according Duration.
	 * 
	 * @pre task has to be in executing, finished or failed state
	 */
	public Duration getElapsedTime(status status) {
		if (status.equals(status.EXECUTING)) {
			LocalDateTime timeNow = LocalDateTime.now();
			Duration elapsedTime = Duration.between(startTime, timeNow);
			return elapsedTime;
		}
		else if (status.equals(status.FAILED) || status.equals(status.FINISHED)) {
			return Duration.between(this.startTime, this.endTime);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
}
