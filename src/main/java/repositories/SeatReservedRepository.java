package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.SeatReserved;

@Repository
public interface SeatReservedRepository extends JpaRepository<SeatReserved, Long> {

}
