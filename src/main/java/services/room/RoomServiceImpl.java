package services.room;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.room.RoomAddDto;
import entities.Room;
import entities.Screening;
import entities.Seat;
import entities.SeatReserved;
import exceptions.seat.NoSeatsFoundException;
import exceptions.room.RoomNotFoundException;
import exceptions.screening.ScreeningNotFoundException;
import repositories.RoomRepository;
import repositories.ScreeningRepository;
import repositories.SeatRepository;
import repositories.SeatReservedRepository;
import services.screening.ScreeningService;

@Component
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private ScreeningRepository screeningRepository;
	
	@Autowired
	private ScreeningService screeningService;
	
	@Autowired
	private SeatReservedRepository seatReservedRepository;

	
	@Override
	public List<Room> findAll() {
		List<Room> rooms = roomRepository.findAll();
		for(Room r : rooms) {
			r = seatsOfRoom(r);
			int rows =0 , cols = 0;
			for(Seat s : r.getSeats()) {
				if(s.getRow()>rows) rows = s.getRow();
				if(s.getNumber()>cols) cols = s.getNumber();
			}
			r.setNbRows(rows);
			r.setNbColumns(cols);
			r.setSeats(null);
		}
		return rooms;
	}

	@Override
	public Room save(RoomAddDto roomDto) {
		Room room = roomRepository.save(new Room(roomDto.getName()));
		for(int row=0; row<=roomDto.getRows(); row++) {
			for(int col=0; col<=roomDto.getColumns(); col++) {
				seatRepository.save(new Seat(row, col, room));
			}
		}
		return room;
	}

	@Override
	public void deleteById(Long id) {
		List<Seat> seats = seatRepository.findAll();
		if(seats.size() != 0) {
			for(Seat seat : seats) {
				if(seat.getRoom().getId().equals(id)) {
					List<SeatReserved> seatReserved = seatReservedRepository.findAll();
					if(seatReserved.size() != 0) {
						for(SeatReserved sr : seatReserved) {
							if(sr.getSeat().getId().equals(seat.getId())) {
								seatReservedRepository.deleteById(sr.getId());
							}
						}
					}
					seatRepository.deleteById(seat.getId());
				}
			}
		}
		List<Screening> screenings = screeningRepository.findAll();
		if(screenings.size()!=0) {
			System.out.println("HERE");
			for(Screening s : screenings) {
				if(s.getRoom().getId().equals(id)) {
					screeningService.deleteById(s.getId());
				}
			}
		}
		roomRepository.deleteById(id);
	}

	@Override
	public Room findById(Long id) {
		Room room = roomRepository.findById(id).orElseThrow(()-> new RoomNotFoundException(id));
		return seatsOfRoom(room);
	}
	
	private Room seatsOfRoom(Room room) {
		List<Seat> seats = seatRepository.findAll();
		if(seats.size() == 0) throw new NoSeatsFoundException(room.getId());
		room.setSeats(new ArrayList<Seat>());
		for(Seat s : seats) {
			if(s.getRoom().getId() == room.getId()) {
				room.getSeats().add(s);
			}
		}
		return room;
	}

	@Override
	public Room roomForScreening(Long screening_id) {
		Screening screening = screeningRepository.findById(screening_id)
				.orElseThrow(() -> new ScreeningNotFoundException(screening_id));
		Room room = screening.getRoom();
		room = seatsOfRoom(room);
		int rows=0, cols=0;
		for(Seat s : room.getSeats()) {
			Long sr = seatReservedRepository.findBySeatAndScreening(s.getId(), screening_id);
			if(sr != null) s.setTaken(true);
			else s.setTaken(false);
			if(s.getRow()>rows) rows = s.getRow();
			if(s.getNumber()>cols) cols = s.getNumber();
		}
		room.setNbRows(rows);
		room.setNbColumns(cols);
		return room;
	}

}
