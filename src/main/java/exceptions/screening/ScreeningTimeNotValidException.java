package exceptions.screening;

import java.io.IOException;

@SuppressWarnings("serial")
public class ScreeningTimeNotValidException extends IOException {

	public ScreeningTimeNotValidException() {
		super("Screening time is not valid.");
	}
}
