package controllers.handleRequests.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import dto.user.UserRegistrationDto;
import entities.User;
import exceptions.user.UserAlreadyExistsException;
import exceptions.user.UserNotFoundException;
import services.user.UserService;
import utils.CurrentUsername;

@Controller
@CrossOrigin
@Secured({"ROLE_USER"})
@RequestMapping("/user/request/profile")
@ResponseBody
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public User me() {
		return userService.me(CurrentUsername.currentUsername());
	}
	
	// if the email changes, must login again (generate token) -> request from front-end
	@PutMapping
	public User updateUser(@RequestBody UserRegistrationDto newUser) {
		try {
			return userService.update(CurrentUsername.currentUsername(), newUser);
		}catch(UserAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}catch(UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@DeleteMapping
	public void delete() {
		userService.delete(CurrentUsername.currentUsername());
	}
}
