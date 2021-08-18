package controllers.handleRequests.user;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import dto.reservation.ReservationAddDto;
import entities.Reservation;
import exceptions.reservation.ReservationEntryNotAllowed;
import exceptions.reservation.ReservationNotFoundException;
import exceptions.reservation.SeatsSelectionException;
import services.reservation.ReservationService;
import utils.CurrentUsername;

@Controller
@CrossOrigin
@Secured({"ROLE_USER"})
@RequestMapping("/user/request/reservations")
@ResponseBody
public class ReservationControllerUser {

	@Autowired
	private ReservationService reservationService;
	
	@GetMapping
	public List<Reservation> myReservations(){
		return reservationService.myReservations(CurrentUsername.currentUsername());
	}
	
	@GetMapping("/{id}")
	public Reservation myReservation(@PathVariable Long id) {
		try {
			return reservationService.findMyReservation(id, CurrentUsername.currentUsername());
		}catch(ReservationNotFoundException e ) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}catch(ReservationEntryNotAllowed e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
		}
	}
	
	@PostMapping
	public Reservation addReservation(@RequestBody ReservationAddDto newRes) {
		try {
			return reservationService.save(newRes, CurrentUsername.currentUsername());
		} catch(SeatsSelectionException e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteReservation(@PathVariable Long id) {
		try {
			reservationService.deleteMyReservation(id, CurrentUsername.currentUsername());
		}catch(ReservationEntryNotAllowed e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
		}
	}
}
