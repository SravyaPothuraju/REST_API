
package com.example.task.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.exception.TaskExistException;
import com.example.task.exception.TaskNotFoundException;
import com.example.task.exception.TaskNullException;
import com.example.task.model.Tasks;
import com.example.task.service.TaskService;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
	

	@Autowired
	TaskService taskService;

	
	@PostMapping("/addtask")
	public ResponseEntity<?> create(@RequestBody @Valid Tasks task){

		try {
			Tasks createdtask = taskService.createtask(task);
            return new ResponseEntity<>(createdtask, HttpStatus.OK);	
		}
		catch(TaskExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/alltasks")
	public ResponseEntity<?> getAll() {
		try {
		List<Tasks> allTask = taskService.getAllTask();
		return ResponseEntity.ok(allTask);
		}
		catch(TaskNullException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/paged/{offset}/{pagesize}")
	public ResponseEntity<List<Tasks>> viewTasksPaged(@PathVariable int offset,@PathVariable int pagesize){
		Page<Tasks> tasks = taskService.viewTasksPaged(offset, pagesize);
		 List<Tasks> taskspaged = tasks.getContent();
		return ResponseEntity.ok().body(taskspaged);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id) {
		try {

			Optional<Tasks> task = taskService.getTaskById(id);
			return new ResponseEntity<>(task, HttpStatus.OK);
		} catch (TaskNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<?> getByStatus(@PathVariable String status){
		try {
			List<Tasks> taskByStatus = taskService.getTaskByStatus(status);
			return  ResponseEntity.ok(taskByStatus);
		}
		catch(TaskNotFoundException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
	}
	
	

	@PutMapping("update/{id}")
	public ResponseEntity<?> putMethodName(@PathVariable long id, @RequestBody Tasks task) {
		try {
			Tasks updatedTask = taskService.update(task);
			return new ResponseEntity<>(updatedTask, HttpStatus.OK);
		} catch (TaskNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id) {
		try {
			String delete = taskService.delete(id);
			return new ResponseEntity<>(delete, HttpStatus.OK);

		} catch (TaskNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
