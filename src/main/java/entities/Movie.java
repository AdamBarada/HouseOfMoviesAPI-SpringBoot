package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String director;
	private String cast;
	private String description;
	private int duration;
	private String image;	// link
	
	public Movie() {}
	
	
	
	public Movie(Long id, String title, String director, String cast, String description, int duration) {
		super();
		this.id = id;
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.description = description;
		this.duration = duration;
	}



	// image for later
	public Movie(String title, String director, String cast, String description, int duration) {
		super();
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.description = description;
		this.duration = duration;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
