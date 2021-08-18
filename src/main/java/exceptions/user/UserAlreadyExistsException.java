package exceptions.user;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException(String email) {
		super("Email already exists : "+ email);
	}
}
