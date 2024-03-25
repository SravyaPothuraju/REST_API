package com.example.task.testcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.task.controller.TaskController;
import com.example.task.exception.TaskExistException;
import com.example.task.exception.TaskNotFoundException;
import com.example.task.exception.TaskNullException;
import com.example.task.model.Tasks;
import com.example.task.service.TaskService;


public class TaskControllerTest {

	@Mock
	private TaskService taskService;

	@InjectMocks
	private TaskController taskController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreate() {

		Tasks task = new Tasks();
		
		when(taskService.createtask(task)).thenReturn(task);

		ResponseEntity<?> response = taskController.create(task);
		
        assertEquals(response.getBody(), task);
        
		assertEquals(response.getStatusCode(),HttpStatus.OK);

	}

	@Test
	public void testCreate_TaskExistException() {

		Tasks task = new Tasks();
		
		when(taskService.createtask(task)).thenThrow(new TaskExistException("Task already exists"));

		ResponseEntity<?> response = taskController.create(task);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testGetAll() {

		List<Tasks> tasks = new ArrayList<>();
		
		when(taskService.getAllTask()).thenReturn(tasks);

		ResponseEntity<?> response = taskController.getAll();

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetAll_TaskNullException() {

		when(taskService.getAllTask()).thenThrow(new TaskNullException("Tasks list is null"));

		ResponseEntity<?> response = taskController.getAll();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testGetById() {

		int id = 1;
		
		Optional<Tasks> task = Optional.of(new Tasks());
		
		when(taskService.getTaskById(id)).thenReturn(task);

		ResponseEntity<?> response = taskController.getbyid(id);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetById_TaskNotFoundException() {

		int id = 1;
		when(taskService.getTaskById(id)).thenThrow(new TaskNotFoundException("Task not found"));

		ResponseEntity<?> response = taskController.getbyid(id);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testGetByStatus() {

		String status = "pending";
		List<Tasks> tasks = new ArrayList<>();
		when(taskService.getTaskByStatus(status)).thenReturn(tasks);

		ResponseEntity<?> response = taskController.getByStatus(status);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetByStatus_TaskNotFoundException() {

		String status = "completed";
		when(taskService.getTaskByStatus(status)).thenThrow(new TaskNotFoundException("Tasks not found"));

		ResponseEntity<?> response = taskController.getByStatus(status);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testUpdate() {

		long id = 1;
		Tasks task = new Tasks();
		when(taskService.update(task)).thenReturn(task);

		ResponseEntity<?> response = taskController.putMethodName(id, task);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdate_TaskNotFoundException() {

		long id = 1;
		Tasks task = new Tasks();
		when(taskService.update(task)).thenThrow(new TaskNotFoundException("Task not found"));

		ResponseEntity<?> response = taskController.putMethodName(id, task);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDeleteById() {

		int id = 1;
		when(taskService.delete(id)).thenReturn("Task deleted successfully");

		ResponseEntity<?> response = taskController.deleteById(id);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteById_TaskNotFoundException() {

		int id = 1;
		
		when(taskService.delete(id)).thenThrow(new TaskNotFoundException("Task not found"));

		ResponseEntity<?> response = taskController.deleteById(id);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	

	@Test
    public void testViewTasksPaged() {
		
        List<Tasks> mockedTasks = new ArrayList<>();
        
        mockedTasks.add(new Tasks(1, "Description 1", "Status 1", "AssignedTo 1"));
        mockedTasks.add(new Tasks(2, "Description 2", "Status 2", "AssignedTo 2"));
        mockedTasks.add(new Tasks(3, "Description 3", "Status 3", "AssignedTo 3"));
        
        Page<Tasks> mockedPage = new PageImpl<>(mockedTasks);
        
        when(taskService.viewTasksPaged(anyInt(), anyInt())).thenReturn(mockedPage);
        
        ResponseEntity<List<Tasks>> response = taskController.viewTasksPaged(0, 3);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        assertEquals(3, response.getBody().size());
    }
	
	

}
