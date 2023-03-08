package src;

/**
 * The status of a project or task can be executing, failed or finished.
 *  The status of a task can also be waiting, and can also be available or unavailable.
 * @author vincent
 */
public class Status {

	/**
	 * @contains the status of the associated object (project or task).
	 */
	protected String status;

	/**
	 * This function will return the status of the task in String form
	 */
	public String makeString()  {return this.status;}
	
	/**
	 * This function changes the status of this task.
	 */
	public void setStatus(String status) {this.status = status;}
	
	/**
	 * Getter to indicate if the task/project is completed or not
	 */
	public boolean isFinished() {
		if (this.status == "finished") {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Getter to indicate if the task/project is failed or not
	 */
	public boolean isFailed() {
		if (this.status == "failed") {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Getter to indicate if the task/project is being executed or not
	 */
	public boolean isExecuting() {
		if (this.status == "executing") {
			return true;
		}
		else {
			return false;
		}
	}
}
