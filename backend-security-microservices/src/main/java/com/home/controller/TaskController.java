/**
 * 
 */
package com.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.home.domain.AppUser;
import com.home.domain.Task;
import com.home.repository.TaskRepository;

/**
 * @author ADAM
 *
 */
@RestController
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping("/tasks")
	public List<Task> listTasks()
	{
		return taskRepository.findAll();
	}
	
	@PostMapping("/addtasks")
	public Task save(@RequestBody Task task)
	{
		return taskRepository.save(task);
	}
	
	
	

}
