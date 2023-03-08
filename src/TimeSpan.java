package src;

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
	private long startTime;
	private long endTime;
	private long extraTime;
	private String timeStamp;
	private Task task;

	/**
	 * The constructor will start the clock and set the the time-collector for replaced tasks to zero.
	 */
	public TimeSpan(Task task) {
		this.startTime = System.currentTimeMillis();
		this.extraTime = 0;
		this.task = task;
	}
	
	/* 
	 * This function will calculate the time that was needed to execute a task and will update the timestamp accordingly.
	 * 
	 * @pre task must be finished or failed
	 */
	public void endTask() {
		this.endTime = System.currentTimeMillis();
		long elapsedTime = (this.endTime - this.startTime);
		int minutes = (int) ((elapsedTime / (1000*60)) % 60);
		int hours = (int) ((elapsedTime / (1000*60*60)) % 24);
		String hrs = Integer.toString(hours);
		String mins = Integer.toString(minutes);
		this.timeStamp = hrs + "h" + mins;
	}

	/** 
	 * This function will calculate the time that was needed until now to try and execute a task and will return the
	 * 	according timestamp.
	 * 
	 * @pre task has to be in executing state
	 */
	public String getElapsedTime(Task task) {
		if (task.executingTask()) {
			long timeNow = System.currentTimeMillis();
			long elapsedTime = (timeNow - this.startTime);
			int minutes = (int) ((elapsedTime / (1000*60)) % 60);
			int hours = (int) ((elapsedTime / (1000*60*60)) % 24);
			String hrs = Integer.toString(hours);
			String mins = Integer.toString(minutes);
			return hrs + "h" + mins;
		}
		else if (task.waitingTask()) {
			throw new IllegalArgumentException();
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Getter to get the timeStamp
	 * 
	 * @pre task must be finished or failed
	 */
	public String getTimeSpan() {return this.timeStamp;}
	
	
	/**
	 * Getter to calculate the total time worked on this task and possibly replaced tasks
	 */
	public String getTotalTime() {
		String[] temp = this.timeStamp.split("h");
		long hrs = Integer.parseInt(temp[0]) * 3600000 ;
		long mins = Integer.parseInt(temp[1]) * 60000;
		long stamp = hrs + mins + this.extraTime;
		String hours = Integer.toString((int) ((stamp / (1000*60*60)) % 24));
		String minutes = Integer.toString((int) ((stamp / (1000*60)) % 60));
		return hours + "h" + minutes;		
	}
	
	/**
	 * Setter to update the extra time of a new task that replaces another one.
	 * 
	 * @param time is the time that the replaced task was being executed in and thus is added to the extra time that is 
	 * 	needed to execute the project.
	 */
	public void newTaskTime(String time) {
		String[] arrOfStr = time.split("h");
		int hours = Integer.parseInt(arrOfStr[0]) * 3600000 ;
		int mins = Integer.parseInt(arrOfStr[1]) * 60000;
		this.extraTime += hours + mins;
	}
}
