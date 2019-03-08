package service;

import java.util.Set;

public interface Service<T> {
	Set<T> getDataFromTheAPI(String firstName, String lastName);
	boolean matchFound(String firstName, String lastName);
}
