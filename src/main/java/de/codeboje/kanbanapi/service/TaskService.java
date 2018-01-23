package de.codeboje.kanbanapi.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.codeboje.kanbanapi.model.Board;
import de.codeboje.kanbanapi.model.Task;

@Service
public class TaskService {

	@Autowired
	private TaskRepository repository;

	@Autowired
	private BoardRepository boardRepository;

	public List<Board> getBaords() {
		List<Board> boards = new LinkedList<Board>();
		Iterator<Board> iter = boardRepository.findAll().iterator();
		while (iter.hasNext()) {
			boards.add(iter.next());
		}
		return boards;
	}

	public Board createBoard(Board board) {
		board.setId(null);
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
		task.setId(null);
		return repository.save(task);
	}

	public Task updateTask(Long taskId, Task task) {
		task.setId(taskId);
		return repository.save(task);
	}

	public void deleteTask(Long taskId) {
		repository.deleteById(taskId);
	}
}
