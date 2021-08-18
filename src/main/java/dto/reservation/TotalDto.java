package dto.reservation;

public class TotalDto {
	private int totalNumber;
	private Float totalIncome;
	
	public TotalDto(int totalNumber, Float totalIncome) {
		super();
		this.totalNumber = totalNumber;
		this.totalIncome = totalIncome;
	}
	
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public Float getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(Float totalIncome) {
		this.totalIncome = totalIncome;
	}
	
}
