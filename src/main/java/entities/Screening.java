package entities;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "screenings")
public class Screening {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Movie.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "movie_id")
	private Movie movie;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Room.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "room_id")
	private Room room;
	
	@DateTimeFormat
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date date;
	
	@JsonFormat(pattern="HH:MM:SS")
	private Time time;
	
	@Column(name = "price_per_seat")
	private Float price;

	public Screening() {}
	
	public Screening(Movie movie, Room room, Date date, Time time, Float price) {
		super();
		this.movie = movie;
		this.room = room;
		this.date = date;
		this.time = time;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}
	
	
}
