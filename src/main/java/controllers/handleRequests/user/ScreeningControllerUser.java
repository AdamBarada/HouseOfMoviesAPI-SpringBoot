package controllers.handleRequests.user;

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

import entities.Screening;
import exceptions.screening.ScreeningNotFoundException;
import services.screening.ScreeningService;

@Controller
@CrossOrigin
@Secured({"ROLE_USER"})
@RequestMapping("/user/request/screening")
@ResponseBody
public class ScreeningControllerUser {

	@Autowired 
	private ScreeningService screeningService;
	
	@GetMapping("/{id}")
	public Screening roomForScreening(@PathVariable Long id) {
		try {
			return screeningService.screeningWithSeats(id);
		}catch(ScreeningNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
}
