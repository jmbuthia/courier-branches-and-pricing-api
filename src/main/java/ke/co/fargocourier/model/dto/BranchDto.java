package ke.co.fargocourier.model.dto;

import java.io.Serializable;
import ke.co.fargocourier.model.Route;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BranchDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	
	private String phone;

	private String physicalLocation;

	private String gpsLocation;
	
	private String imageUrl;
	private String description;

	private Route route;

}
