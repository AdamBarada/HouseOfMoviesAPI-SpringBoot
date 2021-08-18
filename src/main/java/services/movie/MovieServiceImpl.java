package services.movie;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.movie.MovieAddDto;
import dto.movie.MoviePerCategory;
import entities.Category;
import entities.Movie;
import entities.Screening;
import entities.Status;
import exceptions.category.CategoryNotFoundException;
import exceptions.movie.MovieNotFoundException;
import exceptions.screening.NoScreeningForMovieException;
import repositories.CategoryRepository;
import repositories.MovieRepository;
import services.screening.ScreeningService;
import utils.SortByViewers;

@Component
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ScreeningService screeningService;
	
	@Override
	public Movie save(MovieAddDto movieDto) {
		Collection<Category> categories = new ArrayList<Category>();
		for(Long categoryId : movieDto.getCategories()) {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new CategoryNotFoundException(categoryId));
			categories.add(category);
		}
		
		Movie movie = new Movie(movieDto.getTitle(), movieDto.getDirector(), movieDto.getCast(), 
				movieDto.getDescription(), movieDto.getDuration(), Date.valueOf(LocalDate.parse(movieDto.getReleaseDate()).plusDays(1)), categories, movieDto.getTrailer());
		return movieRepository.save(movie);
	}

	@Override
	public List<Movie> findAll() {
		List<Movie> movies = movieRepository.findAll();
		Collections.sort(movies);
		Collections.reverse(movies);
		for(Movie m : movies) {
			m.setStatus(setStatus(m));
			m.setViewers(getNumberViews(m));
		}
		return movies;
	}

	@Override
	public Movie findById(Long id) {
		Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
		movie.setStatus(setStatus(movie));
		movie.setViewers(getNumberViews(movie));
		return movie;
	}

	@Override
	public Movie update(Long id, MovieAddDto movieDto) {
		Collection<Category> categories = new ArrayList<Category>();
		for(Long categoryId : movieDto.getCategories()) {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new CategoryNotFoundException(categoryId));
			categories.add(category);
		}
		Movie updatedMovie = movieRepository.findById(id).map(movie -> {
			movie.setTitle(movieDto.getTitle());
			movie.setDirector(movieDto.getDirector());
			movie.setCast(movieDto.getCast());
			movie.setDescription(movieDto.getDescription());
			movie.setDuration(movieDto.getDuration());
			movie.setCategories(categories);
			movie.setReleaseDate(Date.valueOf(LocalDate.parse(movieDto.getReleaseDate()).plusDays(1)));
			return movieRepository.save(movie);
		}).orElseGet(() -> {
			Movie newMovie = new Movie(id, movieDto.getTitle(), movieDto.getDirector(), movieDto.getCast(),
					movieDto.getDescription(), movieDto.getDuration(), Date.valueOf(LocalDate.parse(movieDto.getReleaseDate()).plusDays(1)), categories, movieDto.getTrailer());
			return movieRepository.save(newMovie);
		});
		return updatedMovie;
	}

	@Override
	public void deleteById(Long id) {
		try {
			List<Screening> screenings = screeningService.findAllForMovie(id);
			if(!screenings.isEmpty()) {
				for(Screening s : screenings) {
					screeningService.deleteById(s.getId());
				}
			}
			movieRepository.deleteById(id);
		} catch(NoScreeningForMovieException e) {
			movieRepository.deleteById(id);
		}
	}
	
	private Status setStatus(Movie m) {
		try {
			List<Screening> screenings = screeningService.findAllForMovie(m.getId());
			for(Screening s : screenings) {
				if(s.getStatus().equals(Status.AVAILABLE) || s.getStatus().equals(Status.STARTED)) {
					return Status.AVAILABLE;
				}	
			}
			return Status.NOT_AVAILABLE;
		}catch(NoScreeningForMovieException e) {
			return Status.NOT_AVAILABLE;
		}
		
	}

	@Override
	public Movie addImage(Long id, String image) {
		Movie updatedMovie = movieRepository.findById(id).map(movie -> {
			movie.setImage(image);
			return movieRepository.save(movie);
		}).orElseThrow(()-> new MovieNotFoundException(id));
		return updatedMovie;
	}

	@Override
	public Movie addLandscape(Long id, String landscape) {
		Movie updatedMovie = movieRepository.findById(id).map(movie -> {
			movie.setLandscape(landscape);
			return movieRepository.save(movie);
		}).orElseThrow(()-> new MovieNotFoundException(id));
		return updatedMovie;
	}

	@Override
	public List<Movie> findByCategory(List<Long> ids) {
		List<Movie> movies = movieRepository.findAll();
		List<Movie> categoryMovies = new ArrayList<Movie>();
		for(Long id : ids) {
			categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id)); // check if category exists
			if(movies.size()!=0) {
				for(Movie m : movies) {
					m.setStatus(setStatus(m));
					m.setViewers(getNumberViews(m));
					for(Category c : m.getCategories()) {
						if(c.getId() == id && !categoryMovies.contains(m)) {
							categoryMovies.add(m);
						}
					}
				}
			}
		}
		Collections.sort(categoryMovies);
		Collections.reverse(categoryMovies);
		return categoryMovies;
	}

	@Override
	public List<Movie> getComingSoonMovies() {
		List<Movie> movies = movieRepository.findAll();
		List<Movie> comingSoonMovies = new ArrayList<Movie>();
		for(Movie m : movies) {
			if(m.getReleaseDate().after(Date.valueOf(LocalDate.now()))) {
				m.setStatus(setStatus(m));
				m.setViewers(getNumberViews(m));
				comingSoonMovies.add(m);
			}
		}
		Collections.sort(comingSoonMovies);
		Collections.reverse(comingSoonMovies);
		return comingSoonMovies;
	}

	@Override
	public List<Movie> getMostViewedMovies() {
		List<Movie> movies = movieRepository.findAll();
		for(Movie m : movies) {
			m.setStatus(setStatus(m));
			m.setViewers(getNumberViews(m));
		}
		Collections.sort(movies, new SortByViewers()); // descending order
		return movies;
	}

	private int getNumberViews(Movie m) {
		/*int views = 0;
		List<SeatReserved> viewers = seatReservedRepository.findAll();
		for(SeatReserved seat : viewers) {
			if(seat.getScreening().getMovie().getId() == m.getId()) {
				views++;
			}
		}
		return views;*/
		return movieRepository.nbViewers(m.getId());
	}

	@Override
	public List<Movie> search(String search) {
		List<Movie> movies = movieRepository.findAll();
		List<Movie> searchMovies = new ArrayList<Movie>();
		for(Movie m : movies) {
			m.setStatus(setStatus(m));
			m.setViewers(getNumberViews(m));
			if(m.getTitle().toLowerCase().contains(search.toLowerCase()) || m.getDirector().toLowerCase().contains(search.toLowerCase()) || m.getCast().toLowerCase().contains(search.toLowerCase())) {
				searchMovies.add(m);
			}
		}
		return searchMovies;
	}

	@Override
	public List<MoviePerCategory> nbMoviesPerCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<Movie> movies = movieRepository.findAll();
		List<MoviePerCategory> list = new ArrayList<MoviePerCategory>();
		for(Category cat : categories) {
			int value = 0;
			for(Movie m : movies) {
				for(Category cm : m.getCategories()) {
					if(cm.getId() == cat.getId()) {
						value++;
						break;
					}
				}
			}
			list.add(new MoviePerCategory(cat.getName(), value));
		}
		return list;
	}
}
