package controllers.handleRequests.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import dto.screening.ScreeningAddDto;
import entities.Screening;
import exceptions.movie.MovieNotFoundException;
import exceptions.screening.NoScreeningForMovieException;
import exceptions.screening.ScreeningNotFoundException;
import exceptions.screening.ScreeningTimeNotValidException;
import services.screening.ScreeningService;

@Controller
@CrossOrigin
@Secured({"ROLE_ADMIN"})
@RequestMapping("/admin/request/screenings")
@ResponseBody
public class ScreeningControllerAdmin {

	@Autowired
	private ScreeningService screeningService;
	
	@GetMapping
	public List<Screening> all(){
		return screeningService.findAll();
	}
	
	@GetMapping("/{id}")
	public Screening one(@PathVariable Long id) {
		try {
			return screeningService.findById(id);
		}catch(ScreeningNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@GetMapping("/movie/{id}")
	public List<Screening> allForMovie(@PathVariable Long id) {
		try {
			return screeningService.findAllForMovie(id);
		}catch(MovieNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}catch(NoScreeningForMovieException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PostMapping
	public Screening addScreening(@RequestBody ScreeningAddDto newScreening) {
		try {
			return screeningService.save(newScreening);
		} catch(ScreeningTimeNotValidException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public Screening updateScreening(@RequestBody ScreeningAddDto newScreening, @PathVariable Long id) {
		try {
			return screeningService.update(id, newScreening);
		} catch(ScreeningTimeNotValidException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{id}")	
	public void deleteScreening(@PathVariable Long id) {
		screeningService.deleteById(id);
	}
}
