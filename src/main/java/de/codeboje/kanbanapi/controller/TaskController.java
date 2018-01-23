package de.codeboje.kanbanapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.kanbanapi.model.Task;
import de.codeboje.kanbanapi.service.TaskService;

@RestController
public class TaskController {

	@Autowired
	private TaskService service;

	@GetMapping("/tasks/{boardId}")
	public List<Task> getTasks(@PathVariable("boardId") Long boardId) {
		return service.getTasks(boardId);
	}

	@PostMapping("/tasks")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Task createTask(@Valid Task task) {
		return service.createTask(task);
	}

	@PutMapping("/task/{id}")
	public Task updateTask(@PathVariable("id") Long taskId, @Valid Task task) {
		return service.updateTask(taskId, task);
	}

	@DeleteMapping("/task/{id}")
	public void deleteTask(@PathVariable("id") Long taskId) {
		service.deleteTask(taskId);
	}
}
