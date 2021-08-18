package services.room;

import java.util.List;

import org.springframework.stereotype.Service;

import dto.room.RoomAddDto;
import entities.Room;

@Service
public interface RoomService {
	List<Room> findAll();
	Room findById(Long id);
	Room save(RoomAddDto roomDto);
	void deleteById(Long id);
	Room roomForScreening(Long screening_id);
}
