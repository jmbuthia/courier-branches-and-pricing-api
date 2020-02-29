package ke.co.fargocourier.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="additional_charges")
@Setter
@Getter
@NoArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"suburb_with_additional_charge_id",
		"suburb_facilitating_additional_charge_id","review_date"})})
public class AdditionalCharge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="suburb_with_additional_charge_id",nullable=false,foreignKey=@ForeignKey(name="FK_SUBURB_WITH_ADDITIONAL_CHARGE_ID"))
	private Suburb suburbWithAdditionalChargeId;
	
	@ManyToOne
	@JoinColumn(name="suburb_facilitating_additional_charge_id",nullable=false,foreignKey=@ForeignKey(name="FK_SUBURB_FACILITATING_ADDITIONAL_CHARGE_ID"))
	private Suburb suburbFacilitatingAdditionalChargeId;
	@Column(nullable=false)
	private double amount;
	@Column(name="review_date",nullable=false)
	private Date reviewDate;
	@Column(name="description")
	private String description;
}
