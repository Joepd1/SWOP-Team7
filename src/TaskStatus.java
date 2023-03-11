package src;

/**
 * 
 * @author vincent
 */
public class TaskStatus extends Status {
	
	/**
	 * Constructs the status of the associated Task and initializes it as pending.
	 */
	public TaskStatus() {
		super.myStatus = status.PENDING;
	}
	
	/**
	 * Getter to indicate if the task is pending or not.
	 */
	public boolean isPending() {return super.myStatus.equals(status.PENDING);}
	
	/**
	 * Getter to indicate if the task is waiting or not.
	 */
	public boolean isWaiting() {return super.myStatus.equals(status.WAITING);}

	/**
	 * Setter to indicate the execution of a task.
	 */
	public void startTask() {super.myStatus = status.EXECUTING;}
		
	/**
	 * Setter to indicate that a task has to wait on another one to be finished.
	 */
	public void haltTask() {super.myStatus = status.WAITING;}
	
	/**
	 * Setter to indicate that a task can be chosen by a developer to be executed.
	 */
	public void freeTask() {super.myStatus = status.PENDING;}
}
