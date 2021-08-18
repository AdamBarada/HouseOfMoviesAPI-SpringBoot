package controllers.handleRequests.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import dto.user.TotalNumberUsers;
import entities.User;
import exceptions.user.UserNotFoundException;
import services.user.UserService;

@Controller
@CrossOrigin
@Secured({"ROLE_ADMIN"})
@RequestMapping("/admin/request/users")
@ResponseBody
public class UserControllerAdmin {
	
	private int nbOfLoyalCustomers = 3;

	@Autowired
    private UserService userService;
	
	@GetMapping("/all")
	public List<User> all(){
		return userService.findAll();
	}
	
	@GetMapping
	public List<User> users(){		// without admins
		return userService.onlyUsers();
	}
	
	@GetMapping("/number-users")
	public TotalNumberUsers total() {	// return number
		return new TotalNumberUsers(userService.onlyUsers().size());
	}
	
	@GetMapping("/loyal-clients")
	public List<User> loyal(){
		List<User> users =  userService.usersWithMostReservations();
		return users.subList(0, nbOfLoyalCustomers);
	}
	
	@GetMapping("/{id}")
	public User one(@PathVariable Long id) {
		try {
			return userService.findById(id);
		}catch(UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}
