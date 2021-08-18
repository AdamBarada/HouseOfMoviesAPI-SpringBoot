package controllers.handleRequests.admin;

import java.io.IOException;
import java.util.ArrayList;
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

import dto.movie.MovieAddDto;
import dto.movie.MoviePerCategory;
import entities.Movie;
import entities.Status;
import exceptions.category.CategoryNotFoundException;
import exceptions.movie.ImageException;
import exceptions.movie.MovieNotFoundException;
import services.movie.MovieService;
import utils.FileUploadUtil;

@Controller
@CrossOrigin
@Secured({"ROLE_ADMIN"})
@RequestMapping("/admin/request/movies")
@ResponseBody
public class MovieControllerAdmin {

	@Autowired
	private MovieService movieService;
	
	private int numberOfTrendingMovies = 10;

	
	@GetMapping
	public List<Movie> all(){
		return movieService.findAll();
	}
	
	@GetMapping("/{id}")
	public Movie one(@PathVariable Long id) {
		try {
			Movie movie = movieService.findById(id);
			return movie;
		} catch(MovieNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PostMapping("/categories-filter")
	public List<Movie> moviesByCategory(@RequestBody List<Long> ids) {
		try {
			return movieService.findByCategory(ids);
		}catch(CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PostMapping
	public Movie addMovie(@RequestBody MovieAddDto movieDto) {
		Movie movie = movieService.save(movieDto);
		if(movieDto.getImage()=="" && movieDto.getLandscape()=="") return movie;
		try {
			if(movieDto.getImage()!="") {
				movie = FileUploadUtil.saveFile(movieDto.getImage(), movie, "image");
				movie = movieService.addImage(movie.getId(), movie.getImage());
			}
			if(movieDto.getLandscape()!="") {
				movie = FileUploadUtil.saveFile(movieDto.getLandscape(), movie, "landscape");
				movie = movieService.addLandscape(movie.getId(), movie.getLandscape());
			}
			System.out.println(movie);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), e);
		} catch (ImageException e) {
			throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), e);
		} catch(MovieNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
		return movie;
	}
	
	@PutMapping("/{id}")
	public Movie updateMovie(@PathVariable Long id,@RequestBody MovieAddDto movieDto) {
		Movie movie = movieService.update(id, movieDto);
		if(movieDto.getImage()=="" && movieDto.getLandscape()=="") return movie;
		try {
			if(movieDto.getImage()!="") {
				movie = FileUploadUtil.saveFile(movieDto.getImage(), movie, "image");
				movie = movieService.addImage(movie.getId(), movie.getImage());
			}
			if(movieDto.getLandscape()!="") {
				movie = FileUploadUtil.saveFile(movieDto.getLandscape(), movie, "landscape");
				movie = movieService.addLandscape(movie.getId(), movie.getLandscape());
			}
			System.out.println(movie);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), e);
		} catch (ImageException e) {
			throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), e);
		} catch(MovieNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
		return movie;
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
	
	@DeleteMapping("/{id}")
	public void deleteMovie(@PathVariable Long id) {
		movieService.deleteById(id);
	}
	
	@GetMapping("/per-categories")
	public List<MoviePerCategory> moviesPerCategory(){
		return movieService.nbMoviesPerCategories();
	}
}
