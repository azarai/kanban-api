package de.codeboje.kanbanapi;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskModel, Long>{

}
