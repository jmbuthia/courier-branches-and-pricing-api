package ke.co.fargocourier.model.dto;
import ke.co.fargocourier.model.Branch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CollectionCentreDto {
	
	private long id;
	
	private String name;
	
	private String phone;
	
	private String physicalLocation;
	
	private String gpsLocation;
	
	private String imageUrl;
	private String description;
	
	
	private Branch branch;

}
