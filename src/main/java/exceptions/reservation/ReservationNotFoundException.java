package exceptions.reservation;

@SuppressWarnings("serial")
public class ReservationNotFoundException extends RuntimeException {
	public ReservationNotFoundException(Long id) {
		super("Reservation " + id + " is not found.");
	}
}
