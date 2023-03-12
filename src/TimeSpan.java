package src;

import java.time.Duration;

/**
 * Each instance of this class represents the TimeSpan of an Item. An item can be a project or a task. 
 * @invar | getStartTime() != null
 * @author vincent
 */
public class TimeSpan {
	
	/**
	 * @invar | startTime != null
	 * @invar | if (Item.isFinished() || Item.isFailed()) then endTime != null
	 * 			else endTime == null
	 */
	private Duration startTime;
	private Duration endTime;

	/**
	 * Initializes this object so that it can represent the timings of an Item.
	 * @post | getStartTime() != null
	 */
	public TimeSpan() {
		this.startTime = Clock.getSystemTime();
	}
	
	/**
	 * Sets this timeSpan's end time.
	 * @pre The item must be finished or failed.
	 * @post | endTime() != null
	 */
	public void endTime() {
		this.endTime = Clock.getSystemTime();
	}

	/** 
	 * This function will calculate the time that was needed until now to try and execute or execute the associated task 
	 *  and will return the according Duration.
	 * @throws IllegalArgumentExxception | status.isWaiting() || status.isPending()
	 */
	public Duration getElapsedTime(Status status) {
		if (status.isExecuting()) {
			Duration timeNow = Clock.getSystemTime();
			Duration elapsedTime = timeNow.minus(this.startTime);
			return elapsedTime;
		}
		else if (status.isFailed() || status.isFinished()) {
			return this.endTime.minus(this.startTime);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
}
