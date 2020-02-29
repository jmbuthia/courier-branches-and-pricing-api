package ke.co.fargocourier.model.dto;

import java.io.Serializable;
import java.util.Optional;

import ke.co.fargocourier.model.Branch;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Setter
@Getter
@NoArgsConstructor
public class CostingBranchJson implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Optional<Volume> volume;
	private Optional<Double> weight;
	private Branch fromBranch;
	private Branch toBranch;

}
