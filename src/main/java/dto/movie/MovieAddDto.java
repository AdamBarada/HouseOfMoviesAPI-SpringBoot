
package dto.movie;

import java.util.List;

public class MovieAddDto {
	
	private String title;
	private String director;
	private String cast;
	private String description;
	private int duration;
	private List<Long> categories;
	private String releaseDate;
	private String trailer;
	private String image;
	private String landscape;
	
	public MovieAddDto() {}
	
	public MovieAddDto(String title, String director, String cast, String description, int duration, 
			List<Long> categories, String releaseDate, String trailer, String image, String landscape) {
		super();
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.description = description;
		this.duration = duration;
		this.categories = categories;
		this.releaseDate = releaseDate;
		this.trailer = trailer;
		this.image = image;
		this.landscape = landscape;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public List<Long> getCategories() {
		return categories;
	}

	public void setCategories_id(List<Long> categories) {
		this.categories = categories;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}

	
}
