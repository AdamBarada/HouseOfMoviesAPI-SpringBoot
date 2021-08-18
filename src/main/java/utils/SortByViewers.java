package utils;

import java.util.Comparator;

import entities.Movie;

public class SortByViewers implements Comparator<Movie> {

	@Override
	public int compare(Movie o1, Movie o2) {
		return o2.getViewers() - o1.getViewers();	// descending order
	}

}
