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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "seats")
public class Seat {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int row;
	private int number;	// column
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Room.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "room_id")
	private Room room;
	
	@Transient
	private boolean isTaken = false;
	
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

	@JsonIgnore
	public Room getRoom() {
		return room;
	}

	@JsonIgnore
	public void setRoom(Room room) {
		this.room = room;
	}

	public boolean isTaken() {
		return isTaken;
	}

	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", row=" + row + ", number=" + number + ", room=" + room + "]";
	}

	
	
}
