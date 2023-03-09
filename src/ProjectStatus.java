package src;

import java.util.List;

/**
 * 
 * @author vincent
 */
public class ProjectStatus extends Status {
	
	/**
	 * @contains the project this status is representing.
	 */
	private Project project;
	
	/**
	 * Constructs the status of the associated project and initializes it as executing
	 */
	public ProjectStatus(Project project) {
		super.myStatus = status.EXECUTING;
		this.project = project;
	}
	
	/**
	 * This function checks if all the tasks in the associated project are completed.
	 *  Returns true if the latter is the case; false if at least one of those tasks are unfinished.
	 */
	public boolean projectFinished() {
		for (Task t : this.project.getTasks()) {
			if (!t.finishedTask()) {
				return false;
			}
		}
		return true;
	}
}
