package src;

/**
 * THIS CLASS NEEDS TO BE SPLIT FOR POLYMORPHISM
 * 
 * It also represents if a task is finished, failed, being executed, is waiting for (an)other
 * 	task(s) to be completed.
 * @author vince
 *
 */
public class Status {

	protected String status; // finished, failed, executing, waiting
	private boolean available;
	
	/**
	 * The constructor will be called when a Project or a Task is created. It represents if the task/project is available 
	 *  for a dev. to execute or not OR.  After creation the task will be available to be executed by a developer.
	 */
	public Status () {
		this.available = true;
	}

	/**
	 * This function will return the status of the task in String form
	 */
	public String makeString()  {return this.status;}

	/**
	 * This function returns true if the task or project is available for execution, and else in any other case.
	 */
	public boolean isAvailable() {return this.available;}
	
	/**
	 * This function changes the status of this task.
	 */
	public void setStatus(String status) {this.status = status;}
	
	public void setUnavailable() {this.available = false;}
	
	public void setAvailable() {this.available = true;}

}
