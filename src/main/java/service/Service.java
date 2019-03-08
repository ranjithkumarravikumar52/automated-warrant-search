package service;

import model.Guest;

public interface Service<T> {
	boolean matchFound(Guest guest);
}
