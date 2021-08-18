package exceptions.reservation;

import java.io.IOException;

@SuppressWarnings("serial")
public class SeatsSelectionException extends IOException {
	
	public SeatsSelectionException() {
		super("No seats are selected.");
	}
	
	public SeatsSelectionException(String message) {
		super(message);
	}
}
