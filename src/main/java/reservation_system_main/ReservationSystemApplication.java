package reservation_system_main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import config.CORSFilter;
import config.JwtAuthenticationEntryPoint;
import config.LoadDatabase;
import config.TokenProvider;
import config.WebSecurityConfig;
import controllers.handleRequests.admin.MovieControllerAdmin;
import controllers.handleRequests.admin.ReservationControllerAdmin;
import controllers.handleRequests.admin.RoomControllerAdmin;
import controllers.handleRequests.admin.ScreeningControllerAdmin;
import controllers.handleRequests.admin.UserControllerAdmin;
import controllers.handleRequests.permitAll.AuthenticationController;
import controllers.handleRequests.permitAll.CategoryController;
import controllers.handleRequests.permitAll.MovieController;
import controllers.handleRequests.permitAll.RoomController;
import controllers.handleRequests.permitAll.ScreeningController;
import controllers.handleRequests.permitAll.UserRegistrationController;
import controllers.handleRequests.user.ReservationControllerUser;
import controllers.handleRequests.user.ScreeningControllerUser;
import controllers.handleRequests.user.UserController;
import entities.AuthToken;
import entities.Category;
import entities.Movie;
import entities.Reservation;
import entities.Role;
import entities.Room;
import entities.Screening;
import entities.Seat;
import entities.SeatReserved;
import entities.User;
import repositories.MovieRepository;
import repositories.ReservationRepository;
import repositories.RoomRepository;
import repositories.ScreeningRepository;
import repositories.SeatRepository;
import repositories.SeatReservedRepository;
import repositories.UserRepository;
import services.category.CategoryService;
import services.category.CategoryServiceImpl;
import services.movie.MovieService;
import services.movie.MovieServiceImpl;
import services.reservation.ReservationService;
import services.reservation.ReservationServiceImpl;
import services.room.RoomService;
import services.room.RoomServiceImpl;
import services.screening.ScreeningService;
import services.screening.ScreeningServiceImpl;
import services.user.UserService;
import services.user.UserServiceImpl;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages= {"assemblers", "config", "controllers.*", "dto.*", "entities", "repositories",
		"services"})
@ComponentScan(basePackageClasses= {
		// config
		LoadDatabase.class, CORSFilter.class, JwtAuthenticationEntryPoint.class,
		TokenProvider.class, WebSecurityConfig.class,
		// entities
		Movie.class, Reservation.class, Role.class, Room.class, Screening.class, Seat.class,
		SeatReserved.class, User.class, Category.class, AuthToken.class,
		// repositories
		MovieRepository.class, ReservationRepository.class, RoomRepository.class, ScreeningRepository.class,
		SeatRepository.class, SeatReservedRepository.class, UserRepository.class,
		// controllers that handle requests and send response bodies
		MovieControllerAdmin.class, ReservationControllerAdmin.class, RoomControllerAdmin.class,
		ScreeningControllerAdmin.class, UserControllerAdmin.class,
		AuthenticationController.class, CategoryController.class, MovieController.class,
		RoomController.class, ScreeningController.class, UserRegistrationController.class,
		ReservationControllerUser.class, ScreeningControllerUser.class, UserController.class,
		// services
		MovieService.class, MovieServiceImpl.class, ScreeningService.class, ScreeningServiceImpl.class,
		ReservationService.class, ReservationServiceImpl.class, RoomService.class, RoomServiceImpl.class,
		CategoryService.class, CategoryServiceImpl.class, UserService.class, UserServiceImpl.class
})
@EntityScan("entities")
@EnableJpaRepositories("repositories")
public class ReservationSystemApplication {

	public static void main(String[] args){
		SpringApplication.run(ReservationSystemApplication.class, args);
	}

}
