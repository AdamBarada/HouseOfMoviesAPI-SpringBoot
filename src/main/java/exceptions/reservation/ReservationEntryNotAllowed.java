package exceptions.reservation;


@SuppressWarnings("serial")
public class ReservationEntryNotAllowed extends RuntimeException {

	public ReservationEntryNotAllowed(Long id) {
		super("Reservation "+ id+ " does not belong to this user." );
	}

}
