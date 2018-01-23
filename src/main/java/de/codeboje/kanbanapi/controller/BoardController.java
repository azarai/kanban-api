package de.codeboje.kanbanapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.kanbanapi.model.Board;
import de.codeboje.kanbanapi.service.TaskService;

@RestController
public class BoardController {

	@Autowired
	private TaskService service;
	
	@GetMapping("/boards")
	public List<Board> getBoards() {
		return service.getBaords();
	}
	
	@PostMapping("/boards")
	@ResponseStatus(code=HttpStatus.CREATED)
	public Board createBoard( @Valid Board board) {
		return service.createBoard(board);
	}
}
