package de.codeboje.kanbanapi;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {

	private String field;
	private List<String> msg = new ArrayList<String>();
	
	public ValidationError(String field) {
		super();
		this.field = field;
	}
	
}
