package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import entities.Reservation;
import entities.SeatReserved;

@Repository
public interface SeatReservedRepository extends JpaRepository<SeatReserved, Long> {
	@Query("select s.id from SeatReserved s where s.seat.id = ?1 AND s.screening.id = ?2")
	Long findBySeatAndScreening(Long seatId, Long screeningId);
	
	List<SeatReserved> findByReservation(Reservation reservation);
}
