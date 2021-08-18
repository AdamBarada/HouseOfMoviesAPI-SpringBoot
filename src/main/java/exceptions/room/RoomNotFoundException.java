package exceptions.room;

@SuppressWarnings("serial")
public class RoomNotFoundException extends RuntimeException {

	public RoomNotFoundException(Long  id) {
		super("Room " + id + " is not found.");
	}
}
