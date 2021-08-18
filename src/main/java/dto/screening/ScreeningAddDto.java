package dto.screening;

public class ScreeningAddDto {

	private Long movie;
	private Long room;
	private Float price;
	private int day;
	private int month;
	private int year;
	private int hour;
	private int min;
	private int sec;
	
	public ScreeningAddDto() {}

	public ScreeningAddDto(Long movie, Long room, Float price, int day, int month, int year, int hour, int min) {
		super();
		this.movie = movie;
		this.room = room;
		this.price = price;
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
		this.min = min;
		this.sec = 0;
	}

	public Long getMovie() {
		return movie;
	}

	public void setMovie(Long movie) {
		this.movie = movie;
	}

	public Long getRoom() {
		return room;
	}

	public void setRoom(Long room) {
		this.room = room;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}
	
}
