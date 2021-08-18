package exceptions.user;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super("User not found.");
	}
	
	public UserNotFoundException(String email) {
		super("User "+ email +" not found.");
	}
	
	public UserNotFoundException(Long id) {
		super("User "+ id +" not found.");
	}
}
