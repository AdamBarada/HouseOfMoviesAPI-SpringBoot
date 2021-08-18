package services.screening;

import java.util.List;

import org.springframework.stereotype.Service;

import dto.screening.ScreeningAddDto;
import entities.Screening;
import exceptions.screening.ScreeningTimeNotValidException;

@Service
public interface ScreeningService {
	Screening save(ScreeningAddDto screeningDto) throws ScreeningTimeNotValidException;
	List<Screening> findAll();
	Screening findById(Long id);
	Screening update(Long id, ScreeningAddDto screeningDto) throws ScreeningTimeNotValidException;
	void deleteById(Long id);
	List<Screening> findAllForMovie(Long id);
	Screening screeningWithSeats(Long id);
}
