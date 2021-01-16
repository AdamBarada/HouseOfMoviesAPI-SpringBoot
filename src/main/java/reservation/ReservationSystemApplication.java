package reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import assemblers.MovieAssembler;
import assemblers.ScreeningAssembler;
import controllers.MovieController;
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
import services.movie.MovieService;
import services.movie.MovieServiceImpl;
import services.screening.ScreeningService;
import services.screening.ScreeningServiceImpl;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages= {"assemblers", "config", "controllers", "dto.*", "entities", "repositories"})
@ComponentScan(basePackageClasses= {
		// entities
		Movie.class, Reservation.class, Role.class, Room.class, Screening.class, Seat.class,
		SeatReserved.class, User.class,
		// repositories
		MovieRepository.class, ReservationRepository.class, RoomRepository.class, ScreeningRepository.class,
		SeatRepository.class, SeatReservedRepository.class, UserRepository.class,
		// controllers
		MovieController.class,
		// services
		MovieService.class, MovieServiceImpl.class, ScreeningService.class, ScreeningServiceImpl.class,
		// assemblers
		MovieAssembler.class, ScreeningAssembler.class
		
})
@EntityScan("entities")
@EnableJpaRepositories("repositories")
public class ReservationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationSystemApplication.class, args);
	}

}
