package de.codeboje.kanbanapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class KanbanControllerAdvice  {

	private Logger LOGGER = LoggerFactory.getLogger(KanbanControllerAdvice.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> defaultException(Exception e) {
		final InternalApiError msg = new InternalApiError();
		msg.setMsgCode(MessageCode.TECHNICAL_ERROR);
		LOGGER.error("Server error with uniqueErrorId={}", msg.getUniqueErrorId(), e);
		return new ResponseEntity<ApiError>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> handleException(AccessDeniedException exception) {
		return new ResponseEntity<ApiError>(new ApiError(MessageCode.ACCESS_DENIED), HttpStatus.FORBIDDEN);
	}
//	@ExceptionHandler(AuthenticationException.class)
//	public ResponseEntity<ApiError> handleException(AuthenticationException exception) {
//		return new ResponseEntity<ApiError>(new ApiError(MessageCode.ACCESS_DENIED), HttpStatus.UNAUTHORIZED);
//	}
	

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> handleException(HttpMessageNotReadableException exception) {
		return new ResponseEntity<ApiError>(new ApiError(MessageCode.REQUEST_PARSING_ERROR), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException exception) {

		final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		Map<String, ValidationError> tempErrors = new HashMap<String, ValidationError>();
		for (FieldError fieldError : fieldErrors) {
			if (!tempErrors.containsKey(fieldError.getField())) {
				tempErrors.put(fieldError.getField(), new ValidationError(fieldError.getField()));
			}

			tempErrors.get(fieldError.getField()).getMsg().add(fieldError.getDefaultMessage());
		}

		final ValidationApiError msg = new ValidationApiError();
		msg.setMsgCode(MessageCode.USER_INVALID);
		msg.setValidationErrors(tempErrors.values());

		return new ResponseEntity<ApiError>(msg, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
