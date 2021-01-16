package exceptions;

@SuppressWarnings("serial")
public class ScreeningNotFoundException extends RuntimeException {
	
	public ScreeningNotFoundException(Long  id) {
		super("Screening " + id + " is not found.");
	}
}
