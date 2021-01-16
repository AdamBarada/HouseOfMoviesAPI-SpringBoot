package exceptions;

import java.io.IOException;

@SuppressWarnings("serial")
public class ScreeningTimeNotValidException extends IOException {

	public ScreeningTimeNotValidException() {
		super("Screening time is not valid.");
	}
}
