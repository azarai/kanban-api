package de.codeboje.kanbanapi.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.kanbanapi.ApiError;
import de.codeboje.kanbanapi.MessageCode;
import de.codeboje.kanbanapi.model.Board;
import de.codeboje.kanbanapi.model.Confirmation;
import de.codeboje.kanbanapi.service.TaskService;
import io.swagger.annotations.ApiParam;

@RestController
public class BoardController {

	@Autowired
	private TaskService service;

	@GetMapping("/boards")
	public List<Board> getBoards() {
		return service.getBaords();
	}

	@PostMapping("/boards")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Board createBoard(@Valid @RequestBody Board board) {
		return service.createBoard(board);
	}

	@PutMapping("/board/{id}")
	@PreAuthorize("hasPermission(#board, 'write')")
	public ResponseEntity<?> updateBoard(@PathVariable("id") Board board, @Valid @RequestBody Board boardIn) {
		if (board == null) {
			return new ResponseEntity<ApiError>(new ApiError(MessageCode.BOARD_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Board>(service.updateBoard(board, boardIn), HttpStatus.OK);
	}

	@DeleteMapping("/board/{id}")
	@PreAuthorize("hasPermission(#board, 'write')")
	public void deleteBoard(@PathVariable("id") Board board, @Valid @RequestBody @ApiParam(value="name of the board")  Confirmation confirmation) {
		if (board != null) {
			if (board.getName().equals(confirmation.getConfirm())) {
				service.deleteBoard(board);
			}
		}
	}
}
