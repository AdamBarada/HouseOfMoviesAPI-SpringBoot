package controllers.handleRequests.permitAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import dto.movie.MovieFilterCategoryDto;
import entities.Movie;
import entities.Status;
import exceptions.category.CategoryNotFoundException;
import exceptions.movie.MovieNotFoundException;
import services.movie.MovieService;

@Controller
@CrossOrigin
@RequestMapping("/public/request/movies")
@ResponseBody
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	private int numberOfTrendingMovies = 5;
	
	@GetMapping
	public List<Movie> all() {
		List<Movie> movies = movieService.findAll();
		if(!movies.isEmpty()) {
			List<Movie> availableMovies = new ArrayList<Movie>();
			for(Movie m : movies) {
				if(m.getStatus().equals(Status.AVAILABLE))
					availableMovies.add(m);
			}
			return availableMovies;
		}
		return movies;
	}
	
	@GetMapping("/{id}")
	public Movie one(@PathVariable Long id) {
		try {
			Movie movie = movieService.findById(id);
			if(movie.getStatus().equals(Status.NOT_AVAILABLE)) throw new MovieNotFoundException(id);
			return movie;
		} catch(MovieNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PostMapping("/categories-filter")
	public List<Movie> moviesByCategory(@RequestBody MovieFilterCategoryDto categories) { // send list of Long : categoriesId
		try {
			List<Movie> movies=  movieService.findByCategory(categories.getCategoriesId());
			if(!movies.isEmpty()) {
				List<Movie> availableMovies = new ArrayList<Movie>();
				for(Movie m : movies) {
					if(m.getStatus().equals(Status.AVAILABLE))
						availableMovies.add(m);
				}
				return availableMovies;
			}
			return movies;
		}catch(CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PostMapping("/search")
	public List<Movie> moviesSearch(@RequestBody Map<String, Object> search) {
		List<Movie> movies=  movieService.search((String) search.get("search"));
		if(!movies.isEmpty()) {
			List<Movie> availableMovies = new ArrayList<Movie>();
			for(Movie m : movies) {
				if(m.getStatus().equals(Status.AVAILABLE))
					availableMovies.add(m);
			}
			return availableMovies;
		}
		return movies;
	}
	
	@GetMapping("/coming-soon")
	public List<Movie> comingSoon(){
		return movieService.getComingSoonMovies();
	}
	
	@GetMapping("/trending")	// most viewed movies (by seats reserved)
	public List<Movie> trending(){
		List<Movie> movies = movieService.getMostViewedMovies();
		if(!movies.isEmpty()) {
			List<Movie> availableMovies = new ArrayList<Movie>();
			for(Movie m : movies) {
				if(m.getStatus().equals(Status.AVAILABLE))
					availableMovies.add(m);
			}
			return availableMovies.subList(0, numberOfTrendingMovies);	// only 5 movies appear
		}
		return movies;
	}
}
