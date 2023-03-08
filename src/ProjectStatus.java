package src;

import java.util.List;

/**
 * 
 * @author vincent
 */
public class ProjectStatus extends Status {
	
	/**
	 * Constructs the status of the associated project and initializes it as executing
	 */
	public ProjectStatus() {
		super.status = "executing";
	}
	
	/**
	 * This function checks if all the tasks in the associated project are completed.
	 *  Returns true if the latter is the case; false if at least one of those tasks are unfinished.
	 */
	public boolean projectFinished(Project project) {
		for (Task t : project.getTasks()) {
			if (!t.finishedTask()) {
				return false;
			}
		}
		return true;
	}
}
