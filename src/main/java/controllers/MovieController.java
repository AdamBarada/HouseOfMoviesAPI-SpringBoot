package controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import assemblers.MovieAssembler;
import dto.movie.MovieAddDto;
import entities.Movie;
import exceptions.MovieNotFoundException;
import services.movie.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private MovieAssembler movieAssembler;

	@GetMapping
	public CollectionModel<EntityModel<Movie>> all() {
		List<EntityModel<Movie>> movies = movieService.findAll().stream()
				.map(movieAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(movies,
				linkTo(methodOn(MovieController.class).all()).withSelfRel());

	}
	
	@GetMapping("/{id}")
	public EntityModel<Movie> one(@PathVariable Long id) {
		try {
			Movie movie = movieService.findById(id);
			return movieAssembler.toModel(movie);
		}catch(MovieNotFoundException e) {
			return null;		// must return the exception message // to fix
		}
	}
	
	// the below part is only for admin
	
	@PostMapping
	public ResponseEntity<EntityModel<Movie>> addMovie(@RequestBody MovieAddDto newMovie){
		EntityModel<Movie> entityModel = movieAssembler.toModel(movieService.save(newMovie));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Movie>> updateMovie(@RequestBody MovieAddDto movieDto,
			@PathVariable Long id) {
		EntityModel<Movie> entityModel = movieAssembler.toModel(movieService.update(id, movieDto));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable Long id){
		movieService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
