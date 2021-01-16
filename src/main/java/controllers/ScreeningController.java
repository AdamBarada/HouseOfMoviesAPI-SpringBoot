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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import assemblers.ScreeningAssembler;
import dto.screening.ScreeningAddDto;
import entities.Screening;
import exceptions.ScreeningNotFoundException;
import services.screening.ScreeningService;

@RestController
@RequestMapping("/screenings")
public class ScreeningController {

	@Autowired
	private ScreeningAssembler screeningAssembler;
	
	@Autowired
	private ScreeningService screeningService;

	@GetMapping
	public CollectionModel<EntityModel<Screening>> all() {
		List<EntityModel<Screening>> screenings = screeningService.findAll().stream()
				.map(screeningAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(screenings,
				linkTo(methodOn(ScreeningController.class).all()).withSelfRel());
	}
	
	@GetMapping("/{id}")
	public EntityModel<Screening> one(@PathVariable Long id) {
		try {
			Screening screening = screeningService.findById(id);
			return screeningAssembler.toModel(screening);
		} catch(ScreeningNotFoundException e) {	// error msg
			return null;
		}
	}

	@PostMapping
	public ResponseEntity<EntityModel<Screening>> addScreening(@RequestBody ScreeningAddDto newScreening){
		EntityModel<Screening> entityModel = screeningAssembler.toModel(screeningService.save(newScreening));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
		
	}
}
