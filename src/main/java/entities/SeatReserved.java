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

@Entity
@Table(name = "seats_reserved")
public class SeatReserved {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Seat.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id")
	private Seat seat;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Reservation.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
	private Reservation reservation;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Screening.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_id")
	private Screening screening;

	public SeatReserved() {}
	
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
