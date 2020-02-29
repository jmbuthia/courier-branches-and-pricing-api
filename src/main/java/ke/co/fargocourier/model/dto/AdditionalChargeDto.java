package ke.co.fargocourier.model.dto;

import java.io.Serializable;
import java.util.Date;
import ke.co.fargocourier.model.Suburb;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AdditionalChargeDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	
	private Suburb suburbWithAdditionalChargeId;
	
	private Suburb suburbFacilitatingAdditionalChargeId;
	
	private double amount;
	private Date reviewDate;
	private String description;
}
