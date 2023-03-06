package src;

public class TimeSpan {
	private long startTime;
	private String span;

	public TimeSpan() {
		this.startTime = System.currentTimeMillis();
	}
	
	public void finishTask() {
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - this.startTime;
		int minutes = (int) ((elapsedTime / (1000*60)) % 60);
		int hours = (int) ((elapsedTime / (1000*60*60)) % 24);
		String hrs = Integer.toString(hours);
		String mins = Integer.toString(minutes);
		this.span = hrs + "h" + mins;
	}
	
	public String getTimeSpan() {
		// if (task.getStatus().getStatus() != "executing") {
		return this.span;
		//}
		//else {
		//	throw new IllegalArgumentException(); 
		
	}
	
	public String getElapsedTime(Task task) {
		if (task.getStatus().getStatus() == "executing") {
			long timeNow = System.currentTimeMillis();
			long elapsedTime = timeNow - this.startTime;
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
	
	
	//The time spans can be used to assess the	estimated time allocated to tasks (to improve future project management).
}
