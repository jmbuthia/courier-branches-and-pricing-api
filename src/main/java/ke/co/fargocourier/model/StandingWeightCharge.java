package ke.co.fargocourier.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="standing_weight_charges")
@Setter
@Getter
@NoArgsConstructor
public class StandingWeightCharge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "extra_per_kg_amount",nullable=false)
	private double extraPerKgAmount;
	@Column(name = "standing_max_weight_charged")
	private double standingMaxWeightCharged=0.00;
	@Column(name = "standing_max_weight_not_charged")
	private double standingMaxWeightNotCharged=0.00;
	@Column(name="description")
	private String description;
	@Column(unique=true,nullable=false)
	private String name;
	@Column(name = "date_updated",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dateUpdated;
	

}
