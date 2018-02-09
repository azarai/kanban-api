package de.codeboje.kanbanapi.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Confirmation {

	@NotEmpty
	private String confirm;
}
