package src;

import java.util.List;

/**
 * Each instance of this class represents the status of a project.
 * @invar | getStatus() != null
 * @author vincent
 */
public class ProjectStatus extends Status {
	
	/**
	 * Initializes this object so that it can represent the status of the associated project.
	 * @post | this.isExecuting() == true
	 */
	public ProjectStatus() {
		super.myStatus = status.EXECUTING;
	}
}
