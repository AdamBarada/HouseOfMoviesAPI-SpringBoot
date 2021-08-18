package utils;

import java.util.Comparator;

import entities.User;

public class SortByNbReservations implements Comparator<User>{

	@Override
	public int compare(User o1, User o2) {
		return o2.getNbReservations() - o1.getNbReservations();
	}



}
