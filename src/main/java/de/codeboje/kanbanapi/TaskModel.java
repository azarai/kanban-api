package de.codeboje.kanbanapi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.codeboje.kanbanapi.model.Lane;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TaskModel {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(length=2048)
    private String content;
	
	@Column(length=10)
    private String category;
	
	@Column(length=10)
	@Enumerated(EnumType.STRING)
    private Lane lane;
	
	
}
