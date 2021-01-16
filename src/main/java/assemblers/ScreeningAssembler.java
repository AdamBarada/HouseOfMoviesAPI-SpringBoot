package assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import controllers.ScreeningController;
import entities.Screening;

@Component
public class ScreeningAssembler implements RepresentationModelAssembler<Screening, EntityModel<Screening>>{

	@Override
	public EntityModel<Screening> toModel(Screening entity) {
		return EntityModel.of(entity,
				linkTo(methodOn(ScreeningController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(ScreeningController.class).all()).withRel("screenings"));
	}

}
