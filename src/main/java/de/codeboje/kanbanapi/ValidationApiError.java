package de.codeboje.kanbanapi;

import java.util.Collection;

import lombok.Data;

@Data
public class ValidationApiError extends ApiError{

	private Collection<ValidationError> validationErrors;
}
