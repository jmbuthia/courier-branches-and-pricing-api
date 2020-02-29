package ke.co.fargocourier.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class Charge implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isVolumetricWeightUsed;
	private boolean isDeadWeightUsed;
	private double totalCost;
	private double volumetricWeight;
	private double deadWeight;
	private double collectionCharge;
	private double additionalCharge;
	private double tariffCharge;
	private double extraKgCharge;
}
