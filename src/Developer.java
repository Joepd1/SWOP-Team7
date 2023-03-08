package src;

import java.util.List;

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
		if (task.getStatus().makeString() == "executing" && !task.getStatus().isAvailable()) {
			this.tasks.add(task);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * This function enables a developer to change the status of a task he is performing.
	 * 	A task that is waiting for another task to be completed fist can't be altered;
	 * 	A task that is waiting to be executed can only be put in executing state;
	 *  A task that is being executed can only be put in failed or finished state.
	 *  
	 * @pre The developer (this) that calls this function must be working on the task 
	 * 	he want's to change the status of. 
	 * @param task is the specific task the developer want's to change the status of.
	 * @param status is the specific status the developer want's to change the status
	 * 	of the task into
	 */
	public void setTaskStatus(Task task, TaskStatus status) {
		if (!this.tasks.contains(task)) {
			throw new IllegalArgumentException();
		}
		//Task is waiting for a depending task to be finished
		if (!task.getStatus().isAvailable() && (task.getStatus().makeString() == "waiting")) {  
			throw new IllegalArgumentException();
		}
		//Task is waiting to be executed -> exec
		else if (task.getStatus().isAvailable() && (task.getStatus().makeString() == "waiting")) {
			if (status.makeString() != "executing" ) {
				throw new IllegalArgumentException();
			}
			else {
				if (!task.startTask(this)) {
					throw new IllegalArgumentException();
				}
			}
		}
		//Task is executing -> fail/finish
		else if (task.getStatus().makeString() == "executing") {
			if (status.makeString() == "failed") {
				task.failTask();
			}
			else if (status.isFinished()) {
				task.finishTask();
			}
			else {
				throw new IllegalArgumentException();
			}
		}
		//Task is failed/finished -> error
		else {
			throw new IllegalArgumentException();
		}
	}
}
