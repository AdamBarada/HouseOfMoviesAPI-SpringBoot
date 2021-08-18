package services.user;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import dto.user.UserRegistrationDto;
import entities.User;

@Service
public interface UserService extends UserDetailsService {
	User save(UserRegistrationDto registrationDto);
	User findById(Long id);
	List<User> findAll();
	void delete(String email);
	User update(String email, UserRegistrationDto registrationDto);
	User me(String email);
	boolean isAdmin(String email);
	List<User> usersWithMostReservations();
	List<User> onlyUsers(); // without the admins
}
