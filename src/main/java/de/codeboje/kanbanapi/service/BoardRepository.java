package de.codeboje.kanbanapi.service;

import org.springframework.data.repository.CrudRepository;

import de.codeboje.kanbanapi.model.Board;

public interface BoardRepository extends CrudRepository<Board, Long>{

}
