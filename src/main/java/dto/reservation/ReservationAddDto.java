package dto.reservation;

import java.util.List;

public class ReservationAddDto {

	private Long screening;
	private List<Long> seats_ids;
	
	public ReservationAddDto(Long screening, List<Long> seats_ids) {
		super();
		this.screening = screening;
		this.seats_ids = seats_ids;
	}

	public Long getScreening() {
		return screening;
	}

	public void setScreening(Long screening) {
		this.screening = screening;
	}

	public List<Long> getSeats_ids() {
		return seats_ids;
	}

	public void setSeats_ids(List<Long> seats_ids) {
		this.seats_ids = seats_ids;
	}

}
