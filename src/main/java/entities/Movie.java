package entities;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "movies")
public class Movie implements Comparable<Movie> {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String director;
	
	@Lob
	@Column(columnDefinition="TEXT")
	private String cast;
	
	@Lob
	@Column(columnDefinition="TEXT")
	private String description;
	
	private int duration;
	
	@DateTimeFormat
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name="release_date")
	private Date releaseDate;
	
	private String image;	// link
	private String landscape; // link
	
	private String trailer;		// youtube link
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
			name = "movies_categories",
			joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
			)
	private Collection<Category> categories;

	@Transient
	private Status status;
	
	@Transient
	private int viewers;
	
	public Movie() {}
	
	
	
	public Movie(String title, String director, String cast, String description, int duration, Date releaseDate,
			String image, String landscape, Collection<Category> categories, String trailer) {
		super();
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.description = description;
		this.duration = duration;
		this.releaseDate = releaseDate;
		this.image = image;
		this.landscape = landscape;
		this.categories = categories;
		this.trailer = trailer;
	}

	public Movie(Long id, String title, String director, String cast, String description, int duration, Date releaseDate,
			Collection<Category> categories, String trailer) {
		super();
		this.id = id;
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.description = description;
		this.duration = duration;
		this.releaseDate = releaseDate;
		this.categories = categories;
		this.trailer = trailer;
	}

	public Movie(String title, String director, String cast, String description, int duration, Date releaseDate,
			Collection<Category> categories, String trailer) {
		super();
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.description = description;
		this.duration = duration;
		this.releaseDate = releaseDate;
		this.categories = categories;
		this.trailer = trailer;
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


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", director=" + director + ", cast=" + cast + ", description="
				+ description + ", duration=" + duration + ", releaseDate=" + releaseDate + ", image=" + image
				+ ", landscape=" + landscape + ", category=" + categories.toString() + "]";
	}



	@Override
	public int compareTo(Movie o) {
		return this.getReleaseDate().compareTo(o.getReleaseDate());
	}



	public String getTrailer() {
		return trailer;
	}



	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	

	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}



	public int getViewers() {
		return viewers;
	}



	public void setViewers(int viewers) {
		this.viewers = viewers;
	}
	
}
