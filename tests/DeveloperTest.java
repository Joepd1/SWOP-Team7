package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.time.*;   

import org.junit.jupiter.api.Test;

import src.Developer;
import src.Project;
import src.Status;
import src.Task;
import src.TaskStatus;
import src.User;


class DeveloperTest {
	
	User developer1 = new Developer ("developer1");
	List<Task> tasksDeveloper1 = new ArrayList<>();
	
	Duration halfHour = Duration.ofMinutes(30);
	Duration oneHour = Duration.ofHours(1);
	Duration duration1 = halfHour.plus(oneHour);
	
	Project project1 = new Project("project1", "project1Desc", duration1 );
	
	List<Task> tasksimWaitingFor1 = new ArrayList<>();
	List<Task> tasksimWaitingFor2 = new ArrayList<>();
	
	Task task1 = new Task(project1, "task1Desc", 2, 0.40, tasksimWaitingFor1);
	Task task2 = new Task(project1, "task2Desc", 3, 0.20, tasksimWaitingFor2);
	
	
	@Test
	void updateTaskStatusTest() {
		Status status1 = new Status() {
        { this.myStatus = status.EXECUTING; }};
        
        // test1 task1 zit niet in de taken van developer1 dus kan die taak niet van status veranderen
        //developer1.updateTaskStatus(task1, status1);
        try {
        	developer1.updateTaskStatus(task1, status1);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {}
        
	}
}
        
        
