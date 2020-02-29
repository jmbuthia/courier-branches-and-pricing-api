package ke.co.fargocourier.model.dto;

import java.io.Serializable;
import java.util.Date;
import ke.co.fargocourier.model.Branch;
import ke.co.fargocourier.model.StandingWeightCharge;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SuburbDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	
	private String name;
	
	private String description;
	
	private Date dateCreated;

	private Branch branch;
	
	private StandingWeightCharge standingWeightCharge;

}
