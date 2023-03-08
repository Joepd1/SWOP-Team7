package src;

/**
 * 
 * @author vincent
 */
public class TaskStatus extends Status {

	/**
	 * @contains a boolean that indicates if the associated task is available for execution.
	 * 	NOTE: A task can only be available when it's waiting && if there are no tasks that
	 * 	 must be completed before this task can be executed.
	 */
	private boolean available; 

	/**
	 * Constructs the status of the associated Task and initializes it as available & waiting.
	 */
	public TaskStatus() {
		this.available = true;
		super.status = "waiting";
	}
	
	/**
	 * This function returns true if the task or project is available for execution, and false in any other case.
	 */
	public boolean isAvailable() {return this.available;}
	
	/**
	 * This function will change the availability of a task.
	 * @pre bool and this.available must be different.
	 * @param bool is the availability the {User} wants to set to this task.
	 */
	public void setAvailability(boolean bool) {
		if (this.available == bool) {
			throw new IllegalArgumentException();
		}
		else {
			this.available = bool;
		}
	}
	
	/**
	 * Getter to indicate if the task is waiting or not
	 */
	public boolean isWaiting() {
		if (this.status == "waiting") {
			return true;
		}
		else {
			return false;
		}
	}
}
