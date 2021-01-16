package services.screening;

import java.util.List;

import org.springframework.stereotype.Service;

import dto.screening.ScreeningAddDto;
import entities.Screening;

@Service
public interface ScreeningService {
	Screening save(ScreeningAddDto screeningDto);
	List<Screening> findAll();
	Screening findById(Long id);
}
