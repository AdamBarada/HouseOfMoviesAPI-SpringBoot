package services.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.movie.MovieAddDto;
import entities.Movie;
import exceptions.MovieNotFoundException;
import repositories.MovieRepository;

@Component
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public Movie save(MovieAddDto movieDto) {
		Movie movie = new Movie(movieDto.getTitle(), movieDto.getDirector(), movieDto.getCast(), 
				movieDto.getDescription(), movieDto.getDuration());
		return movieRepository.save(movie);
	}

	@Override
	public List<Movie> findAll() {
		return movieRepository.findAll();
	}

	@Override
	public Movie findById(Long id) {
		return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
	}

	@Override
	public Movie update(Long id, MovieAddDto movieDto) {
		Movie updatedMovie = movieRepository.findById(id).map(movie -> {
			movie.setTitle(movieDto.getTitle());
			movie.setDirector(movieDto.getDirector());
			
			return movieRepository.save(movie);
		}).orElseGet(() -> {
			Movie newMovie = new Movie(id, movieDto.getTitle(), movieDto.getDirector(), movieDto.getCast(),
					movieDto.getDescription(), movieDto.getDuration());
			return movieRepository.save(newMovie);
		});
		return updatedMovie;
	}

	@Override
	public void deleteById(Long id) {
		movieRepository.deleteById(id);
	}

}
