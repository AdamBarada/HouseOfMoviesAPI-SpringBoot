package exceptions.seat;

@SuppressWarnings("serial")
public class NoSeatsFoundException extends RuntimeException {

	public NoSeatsFoundException(Long id) {
		super("Seats not found for room : "+ id);
	}
}
