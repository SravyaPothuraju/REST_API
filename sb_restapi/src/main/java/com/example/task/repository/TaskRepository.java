package com.example.task.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task.model.Tasks;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Integer> {
	
	 List<Tasks> getTasksByStatus(String status);

   
}
