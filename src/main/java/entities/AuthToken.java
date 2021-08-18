package entities;

public class AuthToken {

	private String token;
	private boolean isAdmin;

	public AuthToken(String token, boolean isAdmin) {
		super();
		this.token = token;
		this.isAdmin = isAdmin;
	}

	public AuthToken() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}
