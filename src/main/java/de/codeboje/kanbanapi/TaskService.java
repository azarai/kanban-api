package de.codeboje.kanbanapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.codeboje.kanbanapi.model.Task;

@Service
public class TaskService implements TaskIdController, TaskController {

	@Autowired
	private TaskRepository repository;
	
	private Task mapToTask(TaskModel taskModel) {
		final Task task = new Task();
		task.setId(taskModel.getId());
		task.setContent(taskModel.getContent());
		task.setCategory(taskModel.getCategory());
		task.setLane(taskModel.getLane());
		return task;
	}
	
	private TaskModel mapToTaskModel(Task task) {
		final TaskModel taskModel = new TaskModel();
		taskModel.setId(task.getId());
		taskModel.setContent(task.getContent());
		taskModel.setCategory(task.getCategory());
		taskModel.setLane(task.getLane());
		return taskModel;
	}
	
	@Override
	public ResponseEntity<List<Task>> getTasks() {
		final List<Task> r = new LinkedList<Task>();
		
		final Iterator<TaskModel> iter = repository.findAll().iterator();
		
		while( iter.hasNext()) {
			r.add(mapToTask(iter.next()));
		}
		return new ResponseEntity<List<Task>>(r, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Task> createTask(@Valid Task task) {
		TaskModel taskModel = mapToTaskModel(task);
		taskModel.setId(null);
		taskModel = repository.save(taskModel);
		return new ResponseEntity<Task>(mapToTask(taskModel), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Task> getTaskById(Long taskId) {
		
		final Optional<TaskModel> task = repository.findById(taskId);
		
		if (task.isPresent()) {
			return new ResponseEntity<Task>(mapToTask(task.get()), HttpStatus.OK);
		}
		return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Task> updateTaskById(Long taskId, @Valid Task task) {
		TaskModel taskModel = mapToTaskModel(task);
		taskModel.setId(taskId);
		taskModel = repository.save(taskModel);
		return new ResponseEntity<Task>(mapToTask(taskModel), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Boolean> deleteTaskById(Long taskId) {
		repository.deleteById(taskId);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

}
