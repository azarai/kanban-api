package de.codeboje.kanbanapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import de.codeboje.kanbanapi.auth.UserPrincipal;
import de.codeboje.kanbanapi.model.Board;
import de.codeboje.kanbanapi.model.Task;

@Service
public class TaskService {

	@Autowired
	private TaskRepository repository;

	@Autowired
	private BoardRepository boardRepository;

	public List<Board> getBaords() {
		return boardRepository.findAllByUser(getLoggedInUserId());
	}

	public Board createBoard(Board board) {
		board.setId(null);

		board.setUser(getLoggedInUserId());
		return boardRepository.save(board);
	}

	public List<Task> getTasks(Board board) {
		return repository.findAllByBoard(board);
	}

	public Task createTask(Task task, Board board) {
		task.setId(null);
		task.setBoard(board);
		return repository.save(task);
	}

	public Task updateTask(Task taskDb, Task taskIn) {
		taskDb.setCategory(taskIn.getCategory());
		taskDb.setContent(taskIn.getContent());
		taskDb.setLane(taskIn.getLane());
		return repository.save(taskDb);
	}

	public void deleteTask(Task task) {
		repository.delete(task);
	}

	private Long getLoggedInUserId() {
		return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
	}
}
