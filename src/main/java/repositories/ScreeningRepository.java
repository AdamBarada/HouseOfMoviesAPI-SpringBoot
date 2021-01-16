package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Room;
import entities.Screening;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
	
}
