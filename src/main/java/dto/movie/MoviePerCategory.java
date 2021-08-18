package dto.movie;

public class MoviePerCategory {
	private String name;
	private int value;
	
	public MoviePerCategory(String name, int value) {
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
	public void setValue(int nb) {
		this.value = nb;
	}
}
