package services.screening;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.screening.ScreeningAddDto;
import entities.Movie;
import entities.Reservation;
import entities.Room;
import entities.Screening;
import entities.Status;
import exceptions.movie.MovieNotFoundException;
import exceptions.room.RoomNotFoundException;
import exceptions.screening.NoScreeningForMovieException;
import exceptions.screening.ScreeningNotFoundException;
import exceptions.screening.ScreeningTimeNotValidException;
import repositories.MovieRepository;
import repositories.RoomRepository;
import repositories.ScreeningRepository;
import services.reservation.ReservationService;
import services.room.RoomService;

@Component
public class ScreeningServiceImpl implements ScreeningService  {

	@Autowired 
	private ScreeningRepository screeningRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private RoomService roomService;
	
	@Override
	public List<Screening> findAll() {
		List<Screening> screenings = screeningRepository.findAll();
		if(screenings.size() != 0) {
			for(Screening s : screenings) {
				s.setStatus(setStatus(s));
				LocalDateTime t=LocalDateTime.of(s.getDate().toLocalDate(), s.getTime().toLocalTime()).plusDays(1);
				s.setDate(Date.valueOf(t.toLocalDate()));
			}
		}
		Collections.sort(screenings);
		return screenings;
	}

	@Override
	public Screening findById(Long id) {
		Screening screening = screeningRepository.findById(id)
				.orElseThrow(() -> new ScreeningNotFoundException(id));
		screening.setStatus(setStatus(screening));
		LocalDateTime t=LocalDateTime.of(screening.getDate().toLocalDate(), screening.getTime().toLocalTime()).plusDays(1);
		screening.setDate(Date.valueOf(t.toLocalDate()));
		return screening;
	}
	
	@Override
	public Screening save(ScreeningAddDto screeningDto) throws ScreeningTimeNotValidException {
		Movie movie = movieRepository.findById(screeningDto.getMovie()).orElseThrow(() -> 
				new MovieNotFoundException(screeningDto.getMovie()));
		Room room = roomRepository.findById(screeningDto.getRoom()).orElseThrow(()->
				new RoomNotFoundException(screeningDto.getMovie()));
		
		Date date = Date.valueOf(LocalDate.of(screeningDto.getYear(), screeningDto.getMonth(), screeningDto.getDay()+1));
		Time time = Time.valueOf(LocalTime.of(screeningDto.getHour(), screeningDto.getMin(), 0));
		
		if(date.before(Date.valueOf(LocalDate.now()))) {
			throw new ScreeningTimeNotValidException();
		}
		
		Screening screening = new Screening(movie, room,
				date, time, screeningDto.getPrice());
		if(!timeIsValid(screening)) {
			throw new ScreeningTimeNotValidException();
		}
		else return screeningRepository.save(screening);
		
	}

	@Override
	public Screening update(Long id, ScreeningAddDto screeningDto) throws ScreeningTimeNotValidException{
		Movie movie = movieRepository.findById(screeningDto.getMovie()).orElseThrow(() -> 
				new MovieNotFoundException(screeningDto.getMovie()));
		Room room = roomRepository.findById(screeningDto.getRoom()).orElseThrow(()->
			new RoomNotFoundException(screeningDto.getMovie()));
		
		Date date = Date.valueOf(LocalDate.of(screeningDto.getYear(), screeningDto.getMonth(), screeningDto.getDay()+ 1));
		Time time = Time.valueOf(LocalTime.of(screeningDto.getHour(), screeningDto.getMin(), 0));
		
		Screening screeningCheck = new Screening(movie, room, date, time, screeningDto.getPrice());
		if(!timeIsValid(screeningCheck)) {
			throw new ScreeningTimeNotValidException();
		}
		
		Screening updatedScreening = screeningRepository.findById(id).map(screening -> {
			screening.setMovie(movie);
			screening.setRoom(room);
			screening.setDate(date);
			screening.setTime(time);
			screening.setPrice(screeningDto.getPrice());
			return screeningRepository.save(screening);
		}).orElseGet(() -> {
			Screening newScreening = screeningCheck;
			newScreening.setId(id);
			return screeningRepository.save(newScreening);
		});
		return updatedScreening;
	}

