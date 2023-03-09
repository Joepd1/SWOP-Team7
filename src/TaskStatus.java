package src;

/**
 * 
 * @author vincent
 */
public class TaskStatus extends Status {
	
	/**
	 * Constructs the status of the associated Task and initializes it as available & waiting.
	 */
	public TaskStatus() {
		super.myStatus = status.PENDING;
	}
	
	/**
	 * Getter to indicate if the task is pending or not
	 */
	public boolean isPending() {return super.myStatus.equals(status.PENDING);}
	
	/**
	 * Getter to indicate if the task is waiting or not
	 */
	public boolean isWaiting() {return super.myStatus.equals(status.WAITING);}

	public void startTask() {super.myStatus = status.EXECUTING;}
		
	public void haltTask() {super.myStatus = status.WAITING;}
	
	public void freeTask() {super.myStatus = status.PENDING;}
}
