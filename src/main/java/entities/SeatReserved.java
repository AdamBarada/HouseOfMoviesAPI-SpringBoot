package entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "seats_reserved")
public class SeatReserved {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Seat.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "seat_id")
	private Seat seat;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Reservation.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "reservation_id")
	private Reservation reservation;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Screening.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "screening_id")
	private Screening screening;

	public SeatReserved() {}
	
	public SeatReserved(Seat seat, Reservation reservation, Screening screening) {
		super();
		this.seat = seat;
		this.reservation = reservation;
		this.screening = screening;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Screening getScreening() {
		return screening;
	}

	public void setScreening(Screening screening) {
		this.screening = screening;
	}
	
	
}
