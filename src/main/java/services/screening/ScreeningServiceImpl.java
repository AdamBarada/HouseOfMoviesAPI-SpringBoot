package services.screening;

import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.screening.ScreeningAddDto;
import entities.Movie;
import entities.Room;
import entities.Screening;
import exceptions.MovieNotFoundException;
import exceptions.RoomNotFoundException;
import exceptions.ScreeningNotFoundException;
import exceptions.ScreeningTimeNotValidException;
import repositories.MovieRepository;
import repositories.RoomRepository;
import repositories.ScreeningRepository;
import utils.TimestampConverter;

@Component
public class ScreeningServiceImpl implements ScreeningService {

	@Autowired 
	private ScreeningRepository screeningRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Override
	public Screening save(ScreeningAddDto screeningDto) {
		Movie movie = movieRepository.findById(screeningDto.getMovie()).orElseThrow(() -> 
				new MovieNotFoundException(screeningDto.getMovie()));
		Room room = roomRepository.findById(screeningDto.getRoom()).orElseThrow(()->
				new RoomNotFoundException(screeningDto.getMovie()));
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, screeningDto.getYear());
		cal.set(Calendar.MONTH, screeningDto.getMonth());
		cal.set(Calendar.DATE, screeningDto.getDay());
		cal.set(Calendar.HOUR_OF_DAY, screeningDto.getHour());
		cal.set(Calendar.MINUTE, screeningDto.getMin());
		cal.set(Calendar.SECOND, screeningDto.getSec());
		Date date = new Date(cal.getTimeInMillis());
		Time time = new Time(cal.getTimeInMillis());
		
		Screening screening = new Screening(movie, room,
				date, time, screeningDto.getPrice());
		try {
			if(!timeIsValid(screening)) {
				throw new ScreeningTimeNotValidException();
			}
			else return screeningRepository.save(screening);
		} catch (ScreeningTimeNotValidException e) {
			return null;
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public boolean timeIsValid(Screening screening) {
		List<Screening> screenings = screeningRepository.findAll();
		if(screenings.size()==0) return true;
		for(Screening s : screenings) {
			if(s.getRoom() == screening.getRoom()) {		
				Calendar start1 = Calendar.getInstance();
				start1.set(Calendar.YEAR, s.getDate().getYear());
				start1.set(Calendar.MONTH, s.getDate().getMonth());
				start1.set(Calendar.DATE, s.getDate().getDate());
				start1.set(Calendar.HOUR_OF_DAY, s.getTime().getHours());
				start1.set(Calendar.MINUTE, s.getTime().getMinutes());
				start1.set(Calendar.SECOND, s.getTime().getSeconds());
				Calendar end1 = start1;
				end1.add(Calendar.MINUTE, start1.get(Calendar.MINUTE) + s.getMovie().getDuration());
				
				Calendar start2 = Calendar.getInstance();
				start2.set(Calendar.YEAR, screening.getDate().getYear());
				start2.set(Calendar.MONTH, screening.getDate().getMonth());
				start2.set(Calendar.DATE, screening.getDate().getDate());
				start2.set(Calendar.HOUR_OF_DAY, screening.getTime().getHours());
				start2.set(Calendar.MINUTE, screening.getTime().getMinutes());
				start2.set(Calendar.SECOND, screening.getTime().getSeconds());
				Calendar end2 = start2;
				end2.add(Calendar.MINUTE, start2.get(Calendar.MINUTE) + screening.getMovie().getDuration());
				
				if(start2.after(start1) && start2.before(end1)) return false;
				if(end2.after(start1) && end2.before(end1)) return false;
			}
		}
		return true;
	}

	@Override
	public List<Screening> findAll() {
		return screeningRepository.findAll();
	}

	@Override
	public Screening findById(Long id) {
		return screeningRepository.findById(id).orElseThrow(() -> new ScreeningNotFoundException(id));
	}

}
