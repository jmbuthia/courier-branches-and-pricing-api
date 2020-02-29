package ke.co.fargocourier.model.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class StandingWeightChargeDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	
	private double extraPerKgAmount;
	
	private double standingMaxWeightCharged;
	
	private double standingMaxWeightNotCharged;
	
	private String name;
	
	private String description;
	
	private Date dateUpdated;
	

}
