package de.codeboje.kanbanapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Board {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length=100)
    private String name;
	
	@Column()
	private Long user;
	
	@OneToMany(mappedBy="board", fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Task> tasks = new ArrayList<Task>();
}
