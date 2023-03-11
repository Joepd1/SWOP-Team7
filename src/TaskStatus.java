package src;

/**
 * Each instance of this class represents the status of a task.
 * @invar | getStatus() != null
 * @author vincent
 */
public class TaskStatus extends Status {
	
	/**
	 * Initializes this object so that it can represent the status of the associated task.
	 * @post | getStatus() == status.PENDING
	 */
	public TaskStatus() {
		super.myStatus = status.PENDING;
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
	 * Sets the status of the associated task to executing.
	 * @post | getStatus() == status.EXECUTING
	 */
	public void startTask() {super.myStatus = status.EXECUTING;}
		
	/**
	 * Sets the status of the associated task to waiting.
	 * @post | getStatus() == status.WAITING
	 */
	public void haltTask() {super.myStatus = status.WAITING;}
	
	/**
	 * Sets the status of the associated task to pending.
	 * @post | getStatus() == status.PENDING
	 */
	public void freeTask() {super.myStatus = status.PENDING;}
}
