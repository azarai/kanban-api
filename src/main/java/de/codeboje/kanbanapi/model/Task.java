package de.codeboje.kanbanapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 2048)
	private String content;

	@Column(length = 10)
	private String category;

	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	private Lane lane;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	@JsonIgnore
	private Board board;
	
	@JsonProperty("board")
	private transient Long boardId;

	@JsonProperty("board")
	public Long getBaordId() {
		return board.getId();
	}
}
