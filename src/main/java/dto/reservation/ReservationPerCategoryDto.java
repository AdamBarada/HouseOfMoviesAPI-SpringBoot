package dto.reservation;

public class ReservationPerCategoryDto {

	private String name;		// name of category
	private int value;		// number of seat reserved
	
	public ReservationPerCategoryDto(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	

}
