package de.codeboje.kanbanapi.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<String> registerUser(@RequestBody User userIn) {
		if (StringUtils.isEmpty(userIn.getUsername()) || StringUtils.isEmpty(userIn.getPassword())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User userDb = new User();
		userDb.setUsername(userIn.getUsername());
		userDb.setPassword(passwordEncoder.encode(userIn.getPassword()));
		
		userDb = userRepository.save(userDb);
		return new ResponseEntity<String>(userDb.getId().toString(), HttpStatus.CREATED);
	}
}
