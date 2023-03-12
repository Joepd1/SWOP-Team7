package src;

/**
 * @author vincent
 */
public abstract class Status {

	/**
	 * @invar | myStatus != null
	 */
	protected status myStatus;

	/**
	 * This documents "status".
	 */
	protected static enum status {
		PENDING,WAITING,EXECUTING,FINISHED,FAILED;
	}
	
	/**
	 * @basic
	 */
	public status getStatus()  {return this.myStatus;}
	

	/**
	 * Sets the Item's status as finished.
	 * @post | this.isFinished()
	 */
	public void finishStatus() {this.myStatus = status.FINISHED;}
	
	/**
	 * Sets the Item's status as failed.
	 * @post | this.isFailed()
	 */
	public void failStatus() {this.myStatus = status.FAILED;} 
	
	
	/**
	 * @basic
	 */
	public boolean isFinished() {return this.myStatus.equals(status.FINISHED);}
	
	/**
	 * @basic
	 */
	public boolean isPending() {return this.myStatus.equals(status.PENDING);}
	
	/**
	 * @basic
	 */
	public boolean isWaiting() {return this.myStatus.equals(status.WAITING);}
	
	/**
	 * @basic
	 */
	public boolean isFailed() {return this.myStatus.equals(status.FAILED);}
	
	/**
	 * @basic
	 */
	public boolean isExecuting() {return this.myStatus.equals(status.EXECUTING);}
}
