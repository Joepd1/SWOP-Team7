package src;

import java.util.List;

import src.Status.status;

/**
 * TODO: (LOGIN & LOGOUT ?)
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
	 * Setter that updates the list of tasks this developer is/ has been working on.
	 * 
	 * @pre The status of the task must be executing & must be unavailable
	 * @param task is the task which this developer has chosen to execute
	 */
	public void addTask(Task task) {
		if (task.pendingTask() && task.startTask(this)) {
			this.tasks.add(task);
		}
		else {
			throw new IllegalArgumentException();
		}
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
	public void setTaskStatus(Task task, status status) {
		if (!this.tasks.contains(task) || !task.executingTask()) {
			throw new IllegalArgumentException();
		}
		else if (status.equals(status.FAILED)) {
			task.failTask();
		}
		else {
			task.finishTask();
		}
	}
	
	/**
	 * Getter that returns the tasks this developer is associated with.
	 */
	public List<Task> getTasks() {
		return this.tasks;
	}
}
