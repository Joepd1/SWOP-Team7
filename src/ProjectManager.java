package src;

import java.util.List;

public class ProjectManager extends User {
	
	protected List<Project> projects;

	public ProjectManager(String name) {
		super.name = name;
	}

	
	public void replaceTask (Project project, Task failedTask, Task newTask) {
		if (failedTask.getStatus().getStatus() !=  "failed") { // || !this.projects.contains(failedTask)
			// Throw new error
		}
		else {
			failedTask.replace(newTask);
			project.replace(failedTask, newTask);
			
			// This alternative task replaces the failed task with respect to
			// dependency management or determining the project status (ongoing or finished).
			// The time spent on the failed task is however counted for the total execution time of
			// the project. 
		}
	}
}
