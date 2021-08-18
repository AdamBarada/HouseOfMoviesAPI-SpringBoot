package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "rooms")
public class Room {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Transient
	private int nbColumns;
	
	@Transient
	private int nbRows;
	
	@Transient
	private List<Seat> seats;

	public Room() {}
	
	public Room(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public int getNbColumns() {
		return nbColumns;
	}

	public void setNbColumns(int nbColumns) {
		this.nbColumns = nbColumns;
	}

	public int getNbRows() {
		return nbRows;
	}

	public void setNbRows(int nbRows) {
		this.nbRows = nbRows;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + "]";
	}
	
	
}
