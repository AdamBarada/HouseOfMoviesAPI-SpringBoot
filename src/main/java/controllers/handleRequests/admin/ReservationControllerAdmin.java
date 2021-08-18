package controllers.handleRequests.admin;

import java.util.List;

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

import dto.reservation.ReservationPerCategoryDto;
import dto.reservation.ReservationPerDayDto;
import dto.reservation.TotalDto;
import entities.Reservation;
import exceptions.reservation.ReservationNotFoundException;
import services.reservation.ReservationService;

@Controller
@CrossOrigin
@Secured({"ROLE_ADMIN"})
@RequestMapping("/admin/request/reservations")
@ResponseBody
public class ReservationControllerAdmin {

	@Autowired
	private ReservationService reservationService;
	
	@GetMapping
	public List<Reservation> all(){
		return reservationService.findAll();
	}
	
	@GetMapping("/{id}")
	public Reservation one(@PathVariable Long id) {
		try {
			return reservationService.findById(id);
		}catch(ReservationNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@GetMapping("/per-categories")
	public List<ReservationPerCategoryDto> reservationPerCategory(){
		return reservationService.seatsReservedPerCategory();
	}
	
	@GetMapping("/per-categories/last-week")
	public List<ReservationPerDayDto> reservationPerDay(){
		return reservationService.seatsReservedPerDayByCategory();
	}
	
	@GetMapping("/total-numbers")
	public TotalDto total() {
		return reservationService.total();
	}
}
