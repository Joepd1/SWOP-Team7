package src;

/**
 * Each instance of this class represents the status of a project.
 * @invar | getStatus() != null
 * @author vincent
 */
public class ProjectStatus extends Status {
	
	/**
	 * Initializes this object so that it can represent the status of the associated project.
	 * @post | getStatus() == status.EXECUTING
	 */
	public ProjectStatus() {
		super.myStatus = status.EXECUTING;
	}
}
