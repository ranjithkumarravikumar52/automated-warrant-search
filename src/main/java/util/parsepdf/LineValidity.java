package util.parsepdf;

public interface LineValidity {
	boolean isLineValid(String line);

	boolean isValidPersonName(String token);

	boolean isValidRoomNumber(String token);

	boolean isValidDate(String token);
}
