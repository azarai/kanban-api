package de.codeboje.kanbanapi.auth;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.kanbanapi.ApiError;
import de.codeboje.kanbanapi.MessageCode;
import de.codeboje.kanbanapi.model.Board;
import de.codeboje.kanbanapi.model.Confirmation;
import de.codeboje.kanbanapi.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TaskService service;

	@PostMapping("/login")
	public void login() {
		// spring session automatically returns the session token in the header
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser( @Validated(UserCreateValidationGroup.class) @RequestBody  User userIn) {
		if (userRepository.findByUsername(userIn.getUsername()) == null) {
			User userDb = new User();
			userDb.setUsername(userIn.getUsername());
			userDb.setPassword(passwordEncoder.encode(userIn.getPassword()));

			userDb = userRepository.save(userDb);
			return new ResponseEntity<String>(userDb.getId().toString(), HttpStatus.CREATED);
		} else {
			
			return new ResponseEntity<ApiError>(new ApiError(MessageCode.USER_ALREADY_EXISTS), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@DeleteMapping("/unregister")
	@Transactional
	public void deleteUser( @Valid @RequestBody @ApiParam(value="Password of user") Confirmation confirmation) {
		
		final Optional<User> userO = userRepository.findById(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
		if (userO.isPresent()) {
			final User user = userO.get();
			if (user.getPassword().equals(passwordEncoder.encode(confirmation.getConfirm()))) {
				service.deleteAllBoards(user);
				userRepository.delete(user);
			}
		}
	}
}
