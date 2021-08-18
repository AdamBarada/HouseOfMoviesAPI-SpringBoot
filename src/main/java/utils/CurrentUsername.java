package utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUsername {

	public static String currentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String user = auth.getPrincipal().toString();
		return user.split("=")[1].split(",")[0];
	}
}
