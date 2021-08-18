package services.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import dto.user.UserRegistrationDto;
import entities.Reservation;
import entities.Role;
import entities.User;
import exceptions.user.UserAlreadyExistsException;
import exceptions.user.UserNotFoundException;
import repositories.ReservationRepository;
import repositories.RoleRepository;
import repositories.UserRepository;
import services.reservation.ReservationService;
import utils.SortByNbReservations;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getAuthorities(user.getRoles()));
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User userValidation = userRepository.findByEmail(registrationDto.getEmail());
		if(userValidation != null) {
			throw new UserAlreadyExistsException(registrationDto.getEmail());
		}
		User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(),
				registrationDto.getEmail(), 
				passwordEncoder.encode(registrationDto.getPassword()), new ArrayList<Role>());
		user = userRepository.save(user);
		user.getRoles().add(roleRepository.findByName("ROLE_USER"));
		return userRepository.save(user);
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        for(Role r : roles) {
        	list.add(new SimpleGrantedAuthority(r.getName()));
        }
        return list;
    }

	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		for(User u : users) {
			u.setNbReservations(this.getNumberReservations(u));
		}
		return users;
	}

	@Override
	public User update(String email, UserRegistrationDto registrationDto) {
		if(!email.equals(registrationDto.getEmail())) {
			User userValidation = userRepository.findByEmail(registrationDto.getEmail());
			if(userValidation != null) {
				throw new UserAlreadyExistsException(registrationDto.getEmail());
			}
		}
		Long id = userRepository.findByEmail(email).getId();
		User updatedUser= userRepository.findById(id).map(user ->{
			user.setEmail(registrationDto.getEmail());
			user.setFirstName(registrationDto.getFirstName());
			user.setLastName(registrationDto.getLastName());
			user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
			return userRepository.save(user);
		}).orElseThrow(() -> new UserNotFoundException(id));
		return updatedUser;
	}

	@Override
	public void delete(String email) {
		User user = userRepository.findByEmail(email);
		List<Reservation> reservations = reservationService.myReservations(email);
		if(reservations.size() != 0) {
			for(Reservation  r : reservations) {
				reservationService.deleteById(r.getId());
			}
		}
		userRepository.deleteById(user.getId());
	}

	@Override
	public User findById(Long id) {
		User user= userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
		user.setNbReservations(this.getNumberReservations(user));
		return user;
	}

	@Override
	public User me(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean isAdmin(String email) {
		User user = userRepository.findByEmail(email);
		for(Role role : user.getRoles()) {
			if(role.getName().equals("ROLE_ADMIN")) return true;
		}
		return false;
	}

	@Override
	public List<User> usersWithMostReservations() {
		List<User> users = userRepository.findAll();
		for(User u : users) {
			u.setNbReservations(this.getNumberReservations(u));
		}
		Collections.sort(users, new SortByNbReservations());
		return users;
	}
	
	private int getNumberReservations(User user) {
		return reservationRepository.findByUser(user).size();
	}

	@Override
	public List<User> onlyUsers() {
		List<User> users = userRepository.findAll();
		List<User> usersOnly = new ArrayList<User>();
		for(User u : users) {
			if(!isAdmin(u.getEmail())) {
				usersOnly.add(u);
			}
		}
		return usersOnly;
	}
}
