package controllers.handleRequests.permitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import dto.user.UserRegistrationDto;
import exceptions.user.UserAlreadyExistsException;
import services.user.UserService;

@Controller
@CrossOrigin
@RequestMapping("/public/request/sign-up")
@ResponseBody
public class UserRegistrationController {		// sign-up

	@Autowired
	 private UserService userService;
	 
	 @PostMapping
	 public Object saveUser(@RequestBody UserRegistrationDto user){
		 try {
			 return userService.save(user);
		 }catch(UserAlreadyExistsException e) {
			 throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		 }
		 
	 }
	 
}