	@Override
	public void deleteById(Long id) {
		List<Reservation> reservations = reservationService.findAll();
		if(reservations.size() != 0) {
			for(Reservation r : reservations) {
				if(r.getScreening().getId().equals(id)) {
					reservationService.deleteById(r.getId());
				}
			}
		}
		screeningRepository.deleteById(id);
	}

	@Override
	public List<Screening> findAllForMovie(Long id) {
		movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
		List<Screening> screenings = screeningRepository.findAll();
		if(screenings.size()==0) throw new NoScreeningForMovieException(id);
		List<Screening> screeningsForMovie = new ArrayList<Screening>();
		for(Screening s : screenings) {
			if(s.getMovie().getId().equals(id)) {
				screeningsForMovie.add(s);
			}
		}
		if(screeningsForMovie.size() == 0) throw new NoScreeningForMovieException(id);
		for(Screening s : screeningsForMovie) {
			s.setStatus(setStatus(s));
			LocalDateTime t=LocalDateTime.of(s.getDate().toLocalDate(), s.getTime().toLocalTime()).plusDays(1);
			s.setDate(Date.valueOf(t.toLocalDate()));
		}
		Collections.sort(screeningsForMovie);
		return screeningsForMovie;
	}

	private boolean timeIsValid(Screening screening) {
		List<Screening> screenings = screeningRepository.findAll();
		if(screenings.size()==0) return true;
		Timestamp start2 = Timestamp.valueOf(LocalDateTime.of(screening.getDate().toLocalDate(), screening.getTime().toLocalTime()));
		Timestamp end2 = Timestamp.valueOf(LocalDateTime.of(screening.getDate().toLocalDate(), 
				screening.getTime().toLocalTime()).plusMinutes(screening.getMovie().getDuration()));
		for(Screening s : screenings) {
			if(s.getRoom().getId() == screening.getRoom().getId()) {
				Timestamp start1 = Timestamp.valueOf(LocalDateTime.of(s.getDate().toLocalDate(), s.getTime().toLocalTime()));
				Timestamp end1 = Timestamp.valueOf(LocalDateTime.of(s.getDate().toLocalDate(), 
						screening.getTime().toLocalTime()).plusMinutes(s.getMovie().getDuration()));
				if(start1.equals(start2) || end1.equals(end2)) return false;
				if(start2.after(start1) && start2.before(end1)) return false;
				if(end2.after(start1) && end2.before(end1)) return false;
			}
		}
		return true;
	}
	
	private Status setStatus(Screening s) {
		Timestamp time = Timestamp.valueOf(LocalDateTime.of(s.getDate().toLocalDate(), s.getTime().toLocalTime()));
		Timestamp time2 = Timestamp.valueOf(LocalDateTime.of(s.getDate().toLocalDate(), s.getTime().toLocalTime()).plusMinutes(20));
		Timestamp now =  Timestamp.valueOf(LocalDateTime.now());
		if(time.after(now)) {
			return Status.AVAILABLE;
		}
		else if(time.equals(now)) {
			return Status.STARTED;
		}
		else if(time.before(now) && time2.after(now)) {
			return Status.STARTED;
		}
		else return Status.PAST;
		
	}

	@Override
	public Screening screeningWithSeats(Long id) {
		Screening screening = screeningRepository.findById(id)
				.orElseThrow(() -> new ScreeningNotFoundException(id));
		screening.setRoom(roomService.roomForScreening(id));
		LocalDateTime t=LocalDateTime.of(screening.getDate().toLocalDate(), screening.getTime().toLocalTime()).plusDays(1);
		screening.setDate(Date.valueOf(t.toLocalDate()));
		return screening;
	}
}
