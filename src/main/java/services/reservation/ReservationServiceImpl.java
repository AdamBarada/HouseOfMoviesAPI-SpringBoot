package services.reservation;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.reservation.ReservationAddDto;
import dto.reservation.ReservationPerCategoryDto;
import dto.reservation.ReservationPerDayDto;
import dto.reservation.TotalDto;
import entities.Category;
import entities.Movie;
import entities.Reservation;
import entities.Screening;
import entities.Seat;
import entities.SeatReserved;
import entities.Status;
import entities.User;
import exceptions.reservation.ReservationEntryNotAllowed;
import exceptions.reservation.ReservationNotFoundException;
import exceptions.reservation.SeatsSelectionException;
import exceptions.screening.ScreeningNotFoundException;
import repositories.CategoryRepository;
import repositories.MovieRepository;
import repositories.ReservationRepository;
import repositories.ScreeningRepository;
import repositories.SeatRepository;
import repositories.SeatReservedRepository;
import repositories.UserRepository;

@Component
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private SeatReservedRepository seatReservedRepository;
	
	@Autowired
	private ScreeningRepository screeningRepository;
	
	@Autowired		// for current user
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public Reservation save(ReservationAddDto reservationDto, String username) throws SeatsSelectionException {
		if(reservationDto.getSeats_ids().size() == 0) throw new SeatsSelectionException();
		List<Seat> seatsSelected = seatRepository.findAllById(reservationDto.getSeats_ids());
		if(seatsSelected.size() == 0) throw new SeatsSelectionException("Seats not found.");
		Screening screening = screeningRepository.findById(reservationDto.getScreening())
				.orElseThrow(() -> new ScreeningNotFoundException(reservationDto.getScreening()));
		for(Seat s : seatsSelected) {
			if(isSeatReserved(s, reservationDto.getScreening()))
				throw new SeatsSelectionException("Seat " + s.getId() + " is already reserved.");
		}
		User user = userRepository.findByEmail(username);
		Float total = screening.getPrice() * seatsSelected.size();
		Reservation reservation = new Reservation(user, screening, Date.valueOf(LocalDate.now()),
				Time.valueOf(LocalTime.now()), total);
		reservation =  reservationRepository.save(reservation);
		for(Seat s : seatsSelected) {
			seatReservedRepository.save(new SeatReserved(s, reservation, screening));
		}
		reservation.setStatus(setStatus(reservation));
		return getSeatReserved(reservation);
	}

	@Override
	public List<Reservation> findAll() {
		List<Reservation> reservations = reservationRepository.findAll();
		if(!reservations.isEmpty()) {
			for(Reservation r : reservations) {
				r.setStatus(setStatus(r));
			}
		}
		Collections.sort(reservations);
		return reservations;
	}

	@Override
	public Reservation findById(Long id) {
		Reservation reservation = getSeatReserved(reservationRepository.findById(id).orElseThrow(() 
				-> new ReservationNotFoundException(id)));
		reservation.setStatus(setStatus(reservation));
		return reservation;
	}

	@Override
	public void deleteById(Long id) {
		List<SeatReserved> seats = seatReservedRepository.findAll();
		if(seats.size() != 0) {
			for(SeatReserved s : seats) {
				if(s.getReservation().getId().equals(id)) {
					seatReservedRepository.deleteById(s.getId());
				}
			}
		}
		reservationRepository.deleteById(id);
	}
	
	@Override
	public boolean isSeatReserved(Seat seat, Long screeningId) {
		List<SeatReserved> seatsReserved = seatReservedRepository.findAll();
		if(seatsReserved.size() == 0) return false;
		for(SeatReserved s : seatsReserved) {			
			if(s.getScreening().getId().equals(screeningId)) {
				if(s.getSeat().getId().equals(seat.getId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Reservation getSeatReserved(Reservation reservation) {
		List<SeatReserved> seatsReserved = seatReservedRepository.findByReservation(reservation);
		reservation.setSeats_reserved(seatsReserved);
		/*reservation.setSeats_reserved(new ArrayList<SeatReserved>()); 
		for(SeatReserved s : seatsReserved) {
			if(s.getReservation().getId() == reservation.getId())
				reservation.getSeats_reserved().add(s);
		}*/
		return reservation;
	}

	@Override
	public List<Reservation> myReservations(String username) {
		User user = userRepository.findByEmail(username);
		List<Reservation> myReservations = reservationRepository.findByUser(user);
		for(Reservation r : myReservations) {
			r.setSeats_reserved(getSeatReserved(r).getSeats_reserved());
			r.setStatus(setStatus(r));
		}
		Collections.sort(myReservations);
		return myReservations;
	}

	@Override
	public void deleteMyReservation(Long id, String username) {
		User user = userRepository.findByEmail(username);
		Reservation r = reservationRepository.findById(id).orElseThrow(() 
				-> new ReservationNotFoundException(id));
		if(r.getUser().getId() != user.getId()) throw new ReservationEntryNotAllowed(id);
		getSeatReserved(r);
		seatReservedRepository.deleteAll(r.getSeats_reserved());
		reservationRepository.deleteById(id);
	}

	@Override
	public Reservation findMyReservation(Long id, String username) {
		User user = userRepository.findByEmail(username);
		Reservation r = reservationRepository.findById(id).orElseThrow(() 
				-> new ReservationNotFoundException(id));
		if(r.getUser().getId() != user.getId()) throw new ReservationEntryNotAllowed(id);
		r.setStatus(setStatus(r));
		getSeatReserved(r);
		return r;
	}
	
	private Status setStatus(Reservation r) {
		Timestamp time = Timestamp.valueOf(LocalDateTime.of(r.getScreening().getDate().toLocalDate(), 
				r.getScreening().getTime().toLocalTime()));
		Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		if(time.before(now)) return Status.PAST;
		return Status.AVAILABLE;
	}

	@Override
	public List<ReservationPerCategoryDto> seatsReservedPerCategory() {
		List<Movie> movies = movieRepository.findAll();
		List<Category> categories = categoryRepository.findAll();
		List<ReservationPerCategoryDto> reservationPerCategory = new ArrayList<ReservationPerCategoryDto>();
		for(Category c : categories) {
			int nb = 0;
			for(Movie m : movies) {
				for(Category cm : m.getCategories()) {
					if(cm.getId().equals(c.getId())) {
						nb = nb + movieRepository.nbViewers(m.getId());
						break;
					}
				}
			}
			ReservationPerCategoryDto r = new ReservationPerCategoryDto(c.getName(), nb);
			reservationPerCategory.add(r);
		}
		return reservationPerCategory;
	}

	@Override
	public List<ReservationPerDayDto> seatsReservedPerDayByCategory() {
		LocalDateTime DT;
		List<ReservationPerDayDto> reservationPerDay = new ArrayList<ReservationPerDayDto>();
		List<Category> categories = categoryRepository.findAll();
		List<Movie> movies = movieRepository.findAll();
		for(Category c : categories) {
			DT = LocalDateTime.now().minusDays(8);
			ReservationPerDayDto res = new ReservationPerDayDto(c.getName(), new ArrayList<ReservationPerCategoryDto>());
			for(int i=1; i<=7; i++) {
				DT = DT.plusDays(1);
				int nb = 0;
				for(Movie m : movies) {
					for(Category cm : m.getCategories()) {
						if(cm.getId() == c.getId()) {
							nb = nb + movieRepository.nbViewersPerDay(m.getId(), Date.valueOf(DT.toLocalDate()));
							break;
						}
					}
				}
				res.getSeries().add(new ReservationPerCategoryDto(Date.valueOf(DT.toLocalDate()).toString(), nb));
			}
			reservationPerDay.add(res);
		}
		return reservationPerDay;
	}

	@Override
	public TotalDto total() {
		Float total = (float) 0;
		List<Reservation> reservations = reservationRepository.findAll();
		for(Reservation r : reservations) {
			total = total + r.getTotal().floatValue();
		}
		return new TotalDto(reservations.size(), total);
	}

}
