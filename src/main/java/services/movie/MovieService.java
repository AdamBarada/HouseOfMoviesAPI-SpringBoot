package services.movie;

import java.util.List;

import org.springframework.stereotype.Service;

import dto.movie.MovieAddDto;
import dto.movie.MoviePerCategory;
import entities.Movie;

@Service
public interface MovieService {
	Movie save(MovieAddDto movieDto);
	Movie addImage(Long id, String image);
	Movie addLandscape(Long id, String landscape);
	List<Movie> findAll();
	Movie findById(Long id);
	Movie update(Long id, MovieAddDto movieDto);
	void deleteById(Long id);
	List<Movie> findByCategory(List<Long> ids);
	List<Movie> getComingSoonMovies();
	List<Movie> getMostViewedMovies(); // trending movies
	List<Movie> search(String search);
	List<MoviePerCategory> nbMoviesPerCategories();
}
