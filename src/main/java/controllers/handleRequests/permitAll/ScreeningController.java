package controllers.handleRequests.permitAll;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import entities.Screening;
import entities.Status;
import exceptions.movie.MovieNotFoundException;
import exceptions.screening.NoScreeningForMovieException;
import exceptions.screening.ScreeningNotFoundException;
import services.screening.ScreeningService;

@Controller
@CrossOrigin
@RequestMapping("/public/request/screenings")
@ResponseBody
public class ScreeningController {

	@Autowired
	private ScreeningService screeningService;
	
	@GetMapping
	public List<Screening> all(){
		List<Screening> screenings = screeningService.findAll();
		if(!screenings.isEmpty()) {
			List<Screening> availableScreenings = new ArrayList<Screening>();
			for(Screening s : screenings) {
				if(s.getStatus().equals(Status.PAST)) continue;
				else availableScreenings.add(s);
			}
			return availableScreenings;
		}
		return screenings;
	}
	
	@GetMapping("/{id}")
	public Object one(@PathVariable Long id) {
		try {
			Screening screening = screeningService.findById(id);
			if(screening.getStatus().equals(Status.PAST)) throw new ScreeningNotFoundException(id);
			else return screening;
		}catch(ScreeningNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@GetMapping("/movie/{id}")
	public List<Screening> allForMovie(@PathVariable Long id) {
		try {
			List<Screening> screenings = screeningService.findAllForMovie(id);
			if(!screenings.isEmpty()) {
				List<Screening> availableScreenings = new ArrayList<Screening>();
				for(Screening s : screenings) {
					if(s.getStatus().equals(Status.PAST)) continue;
					else availableScreenings.add(s);
				}
				return availableScreenings;
			}
			return screenings;
		}catch(MovieNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}catch(NoScreeningForMovieException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}
