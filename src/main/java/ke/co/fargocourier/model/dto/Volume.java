package ke.co.fargocourier.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Volume implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double lengh;
	private double width;
	private double height;
	public double getVolumetricWeight() {
		return (lengh*width*height)/6000;
	}

}
