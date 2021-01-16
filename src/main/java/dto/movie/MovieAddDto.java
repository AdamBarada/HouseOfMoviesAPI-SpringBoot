package dto.movie;

public class MovieAddDto {
	
	private String title;
	private String director;
	private String cast;
	private String description;
	private int duration;
	//  private String image;	// image?? file??
	
	public MovieAddDto() {}
	
	public MovieAddDto(String title, String director, String cast, String description, int duration) {
		super();
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.description = description;
		this.duration = duration;
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
	
	
}
