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
	 * @contains the timestamp of a finished or failed task; If this is not the case for the associated task this will be null.
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
	 * This function will calculate the time that was needed until now to try and execute a task and will return the
	 * 	according timestamp.
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
	
//	/**
//	 * Getter to calculate the total time worked on this task and possibly replaced tasks
//	 */
//	public String getTotalTime() {
//		String[] temp = this.timeStamp.split("h");
//		long hrs = Integer.parseInt(temp[0]) * 3600000 ;
//		long mins = Integer.parseInt(temp[1]) * 60000;
//		long stamp = hrs + mins + this.extraTime;
//		String hours = Integer.toString((int) ((stamp / (1000*60*60)) % 24));
//		String minutes = Integer.toString((int) ((stamp / (1000*60)) % 60));
//		return hours + "h" + minutes;		
//	}
//	
//	/**
//	 * Setter to update the extra time of a new task that replaces another one.
//	 * 
//	 * @param time is the time that the replaced task was being executed in and thus is added to the extra time that is 
//	 * 	needed to execute the project.
//	 */
//	public void newTaskTime(String time) {
//		String[] arrOfStr = time.split("h");
//		int hours = Integer.parseInt(arrOfStr[0]) * 3600000 ;
//		int mins = Integer.parseInt(arrOfStr[1]) * 60000;
//		this.extraTime += hours + mins;
//	}
}
