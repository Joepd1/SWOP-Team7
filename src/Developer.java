package src;

import java.util.List;
import java.util.ArrayList;

/**
 * @author vincent
 */
public class Developer extends User {

	/**
	 * @contains the list of tasks this developer has worked/is working in.
	 */
	private List<Task> tasks;
	
	/**
	 * The constructor instantiates the super class (User) with the correct name.
	 * @param name is the name of the user.
	 */
	public Developer(String name) {
		super.name = name;
	}
	
	/**
	 * This function enables a developer to change the status of a task he is executing.
	 *  A task that is being executed can only be put in failed or finished state; and one
	 *  that isn't being executed can't be altered.
	 *  
	 * @pre The developer (this) that calls this function must be working on the task 
	 * 	he want's to change the status of. 
	 * @param task is the specific task the developer want's to change the status of.
	 * @param status is the specific status the developer want's to change the status
	 * 	of the task into
	 */
	public void updateTaskStatus(Task task, Status status) {
		if (!this.tasks.contains(task) || !super.loggedIn) {
			throw new IllegalArgumentException();
		}
		else if (task.executingTask()) {
			if (status.isFailed()) {
				task.failTask();
			}
			else if (status.isFinished()) {
				task.finishTask();
			}
		}
		else if (task.pendingTask()) {
			if (status.isExecuting()) {
				task.startTask(this);
				this.tasks.add(task);
			}
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Getter that returns all the tasks this developer is executing and the tasks that are pending (waiting to be chosen
	 * 	by a developer to get executed).
	 */
	public List<Task> getMyTasks() {
		List<Task> availableTasks = new ArrayList<Task>();
		if (this.tasks.size() == 0) {
			return availableTasks;
		}
		else {
			for (Task task : this.tasks) {
				if (task.executingTask() || task.pendingTask()) {
					availableTasks.add(task);
				}
			}
			return availableTasks;
		}
	}
	
	/**
	 * Getter that returns the tasks this developer is associated with.
	 */
	public List<Task> getAllTasks() {
		return this.tasks;
	}
}
