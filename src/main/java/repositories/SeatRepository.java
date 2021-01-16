package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

}
