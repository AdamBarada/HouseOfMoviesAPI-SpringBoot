package controllers.handleRequests.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import dto.room.RoomAddDto;
import entities.Room;
import services.room.RoomService;

@Controller
@CrossOrigin
@Secured({"ROLE_ADMIN"})
@RequestMapping("/admin/request/rooms")
@ResponseBody
public class RoomControllerAdmin {

	@Autowired
	private RoomService roomService;
	
	@GetMapping
	public List<Room> all(){
		return roomService.findAll();
	}
	
	@PostMapping
	public Room addRoom(@RequestBody RoomAddDto newRoom) {
		return roomService.save(newRoom);
	}
	
	@DeleteMapping("/{id}")
	public void deleteRoom(@PathVariable Long id) {
		roomService.deleteById(id);
	}
}
