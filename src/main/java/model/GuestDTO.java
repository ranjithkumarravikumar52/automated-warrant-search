package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {
 *         "DateCreatedFormatted": "9/11/2018",
 *         "DateOfBirthFormatted": "9/11/1980",
 *         "DetailsUrl": "/Warrant/Details/29475?procType=RSTR&lastName=ishan",
 *         "Name": "ISHAN, KARINA  ",
 *         "ProcessType": "RSTR",
 *         "PersonNumber": "1884744",
 *         "PID": null,
 *         "Race": "U",
 *         "Ethnicity": "U",
 *         "Sex": "U",
 *         "Street": " ",
 *         "DateOfBirth": "/Date(337492800000)/",
 *         "DateCreated": "/Date(1536723613353)/",
 *         "ViewExternally": true,
 *         "WarrantId": 29475,
 *         "WarrantDetailsUrl": "http://mecksheriffapi.mecklenburgcountync.gov/api/warrant/GetDetails?warrantId=29475&processType=RSTR"
 *     }
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GuestDTO {
	@JsonProperty
	private String DateCreatedFormatted;
	@JsonProperty
	private String DateOfBirthFormatted;
	@JsonProperty
	private String DetailsUrl;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String ProcessType;
	@JsonProperty
	private String PersonNumber;
	@JsonProperty
	private String PID;
	@JsonProperty
	private String Race;
	@JsonProperty
	private String Ethnicity;
	@JsonProperty
	private String Sex;
	@JsonProperty
	private String Street;
	@JsonProperty
	private int WarrantId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GuestDTO guestDTO = (GuestDTO) o;

		return WarrantId == guestDTO.WarrantId;

	}

	@Override
	public int hashCode() {
		return WarrantId;
	}
}
