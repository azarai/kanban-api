package de.codeboje.kanbanapi.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.codeboje.kanbanapi.model.Board;
import de.codeboje.kanbanapi.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long>{

	List<Task> findAllByBoard(Board board);

	void deleteAllByBoard(Board board);

}
