package de.codeboje.kanbanapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import de.codeboje.kanbanapi.auth.User;
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
		return boardRepository.findAllByUser(((UserPrincipal )SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
	}

	public Board createBoard(Board board) {
		board.setId(null);
		
		board.setUser(((UserPrincipal )SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
		return boardRepository.save(board);
	}

	public List<Task> getTasks(Long boardId) {
		Optional<Board> board = boardRepository.findById(boardId);
		if (board.isPresent()) {
			return board.get().getTasks();
		}

		return null;
	}

	public Task createTask(Task task) {
		Optional<Board> board = boardRepository.findById(task.getBoardId());
		if (board.isPresent()) {
			task.setId(null);
			task.setBoard(board.get());
			return repository.save(task);
		}
		return null;
	}

	public Task updateTask(Long taskId, Task task) {
		task.setId(taskId);
		return repository.save(task);
	}

	public void deleteTask(Long taskId) {
		repository.deleteById(taskId);
	}
}
