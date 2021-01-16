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
@Table(name = "seats")
public class Seat {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int row;
	private int number;	// column
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Room.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", insertable=true)
	private Room room;

	public Seat() {}
	
	public Seat(int row, int number, Room room) {
		this.row = row;
		this.number = number;
		this.room = room;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	
}
