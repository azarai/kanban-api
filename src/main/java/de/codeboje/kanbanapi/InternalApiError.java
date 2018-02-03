package de.codeboje.kanbanapi;

import java.util.UUID;

import lombok.Data;

@Data
public class InternalApiError extends ApiError {
	private String uniqueErrorId = UUID.randomUUID().toString().replaceAll("-", "");
}
