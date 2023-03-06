package src;


// TODO The time spans can be used to assess the	estimated time allocated to tasks (to improve future project management).

public class TimeSpan {
	private long startTime;
	private long endTime;
	private long extraTime;
	private String timeStamp;

	/**
	 * The constructor will start the clock
	 */
	public TimeSpan() {
		this.startTime = System.currentTimeMillis();
		this.extraTime = 0;
	}
	
	/**
	 * @pre task must be finished
	 * 
	 * This function will calculate the time that was needed to execute a task and will update the timestamp.
	 */
	public void endTask() {
		this.endTime = System.currentTimeMillis();
		long elapsedTime = this.endTime - this.startTime;
		long totalTime = elapsedTime + this.extraTime;
		int minutes = (int) ((elapsedTime / (1000*60)) % 60);
		int hours = (int) ((elapsedTime / (1000*60*60)) % 24);
		String hrs = Integer.toString(hours);
		String mins = Integer.toString(minutes);
		this.timeStamp = hrs + "h" + mins;
	}

	/**
	 * @pre task can't be finished
	 * 
	 * This function will calculate the time that was needed until now to try and execute a task and will return the
	 * 	according timestamp.
	 */
	public String getElapsedTime(Task task) {
		if (task.getStatus().makeString() != "finished") {
			long timeNow = System.currentTimeMillis();
			long elapsedTime = timeNow - this.startTime;
			long totalTime = elapsedTime + this.extraTime;
			int minutes = (int) ((elapsedTime / (1000*60)) % 60);
			int hours = (int) ((elapsedTime / (1000*60*60)) % 24);
			String hrs = Integer.toString(hours);
			String mins = Integer.toString(minutes);
			return hrs + "h" + mins;
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
	 * Setter to update the extra time of a new task that replaces another one
	 * @param time is the time that the replaced task was being executed and thus is added to the extra time that is 
	 * 	needed to execute the task.
	 */
	public void newTaskTime(String time) {
		String[] arrOfStr = time.split("h");
		int hours = Integer.parseInt(arrOfStr[0]) * 3600000 ;
		int mins = Integer.parseInt(arrOfStr[1]) * 60000;
		this.extraTime += hours + mins;
	}
}
