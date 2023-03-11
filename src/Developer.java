package src;

import java.util.List;
import java.util.ArrayList;

/**
 * Each instance of this class represents a developer.
 * @invar | getTasks() != null
 * @author vincent
 */
public class Developer extends User {

	/**
	 * @invar | tasks != null
	 */
	private List<Task> tasks;
	
	/**
	 * Initializes this object so that it's name is the given name and it's list of tasks is created.
	 * @post | getTasks().size() == 0
	 */
	public Developer(String name) {
		super.name = name;
		this.tasks = new ArrayList<Task>();
	}
	
	/**
	 * This function enables a developer to change the status of a task he is executing.
	 *  A task that is being executed can only be put in failed or finished state; and one
	 *  that isn't being executed can't be altered.
	 * @throws IllegalArgumentException | !this.tasks.contains(task) || !super.loggedIn
	 * @throws IllegalArgumentException | status.isFailed() || status.isFinished() || status.isWaiting()
	 * @throws IllegalArgumentException | !task.executingTask() || !task.pendingTask()
	 * @post | if (old{task}.executingTask()) then (new{task}.failedTask || new{task}.finishedTask)
	 * @post | if (old{task}.pendingTask()) then new{task}.executingTask()
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
		for (Task task : this.tasks) {
			if (task.executingTask() || task.pendingTask()) {
				availableTasks.add(task);
			}
		}
		return availableTasks;
	}
	
	/**
	 * @basic
	 */
	public List<Task> getAllTasks() {
		return this.tasks;
	}
}
