package services.reservation;

import java.util.List;

import org.springframework.stereotype.Service;

import dto.reservation.ReservationAddDto;
import dto.reservation.ReservationPerCategoryDto;
import dto.reservation.ReservationPerDayDto;
import dto.reservation.TotalDto;
import entities.Reservation;
import entities.Seat;
import exceptions.reservation.SeatsSelectionException;

@Service
public interface ReservationService {
	List<Reservation> findAll();
	Reservation findById(Long id);
	void deleteById(Long id);
	boolean isSeatReserved(Seat seat, Long screeningId);
	Reservation save(ReservationAddDto reservationDto, String username) throws SeatsSelectionException;
	List<Reservation> myReservations(String username);
	void deleteMyReservation(Long id, String username);
	Reservation findMyReservation(Long id, String username);
	List<ReservationPerCategoryDto> seatsReservedPerCategory();
	List<ReservationPerDayDto> seatsReservedPerDayByCategory();
	TotalDto total();
}
