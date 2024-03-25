package com.example.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import com.example.task.exception.TaskExistException;
import com.example.task.exception.TaskNotFoundException;
import com.example.task.exception.TaskNullException;
import com.example.task.model.Tasks;
import com.example.task.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;

	public Tasks createtask(Tasks tasks) {
		if (taskRepository.existsById(tasks.getTaskId())) {
			throw new TaskExistException("Task with ID " + tasks.getTaskId() + "  exist");
		} else {
			return taskRepository.save(tasks);
		}

	}

	public List<Tasks> getAllTask() {
		List<Tasks> tasks = taskRepository.findAll();
		if(tasks.isEmpty()) {
			throw new TaskNullException("No Tasks Exists ");
		}
		else {
		return taskRepository.findAll();
		}
	}

	public Optional<Tasks> getTaskById(int id) {
		Optional<Tasks> task = taskRepository.findById(id);
		if (task.isEmpty()) {
			throw new TaskNotFoundException("task not found");

		} else {
			return task;

		}
	}

	public Tasks update(Tasks task) {
		if (taskRepository.existsById(task.getTaskId())) {

			return taskRepository.save(task);

		} else {
			throw new TaskNotFoundException("Task not found");

		}

	}

	public String delete(int id) {
		if (taskRepository.existsById(id)) {
			taskRepository.deleteById(id);
			return "Deleted Successfully";

		} else {
			throw new TaskNotFoundException("task not found");
		}

	}

		public List<Tasks> getTaskByStatus(String status) {
		    List<Tasks> tasks = taskRepository.getTasksByStatus(status);
		    if (!tasks.isEmpty()) {
		        return tasks;
		    } else {
		        throw new TaskNotFoundException("Tasks with status '" + status + "' not found");
		    }
		}

		public Page<Tasks> viewTasksPaged(int page, int pageSize) {
		    Pageable pageable = PageRequest.of(page, pageSize);
		    return taskRepository.findAll(pageable);
		}
		
		

	}

