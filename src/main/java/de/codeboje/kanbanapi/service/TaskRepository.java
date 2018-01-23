package de.codeboje.kanbanapi.service;

import org.springframework.data.repository.CrudRepository;

import de.codeboje.kanbanapi.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long>{

}
