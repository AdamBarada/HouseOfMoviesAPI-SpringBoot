package assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import controllers.MovieController;
import entities.Movie;

@Component
public class MovieAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>>{

	@Override
	public EntityModel<Movie> toModel(Movie entity) {
		return EntityModel.of(entity,
				linkTo(methodOn(MovieController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(MovieController.class).all()).withRel("movies"));
	}


}
