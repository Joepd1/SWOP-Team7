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
	protected status myStatus;

	protected static enum status {
		PENDING,WAITING,EXECUTING,FINISHED,FAILED;
	}
	
	/**
	 * This function will return the status of the task
	 */
	public status getStatus()  {return this.myStatus;}
	
	public void finishStatus() {this.myStatus = status.FINISHED;}
	
	public void failStatus() {this.myStatus = status.FAILED;} 
	
	/**
	 * Getter to indicate if the task/project is completed or not
	 */
	public boolean isFinished() {return this.myStatus.equals(status.FINISHED);}
	
	/**
	 * Getter to indicate if the task/project is failed or not
	 */
	public boolean isFailed() {return this.myStatus.equals(status.FAILED);}
	
	/**
	 * Getter to indicate if the task/project is being executed or not
	 */
	public boolean isExecuting() {return this.myStatus.equals(status.EXECUTING);}
}
