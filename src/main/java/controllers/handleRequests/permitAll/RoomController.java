package controllers.handleRequests.permitAll;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import entities.Room;
import exceptions.room.RoomNotFoundException;
import services.room.RoomService;

@Controller
@CrossOrigin
@RequestMapping("/public/request/rooms")
@ResponseBody
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@GetMapping("/{id}")
	public Room one(@PathVariable Long id) {
		try {
			return roomService.findById(id);
		}catch(RoomNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}
