package model;

public class Guest {
    private String firstName;
    private String lastName;
    private String middleName;
    private int roomNumber;
    private String dob;

    public Guest(String firstName, String lastName, String middleName, int roomNumber, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.roomNumber = roomNumber;
        this.dob = dob;
    }

    public Guest(String firstName, String lastName, int roomNumber, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roomNumber = roomNumber;
        this.dob = dob;
        this.middleName = null;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return lastName +" "+ firstName +" "+ middleName +" "+ roomNumber +" "+ dob;
    }
}
