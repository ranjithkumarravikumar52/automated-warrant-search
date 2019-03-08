package dao;

import java.util.Set;

public interface DAO<T> {
	Set<T> getDataFromTheAPI(String firstName, String lastName);
}
