package src;

import src.Status.status;

/**
 * Each instance of this class represents the status of a task.
 * @invar | getStatus() != null
 * @author vincent
 */
public class TaskStatus extends Status {
	
	/**
	 * Initializes this object so that it can represent the status of the associated task.
	 * @post | this.isPending() == true
	 */
	public TaskStatus() {
		super.myStatus = status.PENDING;
	}
	
	public status getExecuting() {
		return status.EXECUTING;
	}
	
	/**
	 * @basic
	 */
	public boolean isPending() {return super.myStatus.equals(status.PENDING);}
	
	/**
	 * @basic
	 */
	public boolean isWaiting() {return super.myStatus.equals(status.WAITING);}
	
	/**
	 * @basic
	 */
	public boolean isExecuting() {return super.myStatus.equals(status.EXECUTING);}

	/**
	 * Sets the status of the associated task to executing.
	 * @post | this.isExecuting() == true
	 */
	public void startTask() {super.myStatus = status.EXECUTING;}
		
	/**
	 * Sets the status of the associated task to waiting.
	 * @post | this.isWaiting() == true
	 */
	public void haltTask() {super.myStatus = status.WAITING;}
	
	/**
	 * Sets the status of the associated task to pending.
	 * @post | this.isPending() == true
	 */
	public void freeTask() {super.myStatus = status.PENDING;}
}
