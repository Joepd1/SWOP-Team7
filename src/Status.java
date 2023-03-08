package src;

/**
 * The status of a project or task can be executing, failed or finished.
 *  The status of a task can also be waiting, and can also be available or unavailable.
 * @author vincent
 *
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
}
