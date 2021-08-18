package repositories;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	Movie findByTitle(String title);
	
	@Query("SELECT COUNT(sr.id) from Movie m, Screening s, Reservation r, SeatReserved sr "
			+ "WHERE m.id=s.movie.id AND s.id = r.screening.id AND r.id = sr.reservation.id AND m.id =?1")
	int nbViewers(Long id);
	
	@Query("SELECT COUNT(sr.id) from Movie m, Screening s, Reservation r, SeatReserved sr "
			+ "WHERE m.id=s.movie.id AND s.id = r.screening.id AND r.id = sr.reservation.id AND m.id =?1 AND r.date=?2")
	int nbViewersPerDay(Long id, Date date);
}
