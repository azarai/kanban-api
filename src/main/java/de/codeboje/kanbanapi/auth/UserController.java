package de.codeboje.kanbanapi.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.codeboje.kanbanapi.ApiError;
import de.codeboje.kanbanapi.MessageCode;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
}
