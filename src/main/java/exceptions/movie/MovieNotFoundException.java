package exceptions.movie;

@SuppressWarnings("serial")
public class MovieNotFoundException extends RuntimeException {

	public MovieNotFoundException(Long  id) {
		super("Movie " + id + " is not found.");
	}

}
