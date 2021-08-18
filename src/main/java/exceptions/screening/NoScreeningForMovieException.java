package exceptions.screening;

@SuppressWarnings("serial")
public class NoScreeningForMovieException extends RuntimeException {

	public NoScreeningForMovieException(Long id) {
		super("No screenings found for movie nb : " + id);
	}
}
