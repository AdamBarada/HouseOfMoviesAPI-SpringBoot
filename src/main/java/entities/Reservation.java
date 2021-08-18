package entities;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "reservations")
public class Reservation implements Comparable<Reservation>{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=User.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Screening.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "screening_id")
	private Screening screening;
	
	private Date date;
	private Time time;
	private Float total;
	
	@Transient
	private List<SeatReserved> seats_reserved;
	
	@Transient
	private Status status;
	
	public Reservation() {}
	
	public Reservation(User user, Screening screening, Date date, Time time, Float total) {
		super();
		this.user = user;
		this.screening = screening;
		this.date = date;
		this.time = time;
		this.total = total;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Screening getScreening() {
		return screening;
	}
	public void setScreening(Screening screening) {
		this.screening = screening;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
	}

	public List<SeatReserved> getSeats_reserved() {
		return seats_reserved;
	}

	public void setSeats_reserved(List<SeatReserved> seats_reserved) {
		this.seats_reserved = seats_reserved;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int compareTo(Reservation o) {
		Timestamp ts1 = Timestamp.valueOf(LocalDateTime.of(getDate().toLocalDate(), getTime().toLocalTime()));
		Timestamp ts2 = Timestamp.valueOf(LocalDateTime.of(o.getDate().toLocalDate(), o.getTime().toLocalTime()));
		return ts1.compareTo(ts2);
	}
	
	
}
