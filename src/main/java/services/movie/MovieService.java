package services.movie;

import java.util.List;

import org.springframework.stereotype.Service;

import dto.movie.MovieAddDto;
import entities.Movie;

@Service
public interface MovieService {
	Movie save(MovieAddDto movieDto);
	List<Movie> findAll();
	Movie findById(Long id);
	Movie update(Long id, MovieAddDto movieDto);
	void deleteById(Long id);
}
