package de.codeboje.kanbanapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.kanbanapi.ApiError;
import de.codeboje.kanbanapi.MessageCode;
import de.codeboje.kanbanapi.model.Board;
import de.codeboje.kanbanapi.model.Task;
import de.codeboje.kanbanapi.service.TaskService;

@RestController
public class TaskController {

	@Autowired
	private TaskService service;

	@GetMapping("/tasks/{boardId}")
	@PreAuthorize("hasPermission(#board, 'read')")
	public ResponseEntity<?> getTasks(@PathVariable("boardId") Board board) {
		if (board == null) {
			return new ResponseEntity<ApiError>(new ApiError(MessageCode.BOARD_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(service.getTasks(board),HttpStatus.OK);
	}

	@PostMapping("/tasks/{boardId}")
	@PreAuthorize("hasPermission(#board, 'write')")
	public ResponseEntity<?> createTask(@Valid @RequestBody Task task, @PathVariable("boardId") Board board) {
		
		if (board == null) {
			return new ResponseEntity<ApiError>(new ApiError(MessageCode.BOARD_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST);
		}
		
		final Task taskDb =  service.createTask(task, board);
		return new ResponseEntity<Task>(taskDb, HttpStatus.CREATED);
	}

	@PutMapping("/task/{id}")
	@PreAuthorize("hasPermission(#task, 'write')")
	public ResponseEntity<?> updateTask(@PathVariable("id") Task task, @Valid @RequestBody Task taskIn) {
		if (task == null) {
			return new ResponseEntity<ApiError>(new ApiError(MessageCode.TASK_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Task>(service.updateTask(task, taskIn), HttpStatus.OK);
	}

	@DeleteMapping("/task/{id}")
	@PreAuthorize("hasPermission(#task, 'write')")
	public void deleteTask(@PathVariable("id") Task task) {
		service.deleteTask(task);
	}
}
