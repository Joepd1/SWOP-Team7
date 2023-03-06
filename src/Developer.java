package src;

public class Developer extends User {

	/**
	 * The constructor instantiates the super class (User) with the correct name.
	 * @param name is the name of the user.
	 */
	public Developer(String name) {
		super.name = name;
	}

	//public void setStatus(Task task, Status status) {
	//	if (status.isAvailable() && (status.makeString() != "executing" || status.makeString() != "finished" || status.makeString() != "failed" )) {
	//		throw new IllegalArgumentException();
	//	}
	//	task.setStatus(status);
	//}
}
