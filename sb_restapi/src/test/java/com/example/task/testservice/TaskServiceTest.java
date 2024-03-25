package com.example.task.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.task.exception.TaskExistException;
import com.example.task.exception.TaskNotFoundException;
import com.example.task.exception.TaskNullException;
import com.example.task.model.Tasks;
import com.example.task.repository.TaskRepository;
import com.example.task.service.TaskService;

@SpringBootTest
public class TaskServiceTest {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskService taskService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateTask_Success() {

		Tasks task = new Tasks();
		
		task.setTaskId(1);
		
		when(taskRepository.existsById(task.getTaskId())).thenReturn(false);
		
		when(taskRepository.save(task)).thenReturn(task);

		Tasks createdTask = taskService.createtask(task);

		assertEquals(task, createdTask);
	}

	@Test
	public void testCreateTask_TaskExistException() {

		Tasks task = new Tasks();
		
		task.setTaskId(1);
		
		when(taskRepository.existsById(task.getTaskId())).thenReturn(true);

		assertThrows(TaskExistException.class, () -> taskService.createtask(task));
	}

	@Test
	public void testGetAllTask_Success() {

		List<Tasks> tasks = new ArrayList<>();
		
		tasks.add(new Tasks());
		
		when(taskRepository.findAll()).thenReturn(tasks);

		List<Tasks> result = taskService.getAllTask();

		assertEquals(tasks, result);
	}

	@Test
	public void testGetAllTask_TaskNullException() {

		when(taskRepository.findAll()).thenReturn(new ArrayList<>());

		assertThrows(TaskNullException.class, () -> taskService.getAllTask());
	}

	@Test
	public void testGetTaskByStatus_Success() {

		List<Tasks> tasks = new ArrayList<>();
		tasks.add(new Tasks());
		when(taskRepository.getTasksByStatus("pending")).thenReturn(tasks);

		List<Tasks> result = taskService.getTaskByStatus("pending");

		assertEquals(tasks, result);
	}

	@Test
	public void testGetTaskByStatus_TaskNotFoundException() {

		when(taskRepository.getTasksByStatus("completed")).thenReturn(new ArrayList<>());

		assertThrows(TaskNotFoundException.class, () -> taskService.getTaskByStatus("completed"));
	}

	@Test
	public void testGetTaskById_Success() throws TaskNotFoundException {

		int id = 1;
		Tasks task = new Tasks();
		Optional<Tasks> optionalTask = Optional.of(task);
		when(taskRepository.findById(id)).thenReturn(optionalTask);

		Optional<Tasks> result = taskService.getTaskById(id);

		assertEquals(optionalTask, result);
	}

	@Test
	public void testGetTaskById_TaskNotFoundException() {

		int id = 1;
		when(taskRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(id));
	}

	@Test
	public void testUpdate_Success() throws TaskNotFoundException {

		Tasks task = new Tasks();
		when(taskRepository.existsById(task.getTaskId())).thenReturn(true);
		when(taskRepository.save(task)).thenReturn(task);

		Tasks result = taskService.update(task);

		assertEquals(task, result);
	}

	@Test
	public void testUpdate_TaskNotFoundException() {

		Tasks task = new Tasks();
		when(taskRepository.existsById(task.getTaskId())).thenReturn(false);

		assertThrows(TaskNotFoundException.class, () -> taskService.update(task));
	}

	@Test
	public void testDelete_Success() throws TaskNotFoundException {

		int id = 1;
		when(taskRepository.existsById(id)).thenReturn(true);

		String result = taskService.delete(id);

		assertEquals("Deleted Successfully", result);
	}

	@Test
	public void testDelete_TaskNotFoundException() {

		int id = 1;
		when(taskRepository.existsById(id)).thenReturn(false);

		assertThrows(TaskNotFoundException.class, () -> taskService.delete(id));
	}
	
	
	 @Test
	    public void testViewTasksPaged() {
	        List<Tasks> mockedTasks = new ArrayList<>();
	        mockedTasks.add(new Tasks(1, "Description 1", "Status 1", "AssignedTo 1"));
	        mockedTasks.add(new Tasks(2, "Description 2", "Status 2", "AssignedTo 2"));
	        mockedTasks.add(new Tasks(3, "Description 3", "Status 3", "AssignedTo 3"));
	        Page<Tasks> mockedPage = new PageImpl<>(mockedTasks);
	        when(taskRepository.findAll(any(Pageable.class))).thenReturn(mockedPage);

	        Page<Tasks> result = taskService.viewTasksPaged(0, 3);

	        assertEquals(3, result.getContent().size());
	    }
}
