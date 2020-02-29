package ke.co.fargocourier.model.dto;

import java.io.Serializable;
import java.util.Date;
import ke.co.fargocourier.model.Branch;
import ke.co.fargocourier.model.Suburb;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class BranchHeadSuburbDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	

	private Branch branch;
	
	private Suburb suburb;

	private Date startDate;
	
	private String description;

}
