package dto.reservation;

import java.util.List;

public class ReservationPerDayDto {
	private String name;
	private List<ReservationPerCategoryDto> series;
	
	public ReservationPerDayDto(String name, List<ReservationPerCategoryDto> series) {
		super();
		this.setName(name);
		this.setSeries(series);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ReservationPerCategoryDto> getSeries() {
		return series;
	}

	public void setSeries(List<ReservationPerCategoryDto> series) {
		this.series = series;
	}	
	
	
}
