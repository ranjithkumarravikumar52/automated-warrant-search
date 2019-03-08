package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guest {
    private String firstName;
    private String lastName;
    private String middleName;
    private int roomNumber;
    private String dob;
}
